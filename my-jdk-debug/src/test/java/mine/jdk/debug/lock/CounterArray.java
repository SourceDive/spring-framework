package mine.jdk.debug.lock;

public class CounterArray {
    private final int[] counters = new int[10];
    
    public void incrementAll() {
        for (int i = 0; i < counters.length; i++) {
            counters[i]++;
        }
    }
    
    public int getTotal() {
        int sum = 0;
        for (int i = 0; i < counters.length; i++) {
            sum += counters[i];
        }
        return sum;
    }
}