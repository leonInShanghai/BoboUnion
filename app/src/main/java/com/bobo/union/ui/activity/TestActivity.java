package com.bobo.union.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bobo.union.R;
import com.bobo.union.ui.custom.TextFlowLayout;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 这里是用做测试 底部tabbar的第二种实现方式
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_navigation_bar)
    RadioGroup navigationBar;

    @BindView(R.id.test_flow_layout)
    TextFlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initListener();

        List<String> textList = new ArrayList<>();
        textList.add("肥仔快乐水");
        textList.add("肥仔快乐水");
        textList.add("肥仔快乐水");
        textList.add("电脑");
        textList.add("机械键盘");
        textList.add("滑板鞋");
        textList.add("运动鞋");
        textList.add("肥仔快乐水");
        textList.add("阳光沙滩");
        textList.add("Android 编程");
        textList.add("JavaWeb后台");
        flowLayout.setTextList(textList);
        flowLayout.setOnFlowTextItemClickListener(new TextFlowLayout.OnFlowTextItemClickListener() {
            @Override
            public void onFlowItemClick(String text) {
                LogUtils.d(TestActivity.this, " click text --> " + text);
            }
        });
    }

    /**
     * 演示Toast重叠的按钮点击事件
     * @param view
     */
    public void showToast(View view) {
        // Toast.makeText(TestActivity.this, "测试...", Toast.LENGTH_SHORT).show();
        ToastUtil.showToast("测试...");
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
