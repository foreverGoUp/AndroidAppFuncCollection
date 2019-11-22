package com.kc.appfunccollection;

import android.util.Log;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int[] a = new int[]{1, 8, 3, 2, 5, 7, 1, 4, 3};
        quickSort(a, 0, 8);
        System.out.println(Arrays.toString(a));
        assertEquals(4, 2 + 2);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    /**
     *   * 快速排序实现
     *   * @param array
     *   * @param low
     *   * @param high
     *  
     */
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivot = partition(array, low, high);
            System.out.println(Arrays.toString(array));
            System.out.println("pivot="+pivot);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }

    /**
      * @param array 待排序数组
      * @param low 数组下标下界
      * @param high 数组下标上界
      * @return pivot
      */
    public static int partition(int[] array, int low, int high) {
        System.out.println("partition,low="+low+",high="+high);
        //当前位置为第一个元素所在位置
        int p_pos = low;
        //采用第一个元素为轴
        int pivot = array[p_pos];
        for (int i = low + 1; i <= high; i++) {
            if (array[i] < pivot) {
                p_pos++;
                swap(array, p_pos, i);
            }
        }
        swap(array, low, p_pos);
        return p_pos;
    }

    /**
     * 交换指定数组a的两个变量的值
     *
     * @param a 数组应用
     * @param i 数组下标
     * @param j 数组下标
     */
    public static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
///////////////////////////////////////////////////////////////////////////
//
///////////////////////////////////////////////////////////////////////////

    /**
     * 冒泡排序 从小到大
     * 每次冒泡出相对最大的数到相对最后面
     */
    public static void bubbleSort(int[] data) {
        if (data == null) throw new IllegalArgumentException("data can't be null");
        if (data.length < 2) return;
        //外层循环data.length-1次
        for (int i = 0; i < data.length - 1; i++) {
            //内层循环每次选择一个最大的数冒泡到最后 循环次数每次都会少1次直到外层循环完毕data.length-i-1
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    System.out.println("交换位置：" + data[j] + ":" + data[j + 1]);
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
            System.out.println(Arrays.toString(data));
        }
		/*for(int i:data)
			System.out.print(i+" ");*/
        System.out.println(Arrays.toString(data));
    }

    /**
     * 选择排序 从小到大
     * 每次选出一个最小的排前面
     */
    public static void selectSort(int[] data) {
        if (data == null) throw new IllegalArgumentException("data can't be null");
        if (data.length < 2) {
            return;
        }
        //循环次数data.length-1
        for (int i = 0; i < data.length - 1; i++) {
            int index = i;
            //每次选择第i个数依次和后面的数进行比较，谁小谁变成第i个数；循环次数也是data.length-i-1
            for (int j = i; j < data.length; j++) {
                if (data[index] > data[j]) {
                    index = j;
                }
            }
            if (i != index) {
                int temp = data[index];
                data[index] = data[i];
                data[i] = temp;
            }
        }
        /*for(int i:data)
        	System.out.print(i+" ");*/
        System.out.println(Arrays.toString(data));
    }

}