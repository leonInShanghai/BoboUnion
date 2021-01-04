package com.bobo.union.presenter;


import com.bobo.union.base.IBasePresenter;
import com.bobo.union.view.IHomeCallback;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 首页的presenter
 */
public interface IHomePresenter extends IBasePresenter<IHomeCallback> {

    /**
     * 获取商品分类
     */
    void getCategories();


    // 注册和解绑方法已经抽取到父类
    // void registerViewCallback(IHomeCallback callback);
    // void unRegisterViewCallback(IHomeCallback callback);

}
