/* *****************************************************************************
 * Search in a bitonic array. An array is bitonic if it is comprised
 * of an increasing sequence of integers followed immediately
 * by a decreasing sequence of integers. Write a program that,
 * given a bitonic array of nn distinct integer values, determines
 * whether a given integer is in the array.
 * Standard version: Use ∼3lgn compares in the worst case.
 * Signing bonus: Use ∼2lgn compares in the worst case (and prove that
 * no algorithm can guarantee to perform fewer than ∼2lgn compares in the worst case).
 ***************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

public class BitonicArray {
    public static int searchMaxIndex(long[] a, int lo, int hi) {
        int maxIndex = -1;
        if (hi - lo > 1) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > a[mid + 1] && a[mid] > a[mid - 1]) {
                maxIndex = mid;
            }
            else if (a[mid] < a[mid + 1]) {//asc
                maxIndex = searchMaxIndex(a, mid, hi);
            }
            else if (a[mid] > a[mid + 1]) {//desc
                maxIndex = searchMaxIndex(a, lo, mid);
            }

        }
        if (maxIndex == -1) {
            throw new java.lang.IllegalArgumentException("Array is not bitonic");
        }
        return maxIndex;
    }

    public static int ascBynarySearch(long[] a, long key, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1; //key in the left part
            else if (key > a[mid]) lo = mid + 1; //key in the right part
            else return mid;
        }
        return -1; //key is not in a
    }

    public static int descBynarySearch(long[] a, long key, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key > a[mid]) hi = mid - 1; //key in the left part
            else if (key < a[mid]) lo = mid + 1; //key in the right part
            else return mid;
        }
        return -1; //key is not in a
    }

    public static int searchIndexOfKey(long[] a, long key) {
        int n = a.length - 1;
        int maxIndex = searchMaxIndex(a, 0, n);
        if (key == a[maxIndex]) return maxIndex;
        else {
            int indexOfKey = descBynarySearch(a, key, maxIndex + 1, n);
            if (indexOfKey != -1) return indexOfKey;
            else return ascBynarySearch(a, key, 0, maxIndex - 1);
        }
    }

    public static void countFromFile(String filename) {
        In in = new In(filename);
        long key = in.readLong();
        StdOut.println("key = " + key);
        long[] a = in.readAllLongs();
        StdOut.println("array = " + Arrays.toString(a));
        Stopwatch timer = new Stopwatch();
        int indexOfKey = searchIndexOfKey(a, key);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println("Index of key is " + indexOfKey);
    }

    public static void main(String[] args) {
        String filename = args[0];
        countFromFile(filename);
    }
}
