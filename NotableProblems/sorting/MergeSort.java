package sorting;

import java.util.Arrays;

public class MergeSort {
    
    public static void mergeSort(int[] array, int leftIncl, int rightExcl) {
        // the base case: if subarray contains <= 1 items, stop dividing because it's sorted
        if (rightExcl <= leftIncl + 1) {
            return;
        }
     
        /* divide: calculate the index of the middle element */
        int middle = leftIncl + (rightExcl - leftIncl) / 2;
     
        mergeSort(array, leftIncl, middle);  // conquer: sort the left subarray
        mergeSort(array, middle, rightExcl); // conquer: sort the right subarray
     
        /* combine: merge both sorted subarrays into sorted one */
        merge(array, leftIncl, middle, rightExcl);
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int i = left;   // index for the left subarray
        int j = middle; // index for the right subarray
        int k = 0;      // index for the temp subarray
            
        int[] temp = new int[right - left]; // temporary array for merging
     
        /* get the next lesser element from one of two subarrays 
           and then insert it in the array until one of the subarrays is empty */
        while (i < middle && j < right) {
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i++;
            } else {
                temp[k] = array[j];
                j++;
            }
            k++;
        }
     
        /* insert all the remaining elements of the left subarray in the array */
        for (;i < middle; i++, k++) {
            temp[k] = array[i];
        }
     
        /* insert all the remaining elements of the right subarray in the array */
        for (;j < right; j++, k++) {
            temp[k] = array[j];
        }
     
        /* effective copying elements from temp to array */
        System.arraycopy(temp, 0, array, left, temp.length);
    }

    public static void main(String[] args) {
        int[] array1 = { 30, 21, 23, 19, 28, 11, 23 };
 
        mergeSort(array1, 0, array1.length); // { 11, 19, 21, 23, 23, 28, 30 }
        System.out.println(Arrays.toString(array1));
 
        int[] array2 = { 30, 20, 10, 10, 20, 10 };
        mergeSort(array2, 0, array2.length); // { 10, 10, 10, 20, 20, 30 }
        System.out.println(Arrays.toString(array2));
    }

}