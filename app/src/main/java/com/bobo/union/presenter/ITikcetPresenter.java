package com.bobo.union.presenter;

import com.bobo.union.base.IBasePresenter;
import com.bobo.union.view.ITicketPagerCallback;

/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public interface ITikcetPresenter extends IBasePresenter<ITicketPagerCallback> {

    /**
     * 生成淘口令
     * @param title
     * @param url
     * @param cover
     */
    void getTicket(String title, String url, String cover);
}
