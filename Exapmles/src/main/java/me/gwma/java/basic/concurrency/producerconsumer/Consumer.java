package me.gwma.java.basic.concurrency.producerconsumer;

public class Consumer implements Runnable {

    private ServerAlarmQueue serverAlarmQueue = null;
    private boolean          isStart          = true;

    public Consumer(ServerAlarmQueue serverAlarmQueue){
        super();
        this.serverAlarmQueue = serverAlarmQueue;
    }

    @Override
    public void run() {
        while (isStart) {
            getProducts();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void getProducts() {
        serverAlarmQueue.consume();
        // System.out.println(obj);
    }

}
