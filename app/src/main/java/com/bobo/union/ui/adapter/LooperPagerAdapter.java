package com.bobo.union.ui.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bobo.union.R;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.utils.LogUtils;
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

    // 点击事件接口对象
    private OnLooperPagerItemClickListener mOnItemClickListener = null;

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
        // 因为iamgeView的宽高是：MATCH_PARENT，需要拿父控件的宽高
        // int measuredWidth = container.getMeasuredWidth();
        // int measuredHeight = container.getMeasuredHeight();
        // LogUtils.d(LooperPagerAdapter.this, " measuredWidth --> " + measuredWidth
        //         + " measuredHeight --> " + measuredHeight);
        // int ivSize = (measuredWidth > measuredHeight ? measuredWidth : measuredHeight) / 2;
        // 动态计算出540避免不同手机计算出结果不一样导致部分手机请求不到图片 直接写300
        String coverUrl = UrlUtils.getCoverPath(dataBean.getPict_url(), 300);
        // LogUtils.d(LooperPagerAdapter.this, "coverUrl--> " + coverUrl);
        // java代码写布局没有用XML
        ImageView iv = new ImageView(container.getContext());

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(coverUrl).placeholder(R.mipmap.bobo_launch).into(iv);
        // 设置轮播图点击事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    // HomePagerContent.DataBean item = mData.get(realPosition);
                    // mOnItemClickListener.onLooperItemClick(item);
                    mOnItemClickListener.onLooperItemClick(dataBean);
                }
            }
        });
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

    /**
     * 供外界使用设置轮播图点击事件的监听
     * @param l
     */
    public void setOnLooperPagerItemClickListener(OnLooperPagerItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 传递点击轮播图事件的接口
     */
    public interface OnLooperPagerItemClickListener {
        void onLooperItemClick(HomePagerContent.DataBean item);
    }
}
