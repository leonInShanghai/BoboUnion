package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/2/18 Copyright © Leon. All rights reserved.
 * Functions: 搜索页面View层的回调接口
 */
public interface ISearchViewCallback extends IBaseCallback {

    /**
     * 搜索历史结果返回（在本地不存在失败的情况）
     * @param histories
     */
    void onHistoriesLoaded(List<String> histories);

    /**
     * 历史记录删除完成
     */
    void onHistoriesDeleted();

    /**
     * 搜索结果 成功
     * @param result
     */
    void onSearchSuccess(SearchResult result);

    /**
     * 加载到了更多内容
     * @param result
     */
    void onMoreLoaded(SearchResult result);

    /**
     * 加载更多失败
     */
    void onMoreLoadedError();

    /**
     * 加载更多返回空
     */
    void onMoreLoadedEmpty();

    /**
     * 推荐词获取结果
     * @param recommendWords
     */
    void onRecommendWordsLoaded(List<SearchRecommend.DataBean > recommendWords);
}
