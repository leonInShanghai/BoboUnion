package com.bobo.union.presenter.impl;

import androidx.core.app.NavUtils;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.Histories;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.presenter.ISearchPresenter;
import com.bobo.union.utils.JsonCacheUtil;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.view.ISearchViewCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 公众号：IT波 on 2021/2/18 Copyright © Leon. All rights reserved.
 * Functions: 搜索页Presenter
 */
public class SearchPresenterImpl implements ISearchPresenter {

    private final Api mApi;
    private ISearchViewCallback mSearchViewCallback = null;

    // 请求分页默认从第0页开始
    public static final int DEFFAULT_PAGE = 0;
    
    // 本地持久化保存的key
    public static final String KEY_HISTORY = "key_history";

    public static final int DEFAULT_HISTORIES_SIZE = 10;
    private int mHistoriesMaxSize = DEFAULT_HISTORIES_SIZE;

    /**
     * 搜索返回结果分页当前页码
     */
    private int mCurrentPage = DEFFAULT_PAGE;

    // 用作reSearch
    private String mCurrentKeyWord = null;
    private final JsonCacheUtil mJsonCacheUtil;

    public SearchPresenterImpl() {
        RetrofitManage instance = RetrofitManage.getInstance();
        Retrofit retrofit = instance.getRetrofit();
        mApi = retrofit.create(Api.class);
        mJsonCacheUtil = JsonCacheUtil.getInstance();
    }

