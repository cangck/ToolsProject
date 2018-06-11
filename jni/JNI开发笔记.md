###JNI开发笔记


各平台下JNI开发流程
JNI数据类型
JNI函数查找命令规则
字符串处理
本地代码访问Java的属性和方法
局部引用于全局引用
开发当中常见的错误分享
NDK开发环境搭建
NDK编译系统详解和NDK开发综合案例
一个是main线程，另一 个就是GC线程(负责将一些不再使用的对象回收)
Android上的JNI局部引用表最大数量是512个

java反射
java类加载机制

JNI加密、和核心算法

android系统的不同架构?
如何生成不同的cpu架构动态库？
so库搜索路径？
    java.library.path:通过调用System.getProperties("java.library.path")方法获取查找的目录列表
c/c++在队上或栈上申请内存？

静态库动态库区别？

加载动态库的两种方式:
    System.loadLibrary("HelloWorld"); 
    System.load("/Users/yangxin/Desktop/libHelloWorld.jnilib");
    方式1:只需要指定动态库的名字即可，不需要加lib前缀，也不要加.so、.dll和.jnilib后缀 
    方式2:指定动态库的绝对路径名，需要加上前缀和后缀
    处理找不到的问题:
        方式1:将动态链接库拷贝到java.library.path目录下
        方式2:给jvm添加“-Djava.library.path=动态链接库搜索目录”参数，指定系统属性java.library.path的 值
        java -Djava.library.path=/Users/yangxin/Desktop


1、JNI开发流程(不同操作系统环境下编译的动态库)(用一个HelloWorld示例拉开JNI开发的序幕) 
2、JVM查找java native方法的规则
3、JNI数据类型及与Java数据类型的映射关系
4、JNI字符串处理
5、访问数组(基本类型数组与对象数组) 
6、C/C++访问AVA静态方法和实例方法 
7、C/C++访问JAVA实例变量和静态变量 
8、调用构造方法和实例方法 
9、JNI调用性能测试及优化 
10、JNI局部引用、全局引用和弱全局引用 
11、异常处理
12、多线程
13、本地代码嵌入JVM
14、JNI开发的一些注意事项 
15、常见错误分享(局部引用表溢出、本地线程未附加到JVM中的问题) 
16、NDK开发环境建
17、NDK编译系统详解 
18、NDK开发综合实例(Android、Cocos2d-x)



###JVM查找java native方法的规则
    JVM查找native方法有两种方式:(主动查找，被动查找)
    1> 按照JNI规范的命名规则
    2> 调用JNI提供的RegisterNatives函数，将本地函数注册到JVM中。
    用javah工具生成函数原型的头文件，函数命名规则为:Java_类全路径方法名。如 Java_com_study_jnilearn_HelloWorld_sayHello，
    其中Java是函数的前 缀，com_study_jnilearn_HelloWorld是类名，sayHello是方法名，它们之间用 _(下划线) 连接。
    函数参数:
        JNIEXPORT jstring JNICALL Java_com_study_jnilearn_HelloWorld_sayHello(JNIEnv *, jclass, jstring);
        第一个参数:JNIEnv* 是定义任意native函数的第一个参数(包括调用JNI的RegisterNatives函数注册的 函数)，指向JVM函数表的指针，
        函数表中的每一个入口指向一个JNI函数，每个函数用于访问JVM中特定 的数据结构。
        第二个参数:调用java中native方法的实例或Class对象，如果这个native方法是实例方法，则该参数是 jobject，如果是静态方法，则是jclass
        第三个参数:Java对应JNI中的数据类型，Java中String类型对应JNI的jstring类型。(后面会详细介绍 JAVA与JNI数据类型的映射关系)
    函数返回值类型:
        夹在JNIEXPORT和JNICALL宏中间的jstring，表示函数的返回值类型，对应Java的String类型
    总结:
        当我们熟悉了JNI的native函数命名规则之后，就可以不用通过javah命令去生成相应java native方法的函 数原型了，只需要按照函数命名规则编
    写相应的函数原型和实现即可。
    比如com.study.jni.Utils类中还有一个计算加法的native实例方法add，有两个int参数和一个int返回 值:
    public native int add(int num1, int num2)，
    对应JNI的函数原型就是:JNIEXPORT jint JNICALL Java_com_study_jni_Utils_add(JNIEnv *, jobject, jint,jint);
    
