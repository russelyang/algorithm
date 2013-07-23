package com.algorithm;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        int[] test1 = {2,4,5,7,1,8,3,6,11,11,12};

        System.out.println(Arrays.toString(test1));
        mergeSort(test1,0, test1.length - 1);

        System.out.println(Arrays.toString(test1));
    }

    public static void merge(int[] arr, int low, int middle, int high) {

        int lenLeft = middle - low + 1;
        int lenRight = high - middle;

        int[] left = new int[lenLeft ];
        int[] right = new int[lenRight];

        for(int i= 0 ; i< lenLeft; i ++) {
            left[i] = arr[low + i];
        }

        for(int j = 0; j< lenRight; j++) {
            right[j] =  arr[middle + 1 + j];
        }

        int i=0, j=0, k= low;
        while(i < lenLeft && j < lenRight) {
           if(left[i] <= right[j]) {
               arr[k++] = left[i++];
           } else {

               arr[k++] = right[j++];
           }
        }
        while(i < lenLeft) {
            arr[k++] = left[i++];
        }

        while(j < lenRight) {
            arr[k++] = right[j++];
        }

    }

    public static void  mergeSort(int[] arr, int low, int high) {
        if(low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(arr, low, middle);
            mergeSort(arr, middle+1, high);
            merge(arr, low, middle, high);
        }
    }
}
