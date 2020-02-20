package com.kc.common_service;

import android.app.Application;

import com.dovar.router_api.router.DRouter;

import ckc.android.develophelp.lib.util.CrashHandler;

public class MyApp extends Application implements CrashHandler.IApp {


    private static final String TAG = "MyApp";
    private static MyApp sApp;
    //启动器
    private Class mLauncherActivityClass;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //初始化路由
        DRouter.init(this);
    }

    public static MyApp get() {
        return sApp;
    }

    @Override
    public void onFinishApp() {
        //会导致应用onCreate
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 设置启动器类
     */
    public void setLauncherActivityClass(Class launcherActivityClass) {
        mLauncherActivityClass = launcherActivityClass;
    }

    @Override
    public Class getLauncherActivityClass() {
        return mLauncherActivityClass;
    }
}
