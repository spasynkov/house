package synchronization.locks.entities;

import java.util.Arrays;
import java.util.List;

public class Broker implements Runnable {
    private final List<House> houses;

    public Broker(House... houses) {
        this.houses = Arrays.asList(houses);
        new Thread(this).start();
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
