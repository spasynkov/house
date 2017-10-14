package synchronization.locks;

import synchronization.locks.entities.Broker;
import synchronization.locks.entities.House;
import synchronization.locks.entities.LatencySetter;
import synchronization.locks.utils.HouseParams;
import synchronization.locks.utils.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Logger.getInstance(System.out);     // инициализируем логгер для вывода информации
        LatencySetter.set(100);             // устанавливаем задержку

        // создаем объект параметров для дома:
        // количество комнат в квартире, количество таких квартир в доме
        HouseParams params = new HouseParams();
        params.add(1, 90);
        params.add(2, 70);
        params.add(3, 50);
        params.add(5, 10);
        // создаем сам объект 24х-этажного дома
        House house = new House(24, params);

        // создаем трех брокеров, которые будут селить семьи в квартиры
        new Broker(house);
        new Broker(house);
        new Broker(house);

        Thread.sleep(10000);
        house.stop();               // останавливаем автоматическое освобождение квартир
    }
}
