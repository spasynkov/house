package synchronization.locks.entities;

import synchronization.locks.utils.HouseParams;

import java.util.*;
import java.util.stream.Collectors;

public class House implements Runnable {
    private final Map<Flat, Family> registry = new HashMap<>();     // реестр квартир и семей, что в них живут

    private Thread thisThread;      // тред, который выполняет метод run()

    /**
     * Конструктор дома
     *
     * @param floors ориентировочное количество этажей в доме
     * @param params обьект с параметрами квартир в доме
     */
    public House(int floors, HouseParams params) {
        generateFlats(floors, params.getParamsMap());       // создаем необходимое количество квартир в реестре
    }

    /**
     * Создает квартиры
     *
     * @param floors       желаемое количество этажей в доме
     * @param roomsVsFlats карта, в которой указано сколько квартир с каким количеством комнат создавать в доме
     */
    private void generateFlats(int floors, Map<Integer, Integer> roomsVsFlats) {
        // высчитываем сколько квартир на этаже. ищем сумму всех квартир и делим на количество этажей
        int flatsPerFloor = roomsVsFlats.values().stream().mapToInt(Integer::intValue).sum() / floors;

        int flatsCounter = 1;       // счетчик квартир. используется в качестве номера квартиры при ее создании
        int roomsInFlat;            // количество комнат в квартире
        int flatsQuantity;          // количество квартир

        // проходимся по карте
        for (Map.Entry<Integer, Integer> entry : roomsVsFlats.entrySet()) {
            roomsInFlat = entry.getKey();               // получаем из карты сколько комнат в квартире этого типа
            flatsQuantity = entry.getValue();           // и сколько таких квартир делать

            // создаем нужное количество квартир такого типа
            for (int i = 0; i < flatsQuantity; i++) {
                Flat flat = new Flat();                         // создали пустую квартиру
                flat.setNumber(flatsCounter);                   // установили номер квартире
                flat.setFloor(1 + flatsCounter / flatsPerFloor);// и этаж
                flat.setRoomsNumber((byte) roomsInFlat);        // и количество комнат
                flatsCounter++;                 // увеличиваем счетчик квартир чтоб след квартира была с бОльшим номером

                // добавляем в реестр эту квартиру и null в качестве проживающей в ней семье
                registry.put(flat, null);
            }
        }
    }

    /**
     * Позволяет получить пустые квартиры в этом доме
     * @return список свободных квартир
     */
    public List<Flat> getEmptyFlats() {
        List<Flat> result = new ArrayList<>();
        synchronized (registry) {
            result.addAll(registry.entrySet().stream()          // получаем множество записей реестра в качестве потока
                    .filter(entry -> entry.getValue() == null)  // собираем только те записи, где нет жильцов
                    .map(Map.Entry::getKey)                     // собираем множество квартир (ключей) из этих записей
                    .collect(Collectors.toList()));             // возвращаем в виде списка
        }
        result.sort(Comparator.comparingInt(Flat::getNumber));  // и сортируем по номеру квартиры по возрастанию
        return result;
    }

    /**
     * Позволяет заселить семью в квартиру
     * @param flat      квартира куда заселять
     * @param family    семья, которую заселять
     */
    public void putFamily(Flat flat, Family family) {
        // кажется тут чего-то не хватает...
        registry.replace(flat, family);         // добавляем информацию о семье в реестр
        family.setFlat(flat);                   // устанавливаем семье квартиру, где им жить
    }

    /**
     * автоматическое выселение мертвых семей из квартир
     * хотя, эта задача больше подходит для брокеров, а не для самого дома
     * */
    @Override
    public void run() {
        thisThread = Thread.currentThread();        // запоминаем тред, который крутит этот метод

        while (!thisThread.isInterrupted()) {
            synchronized (registry) {
                // вычеркиваем вымершие семьи из реестра
                for (Map.Entry<Flat, Family> entry : registry.entrySet()) {
                    Family family = entry.getValue();
                    if (family != null) {
                        if (family.getMembers().size() == 0) {
                            registry.replace(entry.getKey(), null);
                            family.setFlat(null);
                        }
                    }
                }
            }

            // спим
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                thisThread.interrupt();
            }
        }
    }

    /**
     * Останавливает выполнение треда, который крутит метод run() этого обьекта
     */
    public void stop() {
        if (thisThread != null) {   // если такой тред существует (появляется только после запуска run())
            thisThread.interrupt();
        }
    }
}
