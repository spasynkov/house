package synchronization.locks.entities;

import java.util.Arrays;
import java.util.List;

/**
 * Броккер, который берет пустые квартиры в домах и создает для них семьи
 */
public class Broker implements Runnable {
    private final List<House> houses;       // список домов, с которыми работает брокер

    public Broker(House... houses) {
        // трансформируем массив с параметрами (дома, с которыми должен работать брокер) в список
        this.houses = Arrays.asList(houses);
        new Thread(this).start();       // запускаем брокера в работу
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        // выбирает случайный дом из списка
        // выбирает случайную свободную квартиру из дома
        // создает новую семью
        // селит эту семью в квартиру
        // ...
        // и так раз 100
        // ну или пока не закончатся свободные квартиры в доме
    }
}
