import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final int CAPACITY = 1;
    public static LinkedList<Integer> queue = new LinkedList<Integer>();
    public static Lock lock = new ReentrantLock();
    public static Condition notEmpty = lock.newCondition();
    public static Condition notFull = lock.newCondition();

    public static void main(String[] args) {
        System.out.println("Please insert the number of threads:");
        Scanner in = new Scanner(System.in);
        int nrThreads = in.nextInt();
        in.close();

        Producer[] producers = new Producer[nrThreads];
        Consumer[] consumers = new Consumer[nrThreads];

        for (int i = 0; i < nrThreads; i++) {
            producers[i] = new Producer(i);
            consumers[i] = new Consumer(i);
        }

        for (int i = 0; i < nrThreads; i++) {
            producers[i].run();
            consumers[i].run();
        }

        for (int i = 0; i < nrThreads; i++) {
            try {
                producers[i].join();
            } catch (InterruptedException ie) {
                ie.getMessage();
            }
            try {
                consumers[i].join();
            } catch (InterruptedException ie) {
                ie.getMessage();
            }
        }

        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.get(i) + " ");
        }
    }
}