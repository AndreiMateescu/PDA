public class Consumer extends Thread {
    private int consumerId;

    public Consumer(int consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        try {
            System.out.println("Consumer " + consumerId + " tries to consume " + consume());
            Thread.sleep(300);
        } catch (InterruptedException ie) {
            ie.getMessage();
        }
    }

    @SuppressWarnings("finally")
    private int consume() {
        int value = 0;
        Main.lock.lock();
        try {
            while (Main.queue.isEmpty()) {
                System.out.println("Queue is empty. Waiting for notEmpty condition " + consumerId);
                Main.notEmpty.await();
            }
            value = Main.queue.remove();
            Main.notFull.signal();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        } finally {
            Main.lock.unlock();
            return value;
        }
    }
}