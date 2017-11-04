package me.gwma.java.desingpattern;

/**
 * Simple Singleton Pattern: (Lazy Initialization + ThreadSafe with synchronized block)
 */
public class ThreadSafeSingleton {

    private static ThreadSafeSingleton instance = null;

    protected ThreadSafeSingleton(){
    }

    // Lazy Initialization (If required then only)
    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}
