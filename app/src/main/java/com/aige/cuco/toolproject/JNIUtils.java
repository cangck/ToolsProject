package com.aige.cuco.toolproject;

public class JNIUtils {
    private static JNIUtils mJNIUtils = new JNIUtils();


    public static JNIUtils instances() {
        return mJNIUtils;
    }

    public native String getSystemVersionName();

    public native int getSystemVersion();

    public native String encryption(String userinfo);

    public native int sumArray(int[] arr);
    public native int[][] initInt2DArray(int size);
}
