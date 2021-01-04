package com.bobo.union.presenter;

import com.bobo.union.base.IBasePresenter;
import com.bobo.union.view.ICategoryPagerCallback;

/**
 * Created by Leon on 2020-08-29 Copyright © Leon. All rights reserved.
 * Functions: 首页下 的子页面 presenter接口
 */
public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {

    /**
     * 根据分类id获取内容
     * @param categoryId
     */
    void getContentByCategoryId(int categoryId);

    /**
     * 上拉加载更多
     * @param categoryId
     */
    void loaderMore(int categoryId);

    /**
     * 网络不好时（用户发起）重新加载
     */
    void reload(int categoryId);

}
