package com.bobo.union.presenter.impl;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.presenter.ISearchPresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.view.ISearchViewCallback;

import java.net.HttpURLConnection;

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

    /**
     * 搜索返回结果分页当前页码
     */
    private int mCurrentPage = DEFFAULT_PAGE;

    public SearchPresenterImpl() {
        RetrofitManage instance = RetrofitManage.getInstance();
        Retrofit retrofit = instance.getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getHistories() {

    }

    @Override
    public void delHistories() {

    }

    @Override
    public void doSearch(String keyword) {

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

    @Override
    public void research() {

    }

    @Override
    public void loaderMore() {

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
                        mSearchViewCallback.onRecommendWordsLoaded(response.body().getData());
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
