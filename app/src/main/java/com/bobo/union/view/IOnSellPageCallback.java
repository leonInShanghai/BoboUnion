package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.OnSellContent;

/**
 * Created by 公众号：IT波 on 2021/2/15 Copyright © Leon. All rights reserved.
 * Functions: 特惠presenter 的回调到View接口
 */
public interface IOnSellPageCallback extends IBaseCallback {

    /**
     * 加载特惠内容成功完成
     * @param result
     */
    void onContentLoadedSuccess(OnSellContent result);

    /**
     * 加载更多成功完成
     */
    void onMoreLoaded(OnSellContent moreResult);

    /**
     * 加载更多失败
     */
    void onMoreLoadedError();

    /**
     * (加载更多内容为空)没有更多内容
     */
    void onMoreLoadedEmpty();
}
