package com.aige.cuco.toolproject.jnicallback;

import android.util.Log;

import java.lang.annotation.Target;

public class ClassMethod {
    private static final String TAG = ClassMethod.class.getSimpleName();

    public static void callStaticMethod(String str, int i) {
        Log.i(TAG, "ClassMethod::callstaticMethod!---> str =" + str + " i= " + i);
    }
    public void callInstanceMethod(String str,int i){
        Log.i(TAG, "ClassMethod::callInstanceMethod!---> str =" + str + " i= " + i);
    }
}
