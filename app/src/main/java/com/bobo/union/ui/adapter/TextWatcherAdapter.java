package com.bobo.union.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 公众号：IT波 on 2021/3/13 Copyright © Leon. All rights reserved.
 * Functions: TextWatcher 的适配器 好处在于用不到的方法自己不必重写
 */
public class TextWatcherAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
