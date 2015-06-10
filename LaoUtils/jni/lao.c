#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <android/log.h>
#define LOG_TAG "so_raw_javasenddatatoc_JNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

//utils function...
//jstring -> char*
char* Jstring2CStr(JNIEnv* env, jstring jstr, const char* charsetName) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
//	jstring strencode = (*env)->NewStringUTF(env, "GB2312");
	jstring strencode = (*env)->NewStringUTF(env, charsetName);
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
			strencode); // String .getByte("GB2312");
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0);
	return rtn;
}
/**
 * 执行一个Runnable
 */
void runMethod(JNIEnv *env, jobject runner) {
	jclass runnableClazz = (*env)->FindClass(env, "java/lang/Runnable");
	jmethodID run = (*env)->GetMethodID(env, runnableClazz, "run", "()V");
	(*env)->CallVoidMethod(env, runner, run);
}

//init function...
//在执行完loadLibrary之后执行的方法，如果需要初始化一些参数的话要在这里执行。
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM * vm, void* reserved) {
	LOGD("native library init...");
	return JNI_VERSION_1_6;
}

//jni监控方法。
JNIEXPORT void JNICALL Java_so_raw_laoutil_LaoUtils_addPackageListener(
		JNIEnv *env, jobject obj, jint action, jstring packageName,
		jobject runner) {
	int code = fork();
	if (code < 0) {
		//出错了..
	} else if (code == 0) {
		//子线程
		//创建成功...
		//获取包名路径
		const char * charset = "GB2312";
		char * pkgNamechars = Jstring2CStr(env, packageName, charset);
		char * datapath = "/data/data/";
		char realName[256];
		strcpy(realName, datapath);
		strcat(realName, pkgNamechars);

		int loopFlag = 1;
		if (action == 0) {
			//监控卸载
			while (loopFlag) {
				sleep(1);
				//在创建的子进程 中 不断的监视 我要卸载的应用的包
				FILE* file = fopen(realName, "r");
				if (file == NULL) {
					runMethod(env, runner);
					loopFlag = 0;
				}
			}
		} else if (action == 1) {
			//监控安装
			while (loopFlag) {
				sleep(1);
				//在创建的子进程 中 不断的监视 我要卸载的应用的包
				FILE* file = fopen(pkgNamechars, "r");
				if (file != NULL) {
					runMethod(env, runner);
					loopFlag = 0;
				}
			}
		}

	} else if (code > 0) {

	}

}
