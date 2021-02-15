package com.bobo.union.presenter.impl;


import android.util.Log;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.presenter.ISelectedPagePresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.utils.UrlUtils;
import com.bobo.union.view.ISelectedPageCallbacck;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 公众号：IT波 on 2021/1/31 Copyright © Leon. All rights reserved.
 * Functions: Presenter 实现
 * This ball weight 1 kilogram, or 2.2 pounds.
 * This car's speed is 60 mailes per hour, or abount 97 kilometers an hour.
 * The purse on the left costs $100 more then the purse on the right.
 * This box has 3 dimensions length width and height.
 */
public class SelectedPagePresenterImpl implements ISelectedPagePresenter {

    /**
     * View 层的回调接口
     */
    private ISelectedPageCallbacck mViewCallbacck = null;
    private final Api mApi;

    public SelectedPagePresenterImpl() {
        Retrofit retrofit = RetrofitManage.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getCategories() {
        if (mViewCallbacck != null) {
            // 开始请求进入loading...状态
            mViewCallbacck.onLoading();
        }
        Call<SelectedPageCategory> task = mApi.getSelectedPageCategories();
        task.enqueue(new Callback<SelectedPageCategory>() {
            @Override
            public void onResponse(Call<SelectedPageCategory> call, Response<SelectedPageCategory> response) {
                int resultCode = response.code();
                LogUtils.d(SelectedPagePresenterImpl.this, " result code --> " + resultCode);
                if (resultCode == HttpURLConnection.HTTP_OK) {
                    // 请求成功
                    SelectedPageCategory result = response.body();
                    // 通知ui更新
                    if (mViewCallbacck != null) {
                        mViewCallbacck.onCategoriesLoaded(result);
                    }
                } else {
                    // 请求失败
                    onLoadedError();
                }
            }

            @Override
            public void onFailure(Call<SelectedPageCategory> call, Throwable t) {
                // 请求失败
                onLoadedError();
            }
        });
    }

    /**
     * 通知View层请求失败了
     */
    private void onLoadedError() {
        // 请求失败
        if (mViewCallbacck != null) {
            mViewCallbacck.onError();
        }
    }

    @Override
    public void getContentByCategory(SelectedPageCategory.DataBean item) {
        int categoryId = item.getFavorites_id();
        LogUtils.d(this, "categoryId --> " + categoryId);
        String targetUrl = UrlUtils.getSelectedPageContentUrl(categoryId);
        Call<SelectedContent> task = mApi.getSelectedPageContent(targetUrl);
        task.enqueue(new Callback<SelectedContent>() {
            @Override
            public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                int resultCode = response.code();
                LogUtils.d(SelectedPagePresenterImpl.this, " resultCode --> " + resultCode);
                if (resultCode == HttpURLConnection.HTTP_OK) {
                    // 请求成功
                    SelectedContent result = response.body();
                    if (mViewCallbacck != null) {
                        mViewCallbacck.onContentLoaded(result);
                    }
                } else {
                    // 请求失败
                    onLoadedError();
                }
            }

            @Override
            public void onFailure(Call<SelectedContent> call, Throwable t) {
                // 请求失败
                onLoadedError();
            }
        });
    }

    /**
     * (当加载失败用户点击“加载失败”)重新加载内容
     */
    @Override
    public void reloadContent() {

        // 注释的代码是大据写错了，用户点击从新加载内容应加载左边的分类 不应加载右边的内容
        // if (mCurrentCategoryItem != null) {
        //     getContentByCategory(mCurrentCategoryItem);
        // }

        // 正确的写法（加载分类）
        this.getCategories();
    }

    @Override
    public void registerViewCallback(ISelectedPageCallbacck callbacck) {
        this.mViewCallbacck = callbacck;
    }

    @Override
    public void unregisterViewCallback(ISelectedPageCallbacck iSelectedPageCallbacck) {
        this.mViewCallbacck = null;
    }
}
