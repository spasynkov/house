package synchronization.locks.entities;

import lombok.Data;

@Data
public class Flat {
    private int number;
    private int floor;
    private byte roomsNumber;

    public double getPrice() {
        int basicPrice = 100;
        return basicPrice + basicPrice * roomsNumber;
    }
}
