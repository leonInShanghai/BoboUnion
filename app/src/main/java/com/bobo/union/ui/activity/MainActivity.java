package com.bobo.union.ui.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bobo.union.R;
import com.bobo.union.base.BaseActivity;
import com.bobo.union.base.BaseFragment;
import com.bobo.union.ui.inter.IMainActivity;
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
public class MainActivity extends BaseActivity implements IMainActivity {

    @BindView(R.id.main_navation_bar)
    BottomNavigationView mNavigationView;

    // 各个页面碎片
    private HomeFragment mHomeFragment;
    private SelectedFragment mSelectedFragment;
    private OnSellFragment mRedPacketFragment;
    private SearchFragment mSearchFragment;

    // 切换fragment时的管理者
    private FragmentManager mFm;

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

    /**
     * 跳转到搜索界面
     */
    public void switch2Search() {
        // switchFragment(mSearchFragment);
        mNavigationView.setSelectedItemId(R.id.search);
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
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }

        // 精选
        if (mSelectedFragment == null) {
            mSelectedFragment = new SelectedFragment();
        }

        // 特惠
        if (mRedPacketFragment == null) {
            mRedPacketFragment = new OnSellFragment();
        }

        // 搜索
        if (mSearchFragment == null) {
            mSearchFragment = new SearchFragment();
        }

        // 碎片管理者不用每次都创建放在这里
        if (mFm == null) {
            mFm = getSupportFragmentManager();
        }
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

                        LogUtils.d(MainActivity.this, "切换到首页");
                        
                        // 切换到首页的逻辑处理
                        switchFragment(mHomeFragment);
                        
                        break;

                    // 精选
                    case R.id.selected:

                        LogUtils.d(MainActivity.this, "切换到精选");
                        
                        // 切换到精选
                        switchFragment(mSelectedFragment);
                        
                        break;

                    // 特惠
                    case R.id.red_packet:

                        LogUtils.d(MainActivity.this, "切换到特惠");
                        
                        // 切换到特惠
                        switchFragment(mRedPacketFragment);
                        
                        break;

                    // 搜索
                    case R.id.search:

                        LogUtils.d(MainActivity.this, "切换到搜索");
                        
                        // 切换到搜索
                        switchFragment(mSearchFragment);
                        
                        break;
                }

                return true;
            }
        });

        // 第一次进来选中home item
        mNavigationView.setSelectedItemId(R.id.home);
    }

    // 首页 精选 特惠 搜索 切换业务逻辑 (切换Fragmengt的方法源代码 FIXME:总感觉这种方式没有view pager 靠谱)
    // FIXME:目前在oppo手机上偶尔会出现Fragmnet重叠的情况 新华为手机未出现Fragmnet重叠
    // FIXME:Fragmnet重叠 可以参考 https://github.com/leonInShanghai/ShoppingMall/blob/master/app/src/main/java/com
    //  /bobo/shoppingmall/app/MainActivity.java 进行解决！
    private void switchFragment(BaseFragment targetFragment) {

        // 如果上一个fragment跟当前要切换的fragment是同一个，那么不需要切换
        if(lastOneFragment == targetFragment) {
            LogUtils.d(this,"lastOneFragment == targetFragment");
            return;
        }

        // 开启事物
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();

        // FIXME:replace的方式会导致生命周期的变化 要修改成add和hide的方式
        // fragmentTransaction.replace(R.id.main_page_connecter, targetFragment);
        // 要修改成add和hide的方式来控制Fragment的切换
        if (!targetFragment.isAdded()) {
            // 如果没有被添加过才能添加
            fragmentTransaction.add(R.id.main_page_connecter, targetFragment);
            LogUtils.d(this,"add: " + targetFragment.getClass().getSimpleName());
        } else {
            // 如果被添加过则显示
            fragmentTransaction.show(targetFragment);
            LogUtils.d(this,"show: " + targetFragment.getClass().getSimpleName());
        }
        if (lastOneFragment != null) {
            // 上一个fragment不为空时要隐藏
            fragmentTransaction.hide(lastOneFragment);
            LogUtils.d(this,"hide: " + lastOneFragment.getClass().getSimpleName());
        }
        lastOneFragment = targetFragment;

        // 一定要提交事物
        fragmentTransaction.commit();
    }
}



