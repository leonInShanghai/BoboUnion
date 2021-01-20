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

    @Override
    public void getTicket(String title, String url, String cover) {
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
                    TicketResult ticketResult = response.body();
                    LogUtils.d(TicketPressenterImpl.this, "result: " + ticketResult);
                } else {
                    // 请求失败

                }
            }

            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void registerViewCallback(ITicketPagerCallback iTicketPagerCallback) {

    }

    @Override
    public void unregisterViewCallback(ITicketPagerCallback iTicketPagerCallback) {

    }
}
