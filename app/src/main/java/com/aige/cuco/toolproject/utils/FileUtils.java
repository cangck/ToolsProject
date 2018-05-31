package com.aige.cuco.toolproject.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import java.io.File;

/**
 * Created by wusourece on 2017/7/25.
 * 文件工具类
 */

public class FileUtils {

    public static final int TAKE_PHOTO_REQUEST_CODE = 1; //拍照
    private static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int PHOTO_REQUEST_GALLERY = 3; //相册

    /**
     * 从Uri中获取绝对路径
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static File getCacheDir(Context context) {
//        Context context = application.getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            File sdDir = Environment.getExternalStorageDirectory();
            cacheDir = new File(sdDir, "/android/data/" + context.getPackageName()
                    + "/cache");
        }
        return cacheDir;

    }

    /**
     * 打开相机
     */
    private void openCamera(Activity context) {
        //把获取的文件转成Uri路径
        Uri imageUri = Uri.fromFile(getImageStoragePath(context));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //指定拍照后的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        context.startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 打开相册选择图片
     */
    public void openPhotoAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), PHOTO_REQUEST_GALLERY);
    }

    /**
     * 设置图片保存路径
     *
     * @return
     */
    private File getImageStoragePath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "icon.png");
            return file;
        }
        return null;
    }
}
