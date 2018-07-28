Activity跳转动画
overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
JobScheduler的使用
Geocoder
进程保护

WiFiDirectConfig
FdkAACCodec
AVAPIs_ubia
IOTCAPIs_ubia
VRConfig
MP4writer
amr-codec
Speex
SpeexAndroid
G726Android
ADPCMAndroid
H264Android
Mp3Android
FFmpeg
videodecoder

自适应抖动缓冲器类
System.loadLibrary( "Func" ); //加载libFunc.so
System.loadLibrary( "Ajb" ); //加载libAjb.so


#常用代码片段
    ##MD5加密
    public String generate(String imageUri) {
         byte[] md5 = this.getMD5(imageUri.getBytes());
         BigInteger bi = (new BigInteger(md5)).abs();
         return bi.toString(36);
     }

    private byte[] getMD5(byte[] data) {
        byte[] hash = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException var4) {
            L.e(var4);
        }
        return hash;
    }
###获取视频缩略图

    @Override
    	protected InputStream getStreamFromOtherSource(String imageUri, Object extra)
    			throws IOException {
    		String uri = imageUri.toString();
    		String scheme = "video://";
    		if (uri.startsWith(scheme)) {
    			String filePath = uri.substring(scheme.length());
    			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
    					Thumbnails.MINI_KIND);
    			if (bitmap != null) {
    				ByteArrayOutputStream bos = new ByteArrayOutputStream();
    				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
    				return new ByteArrayInputStream(bos.toByteArray());
    			} else {
    				return null;
    			}
    		}
    
    		return super.getStreamFromOtherSource( imageUri , extra);
    	}
    	
   
###Activity中操作任务栈
    MainActivity.this.moveTaskToBack(true);
    
###JobScheduler
    adb shell dumpsys jobscheduler  打印Jobscheduler
    
###查看背景运行的信息

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }