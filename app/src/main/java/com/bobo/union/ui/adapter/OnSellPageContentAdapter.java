package com.bobo.union.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.OnSellContent;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.UrlUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 公众号：IT波 on 2021/2/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class OnSellPageContentAdapter extends RecyclerView.Adapter<OnSellPageContentAdapter.InnerHolder> {

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mData = new
            ArrayList<>();

    // 传递点击事件的接口
    private OnSellPageItemClickListener mContentItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_sell_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        // 绑定数据
        OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean = mData.get(
                position);
        // 设置数据到item
        holder.setData(mapDataBean);

        // 点击事件的处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContentItemClickListener != null) {
                    mContentItemClickListener.onSellItemClick(mapDataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 外界设置数据的方法
     *
     * @param result
     */
    public void setData(OnSellContent result) {
        // 先清空老数据
        mData.clear();
        // 再添加新数据
        mData.addAll(result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data());
        // 更新ui界面
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据成功了
     * @param moreResult
     */
    public void onMoreLoaded(OnSellContent moreResult) {
        // 添加前数据源数据的长度（局部刷新用）
        int oldDataSize = mData.size();
        // 上拉加载更多添加新数据
        mData.addAll(moreResult.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data());
        // 局部更新ui界面
        notifyItemRangeChanged(oldDataSize - 1, mData.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.on_sell_cover)
        ImageView cover;

        @BindView(R.id.on_sell_content_title)
        TextView titleTv;

        @BindView(R.id.on_sell_orgin_price_tv)
        TextView orginPriceTv;

        @BindView(R.id.on_sell_off_price_tv)
        TextView offPriceTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean
                                    data) {
            titleTv.setText(data.getTitle());

            // LogUtils.d(this, "pic url --- > " + data.getPict_url());
            String coverPath = UrlUtils.getCoverPath(data.getPict_url(), 200);
            Glide.with(cover.getContext()).load(coverPath).placeholder(R.mipmap.bobo_launch).into(cover);

            // 原价
            String originalPrise = data.getZk_final_price();
            orginPriceTv.setText("￥" + originalPrise + " ");
            // 原价上画一条（过时的那种线）
            orginPriceTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            // String转Double
            double originalPriseDouble = Double.parseDouble(originalPrise);
            // 优惠价 ˈkuːpɑːn coupon：优惠的意识
            int couponAmount = data.getCoupon_amount();
            // 原价- 优惠金额 = 优惠价
            double finalPrice = originalPriseDouble - couponAmount;
            offPriceTv.setText("券后价：" + String.format("%.2f", finalPrice));
        }
    }

    /**
     * 供外界监听点击事件的接口
     * @param listener
     */
    public void setOnSellPageItemClickListener(OnSellPageItemClickListener listener) {
        mContentItemClickListener = listener;
    }

    /**
     * 传递点击事件的接口
     */
    public interface OnSellPageItemClickListener {
        void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean
                                     data);
    }
}
