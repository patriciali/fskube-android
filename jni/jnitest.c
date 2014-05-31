#include <jni.h>
#include <stdio.h>
#include "jnitest.h"
 
// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT int JNICALL Java_com_jflei_fskube_FSKubeWrapper_sayHello(JNIEnv *env, jobject thisObj, int a) {
  return a*a;
}
