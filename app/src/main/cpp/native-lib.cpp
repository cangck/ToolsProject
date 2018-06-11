#include <jni.h>
#include <string>
#include "com_aige_cuco_toolproject_JNIUtils.h"
#include "com_aige_cuco_toolproject_jnicallback_AccessMethod.h"
#include <android/log.h>
// log标签
#define  TAG    "这里填写日志的TAG"
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)


extern "C"
class JNIException : jthrowable {

};

/**
 * 异常抛出类
 * @param env
 * @param name
 * @param msg
 */
void JNU_ThrowByName(JNIEnv *env, const char *name, const char *msg) {
    jclass clazz = env->FindClass(name);
    if (clazz == NULL) {
        env->ThrowNew(clazz, msg);
    }
    env->DeleteLocalRef(clazz);
//    env->Throw(_jthrowable);
}

JNIEXPORT jstring
JNICALL
Java_com_aige_cuco_toolproject_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


/*
 * Class:     com_aige_cuco_toolproject_JNIUtils
 * Method:    getSystemVersionName
 * Signature: ()Ljava/lang/String;
 */
//JNICALL在windows中的值为__stdcall，用于约束函数入栈顺序和堆栈清理的规则。
JNIEXPORT jstring JNICALL Java_com_aige_cuco_toolproject_JNIUtils_getSystemVersionName
        (JNIEnv *env, jobject obj) {
    std::string version = "version";
    printf("Java str:%s\n", version.c_str());
    return env->NewStringUTF(version.c_str());
}


/*
 * Class:     com_aige_cuco_toolproject_JNIUtils
 * Method:    encryption
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_aige_cuco_toolproject_JNIUtils_encryption
        (JNIEnv *env, jobject obj, jstring pwd) {
    const jchar *c_str;
    char buf[128] = {0};
    jboolean isCopy = true;
    c_str = env->GetStringChars(pwd, &isCopy);
    if (c_str == NULL) {
        printf("输入的数据不能为空");
    }
    printf("C_str:%s \n", c_str);
//    std::cout << "C_str:%s \n" << c_str << std::end;
    sprintf(buf, "hellow %s", c_str);
    env->ReleaseStringChars(pwd, c_str);
    return env->NewStringUTF(buf);
}


jint getInt(JNIEnv *env, jintArray ints) {
    jboolean isCopy = true;
    jint i, sum = 0;
    jint *c_array;
    jint arr_len;

    arr_len = env->GetArrayLength(ints);
    c_array = (jint *) malloc(sizeof(jint) * arr_len);
    memset(c_array, 0, sizeof(jint) * arr_len);
    env->GetIntArrayRegion(ints, 0, arr_len, c_array);
    for (int i = 0; i < arr_len; ++i) {
        sum += c_array[i];
    }
    free(c_array);
    return sum;
}


jint getArrNumber(JNIEnv *env, jintArray ints) {
    jint i, sum = 0;
    jint *c_array;
    jint arr_len;
    jboolean isCopy = true;
    c_array = env->GetIntArrayElements(ints, &isCopy);

    if (c_array == NULL) {
        return 0;
    }

    arr_len = env->GetArrayLength(ints);
    for (int i = 0; i < arr_len; ++i) {
        sum += c_array[i];
    }
    env->ReleaseIntArrayElements(ints, c_array, 0);
    return sum;


}


/*
 * Class:     com_aige_cuco_toolproject_JNIUtils
 * Method:    sumArray
 * Signature: ([I)I
 * 访问基本类型数组
 */
JNIEXPORT jint JNICALL Java_com_aige_cuco_toolproject_JNIUtils_sumArray
        (JNIEnv *env, jobject obj, jintArray ints) {
//    return getInt(env, ints);
    return getArrNumber(env, ints);
}

/*
 * Class:     com_aige_cuco_toolproject_JNIUtils
 * Method:    initInt2DArray
 * Signature: (I)[[I
 */
JNIEXPORT jobjectArray JNICALL Java_com_aige_cuco_toolproject_JNIUtils_initInt2DArray
        (JNIEnv *env, jobject obj, jint size) {
    jobjectArray result;
    jclass clsIntArray;
    jint i, j;
    clsIntArray = env->FindClass("[I");
    if (clsIntArray == NULL)
        return NULL;
    result = env->NewObjectArray(size, clsIntArray, NULL);
    if (result == NULL)
        return NULL;

    for (int i = 0; i < size; ++i) {
        jint buff[256];
        jintArray intArr = env->NewIntArray(size);
        if (intArr == NULL)
            return NULL;
        for (int j = 0; j < size; ++j) {
            buff[j] = i + j;
        }
        env->SetIntArrayRegion(intArr, 0, size, buff);
        env->SetObjectArrayElement(result, i, intArr);
        env->DeleteLocalRef(intArr);
    }
}



/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    callJavaStaticMethod
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_callJavaStaticMethod
        (JNIEnv *env, jclass cls) {
    jclass clazz = NULL;
    jstring str_arg = NULL;
    jmethodID mid_static_method;

    clazz = env->FindClass("com/aige/cuco/toolproject/jnicallback/ClassMethod");
    if (clazz == NULL)
        return;
    mid_static_method = env->GetStaticMethodID(clazz, "callStaticMethod",
                                               "(Ljava/lang/String;I)V");
    if (mid_static_method == NULL)
        return;

    str_arg = env->NewStringUTF("我是静态方法");
    env->CallStaticVoidMethod(clazz, mid_static_method, str_arg, 100);
    env->DeleteLocalRef(clazz);
    env->DeleteLocalRef(str_arg);

}

