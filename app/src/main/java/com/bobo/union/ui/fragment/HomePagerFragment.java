package com.bobo.union.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
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
import com.bobo.union.presenter.impl.CategoryPagePresenterImpl;
import com.bobo.union.ui.adapter.HomePagerContentAdapter;
import com.bobo.union.ui.adapter.LooperPagerAdapter;
import com.bobo.union.utils.Constants;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.view.ICategoryPagerCallback;

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
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    // 这个页面的presenter
    private CategoryPagePresenterImpl mCategoryPagePresenter;

    // 用于区分fragment的请求id
    private int mMaterialId;

    // 展示内容的循环视图
    @BindView(R.id.home_pager_content_list)
    public RecyclerView mConnectList;

    // 最上面展示轮播图的viewPager
    @BindView(R.id.looper_pager)
    public ViewPager looperPager;

    // 轮播图下recyclerview上中间标题
    @BindView(R.id.home_pager_title)
    public TextView currentCategoryTitleTv;

    // 轮播图上的指示器
    @BindView(R.id.loop_point_container)
    public LinearLayout looperPointContainer;

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
    }

    @Override
    protected void initPresenter() {
        // 实例化presenter
        mCategoryPagePresenter = CategoryPagePresenterImpl.getInstance();

        // 注册回调接口
        mCategoryPagePresenter.registerViewCallback(this);
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
        if (mCategoryPagePresenter != null) {
            mCategoryPagePresenter.getContentByCategoryId(mMaterialId);
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

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        LogUtils.d(this, "looper size --> " + contents.size());
        mLooperPagerAdapter.setData(contents);
        // 当数据来的时候根据数据动态添加指示器上的点
        // 先移除老的数据（点）
        looperPointContainer.removeAllViews();
        GradientDrawable selectDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.
                shape_indicator_point);
        GradientDrawable normalDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.
                shape_indicator_point);
        normalDrawable.setColor(getContext().getColor(R.color.white));

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
                point.setBackground(selectDrawable);
            } else {
                point.setBackground(normalDrawable);
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
        if (mCategoryPagePresenter != null) {
            mCategoryPagePresenter.unregisterViewCallback(this);
        }
    }
}
