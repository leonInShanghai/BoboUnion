package com.bobo.union.ui.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bobo.union.R;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.utils.UrlUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2021-01-03 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class LooperPagerAdapter extends PagerAdapter {

    // 数据源
    private List<HomePagerContent.DataBean> mData = new ArrayList<>();

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public int getDataSize() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        // 设置无限循环轮播图后处理数组越界的问题 取模运算处理
        int realPosition = position % mData.size();

        // 获取轮播图图片url
        HomePagerContent.DataBean dataBean = mData.get(realPosition);
        String coverUrl = UrlUtils.getCoverPath(dataBean.getPict_url());
        // java代码写布局没有用XML
        ImageView iv = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(coverUrl).placeholder(R.mipmap.bobo_launch).into(iv);
        // 添加到container
        container.addView(iv);
        return iv;
    }


    @Override
    public int getCount() {
        // 这是正常的ViewPager的适配器
        // return mData == null ? 0 : mData.size();
        // 设置无限循环轮播图
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        // 先清空老的数据
        if (mData.size() > 0) {
            mData.clear();
        }
        // 再添加新的数据
        mData.addAll(contents);
        // 刷新界面
        notifyDataSetChanged();
    }
}
