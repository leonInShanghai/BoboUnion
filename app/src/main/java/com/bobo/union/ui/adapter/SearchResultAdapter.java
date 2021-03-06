package com.bobo.union.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.utils.ToastUtil;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/3/5 Copyright © Leon. All rights reserved.
 * Functions: 搜索页 搜索结果recyclerview 适配器 后面 首页兼容 这里没有用到了
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.InnerHolder> {

    private List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> mData;

    @NonNull
    @Override
    public SearchResultAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_goods_content, parent,
                false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * (外界)设置数据源的方法
     * @param result
     */
    public void setData(SearchResult result) {

        // 先清空老数据
        if (mData != null) {
            mData.clear();
        }

        try {
            // 再添加新数据
            mData = result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data();
        } catch (NullPointerException e) {
            ToastUtil.showToast("服务器数据解析异常!");
            mData = null;
        }

        // 刷新UI界面
        notifyDataSetChanged();
    }

    static class InnerHolder extends RecyclerView.ViewHolder {

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
