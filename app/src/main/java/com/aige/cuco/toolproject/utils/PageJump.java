package com.aige.cuco.toolproject.utils;

import android.app.Activity;
import android.content.Intent;

import com.ry.ranyeslive.activity.ApplyForEnterActivity;
import com.ry.ranyeslive.activity.HistoryRecordActivity;
import com.ry.ranyeslive.activity.LiveActivity;
import com.ry.ranyeslive.activity.MyAttentionActivity;
import com.ry.ranyeslive.activity.MyEarningsActivity;
import com.ry.ranyeslive.activity.MyMessageActivity;
import com.ry.ranyeslive.activity.SettingsActivity;
import com.ry.ranyeslive.activity.VideoEnterActivity;
import com.ry.ranyeslive.activity.VoiceEnterActivity;

/**
 * Created by wusourece on 2017/6/9.
 * activity跳转的工具类 便于日后的管理和查看
 */

public class PageJump {

    /**
     * 我的--申请入驻页面
     */
    public static void jumpToApplyForEnterActivity(Activity activity){
        Intent intent = new Intent(activity, ApplyForEnterActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 视频入驻的页面
     */
    public static void jumpToLecturerEnterActivity(Activity activity){
        Intent intent = new Intent(activity, VideoEnterActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 语音入驻的页面
     */
    public static void jumpToTheGuildEnterActivity(Activity activity){
        Intent intent = new Intent(activity, VoiceEnterActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 发起直播
     */
    public static void jumpToSendLiveActivity(Activity activity){
        Intent intent = new Intent(activity, LiveActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的 -- 我的关注页面
     * @param activity
     */
    public static void jumpToMyAttentionActivity(Activity activity){
        Intent intent = new Intent(activity, MyAttentionActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的 -- 历史记录
     * @param activity
     */
    public static void jumpToHistoryRecordActivity(Activity activity){
        Intent intent = new Intent(activity, HistoryRecordActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的 -- 我的收益
     * @param activity
     */
    public static void jumpToMyEarningsActivity(Activity activity){
        Intent intent = new Intent(activity, MyEarningsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的 -- 我的消息
     * @param activity
     */
    public static void jumpToMyMessageActivity(Activity activity){
        Intent intent = new Intent(activity, MyMessageActivity.class);
        activity.startActivity(intent);
    }

    public static void jumpToSettingsActivity(Activity activity){
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }
}
