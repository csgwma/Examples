package me.gwma.java.basic;

public class StaticKeyInitilization {

    public static int val = 3;
    static {
        System.out.println("-----" + val);
        val++;
    }

    public static int getVal() {
        val++;
        System.out.println("****" + val);
        return val;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        getVal();
    }

}
