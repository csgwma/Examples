package me.gwma.java.desingpattern;

/**
 * the Singleton instance is created at class-load time.
 */
public class ThreadSafeSingleton2 {

    private static final Object instance = new Object();

    protected ThreadSafeSingleton2(){
    }

    // Runtime initialization
    // By defualt ThreadSafe
    public static Object getInstance() {
        return instance;
    }

}
