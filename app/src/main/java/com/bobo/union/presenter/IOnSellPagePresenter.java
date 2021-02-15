package com.bobo.union.presenter;

import com.bobo.union.base.IBasePresenter;
import com.bobo.union.view.IOnSellPageCallback;

/**
 * Created by 公众号：IT波 on 2021/2/15 Copyright © Leon. All rights reserved.
 * Functions: 特惠P层接口
 */
public interface IOnSellPagePresenter extends IBasePresenter<IOnSellPageCallback> {

    /**
     * 加载特惠内容
     */
    void getOnSellContent();

    /**
     * （当网络异常）重新加载内容
     */
    void reload();

    /**
     * 加载更多特惠内容
     */
    void loaderMore();
}
