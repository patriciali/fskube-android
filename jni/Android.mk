LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := fskube
LOCAL_SRC_FILES := fsk.cpp logging.cpp rs232.cpp stackmat.cpp jnitest.c

include $(BUILD_SHARED_LIBRARY)