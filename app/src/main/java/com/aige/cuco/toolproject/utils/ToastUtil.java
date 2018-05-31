package com.aige.cuco.toolproject.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 显示内容的Toast
 * Created by ZJHL on 2016/11/2.
 */

public class ToastUtil {
    /**
     * 显示内容
     */
    public static void showToast(Context context, String msg){
        try {
            if (msg == null){
                return;
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msgId
     */
    public static void showToast(final Context context, int msgId) {
        try {
            Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
        }
    }

    /**
     * 显示长事件的toast
     * @param context
     * @param msg
     */
    public static void showLongToast( Context context,int msg){
        try {

            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }catch (Exception e){
        }
    }

    /**
     *
     * @param context
     * @param msg
     */
    public static void showLongToast(Context context,String msg){
        try {
            if (msg == null){
                return;
            }
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }catch (Exception e){

        }
    }

}
