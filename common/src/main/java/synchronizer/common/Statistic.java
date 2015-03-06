package synchronizer.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * GKislin
 * 06.03.2015.
 */
public class Statistic {
    private static final Statistic INSTANCE = new Statistic();

    private ConcurrentMap<String, Integer> records = new ConcurrentHashMap<>();

    private Statistic() {
    }

    public static Statistic get() {
        return INSTANCE;
    }

    public void addRecord(String chunkName, int time) {
        records.put(chunkName, time);
    }

    public void clear() {
        records.clear();
    }

    public String toString() {
        return records.toString();
    }
}
