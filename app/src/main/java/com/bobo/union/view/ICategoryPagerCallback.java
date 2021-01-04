package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.HomePagerContent;

import java.util.List;

/**
 * Created by Leon on 2020-08-29 Copyright © Leon. All rights reserved.
 * Functions: 首页下个各个子页面（其实就那一个）回调到ui层的接口
 */
public interface ICategoryPagerCallback extends IBaseCallback {

    /**
     * 数据加载回来
     * @param contents
     */
    void onContentLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 让ui层返回自己的请求id 好区分是哪个子页请求的
     * @return
     */
    int getCategoryId();


    /**
     * 加载更多 失败（错误）
     */
    void onLoadMoreError();

    /**
     * 加载更多 成功但是没有数据（没有更多）
     */
    void onLoadMoreEmpty();

    /**
     * 加载更多成功
     */
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 轮播图内容加载到了
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);


}
