package synchronization.locks.entities;

public final class LatencySetter {
    public static void set(long latency) {
        Person.setLATENCY(latency);
        Family.setLATENCY(latency);
    }

    public static void set(long personLatency, long familyLatency) {
        Person.setLATENCY(personLatency);
        Family.setLATENCY(familyLatency);
    }
}
