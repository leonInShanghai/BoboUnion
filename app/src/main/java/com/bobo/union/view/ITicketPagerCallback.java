package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.TicketResult;

/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public interface ITicketPagerCallback extends IBaseCallback {

    /**
     * 淘口令加载结果
     * @param cover
     * @param result
     */
    void onTicketLoaded(String cover, TicketResult result);
}
