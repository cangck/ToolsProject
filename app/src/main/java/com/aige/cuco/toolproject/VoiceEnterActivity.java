package com.aige.cuco.toolproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.aige.cuco.toolproject.utils.BitmapCompressUtils;
import com.aige.cuco.toolproject.utils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class VoiceEnterActivity extends Activity {

    private static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int TAKE_PHOTO_REQUEST_CODE = 1; //拍照
    public static final int PHOTO_REQUEST_CUT = 2; //裁切
    public static final int PHOTO_REQUEST_GALLERY = 3; //相册
    public static final int VOICE_GUILD_NAME_CODE = 4; //公会名称
    public static final int VOICE_GUILD_INTRODUCTION_CODE = 5;  //简介
    public static final int COURSE_CONTENT_CODE = 6; //课程内容
    public static final int COURSE_CONTACT_CODE = 7; //联系人请求码
    public static final int COURSE_INVITE_CODE = 8;  //邀请码
    public static final int SETTING_WARDEN_CODE = 9; //设置管理员




    private Uri imageUri;
    private File file;
    private Uri uri;
    private Bitmap bitmap;

    private String name;  // 公会名字
    private String introduce; // 简介
    private String pinid;   //课程id
    private String contentname; //开课内容
    private String contact;  //联系人
    private String phone;   //手机号
    private String code;   //验证码
    private String inviteCode;  //邀请码
    private String warden; //后台管理员账号









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_GUILD_NAME_CODE && data != null) {
            name = data.getStringExtra("guildname");
        }else if (requestCode == VOICE_GUILD_INTRODUCTION_CODE && data != null){
            introduce = data.getStringExtra("introduce");
        }else if (requestCode == COURSE_CONTENT_CODE && data != null){
            pinid = data.getStringExtra("contentid");
            if (!pinid.equals("") && !TextUtils.isEmpty(pinid)) {
                contentname = data.getStringExtra("contentname");
            }
        } else if (requestCode == COURSE_CONTACT_CODE && data != null) {
            contact = data.getStringExtra("contact");
            if (!TextUtils.isEmpty(contact) && !contact.equals("")) {
               phone = data.getStringExtra("phone");
               code = data.getStringExtra("code");
            }
        } else if (requestCode == COURSE_INVITE_CODE && data != null) {
            inviteCode = data.getStringExtra("inviteName");
        } else if (requestCode == SETTING_WARDEN_CODE && data != null){
            warden = data.getStringExtra("warden");
        }

        //当没有请求码的时候直接return
        if (resultCode == 0) {
            return;
        }

        if (requestCode == TAKE_PHOTO_REQUEST_CODE) { //拍照的请求码 路径不为null时 执行裁剪操作
            if (imageUri != null) {
                startPhotoZoom(imageUri);    //进行图片裁剪
                file = getImageStoragePath(VoiceEnterActivity.this);  //把裁剪好的图片进行赋值  把拍照的文件赋值到要上传的文件中
            }
        }

        if (requestCode == PHOTO_REQUEST_CUT) {  //裁剪的请求码 获取裁剪后的图片
            File file = getImageStoragePath(getApplicationContext());
            bitmap =  getSmallBitmap(file.getPath());
        }

        /**
         * 如果点击操作后再点击屏幕 当前操作被取消了 这个方法还是会被执行到 会传递data进行裁剪
         * 这时候的data为null 所以进入页面时 先判断是否是空 空就直接return
         */
        if (data == null) {
            return;
        }

        if (requestCode == PHOTO_REQUEST_GALLERY) {  //开启相册 选好照片后 启动裁剪页面
            if (data != null) {
                startPhotoZoom(data.getData());
                Uri uri = data.getData(); //得到图片路径

                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);

                file = new File("/mnt/sdcard/upload.jpg");  //把裁剪好的图片添加到file里

                Bitmap bm = BitmapUtils.getBitmap(path); //把文件转成bitmap
                int orientation = BitmapUtils.getOrientation(this, uri);
                Bitmap mm = BitmapUtils.rotateImage(bm, orientation);
                bm = BitmapCompressUtils.imageZoom(mm, 200.00);   //进行压缩

                //将bitmap转成file
//                file = new File("/mnt/sdcard/upload.jpg");

                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }

    }


    /**
     * 打开相机
     */
    private void openCamera() {
        //把获取的文件转成Uri路径
        imageUri = Uri.fromFile(getImageStoragePath(VoiceEnterActivity.this));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //指定拍照后的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 打开相册选择图片
     */
    public void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(Intent.createChooser(intent, "选择图片"),PHOTO_REQUEST_GALLERY);
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

    /**
     * 把uri转成bitmap
     *
     * @param uri
     * @return
     */
    private Bitmap decodeUriBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            //跳转到截图页面 用户可能也会取消 所以要判断是否为空 取消了就直接返回null
            if (uri != null) {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 把路径转成bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * 调用系统裁剪
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 196);// 输出图片大小
        intent.putExtra("outputY", 196);
        intent.putExtra("output", Uri.fromFile(getImageStoragePath(getApplicationContext())));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}
