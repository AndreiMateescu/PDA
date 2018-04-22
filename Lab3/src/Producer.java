public class Producer extends Thread {

    private int producerId;

    public Producer(int producerId) {
        this.producerId = producerId;
    }

    @Override
    public void run() {
        try {
            System.out.println("Producer " + producerId + " tries to produce " + producerId);
            produce();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    private void produce() {
        Main.lock.lock();
        try {
            while (Main.queue.size() == Main.CAPACITY) {
                System.out.println("Queue is full. Wait for notFull condition " + producerId);
                Main.notFull.await();
            }
            Main.queue.offer(producerId);
            Main.notEmpty.signal();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            Main.lock.unlock();
        }
    }
}