package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.Categories;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 首页presenter 的回调接口
 */
public interface IHomeCallback extends IBaseCallback {

    /**
     * 首页商品分类 数据请求成功回调
     */
    void onCategoriesLoad(Categories categories);

    // 下面的接口已抽取到父类
    //    /**
    //     * 请求失败（错误）
    //     */
    //    void onNetworkError();
    //
    //    /**
    //     * 加载中
    //     */
    //    void onLoading();
    //
    //    /**
    //     * 请求成功但是数据为空
    //     */
    //    void onEmpty();

}
