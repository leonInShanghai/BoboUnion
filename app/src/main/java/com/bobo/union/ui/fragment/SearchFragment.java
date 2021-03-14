package com.bobo.union.ui.fragment;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.Histories;
import com.bobo.union.model.doman.ILinearItemInfo;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.presenter.ISearchPresenter;
import com.bobo.union.ui.adapter.LinearItemContentAdapter;
import com.bobo.union.ui.adapter.TextWatcherAdapter;
import com.bobo.union.ui.custom.TextFlowLayout;
import com.bobo.union.utils.KeyboardUtil;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.utils.TicketUtil;
import com.bobo.union.utils.ToastUtil;
import com.bobo.union.view.ISearchViewCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions: 搜索碎片
 */
public class SearchFragment extends BaseFragment implements ISearchViewCallback, TextFlowLayout.OnFlowTextItemClickListener {

    @BindView(R.id.search_history_view)
    TextFlowLayout mHistoryView;

    @BindView(R.id.search_recomend_view)
    TextFlowLayout mRecomendView;

    @BindView(R.id.search_history_contationer)
    LinearLayout mHistoryContationer;

    @BindView(R.id.search_recomend_contationer)
    LinearLayout mRecommendContationer;

    @BindView(R.id.search_history_deleate)
    ImageView mHistoryDelete;

    // 搜索结果展示列表
    @BindView(R.id.search_result_list)
    RecyclerView mSearchList;

    // 上拉加载更多控件
    @BindView(R.id.search_result_container)
    TwinklingRefreshLayout mRefreshContainer;

    // 搜索/取消 按钮
    @BindView(R.id.search_btn)
    TextView mSearchBtn;

    @BindView(R.id.search_clean_btn)
    ImageView mCleanInputBtn;

    @BindView(R.id.search_input_box)
    EditText mSearchInputBox;

    private ISearchPresenter mSearchPresenter;

    // 搜索结果适配器
    // private SearchResultAdapter mSearchResultAdapter; 2021-03-06后 和首页公用HomePagerContentAdapter
    private LinearItemContentAdapter mSearchResultAdapter;

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresenterManager.getInstance().getSearchPresenter();
        mSearchPresenter.registerViewCallback(this);

