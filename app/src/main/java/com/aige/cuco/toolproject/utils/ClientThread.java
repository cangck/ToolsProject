package com.aige.cuco.toolproject.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wusourece on 2017/6/28.
 *  实现runnable接口 开启子线程  建立socket连接
 */

public class ClientThread implements Runnable {

    private Handler mHandler;
    private Socket mSocket;

    private BufferedReader mBufferedReader = null;
    private OutputStream mOutputStream = null;

    public Handler revHandler;

    public ClientThread(Handler handler){
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            mSocket = new Socket("http://112.74.33.74",9092);
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mOutputStream = mSocket.getOutputStream();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String content = null;
                        while ((content = mBufferedReader.readLine()) != null) {
                            Log.d("xjj",content);
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = content;
                            mHandler.sendMessage(msg);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();

            //由于子线程中没有默认初始化Looper，要在子线程中创建Handler，需要自己写
            Looper.prepare();
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        try {
                            mOutputStream.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
