package com.bobo.union.utils;

import android.security.NetworkSecurityPolicy;

import com.bobo.union.presenter.ICategoryPagerPresenter;
import com.bobo.union.presenter.IHomePresenter;
import com.bobo.union.presenter.ISelectedPagePresenter;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.presenter.impl.CategoryPagePresenterImpl;
import com.bobo.union.presenter.impl.HomePresenterImpl;
import com.bobo.union.presenter.impl.SelectedPagePresenterImpl;
import com.bobo.union.presenter.impl.TicketPressenterImpl;

/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class PresenterManager {

    // 饿汉式单例
    private static final PresenterManager ourInstance = new PresenterManager();

    // 返回接口可以隐藏具体的实现
    private final ICategoryPagerPresenter mCategoryPagePresenter;
    private final IHomePresenter mHomePresenter;
    private final ITikcetPresenter mTicketPressenter;
    private final ISelectedPagePresenter mSelectedPagePresenter;

    // 返回接口隐藏具体的实现
    public ITikcetPresenter getTicketPressenter() {
        return mTicketPressenter;
    }

    public IHomePresenter getHomePresenter() {
        return mHomePresenter;
    }

    public ICategoryPagerPresenter getCategoryPagePresenter() {
        return mCategoryPagePresenter;
    }

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    public ISelectedPagePresenter getSelectedPagePresenter() {
        return mSelectedPagePresenter;
    }

    // 单例标配私有构造方法
    private PresenterManager() {
        mCategoryPagePresenter = new CategoryPagePresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPressenter = new TicketPressenterImpl();
        mSelectedPagePresenter = new SelectedPagePresenterImpl();
    }
}
