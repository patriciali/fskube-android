package com.jflei.fskube;


public class FSKubeWrapper {
    static {
        System.loadLibrary("fskube");
    }

    // Declare a native method sayHello() that receives nothing and returns void
    public native int sayHello(int a);

}
