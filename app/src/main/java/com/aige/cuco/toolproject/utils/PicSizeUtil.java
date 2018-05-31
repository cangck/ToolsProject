package com.aige.cuco.toolproject.utils;

import android.text.TextUtils;

/**
 * 处理客户端显示图片时候根据屏幕宽度返回自适应屏幕的图片大小地址
 * Created by pengfeili on 15/7/15.
 */
public class PicSizeUtil {
    private static int sCutSizeFor1 = -1;
    private static String sXSizeFor1 = null;
    private static String sXSizeFor2 = null;
    private static String sXSizeFor3 = null;
    private static String sXSizeFor4 = null;
    private static String sXSizeFor5 = null;
    private static String sXSizeFor6 = null;

    /**
     * 按照屏幕宽度展示一张整图的大小，获取适当的图片截取路径
     *
     * @param url
     * @return
     */
    public static String getCutUrlFor1(String url) {
//        int li = url.lastIndexOf(".");
//        String end = url.substring(li);
//        String start = url.substring(0, li);
//        return start + getXSizeFor1() + end;
        return url + getXSizeFor1();
    }

    /**
     * 按照屏幕宽度展示两张图的大小，获取适当的图片截取路径
     *
     * @param url
     * @return
     */
    public static String getCutUrlFor2(String url) {
//        int li = url.lastIndexOf(".");
//        String end = url.substring(li);
//        String start = url.substring(0, li);
//        return start + getXSizeFor2() + end;
        return url + getXSizeFor2();
    }

    /**
     * 按照屏幕宽度展示三张图的大小，获取适当的图片截取路径
     *
     * @param url
     * @return
     */
    public static String getCutUrlFor3(String url) {
//        int li = url.lastIndexOf(".");
//        String end = url.substring(li);
//        String start = url.substring(0, li);
//        return start + getXSizeFor3() + end;
        return url + getXSizeFor3();
    }

    /**
     * 按照屏幕宽度展示四张图的大小，获取适当的图片截取路径
     *
     * @param url
     * @return
     */
    public static String getCutUrlFor4(String url) {
//        int li = url.lastIndexOf(".");
//        String end = url.substring(li);
//        String start = url.substring(0, li);
//        return start + getXSizeFor4() + end;
        return url + getXSizeFor4();
    }

    public static int getCutSizeFor1() {
        if (sCutSizeFor1 == -1) {
            int screenWidth = ScreenUtil.getScreenW();
            //根据屏幕设置分辨率
            if (screenWidth < 640) {
                sCutSizeFor1 = 320;
            } else if (screenWidth < 750) {
                sCutSizeFor1 = 640;
            } else if (screenWidth < 1080) {
                sCutSizeFor1 = 750;
            } else if (screenWidth < 1280) {
                //没返回1080 因为1080在网络不好的情况下很容易加载失败，需要对volley做优化
                sCutSizeFor1 = 750;
            } else {
                sCutSizeFor1 = 750;
            }
        }
        return sCutSizeFor1;
    }

    private static String getXSizeFor1() {
        if (sXSizeFor1 == null) {
            int screenWidth = ScreenUtil.getScreenW();
            //根据屏幕设置分辨率
            if (screenWidth < 640) {
                sXSizeFor1 = "-320w";
            } else if (screenWidth < 750) {
                sXSizeFor1 = "-640w";
            } else if (screenWidth < 1080) {
                sXSizeFor1 = "-750w";
            } else if (screenWidth < 1280) {
                //没返回1080 因为1080在网络不好的情况下很容易加载失败，需要对volley做优化
                sXSizeFor1 = "-750w";
            } else {
                sXSizeFor1 = "-750w";
            }
        }
        return sXSizeFor1;
    }

    private static String getXSizeFor2() {
        if (sXSizeFor2 == null) {
            int picWidth = ScreenUtil.getScreenW() / 2;
            //根据屏幕设置分辨率
            if (picWidth < 640) {
                sXSizeFor2 = "-320w";
            } else if (picWidth < 750) {
                sXSizeFor2 = "-640w";
            } else if (picWidth < 1080) {
                sXSizeFor2 = "-750w";
            } else if (picWidth < 1280) {
                //没返回1080 因为1080在网络不好的情况下很容易加载失败，需要对volley做优化
                sXSizeFor2 = "-750w";
            } else {
                sXSizeFor2 = "-750w";
            }
        }
        return sXSizeFor2;
    }

    private static String getXSizeFor3() {
        if (sXSizeFor3 == null) {
            int picWidth = ScreenUtil.getScreenW() / 3;
            //根据屏幕设置分辨率
            if (picWidth < 640) {
                sXSizeFor3 = "-320w";
            } else {
                sXSizeFor3 = "-640w";
            }
        }
        return sXSizeFor3;
    }

    private static String getXSizeFor4() {
        if (sXSizeFor4 == null) {
            int picWidth = ScreenUtil.getScreenW() / 4;
            if (picWidth < 320) {
                sXSizeFor4 = "-160w";
            } else if (picWidth < 640) {
                sXSizeFor4 = "-320w";
            } else {
                sXSizeFor4 = "-640w";
            }
        }
        return sXSizeFor4;
    }

    private static String getXSizeFor5() {
        if (sXSizeFor5 == null) {
            sXSizeFor5 = "-160w";
        }
        return sXSizeFor5;
    }

    private static String getXSizeFor6() {
        if (sXSizeFor6 == null) {
            sXSizeFor6 = "-320w";
        }
        return sXSizeFor6;

    }


    public static String getAvatarUrl(String url) {
        try {
            if (TextUtils.isEmpty(url)) {
                return url;
            }
            // || !FeelUrlUtil.isFeelImageUrl(url)
//            int li = url.lastIndexOf(".");
//            String end = url.substring(li);
//            String start = url.substring(0, li);
//            return start + getXSizeFor5() + end;
            return url + getXSizeFor5();
        }catch (Throwable e){
            return url;
        }
    }

}