###JNI数据类型及与Java数据类型的映射关系
    数据类型：
        1.引用类型:Object、String、数组
        2.基本类型(8个):byte、char、short、int、long、float、double、boolean
        8种基本数据类型分别对应JNI数据类型中的jbyte、jchar、jshort、jint、jlong、jfloat、jdouble、 jboolean。
        所有的JNI引用类型全部是jobject类型，为了使用方便和类型安全，JNI定义了一个引用类型集合，
        集合当中的所有类型都是jobject的子类，这些子类和Java中常用的引用类型相对应。
        例如:jstring表示字符串、jclass表示class字节码对象、jthrowable表示异常、jarray表示数组，
        另外jarray派生了8个子类，分别对应Java中的8种基本数据类型(jintArray、jshortArray、jlongArray等)
        
    2、JNI如果使用C语言编写的话，所有引用类型使用jobject，其它引用类型使用typedef重新定义，如: typedef jobject jstring
        jvalue类型:
        jvalue是一个unio(联合)类型，在C语中为了节约内存，会用联合类型变量来存储声明在联合体中的任意 类型数据 。
        在JNI中将基本数据类型与引用类型定义在一个联合类型中，表示用jvalue定义的变量，可以存 储任意JNI类型的数据，
        后面会介绍jvalue在JNI编程当中的应用。原型如下:
         typedef union jvalue { 
                    jboolean z;
                    jbyte b;
                    jchar c;
                    jshort s; 
                    jint i; 
                    jlong j; 
                    jfloat f; 
                    jdouble d; 
                    jobject l;
         } jvalue;
        
###JNI字符串处理
    JNI把Java中的所有对象当作一个C指针传递到本地方法中，这个指针指向JVM中的内部数据结构，而内部 的数据结构在内存中的存储方式是不可见的。
    只能从JNIEnv指针指向的函数表中选择合适的JNI函数来操作 JVM中的数据结构。第三章的示例中，访问java.lang.String对应的JNI类型jstring时，
    没有像访问基本数 据类型一样直接使用，因为它在Java是一个引用类型，所以在本地代码中只能通过GetStringUTFChars这 样的JNI函数来访问字符串的内容。
    因为Java默认使用Unicode编码，而C/C++默认使用UTF编码，所以在本地代码中操作字符串的时候，必 须使用合适的JNI函数把jstring转换成C风格的字符串。
    因为Java默认使用Unicode编码，而C/C++默认使用UTF编码，所以在本地代码中操作字符串的时候，必 须使用合适的JNI函数把jstring转换成C风格的字符串。JNI支持字符串在Unicode和UTF-8两种编码之间转 换，GetStringUTFChars可以把一个jstring指针(指向JVM内部的Unicode字符序列)转换成一个UTF-8 格式的C字符串。
    JNI支持字符串在Unicode和UTF-8两种编码之间转 换，GetStringUTFChars可以把一个jstring指针(指向JVM内部的Unicode字符序列)转换成一个UTF-8 格式的C字符串。
    
    1.访问字符串
    2.异常检查
    3.释放字符串
    4.创建字符串
    其它字符串处理函数:
    1> GetStringChars和ReleaseStringChars:这对函数和Get/ReleaseStringUTFChars函数功能差不多， 用于获取和释放以Unicode格式编码的字符串。后者是用于获取和释放UTF-8编码的字符串。
    2> GetStringLength:由于UTF-8编码的字符串以'\0'结尾，而Unicode字符串不是。如果想获取一个指 向Unicode编码的jstring字符串长度，在JNI中可通过这个函数获取。
    3> GetStringUTFLength:获取UTF-8编码字符串的长度，也可以通过标准C函数strlen获取
    4> GetStringCritical和ReleaseStringCritical:提高JVM返回源字符串直接指针的可能性
    5> GetStringRegion和GetStringUTFRegion:分别表示获取Unicode和UTF-8编码字符串指定范围内的内容。这对函数会把源字符串复制到一个预先分配的缓冲区内
    注意:
        注意:GetStringUTFRegion和GetStringRegion这两个函数由于内部没有分配内存，所以JNI没有提供 ReleaseStringUTFRegion和ReleaseStringRegion这样的函数。
    
    字符串操作总结:
    1、对于小字符串来说，GetStringRegion和GetStringUTFRegion这两对函数是最佳选择，因为缓冲区可 以被编译器提前分配，而且永远不会产生内存溢出的异常。当你需要处理一个字符串的一部分时，使用这 对函数也是不错。因为它们提供了一个开始索引和子字符串的长度值。另外，复制少量字符串的消耗 也是 非常小的。
    2、使用GetStringCritical和ReleaseStringCritical这对函数时，必须非常小心。一定要确保在持有一个由 GetStringCritical 获取到的指针时，本地代码不会在 JVM 内部分配新对象，或者做任何其它可能导致系 统死锁的阻塞性调用
    3、获取Unicode字符串和长度，使用GetStringChars和GetStringLength函数
    4、获取UTF-8字符串的长度，使用GetStringUTFLength函数
    5、创建Unicode字符串，使用NewStringUTF函数
    6、从Java字符串转换成C/C++字符串，使用GetStringUTFChars函数
    7、通过GetStringUTFChars、GetStringChars、GetStringCritical获取字符串，这些函数内部会分配内 存，必须调用相对应的ReleaseXXXX函数释放内存
    
 
