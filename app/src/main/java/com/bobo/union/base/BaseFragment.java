package com.bobo.union.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bobo.union.R;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.SizeUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public abstract class BaseFragment extends Fragment {

    // 默认状态没有状态 NONE
    private State currentState = State.NONE;

    // loading的view
    private View mLoadingView;

    // 成功的view
    private View mSuccessView;

    // 加载错误界面
    private View mErrorView;

    // 加载成功但是内容为空界面
    private View mEmptyView;


    /**
     * 当前各种状态的枚举值 NONE：默认没有状态,LOADING：加载中,SUCCESS：加载成功
     * ,ERROR：加载失败,EMPTY：加载成功但是数据为空
     */
    public enum State {
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    // ButterKnife对象
    private Unbinder mBind;

    // 基础容器用于不同状态显示不同的布局
    private FrameLayout mBaseContainer;

    @OnClick(R.id.network_error_tips)
    public void retry() {
        // 用户点击了 网络出错，请点击重试...
        LogUtils.d(this, "on retry...");

        onRetryClick();
    }

    /**
     * 用户点击了 网络出错，请点击重试...
     * 各子类根据自身业务逻辑决定是否重写实现
     */
    protected void onRetryClick() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = loadRootView(inflater, container);
        mBaseContainer = rootView.findViewById(R.id.base_container);

        loadStatusView(inflater, container);

        // 绑定ButterKnife
        mBind = ButterKnife.bind(this, rootView);

        // 初始化各个ui控件
        initView(rootView);

        // 实例化监听事件
        initListener();

        // 实例化prestener
        initPresenter();

        // 加载数据
        loadData();

        return rootView;
    }

    /**
     * 实例化监听事件,子类根据自己的业务需要决定是否重新该方法
     */
    protected void initListener() {

    }

    /**
     * 子类根据实际业务需求决定是否重写该方法
     * @param inflater
     * @param container
     * @return
     */
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
       return inflater.inflate(R.layout.base_fragment_layout, container, false);
    }

    /**
     * 加载各种状态的view
     * @param inflater
     * @param container
     */
    private void loadStatusView(LayoutInflater inflater, ViewGroup container) {

        // 成功的view
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);

        // loading的view
        mLoadingView = loadLoadingView(inflater, container);
        mBaseContainer.addView(mLoadingView);

        // 加载错误的页面
        mErrorView = loadErrorView(inflater, container);
        mBaseContainer.addView(mErrorView);

        // 加载成功但数据为空的界面
        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);

        // 默认要置为State.NONE 要全部不可见
        setUpState(State.NONE);
    }

    /**
     * 加载错误页面
     * @param inflater
     * @param container
     * @return
     */
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragmnet_error, container, false);
    }

    /**
     * 加载成功但是数据为空
     * @param inflater
     * @param container
     * @return
     */
    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragmnet_empty, container, false);
    }

    /**
     * 子类通过这个方法来切花页面状态即可
     * @param state
     */
    public void setUpState(State state){

        // 根据请求状况赋值修改状态
        this.currentState = state;

        // 如果当前状态是加载成功 让加载成功的界面显示 反之让加载成功的界面隐藏
        mSuccessView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);

        // 如果当前的状态是加载中 显示loading 反之隐藏loading
        mLoadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);

        // 如果当前状态是加载错误 显示错误界面 否则隐藏
        mErrorView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);

        // 如果当前状态是加载成功数据为空 显示空面 否则隐藏
        mEmptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);
    }

    /**
     * 加载loading界面
     * @param inflater
     * @param container
     * @return
     */
    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragmnet_loading, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.loading_container);
        // 2021-2-15获取loading控件和当前设备屏幕的高度再让其居中
        int intw=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int inth=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearLayout.measure(intw, inth);
        int loadingHeight = linearLayout.getMeasuredHeight();
        int bottomMarginValue = (SizeUtils.getScreenHeight(getActivity()) / 2) - (loadingHeight + loadingHeight / 2);
        // LogUtils.d(this,"loadingHeight: " + loadingHeight + " bottomMarginValue: " + bottomMarginValue);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout.getLayoutParams();
        params.bottomMargin = bottomMarginValue;
        return view;

        // 原来的写法
        // return  inflater.inflate(R.layout.fragmnet_loading, container, false);
    }

    /**
     * 交给各个子类实现的实例化UI的方法
     * @param rootView
     */
    protected void initView(View rootView) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // 当本页面销毁时解除绑定否则会内存泄漏
        if (mBind != null) {
            mBind.unbind();
        }

        // 子类根据自身需要 解绑preserter
        release();
    }

    /**
     * 子类根据自身需要 解绑preserter
     */
    protected void release() {
    }

    /**
     * 子类根据需要 实例化自己的presenter
     */
    protected void initPresenter() {

    }

    /**
     * 各个子类根据需要 实现的加载数据的方法
     */
    protected void loadData() {

    }

    /**
     * 实现的加载布局方法
     * @param inflater
     * @param container
     * @return
     */
    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {

        int resId = getRootViewResId();

        return inflater.inflate(resId, container, false);
    }

    /**
     * 由各个子类返回自己的布局文件id即可
     * @return
     */
    protected abstract int getRootViewResId();

}
