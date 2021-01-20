package com.lcodecore.tkrefreshlayout.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by 公众号：IT波 on 2021/1/10 Copyright © Leon. All rights reserved.
 * Functions: 处理 androidx.core.widget.NestedScrollView 和 androidx.recyclerview.widget.RecyclerView
 * 滚动事件的自定义View
 */
public class TbNestedScrollView extends NestedScrollView {

    private String TAG = "TbNestedScrollView";

    // 485是测量模拟器上最佳高度，不同的安卓设备需要外部调用者从新赋正确的值
    private int mHeaderHeight = 485;
    private int mOriginScroll = 0;

    private RecyclerView mRecyclerView;

    public TbNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 将轮播图的高度传过来
     * @param headerHeight
     */
    public void setHeaderHeight(int headerHeight) {
        this.mHeaderHeight = headerHeight;
    }

    /**
     * 重点：就是修改了这里达到滚动上划 轮播图 先跟着动的效果
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                  int type) {

        if (target instanceof RecyclerView) {
            this.mRecyclerView = (RecyclerView) target;
        }

        // Log.d(TAG, "dy -----> " + dy);
        if (mOriginScroll < mHeaderHeight) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mOriginScroll = t;
        // Log.d(TAG, "vertical -----> " + t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 返回当前是否划动到了底部（允许上拉加载更多）
     * @return
     */
    public boolean isInBottom() {
        if (mRecyclerView != null) {
            // 传一个int类型的direction 正数代表向上复数代表向下 注意：不可直接取反direction 该传正数传正数该传负数传负数
            boolean isBottom = !mRecyclerView.canScrollVertically(1);
            // Log.d(TAG, "canScroll -----> " + isBottom);
            return isBottom;
        } else {
            return false;
        }
    }
}
