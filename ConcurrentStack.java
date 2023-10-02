import java.util.LinkedList;

public class ConcurrentStack<T> {
    private LinkedList<T> stack = new LinkedList<>();

    // Push an element onto the stack
    public synchronized void push(T item) {
        stack.push(item);
    }

    // Pop and return the top element from the stack
    public synchronized T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.pop();
    }

    // Check if the stack is empty
    public synchronized boolean isEmpty() {
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();

        // Push elements onto the stack
        stack.push(1);
        stack.push(2);
        stack.push(3);

        // Create multiple threads to pop elements concurrently
        Thread popThread1 = new Thread(() -> {
            while (!stack.isEmpty()) {
                System.out.println("Thread 1 popped: " + stack.pop());
            }
        });

        Thread popThread2 = new Thread(() -> {
            while (!stack.isEmpty()) {
                System.out.println("Thread 2 popped: " + stack.pop());
            }
        });

        popThread1.start();
        popThread2.start();

        try {
            popThread1.join();
            popThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
