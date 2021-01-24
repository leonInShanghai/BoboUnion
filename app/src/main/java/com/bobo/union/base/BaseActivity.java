package com.bobo.union.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 公众号：IT波 on 2021/1/16 Copyright © Leon. All rights reserved.
 * Functions:
 */
public abstract class BaseActivity extends AppCompatActivity {

    // ButterKnife
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        // 使用butterKnie实例化控件
        mBind = ButterKnife.bind(this);

        // 实例化各个控件
        initView();

        // 各个控件的点击触摸等事件的监听
        initEvent();

        // 实例化presenter
        initPresenter();
    }

    protected abstract void initPresenter();

    /**
     * 子类必须实现 实例化各个控件
     */
    protected abstract void initView();

    /**
     * 子类必须实现 加载xml布局文件
     */
    protected abstract int getLayoutResId();

    /**
     * 各个控件的点击触摸等事件的监听
     * 子类根据自己业务需要决定是否要重写本方法
     */
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {

        // 当页面销毁解绑ButterKnife避免内存泄漏
        if (mBind != null) {
            mBind.unbind();
        }

        this.release();

        super.onDestroy();
    }

    /**
     * 释放（解绑）Presenter
     * 子类根据自己业务需要决定是否要重写本方法
     */
    protected void release() {

    }
}
