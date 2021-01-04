package com.bobo.union.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: (自动生成)饿汉式单例
 */
public class RetrofitManage {


    private static final RetrofitManage ourInstance = new RetrofitManage();


    private final Retrofit mRetrofit;

    public static RetrofitManage getInstance() {
        return ourInstance;
    }

    private RetrofitManage() {

        // 创建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
