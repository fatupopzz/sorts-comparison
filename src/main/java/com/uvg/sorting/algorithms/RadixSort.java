// La parte de Jose 
package com.uvg.sorting.algorithms;
import java.util.Arrays;


public class RadixSort {
    public void sort(int[] arr) {
        if (arr.length == 0) return;
        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    private void countingSortByDigit(int[] arr, int exp) {
        int[] output = new int[arr.length];
        int[] count = new int[10];
        
        for (int num : arr) {
            count[(num / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
}