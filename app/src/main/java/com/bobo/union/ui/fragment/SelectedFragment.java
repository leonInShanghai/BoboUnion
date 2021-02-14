package com.bobo.union.ui.fragment;


import android.graphics.Rect;
import android.view.View;

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
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.SizeUtils;
import com.bobo.union.view.ISelectedPageCallbacck;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class SelectedFragment extends BaseFragment implements ISelectedPageCallbacck,
        SelectedPageLeftAdapter.OnLeftItemClickListener {

    @BindView(R.id.left_category_list)
    public RecyclerView leftCategoryList;

    @BindView(R.id.right_content_list)
    public RecyclerView rightContentList;

    private ISelectedPagePresenter mSelectedPagePresenter;
    private SelectedPageLeftAdapter mLeftAdapter;
    private SelectedPageContentAdapter mRightAdapter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mSelectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        mSelectedPagePresenter.registerViewCallback(this);
        mSelectedPagePresenter.getCategories();
    }

    @Override
    protected void release() {
        super.release();
        if (mSelectedPagePresenter != null) {
            // 当View(this)销毁时解绑
            mSelectedPagePresenter.unregisterViewCallback(this);
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
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLeftAdapter.setOnLeftItemClickListener(this);
    }

    /**
     * 左边分类内容加载成功了
     * @param categorys 左边分类内容
     */
    @Override
    public void onCategoriesLoaded(SelectedPageCategory categorys) {
        setUpState(State.SUCCESS);
        // 设置适配器的数据源
        mLeftAdapter.setData(categorys);

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
        rightContentList.scrollToPosition(0);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {

    }

    /**
     * 左边的RecyclerView某个item被点击了
     * @param item
     */
    @Override
    public void onLeftItemClick(SelectedPageCategory.DataBean item) {
        LogUtils.d(this, "current left selected item --> " + item.toString());
        mSelectedPagePresenter.getContentByCategory(item);
    }
}
