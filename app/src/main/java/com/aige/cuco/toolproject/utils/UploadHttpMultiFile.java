//package com.aige.cuco.toolproject.utils;
//
//import java.io.File;
//import java.net.FileNameMap;
//import java.net.URLConnection;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//
///**
// * Created by wusourece on 2017/7/5.
// * 文件上传
// */
//
//public class UploadHttpMultiFile {
//
//    private static OkHttpClient client;
//
//    private synchronized static OkHttpClient getOkHttpClientInstance() {
//        if (client == null) {
//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    //设置连接超时等属性,不设置可能会报异常
//                    .connectTimeout(120, TimeUnit.SECONDS)
//                    .readTimeout(120, TimeUnit.SECONDS)
//                    .writeTimeout(120, TimeUnit.SECONDS);
//
//            client = builder.build();
//        }
//        return client;
//    }
//
//    /**
//     * 根据url，发送异步Post请求(不带进度)
//     * @param url 提交到服务器的地址
//     * @param fileNames 完整的上传的文件的路径名
//     * @param callback OkHttp的回调接口
//     */
//    public static void doPostRequest(String url, File fileNames, Callback callback) {
//        Call call = getOkHttpClientInstance().newCall(getRequest(url,fileNames));
//        call.enqueue(callback);
//    }
//
//    /**
//     * 获得Request实例(不带进度)
//     * @param url
//     * @return
//     */
//    private static Request getRequest(String url, File fileNames) {
//        Request.Builder builder = new Request.Builder();
//        builder.url(url)
//                .post(getRequestBody(fileNames));
//        return builder.build();
//    }
//
//    /**
//     * 通过上传的文件的完整路径生成RequestBody
//     * @param fileNames 完整的文件路径
//     * @return
//     */
//    private static RequestBody getRequestBody(File fileNames) {
//        //创建MultipartBody.Builder，用于添加请求的数据
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        //根据文件的后缀名，获得文件类型
//        File file = new File(fileNames.getName()); //生成文件
//        String fileType = getMimeType(fileNames.getName());
//        builder.addFormDataPart( //给Builder添加上传的文件
//                "image",  //请求的名字
//                fileNames.getName(), //文件的文字，服务器端用来解析的
//                RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
//        );
//        return builder.build(); //根据Builder创建请求
//    }
//
//    /**
//     * 获取文件MimeType
//     *
//     * @param filename
//     * @return
//     */
//    private static String getMimeType(String filename) {
//        FileNameMap filenameMap = URLConnection.getFileNameMap();
//        String contentType = filenameMap.getContentTypeFor(filename);
//        if (contentType == null) {
//            contentType = "application/octet-stream"; //* exe,所有的可执行程序
//        }
//        return contentType;
//    }
//
//}
