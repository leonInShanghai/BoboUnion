package com.bobo.union.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.presenter.ISearchPresenter;
import com.bobo.union.ui.custom.TextFlowLayout;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.view.ISearchViewCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions: 搜索碎片
 */
public class SearchFragment extends BaseFragment implements ISearchViewCallback {

    @BindView(R.id.search_history_view)
    TextFlowLayout mHistoryView;

    @BindView(R.id.search_recomend_view)
    TextFlowLayout mRecomendView;

    @BindView(R.id.search_history_contationer)
    LinearLayout mHistoryContationer;

    @BindView(R.id.search_recomend_contationer)
    LinearLayout mRecommendContationer;

    private ISearchPresenter mSearchPresenter;

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresenterManager.getInstance().getSearchPresenter();
        mSearchPresenter.registerViewCallback(this);

        // 获取搜索推荐词
        mSearchPresenter.getRecommendWords();
        // （根据用户输入的搜索词）搜索
        mSearchPresenter.doSearch("键盘");
        // 获取用户搜索历史
        mSearchPresenter.getHistories();
    }

    @Override
    protected void release() {
        // onDestroyView 时解除绑定 避免内存泄漏
        if (mSearchPresenter != null) {
            mSearchPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search_layout, container, false);
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }

    /**
     * 搜索历史结果返回（在本地不存在失败的情况但是会有为空的情况）
     * @param histories
     */
    @Override
    public void onHistoriesLoaded(List<String> histories) {
        LogUtils.d(this, "histories --> " + histories);
        if (histories == null || histories.size() == 0) {
            mHistoryContationer.setVisibility(View.GONE);
        } else {
            mHistoryContationer.setVisibility(View.VISIBLE);
            mHistoryView.setTextList(histories);
        }
    }

    @Override
    public void onHistoriesDeleted() {

    }

    /**
     * 搜索结果 成功返回
     * @param result
     */
    @Override
    public void onSearchSuccess(SearchResult result) {
        LogUtils.e(this, "result -- > " + result);
    }

    @Override
    public void onMoreLoaded(SearchResult result) {

    }

    @Override
    public void onMoreLoadedError() {

    }

    @Override
    public void onMoreLoadedEmpty() {

    }

    /**
     * 推荐词获取结果
     * @param recommendWords
     */
    @Override
    public void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommendWords) {
        LogUtils.d(this, "recommendWords size :" + recommendWords.size());
        if (recommendWords == null || recommendWords.size() == 0) {
            mRecommendContationer.setVisibility(View.GONE);
        } else {
            mRecommendContationer.setVisibility(View.VISIBLE);
            List<String> recommendKeywords = new ArrayList<>();
            for (SearchRecommend.DataBean item : recommendWords) {
                recommendKeywords.add(item.getKeyword());
            }
            mRecomendView.setTextList(recommendKeywords);
        }

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
