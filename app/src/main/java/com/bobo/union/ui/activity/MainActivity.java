package com.bobo.union.ui.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bobo.union.R;
import com.bobo.union.base.BaseActivity;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.ui.fragment.HomeFragment;
import com.bobo.union.ui.fragment.OnSellFragment;
import com.bobo.union.ui.fragment.SearchFragment;
import com.bobo.union.ui.fragment.SelectedFragment;
import com.bobo.union.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;


/**
 * Created by Leon on 2020-08-15 Copyright © Leon. All rights reserved.
 * Functions: 首页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_navation_bar)
    BottomNavigationView mNavigationView;

    // 各个页面碎片
    private HomeFragment mHomeFragment;
    private SelectedFragment mSelectedFragment;
    private OnSellFragment mRedPacketFragment;
    private SearchFragment mSearchFragment;

    // 切换fragment时的管理者
    private FragmentManager mFM;

    // ButterKnife
    // private Unbinder mBind;

    // Fragment切换时用于周转的fragment变量（上一次显示的fragment）
    private BaseFragment lastOneFragment = null;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        // 初始化各个fragment
        initFragments();

        // 测试代码tabbar的另外一种实现方式
        // startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    protected void initEvent() {
        initListener();
    }

    // 实例化各个碎片
    private void initFragments() {
        
        // 首页
        mHomeFragment = new HomeFragment();
        
        // 精选
        mSelectedFragment = new SelectedFragment();
        
        // 特惠
        mRedPacketFragment = new OnSellFragment();
        
        // 搜索
        mSearchFragment = new SearchFragment();

        // 碎片管理者不用每次都创建放在这里
        mFM = getSupportFragmentManager();

        // 默认选中第一个fragment
        switchFragment(mHomeFragment);
    }

    /**
     * 监听子控件点击事件
     */
    private void initListener() {

        // 底部tabbar的 点击事件监听
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    // 首页
                    case R.id.home:

                        LogUtils.d(this, "切换到首页");
                        
                        // 切换到首页的逻辑处理
                        switchFragment(mHomeFragment);
                        
                        break;

                    // 精选
                    case R.id.selected:

                        LogUtils.d(this, "切换到精选");
                        
                        // 切换到精选
                        switchFragment(mSelectedFragment);
                        
                        break;

                    // 特惠
                    case R.id.red_packet:

                        LogUtils.d(this, "切换到特惠");
                        
                        // 切换到特惠
                        switchFragment(mRedPacketFragment);
                        
                        break;

                    // 搜索
                    case R.id.search:

                        LogUtils.d(this, "切换到搜索");
                        
                        // 切换到搜索
                        switchFragment(mSearchFragment);
                        
                        break;
                }

                return true;
            }
        });
    }

    
    // 首页 精选 特惠 搜索 切换业务逻辑
    private void switchFragment(BaseFragment targetFragment) {

        // 开启事物
        FragmentTransaction fragmentTransaction = mFM.beginTransaction();

        // FIXME:replace的方式会导致生命周期的变化 要修改成add和hide的方式
        // fragmentTransaction.replace(R.id.main_page_connecter, targetFragment);
        // 要修改成add和hide的方式来控制Fragment的切换
        if (!targetFragment.isAdded()) {
            // 如果没有被添加过才能添加
            fragmentTransaction.add(R.id.main_page_connecter, targetFragment);
        } else {
            // 如果被添加过则显示
            fragmentTransaction.show(targetFragment);
        }
        if (lastOneFragment != null) {
            // 上一个fragment不为空时要隐藏
            fragmentTransaction.hide(lastOneFragment);
        }
        lastOneFragment = targetFragment;

        // 一定要提交事物
        fragmentTransaction.commit();
    }

}
