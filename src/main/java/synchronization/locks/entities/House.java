package synchronization.locks.entities;

import synchronization.locks.utils.HouseParams;

import java.util.*;

public class House implements Runnable {
    private final Map<Flat, Family> registry = new HashMap<>();

    private Thread thisThread;

    public House(int floors, HouseParams params) {
        generateFlats(floors, params.getParamsMap());
    }

    private void generateFlats(int floors, Map<Integer, Integer> roomsVsFlats) {
        int flatsPerFloor = roomsVsFlats.values().stream().mapToInt(Integer::intValue).sum() / floors;
        int flatsCounter = 1;
        int roomsInFlat;
        int flatsQuantity;
        for (Map.Entry<Integer, Integer> entry : roomsVsFlats.entrySet()) {
            roomsInFlat = entry.getKey();
            flatsQuantity = entry.getValue();
            for (int i = 0; i < flatsQuantity; i++) {
                Flat flat = new Flat();
                flat.setNumber(flatsCounter);
                flat.setFloor(1 + flatsCounter / flatsPerFloor);
                flatsCounter++;
                flat.setRoomsNumber((byte) roomsInFlat);
                registry.put(flat, null);
            }
        }
    }

    public List<Flat> getEmptyFlats() {
        List<Flat> result = new ArrayList<>();
        synchronized (registry) {
            for (Map.Entry<Flat, Family> entry : registry.entrySet()) {
                if (entry.getValue() == null) result.add(entry.getKey());
            }
        }
        result.sort(Comparator.comparingInt(Flat::getNumber));
        return result;
    }

    public void putFamily(Flat flat, Family family) {
        // кажется тут чего-то не хватает...
            registry.replace(flat, family);
            family.setFlat(flat);
    }

    /**
     * автоматическое выселение мертвых семей из квартир
     * хотя, эта задача больше подходит для брокеров, а не для самого дома
     * */
    @Override
    public void run() {
        thisThread = Thread.currentThread();
        while (!thisThread.isInterrupted()) {
            synchronized (registry) {
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

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                thisThread.interrupt();
            }
        }
    }

    public void stop() {
        if (thisThread != null) {
            thisThread.interrupt();
        }
    }
}
