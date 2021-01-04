package com.bobo.union.utils;

import android.content.Context;

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

}
