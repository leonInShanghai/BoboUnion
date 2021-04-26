package com.bobo.union.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bobo.union.base.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;


/**
 * Created by 公众号：IT波 on 2021/4/25 Copyright © Leon. All rights reserved.
 * Functions: 判断是否是小米、华为、魅族系统
 * 当华为操作系统时AlertDialog的内边距要加上状态栏的高度 不然它（AlertDialog）不居中
 */
public class SystemUtil {

    private static final String TAG = "SystemUtil";

    public static final String SYS_EMUI = "HONOR";

    /**
     * 获取手机厂商
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getString(BaseApplication.getAppContext(), TAG))) {
            return SharedPreferencesUtil.getString(BaseApplication.getAppContext(), TAG);
        }
        SharedPreferencesUtil.setString(BaseApplication.getAppContext(), TAG, android.os.Build.BRAND);
        return android.os.Build.BRAND;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
