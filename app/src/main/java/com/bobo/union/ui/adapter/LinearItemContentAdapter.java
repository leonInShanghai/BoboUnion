package com.bobo.union.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.ILinearItemInfo;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.UrlUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leon on 2021-01-02 Copyright © Leon. All rights reserved.
 * Functions: 首页 和 搜索页 共用适配器
 */
public class LinearItemContentAdapter extends RecyclerView.Adapter<LinearItemContentAdapter.InnerHolder> {

    private List<ILinearItemInfo> mData = new ArrayList<>();

    // 测试用
    private int testCount = 1;

    // 供外界设置的点击事件对象
    private OnListItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 这里打印log是因为这个方法不可一直调用 item要回收使用的
        LogUtils.d(this, "onCreateViewHolder..." + testCount);
        testCount++;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                item_home_pager_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        // 绑定数据可以一直调用 item要回收使用
        LogUtils.d(this, "onBindViewHolder... " + position);
        ILinearItemInfo dataBean = mData.get(position);
        // 设置（绑定）数据
        holder.setData(dataBean);

        // 在onBindViewHolder中设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击事件传递
                if (mItemClickListener != null) {
                    // HomePagerContent.DataBean item = mData.get(position);
                    // mItemClickListener.onItemClick(item);
                    mItemClickListener.onItemClick(dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    // public void setData(List<HomePagerContent.DataBean> contents) {
    public void setData(List<? extends ILinearItemInfo> contents) {
        // 先清除原来的数据
        if (mData.size() > 0) {
            mData.clear();
        }
        // 再添加新的数据
        mData.addAll(contents);
        // 更新视图
        notifyDataSetChanged();
    }

    /**
     * 上拉加载更多成功后添加到底部数据
     * @param contents
     */
    public void addData(List<? extends ILinearItemInfo> contents) {
        // 添加数据之前拿到原来的size为接下来的局部刷新做准备
        int oldSize = mData.size();
        mData.addAll(contents);
        // 更新ui(局部更新)
        notifyItemRangeChanged(oldSize, contents.size());
    }

    class InnerHolder extends RecyclerView.ViewHolder {

        // 左边的商品图片
        @BindView(R.id.goods_cover)
        public ImageView cover;

        // 右上商品标题
        @BindView(R.id.goods_title)
        public TextView title;

        // 右中省 * 元
        @BindView(R.id.goods_off_price)
        public TextView offPriceTv;

        // 右下二券后价格
        @BindView(R.id.goods_after_off_price)
        public TextView finalPriceTv;

        // 右下三原价
        @BindView(R.id.goods_orignal_price)
        public TextView orignalPriceTv;

        // 右下右 *人购买
        @BindView(R.id.goods_sell_count)
        public TextView sellCountTv;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            // 绑定ButterKnife
            ButterKnife.bind(this, itemView);
        }

        public void setData(ILinearItemInfo dataBean) {
            Context context = itemView.getContext();
            // LogUtils.d(this, "url -- > " + dataBean.getPict_url());

            // 设置左边的图片
            ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
            // int width = layoutParams.width;
            // int height = layoutParams.height;
            // int coverSize = (width > height ? width : height) / 2;
            // LogUtils.d(HomePagerContentAdapter.this, "width --> " + width +
            //         " height --> " + height);
            // String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url(), coverSize);
            // 动态计算出105请求不到图片直接写100
            // String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url(), 100);
            if (!TextUtils.isEmpty(dataBean.getCover())) {
                String coverPath = UrlUtils.getCoverPath(dataBean.getCover(), 100);
                // LogUtils.d(HomePagerContentAdapter.this, "coverPath --> " + coverPath);
                Glide.with(context).load(coverPath)
                        .placeholder(R.mipmap.bobo_launch).into(cover);
            }
            // 设置右上商品标题
            title.setText(dataBean.getTitle());
            String finalPrice = dataBean.getFinalPrice();
            // LogUtils.d(this, "finalPrice --> " + finalPrice);
            long couponAmount = dataBean.getCouponAmount();
            // LogUtils.d(this, "省 * 元 --> " + couponAmount);
            // 原价 - 优惠价格 = 券后价
            float resultPrice = Float.parseFloat(finalPrice) - couponAmount;
            // LogUtils.d(this, "resultPrice --> " + resultPrice);
            // 右下二券后价格 保留2位小数
            finalPriceTv.setText(String.format("%.2f", resultPrice));

            // 右中 省 * 元
            offPriceTv.setText(String.format(itemView.getContext().getResources().getString(
                    R.string.text_goods_off_price), couponAmount));

            // 右下三原价 设置中划线(过去的价格划掉)
            orignalPriceTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // 右下三原价
            orignalPriceTv.setText(String.format(context.getString(
                    R.string.text_goods_original_price), finalPrice));

            // 右下右 *人购买
            sellCountTv.setText(String.format(context.getString(R.string.text_goods_sell_count),
                    dataBean.getVolume()));
        }
    }

    /**
     * 供外界设置的点击事件对象赋值
     * @param l
     */
    public void setOnListItemClickListener(OnListItemClickListener l) {
        this.mItemClickListener = l;
    }

    /**
     * 传递点击事件的接口
     */
    public interface OnListItemClickListener {
        void onItemClick(ILinearItemInfo item);
    }
}
