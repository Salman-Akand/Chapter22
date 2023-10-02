import java.util.LinkedList;

public class LinkedListCorruptionDemo {
    private static LinkedList<Integer> linkedList = new LinkedList<>();

    public static void main(String[] args) {
        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                linkedList.add(i);
            }
        });

        Thread removeThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                if (!linkedList.isEmpty()) {
                    linkedList.removeFirst();
                }
            }
        });

        addThread.start();
        removeThread.start();

        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final size of the linked list: " + linkedList.size());
    }
}
