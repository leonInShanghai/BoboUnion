package com.bobo.union.utils;

import android.widget.Toast;

import com.bobo.union.base.BaseApplication;

/**
 * Created by 公众号：IT波 on 2021/1/9 Copyright © Leon. All rights reserved.
 * Functions: 吐司 工具类
 * She leaves for school at 7:00.
 * She leaves around 3:00.
 */
public class ToastUtil {

    private static Toast sToast;

    public static void showToast(String tips) {
        if (sToast == null) {
            sToast = Toast.makeText(BaseApplication.getAppContext(), tips, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(tips);
        }
        sToast.show();
    }
}
