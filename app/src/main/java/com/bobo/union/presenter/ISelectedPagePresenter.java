package com.bobo.union.presenter;

import com.bobo.union.base.IBasePresenter;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.view.ISelectedPageCallbacck;

/**
 * Created by 公众号：IT波 on 2021/1/31 Copyright © Leon. All rights reserved.
 * Functions: 精选页 P 层接口
 */
public interface ISelectedPagePresenter extends IBasePresenter<ISelectedPageCallbacck> {

    /**
     * 获取分类
     */
    void getCategories();

    /**
     * 根据分类获取分类内容
     */
    void getContentByCategory(SelectedPageCategory.DataBean item);

    /**
     * (当加载失败用户点击“加载失败”)重新加载内容
     */
    void reloadContent();
}
