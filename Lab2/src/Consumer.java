class Consumer implements Runnable
{
    Q q;
    Consumer(Q q){
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    public void run()
    {
        for(int i=0; i < 5; i++)
            // consumer get items
            q.get();
    }
}
