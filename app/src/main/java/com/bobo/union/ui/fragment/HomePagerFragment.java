package com.bobo.union.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bobo.union.base.BaseFragment;

import com.bobo.union.R;
import com.bobo.union.model.doman.Categories;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.presenter.ICategoryPagerPresenter;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.presenter.impl.CategoryPagePresenterImpl;
import com.bobo.union.presenter.impl.TicketPressenterImpl;
import com.bobo.union.ui.activity.TicketActivity;
import com.bobo.union.ui.adapter.HomePagerContentAdapter;
import com.bobo.union.ui.adapter.LooperPagerAdapter;
import com.bobo.union.ui.custom.AutoLoopViewPager;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.TicketUtil;
import com.lcodecore.tkrefreshlayout.views.TbNestedScrollView;
import com.bobo.union.utils.Constants;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.utils.ToastUtil;
import com.bobo.union.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Leon on 2020-08-29 Copyright © Leon. All rights reserved.
 * Functions: 首页下的各个子页面（这一个就是那多个页面）
 *
 * what can you see
 * look there is an ant, it is running
 * look there is a swan, it is swimming
 * look there is a sheep, it is sleeping
 * look there is a frog, it is jumping
 * look there is a kite, it is flying
 * look there is a prince, he is singing
 * look there is a girl, she is dancing
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback, HomePagerContentAdapter
        .OnListItemClickListener, LooperPagerAdapter.OnLooperPagerItemClickListener {

    // 这个页面的presenter
    private ICategoryPagerPresenter mPagePresenter;

    // 用于区分fragment的请求id
    private int mMaterialId;

    // 展示内容的循环视图
    @BindView(R.id.home_pager_content_list)
    public RecyclerView mConnectList;

    // 最上面展示轮播图的viewPager
    @BindView(R.id.looper_pager)
    public AutoLoopViewPager looperPager;

    // 轮播图下recyclerview上中间标题
    @BindView(R.id.home_pager_title)
    public TextView currentCategoryTitleTv;

    // 轮播图上的指示器
    @BindView(R.id.loop_point_container)
    public LinearLayout looperPointContainer;

    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout twinklingRefreshLayout;

    @BindView(R.id.home_pager_header_container)
    public LinearLayout homeHeaderContainer;

    @BindView(R.id.home_pager_nested_scroller)
    public TbNestedScrollView homePagerNestedView;

    // 展示内容的循环视图的适配器
    private HomePagerContentAdapter mContentAdapter;

    // 展示轮播图的viewPager的适配器
    private LooperPagerAdapter mLooperPagerAdapter;

    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID, category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 轮播图开始自动滚动
        looperPager.startLoop();
        LogUtils.d(this, "onResume...");
    }

    @Override
    public void onPause() {
        super.onPause();
        // 暂停轮播图自动滚动
        looperPager.stopLoop();
        LogUtils.d(this, "onPause...");
    }

    @Override
    protected void initListener() {

        // 设置recycler view item点击事件监听
        mContentAdapter.setOnListItemClickListener(this);

        // 设置轮播图的点击事件
        mLooperPagerAdapter.setOnLooperPagerItemClickListener(this);

        twinklingRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              int measuredHeight = twinklingRefreshLayout.getMeasuredHeight();
              if (measuredHeight > 0 ) {
                  LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mConnectList
                          .getLayoutParams();
                  layoutParams.height = measuredHeight;
                  mConnectList.setLayoutParams(layoutParams);
                  // 移除监听避免重复调用
                  twinklingRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              }
              LogUtils.d(HomePagerFragment.this, "measuredHeight --> " + measuredHeight );
            }
        });

        homeHeaderContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int headerHeight = homeHeaderContainer.getMeasuredHeight();
                LogUtils.d(HomePagerFragment.this, "headerHeight --> " + headerHeight );
                if (headerHeight > 0) {
                    homePagerNestedView.setHeaderHeight(headerHeight);
                    homeHeaderContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                LogUtils.d(HomePagerFragment.this, "homeHeaderContainer measuredHeight --> "
                        + headerHeight );
            }
        });

        // 特惠的点击事件(测试用)
        currentCategoryTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int measuredHeight = mConnectList.getMeasuredHeight();
                int height = mConnectList.getHeight();
                LogUtils.d(HomePagerFragment.this, "measuredHeight --> " + measuredHeight + " height --> " + height);
                // 优化前recyclerView的高度：measuredHeight --> 12598
            }
        });

        looperPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 0不可以做除数，避免异常
                if (mLooperPagerAdapter.getDataSize() == 0) {
                    return;
                }
                int tragetPosition = position % mLooperPagerAdapter.getDataSize();

                // 切换轮播图的指示器
                updateLooperIndictor(tragetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 下拉加载更多事件监听
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                LogUtils.d(HomePagerFragment.this, "触发了Load More...");
                // 去加载更多
                if (mPagePresenter != null) {
                    mPagePresenter.loaderMore(mMaterialId);
                }
            }
        });
    }

    /**
     * 切换轮播图的指示器
     * @param tragetPosition
     */
    private void updateLooperIndictor(int tragetPosition) {
        for (int i = 0; i < looperPointContainer.getChildCount(); i++) {
           View point = looperPointContainer.getChildAt(i);
            if (i == tragetPosition) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
    }

    @Override
    protected void initView(View rootView) {
        // 循环视图上面已经用butterknife实例化过了
        // 循环视图设置布局管理器
        mConnectList.setLayoutManager(new LinearLayoutManager(getContext()));
        mConnectList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull
                    RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 8;
                outRect.bottom = 8;
            }

        });

        // 创建适配器
        mContentAdapter = new HomePagerContentAdapter();

        // 设置适配器
        mConnectList.setAdapter(mContentAdapter);

        // 创建轮播图的适配器
        mLooperPagerAdapter = new LooperPagerAdapter();

        // 设置轮播图的适配器
        looperPager.setAdapter(mLooperPagerAdapter);

        // 设置刷新相关属性
        twinklingRefreshLayout.setEnableRefresh(false); // 不要下拉刷新
        twinklingRefreshLayout.setEnableLoadmore(true); // 要上拉加载更多
        // 设置和app主题一样颜色的BallPulseView（上拉加载更多自定义view）后面直接修改第三方的代码此段代码注释不影响使用
