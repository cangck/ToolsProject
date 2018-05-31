package com.aige.cuco.toolproject.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.ry.ranyeslive.R;
import com.ry.ranyeslive.activity.LiveRoomActivity;
import com.ry.ranyeslive.constants.Constant;
import com.ry.ranyeslive.dialog.RoomPassWordDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wusourece on 2017/9/6.
 *   进入直播间判断是否需要密码的工具类
 */

public class JoinJudgmentIsPsdUtil {

    private static RoomPassWordDialog dialog;

    public static void joinRoom(final Context mContext, final String roomName, final String userId){

        //没有网络的情况下不让用户跳转到直播间 提示网络异常 因为有的房间需要密码 有的房间禁止访问 没网的话没法判断
        // 如果userId为空字符串的话就不传 当前是游客
        if (!Utils.isNetworkConnected(mContext)){
            ToastUtil.showToast(mContext,R.string.net_exception);
            return;
        }

        final Intent intent = new Intent(mContext, LiveRoomActivity.class);
        intent.putExtra("roomname", roomName);
        if (!userId.equals("")) {
            intent.putExtra("roomuid", userId);
        }

        //  需要密码的房间 L001 表示要L002表示密码错误 L003 代表禁止访问该房间
        //跳转之前先拿到房间号去请求一下直播间接口 通过errcode判断当前房间是否需要密码

        Map<String, String> joinParams = new HashMap<>();
        joinParams.put("name", roomName);
        if (!userId.equals("")) {
            joinParams.put("uid", userId);
        }
        OkHttpUtils.post(Constant.LIVEROOM_INFO_URL, joinParams, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //errcode 为空说明该房间不需要密码 直接进行跳转
                    String errCode = jsonObject.getString("errCode");
                    if (!errCode.equals("")) {
                        if (errCode.equals("L001")) {
                            ToastUtil.showToast(mContext, "该房间不存在");
                        } else if (errCode.equals("L002")) {
                            dialog = new RoomPassWordDialog(mContext);
                            dialog.setCancelable(false);
                            dialog.show();

                            dialog.btnConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String roomPsd = dialog.etTextPsd.getText().toString().trim();
                                    if (TextUtils.isEmpty(roomPsd) || roomPsd.equals("")) {
                                        ToastUtil.showToast(mContext, R.string.please_input_psd);
                                    } else {
                                        //点击确定后拿到密码等参数判断房间密码是否正确
                                        requestLiveRoomPsdYesOrNo(roomName, roomPsd, intent);
                                    }
                                }
                            });

                            dialog.btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }else if (errCode.equals("L003")){
                            ToastUtil.showToast(mContext, "您被禁止加入该房间");
                        }
                    }else if (errCode.equals("")) {  //如果错误码为空的话 说明请求成功直接跳转
                        //如果不需要密码的话 将参数进行传递   成功了再存储这个json 备用
                        mContext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
            ToastUtil.showToast(mContext,R.string.net_exception);
            }

            /**
             * 输入密码后再请求一次网络 看密码是否正确 不正确作出对应的提示
             *
             * @param name
             * @param psd
             * @param intent
             */
            public void requestLiveRoomPsdYesOrNo(String name, final String psd, final Intent intent) {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                if (!userId.equals("")) {
                    params.put("uid", userId);
                }
                params.put("password", psd);
                OkHttpUtils.post(Constant.LIVEROOM_INFO_URL, params, new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("errCode")) {
                                String errCode = jsonObject.getString("errCode");
                                if (errCode.equals("")) {
                                    intent.putExtra("password", psd);
                                    mContext.startActivity(intent);

                                    dialog.dismiss();
                                } else if (errCode.equals("L002")) {
                                    ToastUtil.showToast(mContext, "房间密码错误");
                                } else if (errCode.equals("L003")) {
                                    ToastUtil.showToast(mContext, "禁止访问该房间");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        ToastUtil.showToast(mContext,R.string.net_exception);
                    }
                });
            }
        });
    }
}
