package com.aige.cuco.toolproject.utils;//package com.ry.ranyeslive.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//
//import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.sdk.modelmsg.WXImageObject;
//import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.sdk.modelmsg.WXTextObject;
//import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import java.io.ByteArrayOutputStream;
//
//public class WeChatShareUtil {
//
//    //????????appId
//    public static final String APP_ID = "wx1af7dc9b5cf66270";
//    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
//
//    //IWXAPI????app??????openapi??
//    private static IWXAPI api;
//    private Context context;
//    public static WeChatShareUtil weChatShareUtil;
//
//    public static WeChatShareUtil getInstance(Context context) {
//        if (weChatShareUtil == null) {
//            weChatShareUtil = new WeChatShareUtil();
//        }
//        if (weChatShareUtil.api != null) {
//            weChatShareUtil.api.unregisterApp();
//        }
//        weChatShareUtil.context = context;
//        weChatShareUtil.regToWx();
//        return weChatShareUtil;
//    }
//
//    //????id???
//    private void regToWx() {
//        //??WXAPIFactory?????IWXAPI???
//        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
//        //????appId?????
//        api.registerApp(APP_ID);
//    }
//
//    /**
//     * ????????????
//     *
//     * @param text  ????
//     * @param scene ????????????
//     */
//    public boolean shareText(String text, int scene) {
//        //?????WXTextObject????????????
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = text;
//        return share(textObj, text, scene);
//    }
//
//    /**
//     * ????????????
//     *
//     * @param bmp   ???Bitmap??
//     * @param scene ????????????
//     */
//    public boolean sharePic(Bitmap bmp, int scene) {
//        //?????WXImageObject??
//        WXImageObject imageObj = new WXImageObject(bmp);
//        //?????
//        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 60, 60, true);
//        bmp.recycle();
//        return share(imageObj, thumb, scene);
//    }
//
//    /**
//     * ???????????????????????????????????????
//     * ?????????
//     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
//     *
//     * @param url         ???url
//     * @param title       ?????????
//     * @param description ??????
//     * @param scene       ????????????
//     */
//    public static boolean shareUrl(String url, String title, Bitmap thumb, String description, int scene) {
//        //?????WXWebpageObject?????url
//        WXWebpageObject webPage = new WXWebpageObject();
//        webPage.webpageUrl = url;
//        return share(webPage, title, thumb, description, scene);
//    }
//
//    private boolean share(WXMediaMessage.IMediaObject mediaObject, Bitmap thumb, int scene) {
//        return share(mediaObject, null, thumb, null, scene);
//    }
//
//    private boolean share(WXMediaMessage.IMediaObject mediaObject, String description, int scene) {
//        return share(mediaObject, null, null, description, scene);
//    }
//
//    private static boolean share(WXMediaMessage.IMediaObject mediaObject, String title, Bitmap thumb, String description, int scene) {
//        //?????WXMediaMessage??????????
//        WXMediaMessage msg = new WXMediaMessage(mediaObject);
//        if (title != null) {
//            msg.title = title;
//        }
//        if (description != null) {
//            msg.description = description;
//        }
//        if (thumb != null) {
//            msg.thumbData = bmpToByteArray(thumb, true);
//        }
//        //????Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = msg;
//        req.scene = scene;
//        return api.sendReq(req);
//    }
//
//    //????????????
//    //??4.2?????????????????API???? ???IWXAPI?getWXAppSupportAPI??,0x21020001??????????
//    public boolean isSupportWX() {
//        int wxSdkVersion = api.getWXAppSupportAPI();
//        return wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;
//    }
//
//    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
//        if (needRecycle) {
//            bmp.recycle();
//        }
//        byte[] result = output.toByteArray();
//        try {
//            output.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//}
//
