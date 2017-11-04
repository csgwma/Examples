package me.gwma.java.basic.concurrency.producerconsumer;

public class Producer implements Runnable {

    private ServerAlarmQueue serverAlarmQueue = null;

    public Producer(ServerAlarmQueue serverAlarmQueue){
        super();
        this.serverAlarmQueue = serverAlarmQueue;
    }

    @Override
    public void run() {
        int val = 0;
        for (int i = 0; i < 20; ++i) {
            val = (int) (Math.random() * 100);
            produce(val);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " produce done!");
    }

    public void produce(Object obj) {
        serverAlarmQueue.produce(obj);
    }

}
