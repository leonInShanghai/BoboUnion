package com.bobo.union.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.bobo.union.R;
import com.bobo.union.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 这里是用做测试 底部tabbar的第二种实现方式
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_navigation_bar)
    RadioGroup navigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


        initListener();
    }

    // RadioGroup点击事件的处理
    private void initListener() {

        navigationBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                // LogUtils.d(TestActivity.class, "CheckedId" + checkedId);

                switch (checkedId) {
                    // 首页
                    case R.id.test_home:

                        LogUtils.d(TestActivity.class, "切换到首页");

                        break;

                    // 精选
                    case R.id.test_selected:

                        LogUtils.d(TestActivity.class, "切换到精选");

                        break;

                    // 特惠
                    case R.id.test_red_packet:

                        LogUtils.d(TestActivity.class, "切换到特惠");

                        break;

                    // 搜索
                    case R.id.test_search:

                        LogUtils.d(TestActivity.class, "切换到搜索");

                        break;
                }
            }
        });

    }

}
