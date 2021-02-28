package com.bobo.union.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bobo.union.base.BaseApplication;
import com.bobo.union.model.doman.CacheWithDuration;
import com.bobo.union.model.doman.Histories;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.gson.Gson;

/**
 * Created by 公众号：IT波 on 2021/2/21 Copyright © Leon. All rights reserved.
 * Functions: 字符串本地持久化保存工具类
 */
public class JsonCacheUtil {

    private static volatile JsonCacheUtil sJsonCache = null;

    public static final String JSON_CACHE_SP_NAME = "json_cache_sp_name";
    private final SharedPreferences mSharedPreferences;
    private Gson mGson;

    // 单例标配私有化构造方法
    private JsonCacheUtil() {
        // 第一个参数是 JSON_CACHE_SP_NAME:名称 第二个参数是 Context.MODE_PRIVATE:模式
        mSharedPreferences = BaseApplication.getAppContext().getSharedPreferences(JSON_CACHE_SP_NAME,
                Context.MODE_PRIVATE);
        // 把object转json的类 谷歌公司的
        mGson = new Gson();
    }

    /**
     * 保存一个Object对象
     */
    public void saveCache(String key, Object value) {
        this.saveCache(key, value, -1L);
    }

    /**
     * 保存一个Object对象
     */
    public void saveCache(String key, Object value, long duration) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        String valueStr = mGson.toJson(value);
        if (duration != -1L) {
            // 当前时间
            duration += System.currentTimeMillis();
        }
        // 创建用户搜索历史对象
        CacheWithDuration cacheWithDuration = new CacheWithDuration(duration, valueStr);
        String cacheWithTime = mGson.toJson(cacheWithDuration);
        // 本地持久化保存
        edit.putString(key, cacheWithTime);
        edit.apply();
    }

    /**
     * 根据key删除已经保存在本地的对象
     * @param key
     */
    public void delCache(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    /**
     * 根据key获取保存数据
     * @param key
     * @param clazz
     */
    public <T> T getValue(String key, Class<T> clazz) {
        String valueWithDuration = mSharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(valueWithDuration)) {
            return null;
        }

        CacheWithDuration cacheWithDuration = mGson.fromJson(valueWithDuration, CacheWithDuration.class);
        // 对事件进行判断
        long duration = cacheWithDuration.getDuration();
        // 判断是否过期了
        if (duration != -1 && duration - System.currentTimeMillis() <= 0) {
            // 过期了
            return null;
        } else {
           // 没有过期或没有时间限制
           String cache = cacheWithDuration.getCahce();
           LogUtils.d(this, cache);
           return mGson.fromJson(cache, clazz);
        }
    }

    // 获取单例对象的方法
    public static JsonCacheUtil getInstance() {
        if (sJsonCache == null) {
            // double check
            synchronized (JsonCacheUtil.class) {
                if (sJsonCache == null) {
                    sJsonCache = new JsonCacheUtil();
                }
            }
        }
        return sJsonCache;
    }
}
