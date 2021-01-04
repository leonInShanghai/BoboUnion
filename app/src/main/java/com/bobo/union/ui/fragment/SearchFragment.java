package com.bobo.union.ui.fragment;

import android.view.View;

import com.bobo.union.R;
import com.bobo.union.base.BaseFragment;

/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class SearchFragment extends BaseFragment {

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
}
