package com.bobo.union.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/2/14 Copyright © Leon. All rights reserved.
 * Functions: 精选页左边的列表适配器
 */
public class SelectedPageLeftAdapter extends RecyclerView.Adapter<SelectedPageLeftAdapter.InnerHolder> {

    private List<SelectedPageCategory.DataBean> mData = new ArrayList<>();

    // 用户默认选中第0个item
    private int mCurrentSelectedPosition = 0;

    // 点击事件监听接口
    private OnLeftItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 打气筒加载布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_left, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        // TextView itemView = itemView.findViewById(R.id.left_category_tv);
        // itemTv.setText();

        // 让用户选中的item改变颜色
        int color = mCurrentSelectedPosition == position ? holder.itemTv.getResources().getColor(R.color.colorEFEEEE,
                null) : Color.parseColor("#FFFFFF");
        holder.itemTv.setBackgroundColor(color);

        SelectedPageCategory.DataBean dataBean = mData.get(position);
        // 调试的时候发现有为空的情况所以多加了判断
        if (dataBean != null) {
            if (dataBean.getFavorites_title() != null) {
                holder.itemTv.setText(dataBean.getFavorites_title());
            } else {
                LogUtils.e(this, "精选 解析不到数据");
                ToastUtil.showToast("精选 解析不到数据");
            }
        } else {
            LogUtils.e(this, "精选 解析不到数据");
            ToastUtil.showToast("精选 解析不到数据");
        }

        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null && mCurrentSelectedPosition != position) {
                    // 修改当前高亮item的索引
                    mCurrentSelectedPosition = position;
                    // 传递点击事件
                    mItemClickListener.onLeftItemClick(dataBean);
                    // 刷新界面 因为修改当前高亮item的索引了
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mData == null ? 0 : this.mData.size();
    }

    /**
     * 外界网络请求成功设置数据源的方法
     * @param categorys
     */
    public void setData(SelectedPageCategory categorys) {
        List<SelectedPageCategory.DataBean> data = categorys.getData();
        if (data != null) {
            this.mData.clear();
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
        if (mData.size() > 0) {
            mItemClickListener.onLeftItemClick(mData.get(mCurrentSelectedPosition));
        }
    }


    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView itemTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.left_category_tv);
        }
    }

    /**
     * 供外界监听点击事件的接口
     * @param listener
     */
    public void setOnLeftItemClickListener(OnLeftItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnLeftItemClickListener {
        void onLeftItemClick(SelectedPageCategory.DataBean item);
    }
}
