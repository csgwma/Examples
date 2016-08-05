package me.gwma.common.utils.alg;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PermunationUtils {

    /**
     * 随机生成0~n-1的不重复的k个随机数
     * <p>
     * 空间复杂度O(n)，时间复杂度O(k)
     * 
     * @param n
     * @param k
     * @return
     */
    public static int[] randPermutation(int n, int k) {
        int[] permutation = new int[n];
        for (int i = 0; i < permutation.length; ++i) {
            permutation[i] = i;
        }
        for (int i = 0; i < k; i++) {
            // 随机生成一个i~n-1的随机数
            int nextIx = (int) (Math.random() * (n - i)) + i;
            if (nextIx != i) {
                int tmp = permutation[i];
                permutation[i] = permutation[nextIx];
                permutation[nextIx] = tmp;
            }
        }
        int[] result = new int[k];
        for (int i = 0; i < k; ++i) {
            result[i] = permutation[i];
        }
        return result;
    }

    /**
     * 随机生成0~n-1的一个排列组合
     * 
     * @param n
     * @return
     */
    public static int[] randPermutation(int n) {
        int[] permutation = new int[n];
        for (int i = 0; i < permutation.length; ++i) {
            permutation[i] = i;
        }
        for (int i = 0; i < n; i++) {
            // 随机生成一个i~n-1的随机数
            int nextIx = (int) (Math.random() * (n - i)) + i;
            if (nextIx != i) {
                int tmp = permutation[i];
                permutation[i] = permutation[nextIx];
                permutation[nextIx] = tmp;
            }
        }
        return permutation;
    }
    
    public static int[][] genearteAllPermutations(int n) {
        int[][] permunations = new int[n*(n-1)][n];
        int[] permutation = new int[n];
        for (int i = 0; i < permutation.length; ++i) {
            permutation[i] = i;
        }
        for (int i = 0; i < n; i++) {
            // 随机生成一个i~n-1的随机数
            int nextIx = (int) (Math.random() * (n - i)) + i;
            if (nextIx != i) {
                int tmp = permutation[i];
                permutation[i] = permutation[nextIx];
                permutation[nextIx] = tmp;
            }
        }
        return permunations;
    }
    
      
    public static void listAll(List<String> candidate, String prefix){  
        System.out.println(prefix);  
        for(int i=0; i<candidate.size(); i++){  
            List<String> temp = new LinkedList<String>(candidate);//new LinkedList<String>(candidate)---copy candidate  
            listAll(temp, prefix+temp.remove(i));  
        }  
    }  
    
    public static void main(String[] args){
        String[] array = new String[]{  
                "1", "2", "3", "4"  
        };  
        listAll(Arrays.asList(array),"--");  
        System.out.println(Arrays.toString(randPermutation(4)));
    }
}
