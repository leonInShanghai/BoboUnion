package com.bobo.union.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.OnSellContent;
import com.bobo.union.presenter.IOnSellPagePresenter;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.ui.activity.TicketActivity;
import com.bobo.union.ui.adapter.OnSellPageContentAdapter;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.utils.TicketUtil;
import com.bobo.union.utils.ToastUtil;
import com.bobo.union.view.IOnSellPageCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vondear.rxui.view.wavesidebar.adapter.OnLoadMoreListener;

import butterknife.BindView;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions: 特惠
 */
public class OnSellFragment extends BaseFragment implements IOnSellPageCallback, OnSellPageContentAdapter.
        OnSellPageItemClickListener {

    private IOnSellPagePresenter mOnSellPagePresenter;
    public static final int DEFAULT_SPAN_COUNT = 2;

    @BindView(R.id.on_sell_content_list)
    public RecyclerView mContentRv;

    @BindView(R.id.fragment_bar_title_tv)
    public TextView barTitleTv;

    @BindView(R.id.on_sell_refresh_layout)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;

    private OnSellPageContentAdapter mOnSellPageContentAdapter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mOnSellPagePresenter = PresenterManager.getInstance().getOnSellPagePresenter();
        mOnSellPagePresenter.registerViewCallback(this);

        // 网络请求获取当前页面数据
        mOnSellPagePresenter.getOnSellContent();
    }

    /**
     * 当加载失败时用户点击了“网络出错请点击重试”
     */
    @Override
    protected void onRetryClick() {
        // 重试
        if (mOnSellPagePresenter != null) {
            // 重新加载内容
            mOnSellPagePresenter.reload();
        }
    }

    @Override
    protected void release() {
        super.release();
        // 当本页面销毁后解绑 否则会造成内存泄漏
        if (mOnSellPagePresenter != null) {
            mOnSellPagePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_on_sell;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                // 去加载更多的内容
                if (mOnSellPagePresenter != null) {
                    mOnSellPagePresenter.loaderMore();
                }
            }
        });

        // 监听适配器的点击事件
        mOnSellPageContentAdapter.setOnSellPageItemClickListener(this);
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_with_bar_layout, container, false);
    }

    @Override
    protected void initView(View rootView) {
        mOnSellPageContentAdapter = new OnSellPageContentAdapter();
        // 设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mContentRv.setLayoutManager(gridLayoutManager);
        // 设置适配器
        mContentRv.setAdapter(mOnSellPageContentAdapter);
        // RecyclerView设置间距
        mContentRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.left = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.right = SizeUtils.dip2px(getContext(), 2.5f);
            }
        });

        // 开启上拉加载更多
        mTwinklingRefreshLayout.setEnableLoadmore(true);
        // 不要下拉刷新
        mTwinklingRefreshLayout.setEnableRefresh(false);

        // 设置标题
        barTitleTv.setText(getResources().getText(R.string.text_on_sell_title));
    }

    /**
     * 数据请求成功
     * @param result
     */
    @Override
    public void onContentLoadedSuccess(OnSellContent result) {
        // 数据回来了显示正常页面
        setUpState(State.SUCCESS);
        // 数据回来了更新UI渲染到界面
        mOnSellPageContentAdapter.setData(result);
    }

    /**
     * 用户上拉加载更多成功
     * @param moreResult
     */
    @Override
    public void onMoreLoaded(OnSellContent moreResult) {
        // （无论结果如何）先结束刷新
        mTwinklingRefreshLayout.finishLoadmore();
        // 添加内容到适配器里
        mOnSellPageContentAdapter.onMoreLoaded(moreResult);
    }

    @Override
    public void onMoreLoadedError() {
        // （无论结果如何）先结束刷新
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtil.showToast("加载更多失败...");
    }

    @Override
    public void onMoreLoadedEmpty() {
        // （无论结果如何）先结束刷新
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtil.showToast("没有更多的内容...");
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
     * 特惠列表的某个item被点击了
     * @param item
     */
    @Override
    public void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean
                                            item) {
        // // 跳转到淘口令界面
        // String title = item.getTitle();
        // String url = item.getCoupon_click_url();
        // if (TextUtils.isEmpty(url)) {
        //     // 详情的url
        //     url = item.getClick_url();
        // }
        // String cover = item.getPict_url();
        // // 拿到TicketPressenter去加载数据
        // ITikcetPresenter ticketPressenter = PresenterManager.getInstance().getTicketPressenter();
        // ticketPressenter.getTicket(title, url, cover);
        // startActivity(new Intent(getContext(), TicketActivity.class));

        TicketUtil.toTicketPage(getContext(), item);
    }
}
