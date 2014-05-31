package com.jflei.fskube;


public class FSKubeWrapper {
    static {
        System.loadLibrary("fskube");
    }

    // Declare a native method sayHello() that receives nothing and returns void
    public native void initialize(int sampleRate);

    public native void addSample(double sample);

    public native int getTimeMillis();

}