// 2021-02-20修复Fragment切换切换错乱前的代码
//    @BindView(R.id.main_navation_bar)
//    BottomNavigationView mNavigationView;
//
//    // 各个页面碎片
//    private HomeFragment mHomeFragment;
//    private SelectedFragment mSelectedFragment;
//    private OnSellFragment mRedPacketFragment;
//    private SearchFragment mSearchFragment;
//
//    // 切换fragment时的管理者
//    private FragmentManager mFm;
//
//    // ButterKnife
//    // private Unbinder mBind;
//
//    // Fragment切换时用于周转的fragment变量（上一次显示的fragment）
//    private BaseFragment lastOneFragment = null;
//
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initPresenter() {
//
//    }
//
//    @Override
//    protected void initView() {
//        // 初始化各个fragment
//        initFragments();
//
//        // 测试代码tabbar的另外一种实现方式
//        // startActivity(new Intent(this, TestActivity.class));
//    }
//
//    @Override
//    protected void initEvent() {
//        initListener();
//    }
//
//    // 实例化各个碎片
//    private void initFragments() {
//
//        // 首页
//        mHomeFragment = new HomeFragment();
//
//        // 精选
//        mSelectedFragment = new SelectedFragment();
//
//        // 特惠
//        mRedPacketFragment = new OnSellFragment();
//
//        // 搜索
//        mSearchFragment = new SearchFragment();
//
//        // 碎片管理者不用每次都创建放在这里
//        mFm = getSupportFragmentManager();
//
//        // 默认选中第一个fragment
//        switchFragment(mHomeFragment);
//    }
//
//    /**
//     * 监听子控件点击事件
//     */
//    private void initListener() {
//
//        // 底部tabbar的 点击事件监听
//        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.
//                OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//                    // 首页
//                    case R.id.home:
//
//                        LogUtils.d(this, "切换到首页");
//
//                        // 切换到首页的逻辑处理
//                        switchFragment(mHomeFragment);
//
//                        break;
//
//                    // 精选
//                    case R.id.selected:
//
//                        LogUtils.d(this, "切换到精选");
//
//                        // 切换到精选
//                        switchFragment(mSelectedFragment);
//
//                        break;
//
//                    // 特惠
//                    case R.id.red_packet:
//
//                        LogUtils.d(this, "切换到特惠");
//
//                        // 切换到特惠
//                        switchFragment(mRedPacketFragment);
//
//                        break;
//
//                    // 搜索
//                    case R.id.search:
//
//                        LogUtils.d(this, "切换到搜索");
//
//                        // 切换到搜索
//                        switchFragment(mSearchFragment);
//
//                        break;
//                }
//
//                return true;
//            }
//        });
//    }
//
//
//    // 首页 精选 特惠 搜索 切换业务逻辑
//    private void switchFragment(BaseFragment targetFragment) {
//
//        // 如果上一个fragment跟当前要切换的fragment是同一个，那么不需要切换
//        if(lastOneFragment == targetFragment) {
//            LogUtils.d(this,"lastOneFragment == targetFragment");
//            return;
//        }
//
//        // 开启事物
//        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
//
//        // FIXME:replace的方式会导致生命周期的变化 要修改成add和hide的方式
//        // fragmentTransaction.replace(R.id.main_page_connecter, targetFragment);
//        // 要修改成add和hide的方式来控制Fragment的切换
//        if (!targetFragment.isAdded()) {
//            // 如果没有被添加过才能添加
//            fragmentTransaction.add(R.id.main_page_connecter, targetFragment);
//            LogUtils.d(this,"add: " + targetFragment.getClass().getSimpleName());
//        } else {
//            // 如果被添加过则显示
//            fragmentTransaction.show(targetFragment);
//            LogUtils.d(this,"show: " + targetFragment.getClass().getSimpleName());
//        }
//        if (lastOneFragment != null) {
//            // 上一个fragment不为空时要隐藏
//            fragmentTransaction.hide(lastOneFragment);
//            LogUtils.d(this,"hide: " + lastOneFragment.getClass().getSimpleName());
//        }
//        lastOneFragment = targetFragment;
//
//        // 一定要提交事物
//        fragmentTransaction.commit();
//    }
