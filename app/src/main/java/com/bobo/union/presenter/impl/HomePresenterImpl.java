package com.bobo.union.presenter.impl;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.Categories;
import com.bobo.union.presenter.IHomePresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 首页的presenter 实现 IHomePresenter 接口
 */
public class HomePresenterImpl implements IHomePresenter {


    private IHomeCallback mCallback = null;

    /**
     * 获取商品分类
     */
    @Override
    public void getCategories() {

        // 开始加载流程
        if (mCallback != null) {
            mCallback.onLoading();
        }

        // 加载分类内容数据
        Retrofit retrofit = RetrofitManage.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();

        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                // 判断如果是请求成功
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    // 请求成功
                    Categories categories = response.body();
                    LogUtils.d(HomePresenterImpl.this, "请求成功：" + categories.toString());

                    if (mCallback != null){

                        // 判断加载成功但是没有数据
                        if (categories == null || categories.getData().size() == 0){
                            // 设置显示空页面
                            mCallback.onEmpty();
                        } else {
                            // 请求成功回调刷新UI
                            mCallback.onCategoriesLoad(categories);
                        }
                    }

                } else {
                    // 请求失败
                    LogUtils.i(HomePresenterImpl.this, "请求失败：" + response.code());

                    // 请求失败（错误）
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }

            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                // 请求错误
                LogUtils.e(HomePresenterImpl.this,"请求错误" + t.getMessage());

                // 请求失败（错误）
                if (mCallback != null) {
                    mCallback.onError();
                }

            }
        });

    }

    /**
     * 注册Ui通知接口
     * @param callback
     */
    @Override
    public void registerViewCallback(IHomeCallback callback) {
        this.mCallback = callback;
    }

    /**
     * 取消UI通知接口
     * @param callback
     */
    @Override
    public void unregisterViewCallback(IHomeCallback callback) {
        // 取消注册置空
        mCallback = null;
    }
}
