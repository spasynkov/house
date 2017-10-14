package synchronization.locks.entities;

import lombok.Data;

/**
 * Квартира в доме
 */
@Data                   // сгенерировать геттеры/сеттеры, конструктор, equals() и hashCode()
public class Flat {
    private int number;         // номер квартиры
    private int floor;          // этаж квартиры
    private byte roomsNumber;   // количество комнат

    /**
     * Иммитация геттера для определения стоимости квартиры
     *
     * @return стоимость квартиры
     */
    public double getPrice() {
        int basicPrice = 100;       // базовая цена

        // стоимость квартиры = базовая цена + базовая цена умноженная на количество комнат в квартире
        return basicPrice + basicPrice * roomsNumber;
    }
}
