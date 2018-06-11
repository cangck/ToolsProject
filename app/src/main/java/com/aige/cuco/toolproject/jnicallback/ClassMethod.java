package com.aige.cuco.toolproject.jnicallback;

import android.util.Log;

import java.lang.annotation.Target;

public class ClassMethod {
    public static int num;
    public String str;

//    public ClassMethod(String str) {
//        this.str = str;
//    }

    public  int getNum() {
        return num;
    }

    public  void setNum(int num) {
        ClassMethod.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    private static final String TAG = ClassMethod.class.getSimpleName();

    public static void callStaticMethod(String str, int i) {
//        Log.i(TAG, "ClassMethod::callstaticMethod!---> str =" + str + " i= " + i);
    }

    public void callInstanceMethod(String str, int i) {
//        Log.i(TAG, "ClassMethod::callInstanceMethod!---> str =" + str + " i= " + i);
    }
}
