package com.aige.cuco.toolproject.jnicallback;

import android.util.Log;

public class Animal {
    private static final String TAG = Animal.class.getSimpleName();


    protected String name;

    public Animal(String name) {
        this.name = name;
        Log.i(TAG, "Animal Construct call....");
    }

    public String getName() {
        Log.i(TAG, "Animal Construct call....");
        return this.name;
    }

    public void run() {
        Log.i(TAG, "Animal.run");
    }
}
