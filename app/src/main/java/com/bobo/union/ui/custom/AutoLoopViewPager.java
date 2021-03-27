package com.bobo.union.ui.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bobo.union.R;

/**
 * Created by 公众号：IT波 on 2021/1/16 Copyright © Leon. All rights reserved.
 * Functions:自动滚动的（轮播图）viewPager
 * A fisherman catchs fish.
 * A thif steal things such as purses and wallets.
 * Businesspeople often work in office buildings.
 * A bus driver drives a bus.
 * A farmer grows food, such as rice or tomatoes.
 * You can buy clothes and many other things in a department store.
 * You can see a movie in a cinema.
 * You can have coffee and some food in a coffee shop.
 * You can buy food toothpaste and shampoo in a supermarket.
 * Policemen catch people who drive too fast.
 * You can watch sports in a stadium.
 * Many people go to stadiums to watch sports like football.
 */
public class AutoLoopViewPager extends ViewPager {

    // 默认切换间隔时长，单位毫秒
    public static final int DEFAULT_DURATION = 3000;

    private int mDuration = DEFAULT_DURATION;

    // 是否允许循环播放轮播图，默认时false即不允许循环
    private boolean isLoop = false;

    // 当只有一张banner图的时候
    private boolean noOnly = true;

    public AutoLoopViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 读取XML自定义属性
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.AutoLooperStyle);
        // 获取属性
        mDuration = t.getInteger(R.styleable.AutoLooperStyle_duration, DEFAULT_DURATION);
        // 回收
        t.recycle();
    }

    /**
     * 开始自动滚动轮播图
     */
    public void startLoop() {
        isLoop = true;
        // post(mTask); ←这个方法（太快了）无法往左划动
        postDelayed(mTask, mDuration);
    }

    /**
     * 供外界设置轮播间隔时长(也可以在XML中设置)
     * @param duration 时长单位毫秒
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    // task : 任务
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            // 先拿到当前的位置
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            // 判断是否可以自动循环滚动轮播图
            // LogUtils.d(AutoLoopViewPager.this, "looping... : " + isLoop);
            if (isLoop) {
                // 开启下一次的循环 mDuration毫秒轮播一次
                postDelayed(this, mDuration);
            }
        }
    };

    /**
     * 停止自动滚动轮播图
     */
    public void stopLoop() {
        // 不允许播放轮播图
        isLoop = false;
        removeCallbacks(mTask);
    }

    /**
     * 分发
     * 备注：如果希望在原先的触摸逻辑的基础上面，添加一些额外的业务逻辑，并且该业务逻辑不需要影响到先前
     * 的触摸逻辑，可以考虑将业务逻辑代码写在分发方法中
     * ACTION_CANCEL:事件类型：当控件在触摸的一系列过程中，突然中断了。
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        // 当只有一张banner图的时候就不用自动滚动了
        if (getAdapter() != null) {
            noOnly = getAdapter().getCount() == 1 ? false : true;
        }

        switch (eventAction){
            case MotionEvent.ACTION_DOWN:
                //用户按下了banner图 停止发送让banner图无限滚动的消息
                stopLoop();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //用户抬起了手指开始发送让banner图无限滚动的消息
                if (noOnly){
                    startLoop();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //用户取消了（当控件在触摸的一系列过程中，突然中断了）（手指移动到别的控件上了）
                if (noOnly) {
                    startLoop();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        /**
         * 和window对象断开关系
         * 视图死亡（视图被移除，或者activity死亡之前）时调用
         * stopLoop();方法中会移除mTask合理的管理内存
         */
        stopLoop();
        super.onDetachedFromWindow();
    }
}