//        BallPulseView mBallPulseView = new BallPulseView(getContext());
//        mBallPulseView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
//        twinklingRefreshLayout.setBottomView(mBallPulseView);
    }

    @Override
    protected void initPresenter() {
        // 实例化presenter
        mPagePresenter = PresenterManager.getInstance().getCategoryPagePresenter();

        // 注册回调接口
        mPagePresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {

        // 先获取到bundle 再获取到对应的参数
        Bundle arguments = getArguments();

        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);

        LogUtils.d(this, "title --> " + title);
        LogUtils.d(this, "materialId --> " + mMaterialId);

        // 加载数据
        if (mPagePresenter != null) {
            mPagePresenter.getContentByCategoryId(mMaterialId);
        }

        if (currentCategoryTitleTv != null) {
            currentCategoryTitleTv.setText(title);
        }

    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {

        // 正常数据列表加载成功
        mContentAdapter.setData(contents);
        setUpState(State.SUCCESS);

    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onLoading() {

        // 加载中
        setUpState(State.LOADING);
    }

    @Override
    public void onError() {

        // 网络错误
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        
        setUpState(State.EMPTY);
    }

    /**
     * 上拉加载更多失败
     */
    @Override
    public void onLoadMoreError() {
        ToastUtil.showToast("网络异常，请稍后重试");
        if (twinklingRefreshLayout != null) {
            // (上拉加载更多控件)结束上拉加载更多
            twinklingRefreshLayout.finishLoadmore();
        }
    }

    /**
     * 上拉加载更多成功但是数据为空
     */
    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.showToast("没有更多的商品");
        if (twinklingRefreshLayout != null) {
            // (上拉加载更多控件)结束上拉加载更多
            twinklingRefreshLayout.finishLoadmore();
        }
    }

    /**
     * 上拉加载更多成功并且有数据
     * @param contents
     */
    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {
        // 添加到适配器数据的底部
        mContentAdapter.addData(contents);
        if (twinklingRefreshLayout != null) {
            // (上拉加载更多控件)结束上拉加载更多
            twinklingRefreshLayout.finishLoadmore();
        }
        // Toast.makeText(getContext(), "加载了" + contents.size() + "条数据",
        //         Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        LogUtils.d(this, "looper size --> " + contents.size());
        mLooperPagerAdapter.setData(contents);

        // 轮播图第一次显示设置到中间点 这样可以往左划动
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetConterPosition = (Integer.MAX_VALUE / 2) - dx;
        looperPager.setCurrentItem(targetConterPosition);
        // LogUtils.d(this, "url 0--> " + contents.get(0).getPict_url());

        // 当数据来的时候根据数据动态添加指示器上的点
        // 先移除老的数据（点）
        looperPointContainer.removeAllViews();
        // GradientDrawable selectDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.
        //         shape_indicator_point_selected);
        // GradientDrawable normalDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.
        //         shape_indicator_point_normal);

        // 再添加新的点
        for (int i = 0; i < contents.size(); i++) {
            // 创建点
            View point = new View(getContext());
            int size = SizeUtils.dip2px(getContext(),8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(), 5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(), 5);
            point.setLayoutParams(layoutParams);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }

            // 添加点
            looperPointContainer.addView(point);
        }
    }

    /**
     * 当页面onDestory的时候解绑presenter
     */
    @Override
    protected void release() {
        if (mPagePresenter != null) {
            mPagePresenter.unregisterViewCallback(this);
        }
    }

    /**
     * recyclerView item点击事件的监听
     * @param item
     */
    @Override
    public void onItemClick(HomePagerContent.DataBean item) {
        // 列表内容被点击了
        LogUtils.d(this, "item click --> " + item.getTitle());
        handleItemClick(item);
    }

    // 跳转到口令页面
    private void handleItemClick(HomePagerContent.DataBean item) {
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

    /**
     * 轮播图（来自viewPager的适配器）点击事件的监听
     * @param item
     */
    @Override
    public void onLooperItemClick(HomePagerContent.DataBean item) {
        // 列表内容被点击了
        LogUtils.d(this, "Looper item click --> " + item.getTitle());
        handleItemClick(item);
    }
}
