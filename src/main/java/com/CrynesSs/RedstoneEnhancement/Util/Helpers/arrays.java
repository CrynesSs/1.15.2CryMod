package com.CrynesSs.RedstoneEnhancement.Util.Helpers;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class arrays<T> {
    public static int min(Integer[] arr) {
        if (arr.length == 0) {
            throw new ValueException("Array without Length provided");
        }
        int minvalue = arr[0];
        for (int i : arr) {
            if (i < minvalue) {
                minvalue = i;
            }
        }
        return minvalue;
    }

    public static int max(Integer[] arr) {
        if (arr.length == 0) {
            throw new ValueException("Array without Length provided");
        }
        int maxvalue = arr[0];
        for (int i : arr) {
            if (i > maxvalue) {
                maxvalue = i;
            }
        }
        return maxvalue;
    }

    public static boolean contains(Integer[] arr, Integer i) {
        for (int k : arr) {
            if (k == i) {
                return true;
            }
        }
        return false;
    }


}
