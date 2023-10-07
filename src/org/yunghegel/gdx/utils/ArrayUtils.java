package org.yunghegel.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ArrayUtils {

    /**
     * Adds all elements of the given iterable to the given array.
     */
    public static <T> void addAll(Array<T> array, Iterable<T> elements)
    {
        for(T element : elements) array.add(element);
    }

    /**
     * Returns a new array containing the elements of the given iterable.
     */
    public static <T> Array<T> array(Iterable<T> elements)
    {
        Array<T> array = new Array<T>();
        addAll(array, elements);
        return array;
    }

    /**
     * Returns a new array containing the elements of the given array.
     */
    public static <T> Array<T> array(T[] elements)
    {
        Array<T> array = new Array<T>();
        for(T element : elements) array.add(element);
        return array;
    }

    /**
     * Returns a random element from the array.
     */
    public static <T> T any(Array<T> array) {
        if(array.size > 0){
            return array.get((int)(array.size * MathUtils.random()));
        }
        return null;
    }

    public static void arraySwap(Object[] arr, int i1, int i2) {
        Object tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    public static void reverseArray(Object[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            Object tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
