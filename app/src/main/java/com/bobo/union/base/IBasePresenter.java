package com.bobo.union.base;

import com.bobo.union.view.ICategoryPagerCallback;

/**
 * Created by Leon on 2020-08-30 Copyright © Leon. All rights reserved.
 * Functions: view层到presenter的接口
 */
public interface IBasePresenter<T> {

    /**
     * 回调（到view层）接口注册
     * @param
     */
    void registerViewCallback(T t);

    /**
     * 回调（到view层）接口解绑
     * @param
     */
    void unregisterViewCallback(T t);
}
