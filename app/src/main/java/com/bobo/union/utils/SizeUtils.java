package com.bobo.union.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;

import com.bobo.union.base.BaseApplication;

/**
 * Created by Leon on 2021-01-04 Copyright © Leon. All rights reserved.
 * Functions: px 转 dp 的工具类
 */
public class SizeUtils {

    /**
     * px 转 dp 的工具类
     * @param context
     * @param dpValue
     * @return dp单位数
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 获取当前屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        if (activity == null) {
            return 1920;
        }
        Display display = activity.getWindowManager().getDefaultDisplay();;
        // int width = display.getWidth();
        // int height = display.getHeight();
        return display.getHeight();
    }

}
