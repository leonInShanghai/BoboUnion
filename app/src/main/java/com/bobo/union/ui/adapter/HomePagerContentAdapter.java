package com.bobo.union.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.UrlUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leon on 2021-01-02 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class HomePagerContentAdapter extends RecyclerView.Adapter<HomePagerContentAdapter.InnerHolder> {

    private List<HomePagerContent.DataBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                item_home_pager_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = mData.get(position);
        // 设置（绑定）数据
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
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
    public void addData(List<HomePagerContent.DataBean> contents) {
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

        public void setData(HomePagerContent.DataBean dataBean) {
            Context context = itemView.getContext();
            LogUtils.d(this, "url -- > " + dataBean.getPict_url());

            // 设置左边的图片
            Glide.with(context).load(UrlUtils.getCoverPath(dataBean.getPict_url()))
                    .placeholder(R.mipmap.bobo_launch).into(cover);

            // 设置右上商品标题
            title.setText(dataBean.getTitle());
            String finalPrice = dataBean.getZk_final_price();
            LogUtils.d(this, "finalPrice --> " + finalPrice);
            long couponAmount = dataBean.getCoupon_amount();
            LogUtils.d(this, "省 * 元 --> " + couponAmount);
            // 原价 - 优惠价格 = 券后价
            float resultPrice = Float.parseFloat(finalPrice) - couponAmount;
            LogUtils.d(this, "resultPrice --> " + resultPrice);
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
}
