package me.gwma.common.utils.alg;

import java.util.ArrayList;

import org.junit.Test;


/**
 * 类Permunation.java的实现描述：TODO 类实现描述
 * 
 * @author Ace Jan 15, 2016 4:12:33 PM
 */
public class PermunationTest {

    @Test
    public void testPermute1() {
        // int[] num = { 1, 2, 4, 5, 3, 6, 7, 8, 9 };
        int[] num = { 1, 2, 3 };
        ArrayList<ArrayList<Integer>> per = permute1(num);
        System.out.println(per);
    }

    /**
     * Loop through the array, in each iteration, a new number is added to different locations of results of previous
     * iteration.
     * <p>
     * Start from an empty List.
     * 
     * @param num
     * @return
     */
    public  ArrayList<ArrayList<Integer>> permute1(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        // start from an empty list
        result.add(new ArrayList<Integer>());
        for (int i = 0; i < num.length; i++) {
            // list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size() + 1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);
                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);
                    // System.out.println(temp);
                    // - remove num[i] add
                    l.remove(j);
                }
            }
            result = new ArrayList<ArrayList<Integer>>(current);
        }
        return result;
    }

    @Test
    public void testPermute() {
        // int[] num = { 1, 2, 4, 5, 3, 6, 7, 8};
        int[] num = { 1, 2, 3, 4 };
        ArrayList<ArrayList<Integer>> per = permute(num);
        // System.out.println(per);
    }

    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        permute(num, 0, result);
        return result;
    }

    void permute(int[] num, int start, ArrayList<ArrayList<Integer>> result) {
        if (start == num.length) {
            ArrayList<Integer> item = convertArrayToList(num);
            System.out.println("add items = " + item);
            result.add(item);
        }
        for (int j = start; j <= num.length - 1; j++) {
            swap(num, start, j);
            permute(num, start + 1, result);
            swap(num, start, j);
        }
    }

    private ArrayList<Integer> convertArrayToList(int[] num) {
        ArrayList<Integer> item = new ArrayList<Integer>();
        for (int h = 0; h < num.length; h++) {
            item.add(num[h]);
        }
        return item;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
}
