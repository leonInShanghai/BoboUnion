package com.bobo.union.presenter.impl;

import android.view.View;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.OnSellContent;
import com.bobo.union.presenter.IOnSellPagePresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.utils.UrlUtils;
import com.bobo.union.view.IOnSellPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 公众号：IT波 on 2021/2/15 Copyright © Leon. All rights reserved.
 * Functions: 特惠页的P层
 */
public class OnSellPagePresenterImpl implements IOnSellPagePresenter {

    // 默认从第一页开始
    public static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;

    // V层回调接口
    private IOnSellPageCallback mOnSellPageCallback = null;
    private final Api mApi;

    // 当前加载的状态
    private boolean mIsLoading = false;

    public OnSellPagePresenterImpl() {
        Retrofit retrofit = RetrofitManage.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    /**
     * 获取特惠内容
     */
    @Override
    public void getOnSellContent() {

        // 当前正在加载数据的时候再次加载避免不必要的重复加载
        if (mIsLoading) {
            return;
        }

        // 加载中状态改为true
        mIsLoading = true;

        // 通知UI状态为加载中..
        if (mOnSellPageCallback != null) {
            mOnSellPageCallback.onLoading();
        }

        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
                // 请求成功或是失败结果回来时置为false
                mIsLoading = false;
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    OnSellContent result = response.body();
                    onSuccess(result);
                } else {
                    onError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                // 请求成功或是失败结果回来时置为false
                mIsLoading = false;
                onError();
            }
        });
    }

    /**
     * 请求成功
     * @param result
     */
    private void onSuccess(OnSellContent result) {
        if (mOnSellPageCallback != null) {
            if (isEmpty(result)) {
                // 请求成功但是数据为空回调到V
                onEmpty();
            } else {
                // 请求成功回调到V
                mOnSellPageCallback.onContentLoadedSuccess(result);
            }
        }
    }

    /**
     * 判断请求成功后数据是否为空
     * @return
     */
    private boolean isEmpty(OnSellContent result) {
        if (result != null && result.getData() != null && result.getData().getTbk_dg_optimus_material_response() !=
                null && result.getData().getTbk_dg_optimus_material_response().getResult_list() != null &&
                result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data() != null) {

            int size = result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();

            if (size == 0) {
                // 请求成功但是数据为空回调到V 为空
                return true;
            } else {
                // 请求成功回调到V
                return false;
            }
        } else {
            // 为空
            return true;
        }
    }

    /**
     * 请求成功但是数据为空
     */
    private void onEmpty() {
        if (mOnSellPageCallback != null) {
            // 请求成功但是数据为空回调到V
            mOnSellPageCallback.onEmpty();
        }
    }

    /**
     * 请求失败
     */
    private void onError() {
        if (mOnSellPageCallback != null) {
            // 请求失败回调到V
            mOnSellPageCallback.onError();
        }
    }

    /**
     * 当加载失败用户点击了从新加载
     */
    @Override
    public void reload() {
        // 从新加载
        this.getOnSellContent();
    }

    /**
     * 加载更多
     */
    @Override
    public void loaderMore() {

        // 当前正在加载数据的时候不要上拉加载更多
        if (mIsLoading) {
            return;
        }

        // 加载中状态改为true
        mIsLoading = true;

        // 加载更多先当前页码++
        mCurrentPage++;
        // 去加载更多内容
        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {

                // 请求成功或是失败结果回来时置为false
                mIsLoading = false;

                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    OnSellContent result = response.body();
                    onMoreLoaded(result);
                } else {
                    onMoreLoadedError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                // 请求成功或是失败结果回来时置为false
                mIsLoading = false;
                onMoreLoadedError();
            }
        });
    }

    /**
     * 加载更多失败了
     */
    private void onMoreLoadedError() {
        if (mOnSellPageCallback != null) {
            // 请求失败了页码要减去
            mCurrentPage--;
            mOnSellPageCallback.onMoreLoadedError();
        }
    }

    /**
     * 加载更多加载成功
     * @param result
     */
    private void onMoreLoaded(OnSellContent result) {
        if (mOnSellPageCallback != null) {
            if (isEmpty(result)) {
                // 请求成功了为空页码也要减去
                mCurrentPage--;
                // 请求成功但是数据为空回调到V
                mOnSellPageCallback.onMoreLoadedEmpty();
            } else {
                // 请求成功回调到V
                mOnSellPageCallback.onMoreLoaded(result);
            }
        }
    }

    @Override
    public void registerViewCallback(IOnSellPageCallback iOnSellPageCallback) {
        mOnSellPageCallback = iOnSellPageCallback;
    }

    @Override
    public void unregisterViewCallback(IOnSellPageCallback iOnSellPageCallback) {
        this.mOnSellPageCallback = null;
    }
}
