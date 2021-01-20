package com.bobo.union.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.Categories;
import com.bobo.union.presenter.IHomePresenter;
import com.bobo.union.presenter.impl.HomePresenterImpl;
import com.bobo.union.ui.adapter.HomePagerAdapter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;


/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {

    // 顶部导航栏最下面的指示器
    @BindView(R.id.home_incator)
    TabLayout mTabLayout;

    // 本页面的presenter
    private IHomePresenter mHomePresenter;

    // 顶部导航栏最下面的指示器切换时的viewpager
    @BindView(R.id.home_pager)
    ViewPager homePager;

    // 指示器切换时的viewpager 的适配器
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.d(this, "on create view...");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtils.d(this, "on Destroy view...");
        super.onDestroy();
    }

    @Override
    protected void initView(View rootView) {
        // 实例化各个ui
        mTabLayout.setupWithViewPager(homePager);

        // 给viewpager设置适配器 由于MainActivity用了SupportFragmentManager
        // 这里只能使用ChildFragmentManager
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());

        // 给viewpager设置适配器
        homePager.setAdapter(mHomePagerAdapter);

    }

    @Override
    protected void initPresenter() {
        // 实例化自己的prestenter
        mHomePresenter = PresenterManager.getInstance().getHomePresenter();

        // 注册回调接口
        mHomePresenter.registerViewCallback(this);
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout, container, false);
    }

    @Override
    protected void loadData() {
        // 加载数据
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoad(Categories categories) {

        // 设置显示成功页面
        setUpState(State.SUCCESS);

        LogUtils.d(this, "onCategoriesLoad..");

        // 加载成功数据会从这里回来
        if (mHomePagerAdapter != null) {
            // 设置一次加载所有页面的数据 不设置则只预加载多一个页面
            // homePager.setOffscreenPageLimit(categories.getData().size());
            mHomePagerAdapter.setCategories(categories);
        }
    }

    @Override
    public void onError() {
        // 请求失败（错误）
        setUpState(State.ERROR);
    }

    @Override
    public void onLoading() {
        // 加载中
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        // 请求成功但数据为空
        setUpState(State.EMPTY);
    }

    @Override
    protected void release() {
        // 解绑presenter否则会内存泄漏
        if (mHomePresenter != null) {
            mHomePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void onRetryClick() {
        // 用户点击了 网络出错，请点击重试...
        // 从新加载分类
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }
}