/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    callJavaInstaceMethod
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_callJavaInstaceMethod
        (JNIEnv *env, jclass cls) {

    jclass clazz = NULL;
    jobject jobj = NULL;
    jmethodID mid_construct = NULL;
    jmethodID mid_instance = NULL;
    jstring str_arg = NULL;


    clazz = env->FindClass("com/aige/cuco/toolproject/jnicallback/ClassMethod");
    if (clazz == NULL)
        return;

    mid_construct = env->GetMethodID(clazz, "<init>", "()V");
    mid_instance = env->GetMethodID(clazz, "callInstanceMethod", "(Ljava/lang/String;I)V");
    if (mid_instance == NULL)
        return;

    jobj = env->NewObject(clazz, mid_construct);
    if (jobj == NULL)
        return;

    str_arg = env->NewStringUTF("我是实例方法");

    env->CallVoidMethod(jobj, mid_instance, str_arg, 200);


    env->DeleteLocalRef(clazz);
    env->DeleteLocalRef(jobj);
    env->DeleteLocalRef(str_arg);
}


/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    accessInstanceField
 * Signature: (Lcom/aige/cuco/toolproject/jnicallback/ClassMethod;)V
 */
JNIEXPORT void JNICALL Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_accessInstanceField
        (JNIEnv *env, jclass clazz, jobject obj) {
    jclass jclazz = env->GetObjectClass(obj);
    if (jclazz == NULL) {
        return;
    }
    jfieldID fid = env->GetFieldID(jclazz, "str", "Ljava/lang/String;");
    if (jclazz == NULL) {
        return;
    }
    jstring j_str = (jstring) env->GetObjectField(obj, fid);
    const char *c_str = env->GetStringUTFChars(j_str, NULL);
    env->ReleaseStringUTFChars(j_str, c_str);
    jstring j_newString = env->NewStringUTF("this is C String");
    if (j_newString == NULL)
        return;

    env->SetObjectField(obj, fid, j_newString);
    env->DeleteLocalRef(jclazz);
    env->DeleteLocalRef(j_str);
    env->DeleteLocalRef(j_newString);
}

/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    accessStaticField
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_accessStaticField
        (JNIEnv *env, jclass clazz) {
    jclass jclazz = env->FindClass("com/aige/cuco/toolproject/jnicallback/ClassMethod");
    if (jclazz == NULL)
        return;

    jfieldID fid = env->GetStaticFieldID(jclazz, "num", "I");

    if (fid == NULL)
        return;

    jint num = env->GetStaticIntField(jclazz, fid);

    env->SetStaticIntField(jclazz, fid, 200);
    env->DeleteLocalRef(jclazz);

}


/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    callSupperInstanceMethod
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_callSupperInstanceMethod
        (JNIEnv *env, jclass clazz) {
    jclass jclazz = env->FindClass("com/aige/cuco/toolproject/jnicallback/Cat");
    if (jclazz == NULL)
        return;
    jmethodID mid_cat_init = env->GetMethodID(jclazz, "<init>", "(Ljava/lang/String;)V");

    if (mid_cat_init == NULL)
        return;

    jstring c_str_name = env->NewStringUTF("汤姆猫");

    if (c_str_name == NULL)
        return;
    jobject obj_cat = env->NewObject(jclazz, mid_cat_init, c_str_name);

    if (obj_cat == NULL)
        return;

    jclass cls_animal = env->FindClass("com/aige/cuco/toolproject/jnicallback/Animal");
    if (cls_animal == NULL)
        return;
    jmethodID mid_run = env->GetMethodID(cls_animal, "run", "()V");
    if (mid_run == NULL)
        return;
    env->CallNonvirtualVoidMethod(obj_cat, cls_animal, mid_run);

    jmethodID mid_getName = env->GetMethodID(cls_animal, "getName", "()Ljava/lang/String;");
    if (mid_getName == NULL)
        return;
    c_str_name = (jstring) env->CallNonvirtualObjectMethod(obj_cat, cls_animal, mid_getName);
    const char *animal_c_str = env->GetStringUTFChars(c_str_name, NULL);

    env->ReleaseStringUTFChars(c_str_name, animal_c_str);
    quit:
    env->DeleteLocalRef(jclazz);
    env->DeleteLocalRef(cls_animal);
    env->DeleteLocalRef(c_str_name);
    env->DeleteLocalRef(obj_cat);

    //    env->NewGlobalRef()
    //    env->NewLocalRef()
    //    env->NewWeakGlobalRef()


//    env->EnsureLocalCapacity()
//    env->PushLocalFrame()
//    env->PopLocalFrame()
//    env->NewLocalRef()
//    env->IsSameObject(obj1,obj2)比较两个对象是否相等
}



/*
 * Class:     com_aige_cuco_toolproject_jnicallback_AccessMethod
 * Method:    testJNIException
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_aige_cuco_toolproject_jnicallback_AccessMethod_testJNIException
        (JNIEnv *env, jclass clazz, jstring str) {
//    env->ExceptionCheck()
//    env->ExceptionClear()
//    env->ExceptionDescribe()
//    env->ExceptionOccurred()
//    env->ThrowNew(clazz,)

    const jchar *cstr = env->GetStringChars(str, NULL);

    if (env->ExceptionOccurred()) {
        env->ReleaseStringChars(str, cstr);
        JNU_ThrowByName(env, "com/aige/cuco/toolproject/jnicallback/JNIException", "字符串转换异常");
        return;
    }
    env->ReleaseStringChars(str, cstr);

//    env->RegisterNatives(clazz,)
}


/*
 * Prototypes for functions exported by loadable shared libs.  These are
 * called by JNI, not provided by JNI.
 */
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("加载so库");
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGI("卸载so库");
}


