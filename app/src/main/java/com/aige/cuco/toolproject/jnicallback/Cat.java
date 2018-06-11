package com.aige.cuco.toolproject.jnicallback;

import android.util.Log;

public class Cat extends Animal {
    private static final String TAG = Cat.class.getSimpleName();

    public Cat(String name) {
        super(name);
        Log.i(TAG, "Cat constructer");
    }

    public String getName() {
        return "My name is " + this.name;
    }

    @Override
    public void run() {
        Log.i(TAG, name + "Cat.run....");
    }
}
