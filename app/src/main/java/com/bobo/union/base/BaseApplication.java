package com.bobo.union.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 公众号：IT波 on 2021/1/9 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class BaseApplication extends Application {

    // 全局上下文
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getBaseContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