        // 获取搜索推荐词
        mSearchPresenter.getRecommendWords();
        // 获取用户搜索历史
        mSearchPresenter.getHistories();
    }

    @Override
    protected void onRetryClick() {
        // 当由于网络原因加载失败用户又点击了重新加载
        if (mSearchPresenter != null) {
            // 此时开始重新搜索
            mSearchPresenter.research();
        }
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
    protected void initListener() {

        // 历史和推荐词点击事件的处理
        mHistoryView.setOnFlowTextItemClickListener(this);
        mRecomendView.setOnFlowTextItemClickListener(this);

        // 发起搜索
        mSearchBtn.setOnClickListener(view -> {
            if (hasInput(false)) {
                // 如果有内容搜索-发起搜索
                if (mSearchPresenter != null) {
                    // 隐藏键盘
                    KeyboardUtil.hide(getContext(), view);
                    // 发起搜索
                    // mSearchPresenter.doSearch(mSearchInputBox.getText().toString().trim());
                    toSearch(mSearchInputBox.getText().toString().trim());
                }
            } else {
                // 如果没有内容取消-仅仅隐藏键盘
                KeyboardUtil.hide(getContext(), view);
            }
        });

        // 清除输入框里的内容
        mCleanInputBtn.setOnClickListener(view -> {
           // 按钮被点击开始清除输入框里的内容
            mSearchInputBox.setText("");
            // 回到历史记录界面
            switch2HistoryPage();

            // 也要隐藏键盘
            KeyboardUtil.hide(getContext(), view);
        });

        // 监听输入框的内容变化
        mSearchInputBox.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                // 当输入框内容变化时会回调这个方法
                // LogUtils.d(SearchFragment.this, "input text == " + s.toString().trim());
                // 如果内容长度不为0，那么显示删除按钮
                // 否则隐藏删除按钮  Visibility ： 为势必立体😄
                mCleanInputBtn.setVisibility(hasInput(true) ? View.VISIBLE : View.GONE);
                mSearchBtn.setText(hasInput(false) ? "搜索" : "取消");
            }
        });

        mSearchInputBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                LogUtils.d(SearchFragment.this, "actionId --> " + actionId);

                if (actionId == EditorInfo.IME_ACTION_SEARCH && mSearchPresenter != null) {
                    // 判断输入框内用户输入的内容不可为空
                    String keyword = textView.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)) {
                        LogUtils.d(SearchFragment.this, "keyword --> " + keyword);
                        // （根据用户输入的搜索词）搜索
                        // mSearchPresenter.doSearch(keyword);
                        toSearch(keyword);
                    }
                }

                return false;
            }
        });

        mHistoryDelete.setOnClickListener(view -> {
            // 用户点击了删除历史的按钮 - 删除历史记录
            mSearchPresenter.delHistories();
        });

        mRefreshContainer.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                // 用户通过上拉触发加载更多 -  去加载更多内容
                if (mSearchPresenter != null) {
                    mSearchPresenter.loaderMore();
                }
            }
        });

        // 搜索列表内容被点击了
        mSearchResultAdapter.setOnListItemClickListener(new LinearItemContentAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(ILinearItemInfo item) {
                // 跳转到淘口令界面
                TicketUtil.toTicketPage(getContext(), item);
            }
        });
    }

    /**
     * 切换到显示历史记录和推荐界面
     */
    private void switch2HistoryPage() {

        if (mSearchPresenter != null) {
            // 每次这里要获取一下历史
            mSearchPresenter.getHistories();
        }

        if (mHistoryView.getContentSize() > 0) {
            // 显示历史记录
            mHistoryContationer.setVisibility(View.VISIBLE);
        } else {
            mHistoryContationer.setVisibility(View.GONE);
        }
        if (mRecomendView.getContentSize() > 0) {
            // 显示推荐词
            mRecommendContationer.setVisibility(View.VISIBLE);
        } else {
            mRecommendContationer.setVisibility(View.GONE);
        }

        if (mHistoryView.getContentSize() > 0 || mRecomendView.getContentSize() > 0) {
            // 内容要隐藏
            mRefreshContainer.setVisibility(View.GONE);
        }
    }

    private boolean hasInput(boolean containSpace) {
        if (containSpace) {
            // 显示清除按钮 ×
            return mSearchInputBox.getText().toString().length() > 0;
        } else {
            // 搜索/取消 按钮
            return mSearchInputBox.getText().toString().trim().length() > 0;
        }
    }

    @Override
    protected void initView(View rootView) {
        // 设置布局管理器
        mSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        // 设置适配器
        // mSearchResultAdapter = new SearchResultAdapter(); 2021-03-06后 和首页公用HomePagerContentAdapter
        mSearchResultAdapter = new LinearItemContentAdapter();
        mSearchList.setAdapter(mSearchResultAdapter);
        // 让刷新控件只能上拉加载更多不用下拉刷新
        mRefreshContainer.setEnableLoadmore(true);
        mRefreshContainer.setEnableRefresh(false);
        // mRefreshContainer.setEnableOverScroll(true); 这个不用设置默认就是true
        // 设置item的间距（不可以重复设置会变宽（高））
        mSearchList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull
                    RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = SizeUtils.dip2px(getContext(), 1.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 1.5f);
            }

        });
    }

    /**
     * 搜索历史结果返回（在本地不存在失败的情况但是会有为空的情况）
     * @param histories
     */
    @Override
    public void onHistoriesLoaded(Histories histories) {
        LogUtils.d(this, "histories --> " + histories);
        if (histories == null || histories.getHistories().size() == 0) {
            mHistoryContationer.setVisibility(View.GONE);
            // 没有历史也要设置数据源后面有空数据处理
            mHistoryView.setTextList(null);
        } else {
            // 此时就要设置为State.SUCCESS 否则用户看历史数据
            setUpState(State.SUCCESS);
            mHistoryContationer.setVisibility(View.VISIBLE);
            mHistoryView.setTextList(histories.getHistories());
        }
    }

    /**
     * 历史记录删除完成
     */
    @Override
    public void onHistoriesDeleted() {
        // 更新历史记录
        if (mSearchPresenter != null) {
            mSearchPresenter.getHistories();
        }
    }

    /**
     * 搜索结果 成功返回
     * @param result
     */
    @Override
    public void onSearchSuccess(SearchResult result) {
        // LogUtils.d(this, "result -- > " + result);

        // 给适配器设置数据
        try {
            setUpState(State.SUCCESS);
            // 搜索结果返回要隐藏掉历史记录和推荐
            mRecommendContationer.setVisibility(View.GONE);
            mHistoryContationer.setVisibility(View.GONE);
            // 显示搜索结果
            mRefreshContainer.setVisibility(View.VISIBLE);
            // 再添加新数据
            List<? extends ILinearItemInfo> mapData = result.getData().getTbk_dg_material_optional_response().getResult_list()
                    .getMap_data();
            mSearchResultAdapter.setData(mapData);
        } catch (NullPointerException e) {
            ToastUtil.showToast("服务器数据解析异常!");
            // 切换到内容为空
            setUpState(State.EMPTY);
        }
    }

    /**
     * 获取更多的搜索结果成功返回
     */
    @Override
    public void onMoreLoaded(SearchResult result) {
        // 加载更多结果返回成功
        try {
            List<? extends ILinearItemInfo> moreData = result.getData().getTbk_dg_material_optional_response()
                    .getResult_list().getMap_data();
            // 拿到结果添加到适配器的尾部
            mSearchResultAdapter.addData(moreData);
        } catch (NullPointerException e) {
            ToastUtil.showToast("服务器数据解析异常!");
        } finally {
            mRefreshContainer.finishLoadmore();
        }
    }

    /**
     * 加载更多失败
     */
    @Override
    public void onMoreLoadedError() {
        ToastUtil.showToast("加载失败请稍后重试");
        mRefreshContainer.finishLoadmore();
    }

    /**
     * 加载更多返回空
     */
    @Override
    public void onMoreLoadedEmpty() {
        ToastUtil.showToast("没有更多数据");
        mRefreshContainer.finishLoadmore();
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
            // 此时就要设置为State.SUCCESS 否则用户看不到推荐词获取结果
            setUpState(State.SUCCESS);

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
        setUpState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    /**
     * 历史记录或推荐词被点了
     * @param text
     */
    @Override
    public void onFlowItemClick(String text) {
        toSearch(text);
    }

    /**
     * 发起搜索统一方法
     * @param text
     */
    private void toSearch(String text) {
        // 发起搜索
        if (mSearchPresenter != null && !TextUtils.isEmpty(text)) {
            // 也要隐藏键盘
            KeyboardUtil.hide(getContext(), mSearchInputBox);
            mSearchInputBox.setText(text);
            // 重新设置内容后要设置光标的位置否则光标会在最前面不好看
            mSearchInputBox.setSelection( text.length());
            // 设置显示光标
            mSearchInputBox.requestFocus();
            mSearchPresenter.doSearch(text);
            // 每次新的搜索都应该从第一个开始
            mSearchList.scrollToPosition(0);
        }
    }
}