###访问数组(基本类型数组与对象数组)
    JNI中的数组分类:
        1.基本类型数组
        2.对象数组
        基本类型数组中的所有元素都是 JNI的基本数据类型，可以直接访问。而对象数组中的所有元素是一个类的实例或其它数组的引用
        
        小结:
        1、对于小量的、固定大小的数组，应该选择Get/SetArrayRegion函数来操作数组元素是效率最高的。因 为这对函数要求提前分配一个C临时缓冲区来存储数组元素，你可以直接在Stack(栈)上或用malloc在堆 上来动态申请，当然在栈上申请是最快的。有童鞋可能会认为，访问数组元素还需要将原始数据全部拷贝 一份到临时缓冲区才能访问而觉得效率低?我想告诉你的是，像这种复制少量数组元素的代价是很小的， 几乎可以忽略。这对函数的另外一个优点就是，允许你传入一个开始索引和长度来实现对子数组元素的访 问和操作(SetArrayRegion函数可以修改数组)，不过传入的索引和长度不要越界，函数会进行检查，如 果越界了会抛出ArrayIndexOutOfBoundsException异常。
        2、如果不想预先分配C缓冲区，并且原始数组长度也不确定，而本地代码又不想在获取数组元素指针时被 阻塞的话，使用Get/ReleasePrimitiveArrayCritical函数对，就像Get/ReleaseStringCritical函数对一样， 使用这对函数要非常小心，以免死锁。
        3、Get/ReleaseArrayElements系列函数永远是安全的，JVM会选择性的返回一个指针，这个指针可能指 向原始数据，也可能指向原始数据的复制
        在JNI中，只有jobject以及子类属于引用变 量，会占用引用表的空间，jint，jfloat，jboolean等都是基本类型变量，不会占用引用表空间，即不需要 释放。引用表最大空间为512个，如果超出这个范围，JVM就会挂掉。
        
        
###C/C++访问AVA静态方法和实例方法
    当我们在运行一 个Java程序时，JVM会先将程序运行时所要用到所有相关的class文件加载到JVM中，并采用按需加载的方 式加载，也就是说某个类只有在被用到的时候才会被加载，这样设计的目的也是为了提高程序的性能和节 约内存。所以我们在用类名调用一个静态方法之前，JVM首先会判断该类是否已经加载，如果没有 被ClassLoader加载到JVM中，JVM会从classpath路径下查找该类，如果找到了，会将其加载到JVM中， 然后才是调用该类的静态方法。如果没有找到，JVM会抛出java.lang.ClassNotFoundException异常，提 示找不到这个类。ClassLoader是JVM加载class字节码文件的一种机制
    虽然函数结束后，JVM会自动释放所有局部引用变量所占的内存空间。但还是手动释放一下比较安全，因 为在JVM中维护着一个引用表，用于存储局部和全局引用变量，经测试在Android NDK环境下，这个表的 最大存储空间是512个引用，如果超过这个数就会造成引用表溢出，JVM崩溃。在PC环境下测试，不管申 请多少局部引用也不释放都不会崩，我猜可能与JVM和Android Dalvik虚拟机实现方式不一样的原因。所 以有申请就及时释放是一个好的习惯!(局部引用和全局引用在后面的文章中会详细介绍)
    
    void VideoPlayerController::setInitializedStatus(bool initCode) {
        LOGI("enter VideoPlayerController::setInitializedStatus...");
        JNIEnv *env = 0;
        int status = 0;
        bool needAttach = false;
        status = g_jvm->GetEnv((void **) (&env), JNI_VERSION_1_4);
    
        // don't know why, if detach directly, will crash
        if (status < 0) {
            if (g_jvm->AttachCurrentThread(&env, NULL) != JNI_OK) {
                LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
                return;
            }
    
            needAttach = true;
        }
    
        jclass jcls = env->GetObjectClass(obj);
    
        jmethodID onInitializedFunc = env->GetMethodID(jcls, "onInitializedFromNative", "(Z)V");
        env->CallVoidMethod(obj, onInitializedFunc, initCode);
    
        if (needAttach) {
            if (g_jvm->DetachCurrentThread() != JNI_OK) {
                LOGE("%s: DetachCurrentThread() failed", __FUNCTION__);
            }
        }
        LOGI("leave VideoPlayerController::setInitializedStatus...");
    }
    
