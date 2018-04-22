import java.util.LinkedList;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
        MyQueue myQueue = new MyQueue();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myQueue.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myQueue.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
