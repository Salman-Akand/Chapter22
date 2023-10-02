import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedQueueDemo {
    private static final int MAX_SIZE = 10;
    private static final int NUM_ITERATIONS = 100;
    private static Queue<String> queue = new LinkedList<>();

    public static synchronized void add(String item) {
        while (queue.size() >= MAX_SIZE) {
            try {
                SynchronizedQueueDemo.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(item);
        System.out.println("Producer added: " + item);
        SynchronizedQueueDemo.class.notifyAll();
    }

    public static synchronized String remove() {
        while (queue.isEmpty()) {
            try {
                SynchronizedQueueDemo.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String item = queue.remove();
        System.out.println("Consumer removed: " + item);
        SynchronizedQueueDemo.class.notifyAll();
        return item;
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                String item = new java.util.Date().toString();
                add(item);
            }
        });

        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                String item = remove();
            }
        });

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
