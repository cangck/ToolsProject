#include <jni.h>
#include <string>
#include "com_aige_cuco_toolproject_JNIUtils.h"
#include "com_aige_cuco_toolproject_jnicallback_AccessMethod.h"

extern "C" JNIEXPORT jstring

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

