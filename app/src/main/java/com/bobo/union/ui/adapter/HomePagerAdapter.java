package com.bobo.union.ui.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bobo.union.model.doman.Categories;
import com.bobo.union.ui.fragment.HomePagerFragment;
import com.bobo.union.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2020-08-29 Copyright © Leon. All rights reserved.
 * Functions: 首页导航栏下面的指示器 的适配器
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    // 数据源
    private List<Categories.DataBean> categoryList = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 根据位置得到对象
        Categories.DataBean dataBean = categoryList.get(position);
        // 根据对象实例化不同的碎片
        HomePagerFragment homePagerFragment = HomePagerFragment.newInstance(dataBean);

        return homePagerFragment;
    }

    @Override
    public int getCount() {

        if (categoryList != null){
            // LogUtils.d(this, "getCount() categoryList.size == " + categoryList.size());
        }

        return categoryList == null ? 0 : categoryList.size();
    }

    /**
     * 设置数据源方法
     * @param categories
     */
    public void setCategories(Categories categories) {

        // 先清除老的数据
        this.categoryList.clear();

        List<Categories.DataBean> data = categories.getData();

        // 再添加新数据
        this.categoryList.addAll(data);

        // LogUtils.d(this, "setCategories categoryList.size == " + categoryList.size());

        // 刷新界面
        notifyDataSetChanged();
    }
}
