package com.bobo.union.view;

import com.bobo.union.base.IBaseCallback;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.model.doman.SelectedPageCategory;

/**
 * Created by 公众号：IT波 on 2021/1/31 Copyright © Leon. All rights reserved.
 * Functions: 精选页view层的回调接口
 * The red car is going wast, and the green car is going east.
 * These two purses look the same, but their cost is quite different.
 * The purse on the left costs $100 more than the purse on the right.
 * They are going in the opposite direction.
 * This box is 1 centinmeter long and 1.5 centimeters height.
 * These two cars are moving in the opposite direction.
 */
public interface ISelectedPageCallbacck extends IBaseCallback {

    /**
     * (精选)分类内容结果
     * @param categorys 左边分类内容
     */
    void onCategoriesLoaded(SelectedPageCategory categorys);

    /**
     * (精选)内容结果
     * @param content 右边的内容
     */
    void onContentLoaded(SelectedContent content);

}
