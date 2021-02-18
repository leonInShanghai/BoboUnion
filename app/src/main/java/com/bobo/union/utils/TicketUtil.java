package com.bobo.union.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bobo.union.base.BaseApplication;
import com.bobo.union.model.doman.IBaseInfo;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.ui.activity.TicketActivity;

/**
 * Created by 公众号：IT波 on 2021/2/18 Copyright © Leon. All rights reserved.
 * Functions: 淘口令（Ticket：票）工具类
 */
public class TicketUtil {

    /**
     * 跳转到淘口令界面方法
     */
    public static void toTicketPage(Context context, IBaseInfo baseInfo) {
        // 跳转到淘口令界面
        String title = baseInfo.getTitle();
        String url = baseInfo.getUrl();
        if (TextUtils.isEmpty(url)) {
            // 详情的url
            url = baseInfo.getUrl();
        }
        String cover = baseInfo.getCover();
        // 拿到TicketPressenter去加载数据
        ITikcetPresenter ticketPressenter = PresenterManager.getInstance().getTicketPressenter();
        ticketPressenter.getTicket(title, url, cover);
        context.startActivity(new Intent(context, TicketActivity.class));
    }

}
