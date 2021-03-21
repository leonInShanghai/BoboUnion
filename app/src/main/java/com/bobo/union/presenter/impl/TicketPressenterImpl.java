package com.bobo.union.presenter.impl;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.model.doman.TicketParams;
import com.bobo.union.model.doman.TicketResult;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.utils.UrlUtils;
import com.bobo.union.view.ITicketPagerCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class TicketPressenterImpl implements ITikcetPresenter {

    private ITicketPagerCallback mViewCallback = null;

    /**
     * 代表网络数据加载状态的枚举类
     */
    enum LoadState {
        LOADING, SUCCESS, ERROR, NONE 
    }

    // 网络数据加载状态默认为NONE 
    private LoadState mCurrentStatus = LoadState.NONE;

    private String mCover = null;
    private TicketResult mTticketResult = null;

    @Override
    public void getTicket(String title, String url, String cover) {

        onTicketLoadedLoading();
        this.mCover = cover;

        LogUtils.d(this, "title --> " + title + " url --> " + url + " cover --> " + cover);
        String targetUrl = UrlUtils.getTicketUrl(url);
        // 获取淘口令
        Retrofit retrofit = RetrofitManage.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        TicketParams ticketParams = new TicketParams(targetUrl, title);
        Call<TicketResult> task = api.getTicket(ticketParams);
        task.enqueue(new Callback<TicketResult>() {
            @Override
            public void onResponse(Call<TicketResult> call, Response<TicketResult> response) {
                int code = response.code();
                LogUtils.d(TicketPressenterImpl.this, "result code --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    // 请求成功
                    mTticketResult = response.body();
                    LogUtils.d(TicketPressenterImpl.this, "result: " + mTticketResult);

                    // 加载成功处理
                    onTicketLoadedSuccess();
                } else {
                    // 请求失败
                    onLoadedTicketError();
                }
            }

            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {
                // 请求失败
                onLoadedTicketError();
            }
        });
    }

    private void onTicketLoadedSuccess() {
        // 通知UI更新
        if (mViewCallback != null) {
            mViewCallback.onTicketLoaded(mCover, mTticketResult);
        } else {
            // 加载成功
            mCurrentStatus = LoadState.SUCCESS;
        }
    }

    /**
     * 请求失败的统一处理
     */
    private void onLoadedTicketError() {
        // 请求失败
        if (mViewCallback != null) {
            mViewCallback.onError();
        } else {
            // 请求失败
            mCurrentStatus = LoadState.ERROR;
        }
    }

    @Override
    public void registerViewCallback(ITicketPagerCallback callback) {

        this.mViewCallback = callback;

        if (mCurrentStatus != LoadState.NONE) {
            // 说明加载状态已经在Activity跳转前改变了
            // 更新UI
            if (mCurrentStatus == LoadState.SUCCESS) {
                onTicketLoadedSuccess();
            } else if (mCurrentStatus == LoadState.ERROR) {
                onLoadedTicketError();
            } else if (mCurrentStatus == LoadState.LOADING) {
                onTicketLoadedLoading();
            }
        }
    }

    private void onTicketLoadedLoading() {
        if (mViewCallback != null) {
            mViewCallback.onLoading();
        } else {
            // 加载中
            mCurrentStatus = LoadState.LOADING;
        }
    }

    @Override
    public void unregisterViewCallback(ITicketPagerCallback iTicketPagerCallback) {
        this.mViewCallback = null;
    }
}
