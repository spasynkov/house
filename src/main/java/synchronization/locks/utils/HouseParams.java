package synchronization.locks.utils;

import java.util.HashMap;
import java.util.Map;

public class HouseParams {
    private final Map<Integer, Integer> params = new HashMap<>();

    public void add(int roomsInFlat, int flatsQuantity) {
        if (roomsInFlat <= 0 || flatsQuantity <= 0)
            throw new RuntimeException("Passed arguments should be positive numbers! " +
                    "Your input: (" + roomsInFlat + ", " + flatsQuantity + ")");

        params.put(roomsInFlat, flatsQuantity);
    }

    public Map<Integer, Integer> getParamsMap() {
        return params;
    }
}
