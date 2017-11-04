package me.gwma.common.utils.alg;

/**
 * 用到二分查找思想的算法
 */
public class BinarySearch {

    public static int bsearchWithoutRecursion(int[] array, int target) {
        int low = 0;
        int high = array.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] > target) high = mid - 1;
            else if (array[mid] < target) low = mid + 1;
            else // find the target
                return mid;
        }
        // the array does not contain the target
        return -1;
    }

    public static void main(String[] args) {
    }
}
