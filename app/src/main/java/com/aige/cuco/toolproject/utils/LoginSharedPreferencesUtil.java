package com.aige.cuco.toolproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ry.ranyeslive.bean.HomePageBean;
import com.ry.ranyeslive.constants.ConstantLoginKey;

/**
 * Created by wusourece on 2017/6/30.
 *    存储登录和用户相关信息的sp
 */

public class LoginSharedPreferencesUtil {

    private static Context mContext;

    /**
     * 传递上下文对象，便于SharedPreferences的初始化操作
     *
     * @param context
     */
    public static void initContext(Context context) {
        mContext = context;
    }

    /**
     * 获取config file name
     *
     * @return
     */
    private static String getConfigFileName() {
        return "login_prefs";
    }

    /**
     * 获取preferences 对象
     *
     * @return
     */
    public static SharedPreferences getSharedPrefs() {
        return mContext.getSharedPreferences(getConfigFileName(), Context.MODE_PRIVATE);
    }

    /**
     * 保存登录信息
     */
    public static final void saveLoginState(String key,boolean value){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    /**
     * 获取登录状态
     */
    public static final boolean getLoginState(String key){
        return getSharedPrefs().getBoolean(key,false);
    }

    /**
     * 保存用户的id
     */
    public static final void saveLoginID(String key,String value){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putString(key,value);
        editor.commit();
    }

    /**
     * 获取用户的id
     */
    public static final String getLoginID(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 存储首页对象的json
     * @param key
     * @param homeJson
     */
    public static final void saveHomePageObject(String key, String homeJson){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putString(key,homeJson);
        editor.commit();
    }

    /**
     * 获取首页对象
     * @param key
     * @return
     */
    public static final String getHomePageObject(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存直播间信息
     * @param key
     * @param liveroominfobean
     */
    public static final void saveLiveRoomInfoBean(String key,String liveroominfobean){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,liveroominfobean);
        edit.commit();
    }

    /**
     * 获取直播间信息
     * @param key
     * @return
     */
    public static final String getLiveRoomInfoBean(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存用户的身份类型
     * @param key
     * @param value
     */
    public static final void saveUserRoleType(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取用户的身份类型
     * @param key
     * @return
     */
    public static final String getUserRoleType(String key){
      return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存房间id
     * @param key
     * @param value
     */
    public static final void saveRoomId(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取房间id
     * @param key
     * @return
     */
    public static final String getRoomId(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存当前房间的身份
     * @param key
     * @param value
     */
    public static final void saveRoomRoleType(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取当前房间的身份
     * @param key
     * @return
     */
    public static final String getRoomRoleType(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存公会的id
     * @param key
     * @param value
     */
    public static final void saveTheId(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取公会的id
     * @param key
     * @return
     */
    public static final String getTheId(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存用户的nickname
     * @param key
     * @param value
     */
    public static final void saveUserLoginName(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取用户的nickname
     * @param key
     * @return
     */
    public static final String getUserLoginName(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存用户的头像地址
     * @param key
     * @param value
     */
    public static final void saveUserHeadIcon(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取用户的头像地址
     * @param key
     * @return
     */
    public static final String getUserHeadIcon(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存用户的修改后的头像
     * @param key
     * @param value
     */
    public static final void saveNewUserHeadIcon(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取用户修改后的头像
     * @param key
     * @return
     */
    public static final String getNewUserHeadIcon(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存打赏的人数
     * @param key
     * @param value
     */
    public static final void saveExceptionalNumber(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取打赏的人数
     * @param key
     * @return
     */
    public static final String getExceptionalNumber(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存外网的ip
     * @param key
     * @param value
     */
    public static final void saveNetWorkIp(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取外网的ip
     * @param key
     */
    public static final String getNetWorkIp(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存直播的状态
     * @param key
     * @param value
     */
    public static final void saveLiveState(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    public static final String getLiveState(String key){
        return getSharedPrefs().getString(key,"");
    }


    /**
     * 保存开课的开始时间 用来和结束时间做比较
     * @param key
     * @param value
     */
    public static final void saveCourseStartTime(String key,long value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putLong(key,value);
        edit.commit();
    }

    public static final Long getCourseStartTime(String key){
        return getSharedPrefs().getLong(key,1);
    }

    /**
     * 保存用户的name
     * @param key
     * @param value
     */
    public static final void saveUserName(String key,String value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putString(key,value);
        edit.commit();
    }

    /**
     * 获取用户的name
     * @param key
     * @return
     */
    public static final String getUserName(String key){
        return getSharedPrefs().getString(key,"");
    }

    /**
     * 保存播放语音的状态
     * @param key
     * @param value
     */
    public static final void saveVoicePlayState(String key,boolean value){
        SharedPreferences.Editor edit = getSharedPrefs().edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static final boolean getVoicePlayState(String key){
        return getSharedPrefs().getBoolean(key,false);
    }
}
