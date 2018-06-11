package com.aige.cuco.toolproject.jnicallback;

public class AccessMethod {

    public static native void callJavaStaticMethod();

    public static native void callJavaInstaceMethod();

    public static native void accessInstanceField(ClassMethod obj);

    public native static void accessStaticField();
    public native static void callSupperInstanceMethod();

    public native static void testJNIException(String str);
}
