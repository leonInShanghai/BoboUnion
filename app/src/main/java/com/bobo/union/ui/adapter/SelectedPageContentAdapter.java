package com.bobo.union.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.utils.Constants;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.ToastUtil;
import com.bobo.union.utils.UrlUtils;
import com.bumptech.glide.Glide;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 公众号：IT波 on 2021/2/14 Copyright © Leon. All rights reserved.
 * Functions: 精选右侧RecyclerView适配器
 */
public class SelectedPageContentAdapter extends RecyclerView.Adapter<SelectedPageContentAdapter.InnerHolder> {

    private List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.result_listBean.UatmTbkItemBean>
            mUatmTbkItemBeans;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_content,
                parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.result_listBean.UatmTbkItemBean itemData =
                mUatmTbkItemBeans.get(position);
        holder.setData(itemData);
    }

    @Override
    public int getItemCount() {
        return mUatmTbkItemBeans == null ? 0 : mUatmTbkItemBeans.size();
    }

    /**
     * (宿主fragment)设置数据
     * @param content
     */
    public void setData(SelectedContent content) {
        if (content.getCode() == Constants.SUCCESS_CODE && content.getData() != null &&
                content.getData().gettbk_dg_optimus_material_response() != null) {
            if (mUatmTbkItemBeans != null && mUatmTbkItemBeans.size() > 0) {
                mUatmTbkItemBeans.clear();
                // mUatmTbkItemBeans.addAll(content.getData().
                //        gettbk_dg_optimus_material_response().getresult_list().getmap_data());
            }
            // FIXME:List直接赋值？经测试这样写也是可以的
            mUatmTbkItemBeans = content.getData().gettbk_dg_optimus_material_response().getresult_list().getmap_data();

            // mUatmTbkItemBeans.addAll(content.getData().gettbk_dg_optimus_material_response().getresult_list()
            //        .getmap_data());

            notifyDataSetChanged();
        } else {
            LogUtils.e(this, "精选 服务器返回内容异常");
            ToastUtil.showToast("精选 服务器返回内容异常");
        }
    }

    //  [ˈɪnər] : 内部
    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selected_cover)
        ImageView cover;
        @BindView(R.id.selected_off_price)
        TextView offPriceTv;
        @BindView(R.id.selected_title)
        TextView title;
        @BindView(R.id.selected_buy_btn)
        TextView buyBtn;
        @BindView(R.id.selected_original_price)
        TextView originalPriceTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.result_listBean.UatmTbkItemBean
                                    itemData) {
            // 设置商品标题
            title.setText(itemData.getTitle());

            // 判断不为空再设置使用一个奇怪的问题RecyclerView这样使用的时候总是会出现空指针但是做非空判断后就不为空了
            if (!TextUtils.isEmpty(itemData.getPict_url())){
                String pict_url = UrlUtils.getTicketUrl(itemData.getPict_url());
                // 设置商品图片
                // UI控件可以直接getContext()
                Glide.with(itemView.getContext()).load(pict_url).placeholder(R.mipmap.bobo_launch).into(cover);
            }

            if (itemData.getZk_final_price() == null || TextUtils.isEmpty(itemData.getCoupon_click_url())) {
                // 设置没有优惠券了
                originalPriceTv.setText("晚了没有优惠券了");
                // 隐藏"领券购买"
                buyBtn.setVisibility(View.GONE);
            } else {
                // 设置原价
                originalPriceTv.setText("原价：" + itemData.getZk_final_price());
                // 显示"领券购买"
                buyBtn.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(itemData.getCoupon_info())) {
                offPriceTv.setVisibility(View.GONE);
            } else {
                offPriceTv.setVisibility(View.VISIBLE);
                // 显示优惠力度 如：满399减300元
                offPriceTv.setText(itemData.getCoupon_info());
            }
        }
    }
}
