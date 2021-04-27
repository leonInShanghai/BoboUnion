package com.bobo.union.ui.fragment;


import android.app.AlertDialog;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.presenter.ISelectedPagePresenter;
import com.bobo.union.ui.adapter.SelectedPageContentAdapter;
import com.bobo.union.ui.adapter.SelectedPageLeftAdapter;
import com.bobo.union.ui.custom.AlertContentLoadingView;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.utils.TicketUtil;
import com.bobo.union.view.ISelectedPageCallbacck;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions: 精选页面
 */
public class SelectedFragment extends BaseFragment implements ISelectedPageCallbacck,
        SelectedPageLeftAdapter.OnLeftItemClickListener, SelectedPageContentAdapter.OnSelectedPageContentItemClickLinster {

    @BindView(R.id.left_category_list)
    public RecyclerView leftCategoryList;

    @BindView(R.id.right_content_list)
    public RecyclerView rightContentList;

    private ISelectedPagePresenter mSelectedPagePresenter;
    private SelectedPageLeftAdapter mLeftAdapter;
    private SelectedPageContentAdapter mRightAdapter;

    @BindView(R.id.fragment_bar_title_tv)
    public TextView barTitleTv;

    // loading弹窗，左边已经加载好右边变化的时候显示给用户看的
    private AlertDialog mAlterDiaglog;

    @Override
    protected View loadRootView(LayoutInflater inflater,ViewGroup container) {
        return inflater.inflate(R.layout.fragment_with_bar_layout, container, false);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mSelectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        mSelectedPagePresenter.registerViewCallback(this);
        mSelectedPagePresenter.getCategories();
    }

    /**
     * 当加载失败时用户点击了“网络出错请点击重试”
     */
    @Override
    protected void onRetryClick() {
        // 重试
        if (mSelectedPagePresenter != null) {
            // 重新加载内容
            mSelectedPagePresenter.reloadContent();
        }
    }

    @Override
    protected void release() {
        super.release();
        if (mSelectedPagePresenter != null) {
            // 当View(this)销毁时解绑
            mSelectedPagePresenter.unregisterViewCallback(this);
        }
        // 2021-4-18当用户点击了左边的分类此时要加载右边的子分类，logding弹窗出现
        if (mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);

        // 设置左边类型列表的适配器
        leftCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter = new SelectedPageLeftAdapter();
        leftCategoryList.setAdapter(mLeftAdapter);

        // 设置右边对应内容的适配器
        rightContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightAdapter = new SelectedPageContentAdapter();
        rightContentList.setAdapter(mRightAdapter);
        rightContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                int topAndBottom = SizeUtils.dip2px(getContext(), 4);
                int leftAndRight = SizeUtils.dip2px(getContext(), 6);
                outRect.top = topAndBottom;
                outRect.bottom = topAndBottom;
                outRect.left = leftAndRight;
                outRect.right = leftAndRight;
            }
        });

        // 设置标题
        barTitleTv.setText(getResources().getText(R.string.text_selected_title));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLeftAdapter.setOnLeftItemClickListener(this);
        mRightAdapter.setOnSelectedPageContentItemClickLinster(this);
    }

    /**
     * 左边分类内容加载成功了
     * @param categorys 左边分类内容
     */
    @Override
    public void onCategoriesLoaded(SelectedPageCategory categorys) {

        // 设置适配器的数据源
        mLeftAdapter.setData(categorys);
        setUpState(State.SUCCESS);

        // 分类内容
        // LogUtils.d(this, "onCategoriesLoaded --> " + categorys.getData().toString());
        // 更新UI
        // 根据当前选中的分类，（右边）分类的详情内容
        // List<SelectedPageCategory.DataBean> data = categorys.getData();
        // mSelectedPagePresenter.getContentByCategory(data.get(0));
    }

    /**
     * (精选)内容结果
     * @param content 右边的内容
     * 全局修改方法名 shift + F6
     */
    @Override
    public void onContentLoaded(SelectedContent content) {
        // LogUtils.d(this, "onContentLoaded --- > " + content.getData().
        //                getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item().get(0));
        // LogUtils.d(this, "onContentLoaded --- > " + content.getData()
        // .gettbk_dg_optimus_material_response().getresult_list().getmap_data().get(0).getTitle());
        mRightAdapter.setData(content);
        // 每当右边的内容数据加载回来 滚动到第0个item
        rightContentList.scrollToPosition(0);

        // 2021-4-18当用户点击了左边的分类此时要加载右边的子分类，logding弹窗出现
        if (mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    @Override
    public void onError() {
        setUpState(State.ERROR);
        // 2021-4-18当用户点击了左边的分类此时要加载右边的子分类，logding弹窗出现
        if (mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        // 2021-4-18当用户点击了左边的分类此时要加载右边的子分类，logding弹窗出现
        if (mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    /**
     * 左边的RecyclerView某个item被点击了
     * @param item
     */
    @Override
    public void onLeftItemClick(SelectedPageCategory.DataBean item) {
        LogUtils.d(this, "current left selected item --> " + item.toString());
        mSelectedPagePresenter.getContentByCategory(item);

        // 2021-4-18当用户点击了左边的分类此时要加载右边的子分类，logding弹窗出现
        if (mAlterDiaglog == null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog)
                    .setCancelable(false)
                    .setView(new AlertContentLoadingView(getContext()));
            // 第一次为null的时候创建好不要显示因为loading布局会出来
            mAlterDiaglog = builder.show();
            // mAlterDiaglog = builder.create();
        } else {
            // 第二次才应该显示
            mAlterDiaglog.show();
        }
    }

    /**
     * 右边的某个item被点击了
     * @param item
     */
    @Override
    public void onContentItemClick(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.result_listBean.
                                               UatmTbkItemBean item) {
        // // 右边的内容item被点击后跳转到淘口令界面
        // String title = item.getTitle();
        // String url = item.getCoupon_click_url();
        // if (TextUtils.isEmpty(url)) {
        //     // 详情的url
        //     url = item.getClick_url();
        // }
        // String cover = item.getPict_url();
        // // 拿到TicketPressenter去加载数据
        // ITikcetPresenter ticketPressenter = PresenterManager.getInstance().getTicketPressenter();
        // ticketPressenter.getTicket(title, url, cover);
        // startActivity(new Intent(getContext(), TicketActivity.class));

        TicketUtil.toTicketPage(getContext(), item);
    }
}
