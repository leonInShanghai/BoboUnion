package com.bobo.union.base;

/**
 * Created by Leon on 2020-08-30 Copyright © Leon. All rights reserved.
 * Functions: 回调到view层的接口(父类)
 */
public interface IBaseCallback {


    /**
     * 请求失败（错误）
     */
    void onError();

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 请求成功但是数据为空
     */
    void onEmpty();

}
