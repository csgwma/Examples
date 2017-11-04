package me.gwma.common.utils.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Utilities for random sampling.
 */
public class RandomUtils {

    /**
     * Random sampling.<br>
     * 
     * @param population
     * @param nSamplesNeeded
     * @param r
     * @return
     */
    public static <T> List<T> pickSample(List<T> population, int nSamplesNeeded, Random r) {
        if (population == null) {
            throw new IllegalArgumentException("null population array!");
        }
        if (nSamplesNeeded <= 0) {
            throw new IllegalArgumentException("invalid nSampledsNeeded, must > 0!");
        }
        if (r == null) {
            throw new IllegalArgumentException("null random engine!");
        }
        if (nSamplesNeeded > population.size()) {
            return population;
        }

        List<T> ret = new ArrayList<T>();
        int i = 0, nLeft = population.size();
        while (nSamplesNeeded > 0) {
            int rand = r.nextInt(nLeft);
            if (rand < nSamplesNeeded) {
                ret.add(population.get(i));
                nSamplesNeeded--;
            }
            nLeft--;
            i++;
        }
        return ret;
    }

    /**
     * Random sampling.<br>
     * 
     * @param population
     * @param nSamplesNeeded
     * @param r
     * @return
     */
    public static <T> List<T> pickSample(T[] population, int nSamplesNeeded, Random r) {
        if (population == null) {
            throw new IllegalArgumentException("null population array!");
        }
        if (nSamplesNeeded <= 0) {
            throw new IllegalArgumentException("invalid nSampledsNeeded, must > 0!");
        }
        if (r == null) {
            throw new IllegalArgumentException("null random engine!");
        }
        if (nSamplesNeeded > population.length) {
            return Arrays.asList(population);
        }

        List<T> ret = new ArrayList<T>();
        int i = 0, nLeft = population.length;
        while (nSamplesNeeded > 0) {
            int rand = r.nextInt(nLeft);
            if (rand < nSamplesNeeded) {
                ret.add(population[i]);
                nSamplesNeeded--;
            }
            nLeft--;
            i++;
        }
        return ret;
    }

    /**
     * 随机生成0~n-1的不重复的k个随机数
     * <p>
     * 空间复杂度O(n)，时间复杂度O(k)
     * 
     * @param n
     * @param k
     * @return
     */
    public int[] randPermutation(int n, int k) {
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

    public static void main(String[] args) {
    }
}