    /**
     * 获取搜索历史
     */
    @Override
    public void getHistories() {
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORY, Histories.class);
        if (mSearchViewCallback != null) {
            // 回调到UI
            mSearchViewCallback.onHistoriesLoaded(histories);
        }
    }

    /**
     * 删除搜索历史
     */
    @Override
    public void delHistories() {
        mJsonCacheUtil.delCache(KEY_HISTORY);
        // 删除后回调到UI
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onHistoriesDeleted();
        }
    }

    /**
     * 添加历史记录
     * @param history
     */
    private void saveHistories(String history) {
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORY, Histories.class);
        List<String> historiesList = null;
        // 如果已经有了，就干掉，然后再添加 ①去重
        if (histories != null && histories.getHistories() != null) {
            historiesList = histories.getHistories();
            // 如果已经包含了
            if (historiesList.contains(history)) {
                // 就删除
                historiesList.remove(history);
            }
        }

        // 去重完成 ②添加
        // 处理为空的情况
        if (historiesList == null) {
            historiesList = new ArrayList<>();
        }

        if (histories == null) {
            histories = new Histories();
        }
        histories.setHistories(historiesList);

        // 对个数进行限制
        if (historiesList.size() > mHistoriesMaxSize) {
            historiesList = historiesList.subList(0, mHistoriesMaxSize);
        }

        // 添加记录
        historiesList.add(history);

        // 保存记录
        mJsonCacheUtil.saveCache(KEY_HISTORY, histories);
    }

    @Override
    public void doSearch(String keyword) {

        if (mCurrentKeyWord  == null || !mCurrentKeyWord.equals(keyword)) {
            // 保存搜索记录
            this.saveHistories(keyword);
            // mCurrentKeyWord赋值用作从新搜索
            mCurrentKeyWord = keyword;
            // FIXME:用户重新搜索的关键字此时要从第0页开始
            mCurrentPage = DEFFAULT_PAGE;
        }

        // 更新UI状态-loading...
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onLoading();
        }

        Call<SearchResult> task = mApi.doSourch(mCurrentPage, keyword);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, "do search result code --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    // doSearch请求成功处理
                    handleSearchResult(response.body());
                } else {
                    // doSearch请求失败处理
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                LogUtils.d(SearchPresenterImpl.this, "do search onFailure --> " + t.toString());
                // doSearch请求失败处理
                onError();
            }
        });
    }

    // doSearch请求失败处理
    private void onError() {
        if (mSearchViewCallback != null) {
            mSearchViewCallback.onError();
        }
    }

    // doSearch请求成功处理
    private void handleSearchResult(SearchResult result) {
        if (mSearchViewCallback != null) {
            if (isResultEmpty(result)) {
                // doSearch 返回结果为空
                mSearchViewCallback.onEmpty();
            } else {
                // doSearch 返回成功
                mSearchViewCallback.onSearchSuccess(result);
            }
        }
    }

    // 判断返回结果是否为空
    private boolean isResultEmpty(SearchResult result) {
        if (result == null || result.getData() == null || result.getData().getTbk_dg_material_optional_response()
                == null || result.getData().getTbk_dg_material_optional_response().getResult_list() == null ||
                result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data() == null ||
                result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data().size() == 0) {
            // 返回结果为空
            LogUtils.e(SearchPresenterImpl.this, "doSearch 返回结果为空!!!");
            return true;
        } else {
            // 返回结果不为空
            return false;
        }
    }

    /**
     * 当加载失败 用户点击了重新搜索
     * 重新搜索
     */
    @Override
    public void research() {
        // 没有关键词
        if (mCurrentKeyWord == null) {
            if (mSearchViewCallback != null) {
                // 没有关键词-就没有搜索结果
                mSearchViewCallback.onEmpty();
            }
        } else {
            // (有搜索词)可以重新搜索
            this.doSearch(mCurrentKeyWord);
        }
    }

    /**
     * (用户上拉)获取更多的搜索结果
     */
    @Override
    public void loaderMore() {
        // 分页增加
        mCurrentPage++;

        // 进行搜索 （如果）没有关键词
        if (mCurrentKeyWord == null) {
            if (mSearchViewCallback != null) {
                // 没有关键词-就没有搜索结果
                mSearchViewCallback.onEmpty();
                // FIXME:要减回来
                mCurrentPage--;
            }
        } else {
            // （分页增加后）开始搜索
            doSearchMore();
        }
    }

    /**
     * （分页增加后）开始搜索  (用户上拉)获取更多的搜索结果
     */
    private void doSearchMore() {
        Call<SearchResult> task = mApi.doSourch(mCurrentPage, mCurrentKeyWord);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, "do search result code --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    // doSearch加载更多请求成功处理
                    handleMoreSearchResult(response.body());
                } else {
                    // doSearch加载更多请求失败处理
                    onLoaderMoreError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                LogUtils.d(SearchPresenterImpl.this, "doSearch加载更多 onFailure --> " + t.toString());
                // doSearch加载更多请求失败处理
                onLoaderMoreError();
            }
        });
    }

    /**
     * doSearch加载更多请求成功处理
     * @param result
     */
    private void handleMoreSearchResult(SearchResult result) {
        if (mSearchViewCallback != null) {
            if (isResultEmpty(result)) {
                // 请求成功但是数据为空 页码要减回去
                mCurrentPage--;
                mSearchViewCallback.onMoreLoadedEmpty();
            } else {
                // 请求成功并且有数据通知UI刷新
                mSearchViewCallback.onMoreLoaded(result);
            }
        }
    }

    /**
     * doSearch加载更多失败
     */
    private void onLoaderMoreError() {
        // 相应的分页要减回去
        mCurrentPage--;
        if (mSearchViewCallback != null) {
            // 通知UIdoSearch加载更多失败
            mSearchViewCallback.onMoreLoadedError();
        }
    }

    /**
     * 获取推荐词
     */
    @Override
    public void getRecommendWords() {
        Call<SearchRecommend> task = mApi.getRecommendWords();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                int code = response.code();
                LogUtils.d(SearchPresenterImpl.this, " getRecommendWords result code --> " + code);
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    // 处理结果(注意这里不存在失败回调，失败UI那边就啥也不用做)
                    if (mSearchViewCallback != null) {
                        if (response.body() == null) {
                            mSearchViewCallback.onRecommendWordsLoaded(null);
                        } else {
                            mSearchViewCallback.onRecommendWordsLoaded(response.body().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {
                LogUtils.e(SearchPresenterImpl.this, " getRecommendWords onFailure --> " + t.toString());
            }
        });
    }

    @Override
    public void registerViewCallback(ISearchViewCallback callback) {
        mSearchViewCallback = callback;
    }

    @Override
    public void unregisterViewCallback(ISearchViewCallback iSearchViewCallback) {
        mSearchViewCallback = null;
    }
}
