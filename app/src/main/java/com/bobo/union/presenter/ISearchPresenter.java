package com.bobo.union.presenter;

import com.bobo.union.base.IBasePresenter;
import com.bobo.union.view.ISearchViewCallback;

/**
 * Created by 公众号：IT波 on 2021/2/18 Copyright © Leon. All rights reserved.
 * Functions: 搜索页面P层接口
 */
public interface ISearchPresenter extends IBasePresenter<ISearchViewCallback> {

    /**
     * 获取搜索历史
     */
    void getHistories();

    /**
     * 删除搜索历史
     */
    void delHistories();

    /**
     * 搜索
     * @param keyword
     */
    void doSearch(String keyword);

    /**
     * 重新搜索
     */
    void research();

    /**
     * 获取更多的搜索结果
     */
    void loaderMore();

    /**
     * 获取推荐词
     */
    void getRecommendWords();
}
