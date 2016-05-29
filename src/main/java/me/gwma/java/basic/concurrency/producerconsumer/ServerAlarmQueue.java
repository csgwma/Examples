package me.gwma.java.basic.concurrency.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

public class ServerAlarmQueue {

    private static int                        MAX_SIZE   = 1000;
    private static final Logger               logger     = Logger.getLogger(ServerAlarmQueue.class.getSimpleName());
    private static ArrayBlockingQueue<Object> alramQueue = null;

    public ServerAlarmQueue(int capacity){
        super();
        MAX_SIZE = capacity;
        alramQueue = new ArrayBlockingQueue<Object>(capacity);
    }

    public ServerAlarmQueue(){
        super();
        alramQueue = new ArrayBlockingQueue<Object>(MAX_SIZE);
    }

    /**
     * 从队列终获取一个对象
     * 
     * @return
     */
    public synchronized Object consume() {
        Object object = null;
        while (alramQueue.isEmpty()) {
            try {
                wait();
                logger.warning("队列已空，消费等待...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        object = alramQueue.poll();
        System.out.println(Thread.currentThread().getName() + " 消费产品：" + object.toString());
        this.notifyAll();
        return object;
    }

    /**
     * 向对另种插入一个对象
     * 
     * @param object
     * @return
     */
    public synchronized boolean produce(Object object) {
        boolean flag = false;
        while (alramQueue.size() == MAX_SIZE) {
            try {
                wait();
                logger.warning(Thread.currentThread().getName() + " 队列已满，生产等待....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flag = alramQueue.offer(object);
        System.out.println(Thread.currentThread().getName() + " 生产出产品：" + object.toString());
        this.notifyAll();
        return flag;
    }

    /**
     * 从队列终获取一个对象，这里用到的阻塞队列的put,take 方法； 若条件不满足会自动阻塞，但最后需要notifyAll();不然会出现线程饿死 这种方法虽然简单，但不如上面的方法记录日志方便
     * 
     * @return
     */
    public synchronized Object consumeSimple() {
        Object obj = null;
        try {
            obj = alramQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "消费产品：" + obj.toString());
        this.notifyAll();
        return obj;
    }

    public synchronized void produceSimple(Object obj) {
        try {
            alramQueue.put(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "生产出产品：" + obj.toString());
        this.notifyAll(); // 这个不要忘了，不然消费者会饿死的
    }

    public void clear() {
        alramQueue.clear();
    }

}
