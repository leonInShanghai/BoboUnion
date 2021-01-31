package com.bobo.union.ui.fragment;


import android.view.View;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.presenter.ISelectedPagePresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.view.ISelectedPageCallbacck;

import java.util.List;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class SelectedFragment extends BaseFragment implements ISelectedPageCallbacck {


    private ISelectedPagePresenter mSelectedPagePresenter;

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
    }

    /**
     * 左边分类内容加载成功了
     * @param categorys 左边分类内容
     */
    @Override
    public void onCategoriesLoaded(SelectedPageCategory categorys) {
        // 分类内容
        // LogUtils.d(this, "onCategoriesLoaded --> " + categorys.getData().toString());
        // 更新UI
        // 根据当前选中的分类，（右边）分类的详情内容
        List<SelectedPageCategory.DataBean> data = categorys.getData();
        mSelectedPagePresenter.getContentByCategory(data.get(0));
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
        LogUtils.d(this, "onContentLoaded --- > " + content.getData()
        .gettbk_dg_optimus_material_response().getresult_list().getmap_data().get(0).getTitle());
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
