//package com.aige.cuco.toolproject.utils;
//
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.ExifInterface;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 图片压缩工具类
// *
// * @author
// */
//public class ImageCompressUtil {
//
//    //500K
//    private final static int FEEL_UPLOAD_COMPRESS_SIZE = 500 * 1024;
//    //300K
//    private final static int FEEL_UPLOAD_NO_COMPRESS_SIZE = 300 * 1024;
//
//    /**
//     * 给Feel上传图片的时候压缩图片质量使用,宽高最大1400,
//     * 根据图片质量压缩图片，如果没超过 FEEL_UPLOAD_COMPRESS_SIZE KB 压缩质量是90，超过是70，否则按照压缩比例压缩图片
//     *
//     * @param filePath    原始图片路径
//     * @param newFilePath 新文件存储路径
//     * @return 返回图片高度，宽度
//     */
//    public static Map<String, Integer> compressByQuality(String filePath, String newFilePath) {
//        Bitmap bm = null;
//        ByteArrayOutputStream baos = null;
//        FileOutputStream fos = null;
//        Map<String, Integer> res = null;
//        try {
//            File f = new File(filePath);
//            if (!f.isFile()) {
//                return null;
//            }
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, opts);
//
//            // Calculate inSampleSize
//            opts.inSampleSize = calculateInSampleSize(opts, 1080, 1080);
//            // Decode bitmap with inSampleSize set
//            opts.inJustDecodeBounds = false;
//            try {
//                bm = BitmapFactory.decodeFile(filePath, opts);
//            } catch (OutOfMemoryError e) {
//                return null;
//            } catch (Throwable e) {
//            }
//
//
//            baos = new ByteArrayOutputStream();
//            long size = Utils.getBitmapBytes(bm);
//            if (size <= FEEL_UPLOAD_NO_COMPRESS_SIZE) {//小于300K不压缩
//                bm.compress(CompressFormat.JPEG, 90, baos);
//            } else if (size > FEEL_UPLOAD_NO_COMPRESS_SIZE && size <= FEEL_UPLOAD_COMPRESS_SIZE) {//500K 质量高一些
//                bm.compress(CompressFormat.JPEG, 75, baos);
//            } else { //质量差一些
//                bm.compress(CompressFormat.JPEG, 65, baos);
//            }
//            res = new HashMap<String, Integer>();
//            res.put("width", bm.getWidth());
//            res.put("height", bm.getHeight());
//
//            File newFile = new File(newFilePath);
//            if (!newFile.exists()) {
//                newFile.createNewFile();
//            }
//            fos = new FileOutputStream(newFile);
//            baos.writeTo(fos);
//            fos.flush();
//
//            return res;
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        } catch (OutOfMemoryError e) {
//        } catch (Throwable e) {
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (Exception e) {
//            }
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (Exception e) {
//            }
//            try {
//                if (bm != null && !bm.isRecycled()) {
//                    bm.recycle();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return res;
//    }
//
//    /**
//     * 上传图片的时候压缩图片质量使用,宽高最大1400,
//     * 根据图片质量压缩图片，如果没超过 FEEL_UPLOAD_COMPRESS_SIZE KB 压缩质量是90，超过是70，否则按照压缩比例压缩图片
//     *
//     * @param filePath 原始图片路径
//     * @return 返回图片高度，宽度
//     */
//    public static Bitmap compressByQuality(String filePath) {
//        Bitmap bm = null;
//        ByteArrayOutputStream baos = null;
//        FileOutputStream fos = null;
//        try {
//            File f = new File(filePath);
//            if (!f.isFile()) {
//                return null;
//            }
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, opts);
//
//            // Calculate inSampleSize
//            opts.inSampleSize = calculateInSampleSize(opts, 1080, 1080);
//            // Decode bitmap with inSampleSize set
//            opts.inJustDecodeBounds = false;
//            try {
//                bm = BitmapFactory.decodeFile(filePath, opts);
//            } catch (OutOfMemoryError e) {
//                return null;
//            } catch (Throwable e) {
//            }
//
//
//            baos = new ByteArrayOutputStream();
//            long size = Utils.getBitmapBytes(bm);
//            if (size <= FEEL_UPLOAD_NO_COMPRESS_SIZE) {//小于300K不压缩
//                bm.compress(CompressFormat.JPEG, 90, baos);
//            } else if (size > FEEL_UPLOAD_NO_COMPRESS_SIZE && size <= FEEL_UPLOAD_COMPRESS_SIZE) {//500K 质量高一些
//                bm.compress(CompressFormat.JPEG, 75, baos);
//            } else { //质量差一些
//                bm.compress(CompressFormat.JPEG, 65, baos);
//            }
//
//            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
//                    baos.toByteArray(), 0, baos.toByteArray().length);
//
//            recycleBitmap(bm);
//
//            return compressedBitmap;
//        } catch (Throwable e) {
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (Exception e) {
//            }
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (Exception e) {
//            }
//            try {
//                if (bm != null && !bm.isRecycled()) {
//                    bm.recycle();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 通过降低图片的质量来压缩图片
//     *
//     * @param bitmap  要压缩的图片位图对象
//     * @param maxSize 压缩后图片大小的最大值,单位KB
//     * @return 压缩后的图片位图对象
//     */
//    public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int quality = 100;
//        bitmap.compress(CompressFormat.JPEG, quality, baos);
//        System.out.println("图片压缩前大小：" + baos.toByteArray().length + "byte");
//        boolean isCompressed = false;
//        while (baos.toByteArray().length / 1024 > maxSize) {
//            quality -= 10;
//            baos.reset();
//            bitmap.compress(CompressFormat.JPEG, quality, baos);
//            System.out.println("质量压缩到原来的" + quality + "%时大小为："
//                    + baos.toByteArray().length + "byte");
//            isCompressed = true;
//        }
//        System.out.println("图片压缩后大小：" + baos.toByteArray().length + "byte");
//        if (isCompressed) {
//            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
//                    baos.toByteArray(), 0, baos.toByteArray().length);
//            recycleBitmap(bitmap);
//            return compressedBitmap;
//        } else {
//            return bitmap;
//        }
//    }
//
//    /**
//     * 通过压缩图片的尺寸来压缩图片大小，仅仅做了缩小，如果图片本身小于目标大小，不做放大操作
//     *
//     * @param pathName     图片的完整路径
//     * @param targetWidth  缩放的目标宽度
//     * @param targetHeight 缩放的目标高度
//     * @return 缩放后的图片
//     */
//    public static Bitmap compressBySize(String pathName, int targetWidth,
//                                        int targetHeight) {
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
//        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
//        // 得到图片的宽度、高度；
//        int imgWidth = opts.outWidth;
//        int imgHeight = opts.outHeight;
//        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
//        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
//        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
//        if (widthRatio > 1 || heightRatio > 1) {
//            if (widthRatio > heightRatio) {
//                opts.inSampleSize = widthRatio;
//            } else {
//                opts.inSampleSize = heightRatio;
//            }
//        }
//        // 设置好缩放比例后，加载图片进内容；
//        opts.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeFile(pathName, opts);
//        return bitmap;
//    }
//
//    /**
//     * 通过压缩图片的尺寸来压缩图片大小
//     *
//     * @param bitmap       要压缩图片
//     * @param targetWidth  缩放的目标宽度
//     * @param targetHeight 缩放的目标高度
//     * @return 缩放后的图片
//     */
//    public static Bitmap compressBySize(Bitmap bitmap, int targetWidth,
//                                        int targetHeight) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(CompressFormat.JPEG, 100, baos);
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
//                baos.toByteArray().length, opts);
//        // 得到图片的宽度、高度；
//        int imgWidth = opts.outWidth;
//        int imgHeight = opts.outHeight;
//        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
//        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
//        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
//        if (widthRatio > 1 || heightRatio > 1) {
//            if (widthRatio > heightRatio) {
//                opts.inSampleSize = widthRatio;
//            } else {
//                opts.inSampleSize = heightRatio;
//            }
//        }
//        // 设置好缩放比例后，加载图片进内存；
//        opts.inJustDecodeBounds = false;
//        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
//                baos.toByteArray(), 0, baos.toByteArray().length, opts);
//        recycleBitmap(bitmap);
//        return compressedBitmap;
//    }
//
//    /**
//     * 通过压缩图片的尺寸来压缩图片大小，通过读入流的方式，可以有效防止网络图片数据流形成位图对象时内存过大的问题；
//     *
//     * @param is           要压缩图片，以流的形式传入
//     * @param targetWidth  缩放的目标宽度
//     * @param targetHeight 缩放的目标高度
//     * @return 缩放后的图片
//     * @throws IOException 读输入流的时候发生异常
//     */
//    public static Bitmap compressBySize(InputStream is, int targetWidth,
//                                        int targetHeight) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buff = new byte[1024];
//        int len = 0;
//        while ((len = is.read(buff)) != -1) {
//            baos.write(buff, 0, len);
//        }
//
//        byte[] data = baos.toByteArray();
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
//                opts);
//        // 得到图片的宽度、高度；
//        int imgWidth = opts.outWidth;
//        int imgHeight = opts.outHeight;
//        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
//        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
//        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
//        if (widthRatio > 1 || heightRatio > 1) {
//            if (widthRatio > heightRatio) {
//                opts.inSampleSize = widthRatio;
//            } else {
//                opts.inSampleSize = heightRatio;
//            }
//        }
//        // 设置好缩放比例后，加载图片进内存；
//        opts.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
//        return bitmap;
//    }
//
//    /**
//     * 旋转图片摆正显示
//     *
//     * @param srcPath
//     * @param bitmap
//     * @return
//     */
//    public static Bitmap rotateBitmapByExif(String srcPath, Bitmap bitmap) {
//        ExifInterface exif;
//        Bitmap newBitmap = null;
//        try {
//            exif = new ExifInterface(srcPath);
//            if (exif != null) { // 读取图片中相机方向信息
//                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                        ExifInterface.ORIENTATION_NORMAL);
//                int digree = 0;
//                switch (ori) {
//                    case ExifInterface.ORIENTATION_ROTATE_90:
//                        digree = 90;
//                        break;
//                    case ExifInterface.ORIENTATION_ROTATE_180:
//                        digree = 180;
//                        break;
//                    case ExifInterface.ORIENTATION_ROTATE_270:
//                        digree = 270;
//                        break;
//                }
//                if (digree != 0) {
//                    Matrix m = new Matrix();
//                    m.postRotate(digree);
//                    newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                            bitmap.getWidth(), bitmap.getHeight(), m, true);
//                    recycleBitmap(bitmap);
//                    return newBitmap;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
//    /**
//     * 回收位图对象
//     *
//     * @param bitmap
//     */
//    public static void recycleBitmap(Bitmap bitmap) {
//        if (bitmap != null && !bitmap.isRecycled()) {
//            bitmap.recycle();
//            System.gc();
//            bitmap = null;
//        }
//    }
//
//    public static int calculateInSampleSize(BitmapFactory.Options options,
//                                            int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height
//                    / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
//        }
//
//        return inSampleSize;
//    }
//
//    /**
//     * 设置bitmap的大小
//     *
//     * @param bitmap
//     * @param newWidth
//     * @param newHeight
//     * @return
//     */
//    public static Bitmap setBitmapSize(Bitmap bitmap, int newWidth,
//                                       int newHeight) {
//        try {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            // 计算缩放比例
//            float scaleWidth = ((float) newWidth) / width;
//            float scaleHeight = ((float) newHeight) / height;
//            // 取得想要缩放的matrix参数
//            Matrix matrix = new Matrix();
//            matrix.postScale(scaleWidth, scaleHeight);
//            // 得到新的图片
//            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
//                    true);
//        } catch (Throwable e) {
//            return bitmap;
//        }
//    }
//
//    /**
//     * 调整bitmap 宽度
//     *
//     * @param bitmap
//     * @param newWidth
//     * @return
//     */
//    public static Bitmap zoomBitmapSize(Bitmap bitmap, int newWidth) {
//        try {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            // 计算缩放比例
//            float scaleWidth = ((float) newWidth) / width;
//            // 取得想要缩放的matrix参数
//            Matrix matrix = new Matrix();
//            matrix.postScale(scaleWidth, scaleWidth);
//            // 得到新的图片
//            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
//                    true);
//        } catch (Throwable e) {
//            return bitmap;
//        }
//    }
//
//
//    public static Integer checkImageScale(String filePath, double maxScale) {
//        File f = new File(filePath);
//        if (!f.isFile()) {
//            return null;
//        }
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, opts);
//        Boolean wh = ((opts.outHeight * 1.0f) / opts.outWidth) > maxScale;
//        Boolean hw = ((opts.outWidth * 1.0f) / opts.outHeight) > maxScale;
//        if (wh) return -1;
//        if (hw) return 1;
//        return 0;
//    }
//
//}