###调用构造方法和实例方法  
    写过C或C++的同学应该都有一个很深刻的内存管理概念，栈空间和堆空间，栈空间的内存大小受操作 系统限制，由操作系统自动来管理，
    速度较快，所以在函数中定义的局部变量、函数形参变量都存储在栈空间。操作系统没有限制堆空间的内存大小，只受物理内存的限制，
    内存需要程序员自己管理。在C语言中用malloc关键字动态分配的内存和在C++中用new创建的对象所分配内存都存储在堆空间，内
    存使用完之后分别用free或delete/delete[]释放。这里不过多的讨论C/C++内存管理方面的知识，有兴 趣的同学请自行百度
    做Java的童鞋众所周知，写Java程序是不需要手动来管理内存的，内存管理那些 烦锁的事情全都交由一个叫GC的线程来管理
    (当一个对象没有被其它对象所引用时，该对象就会被GC 释放)。但我觉得Java内部的内存管理原理和C/C++是非常相似的，
    上例中，Animal cat = new Cat(“汤姆”); 局部变量cat存放在栈空间上，new Cat(“汤姆”);创建的实例对象存放在堆空间，
    返回 一个内存地址的引用，存储在cat变量中。这样就可以通过cat变量所指向的引用访问Cat实例当中所有可见的成员了
    
    
    由于Java程序运行在虚拟机中的这个特点，在Java中创建的对象、定义的变量和方法，内部对象的数据
    结构是怎么定义的，只有JVM自己知道。如果我们在C/C++中想要访问Java中对象的属性和方法时， 是不能够直接操作JVM内部
    Java对象的数据结构的。想要在C/C++中正确的访问Java的数据结 构，JVM就必须有一套规则来约束C/C++与Java互相访问的机制，所以才有了JNI规范，
    JNI规范定义 了一系列接口，任何实现了这套JNI接口的Java虚拟机，C/C++就可以通过调用这一系列接口来间接的 访问Java中的数据结构。比如前面文章
    中学习到的常用JNI接口有:GetStringUTFChars(从Java虚拟 机中获取一个字符串)、ReleaseStringUTFChars(释放从JVM中获取字符串所分配的内存空间)、
     NewStringUTF、GetArrayLength、GetFieldID、GetMethodID、FindClass等。
     
     
     
###异常处理
    总结
        1、当调用一个JNI函数后，必须先检查、处理、清除异常后再做其它 JNI 函数调用，否则会产生不可预知 的结果。
        2、一旦发生异常，立即返回，让调用者处理这个异常。或 调用 ExceptionClear 清除异常，然后执行自己 的异常处理代码。
        3、异常处理的相关JNI函数总结:
        1> ExceptionCheck:检查是否发生了异常，若有异常返回JNI_TRUE，否则返回JNI_FALSE
        2> ExceptionOccurred:检查是否发生了异常，若用异常返回该异常的引用，否则返回NULL 3> ExceptionDescribe:打印异常的堆栈信息
        4> ExceptionClear:清除异常堆栈信息
        5> ThrowNew:在当前线程触发一个异常，并自定义输出异常信息
        jint (JNICALL *ThrowNew) (JNIEnv *env, jclass clazz, const char *msg); 6> Throw:丢弃一个现有的异常对象，在当前线程触发一个新的异常
        jint (JNICALL *Throw) (JNIEnv *env, jthrowable obj);
        7> FatalError:致命异常，用于输出一个异常信息，并终止当前VM实例(即退出程序)
        void (JNICALL *FatalError) (JNIEnv *env, const char *msg);