package com.kc.common_service;

import android.app.Application;

import com.dovar.router_api.router.DRouter;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        DRouter.init(this);
    }
}
