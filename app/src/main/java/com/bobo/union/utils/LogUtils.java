package com.bobo.union.utils;

import android.util.Log;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 这是一个日志打印工具类 debug环境输出log release环境只输出重要的log
 */
public class LogUtils {

    // 当前的log等级 默认为允许所有输出
    private static int currentLev = 4;

    // log级别越小级别越高 error级是最高级别
    private static final int DEBUG_LEV = 4;
    private static final int INFO_LEV = 3;
    private static final int WARING_LEV = 2;
    private static final int ERROR_LEV = 1;


    public static void d(Object object, String log){

        // 如果当前的环境允许输出该级别的bug
        if (currentLev >= DEBUG_LEV){
            Log.d(object.getClass().getSimpleName(), log);
        }

    }

    public static void i(Object object, String log){
        // 如果当前的环境允许输出该级别的bug
        if (currentLev >= INFO_LEV){
            Log.i(object.getClass().getSimpleName(), log);
        }
    }

    public static void w(Object object, String log){
        // 如果当前的环境允许输出该级别的bug
        if (currentLev >= WARING_LEV){
            Log.w(object.getClass().getSimpleName(), log);
        }
    }

    public static void e(Object object, String log){
        // 如果当前的环境允许输出该级别的bug
        if (currentLev >= ERROR_LEV){
            Log.e(object.getClass().getSimpleName(), log);
        }
    }

}


/**
 * 2020-08-22  A day at Zoo
 * It is sunny today
 * I am at a zoo
 * I see the Sun
 * It is shining
 * I see a cute monkey
 * It is eating fruit
 * I see a big cow
 * It is eating gruss
 * I see a happy girl
 * She is playing
 * I see an old man
 * He is taking photos
 */
