package synchronization.locks.entities;

/**
 * Устанавливает задержку для некоторых классов
 */
public final class LatencySetter {
    /**
     * Устанавливает задержку для всех классов
     *
     * @param latency количество милисекунд, сколько треды будут спать
     */
    public static void set(long latency) {
        Person.setLATENCY(latency);
        Family.setLATENCY(latency);
    }

    /**
     * Устанавливает разные задержки для классов
     *
     * @param personLatency задержка для объектов класса Person, количество милисекунд сколько треды будут спать
     * @param familyLatency задержка для объектов класса Family, количество милисекунд сколько треды будут спать
     */
    public static void set(long personLatency, long familyLatency) {
        Person.setLATENCY(personLatency);
        Family.setLATENCY(familyLatency);
    }
}
