package com.bobo.union.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo.union.R;
import com.bobo.union.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/2/27 Copyright © Leon. All rights reserved.
 * Functions: 搜索记录自定义控件
 * 自定义控件分为三大类：① 自定义view ②自定义ViewGroup 如：RecycerView ③自定义组合控件如：banner图，购物车加减
 */
public class TextFlowLayout extends ViewGroup {
    public static final int DEFAULT_SPACE = 10;

    // 水平方向间距
    private float mItemHorizontalSpace = DEFAULT_SPACE;
    // 垂直方向间距
    private float mItemVerticalSpace = DEFAULT_SPACE;

    // 数据源
    private List<String> mTextList = new ArrayList<>();

    // 这个是描述所有的行
    private List<List<View>> lines = new ArrayList<>();
    private int mSelfWidth;
    private int mItemHeight;

    // 点击事件传递接口
    private OnFlowTextItemClickListener mItemClickListener = null;

    public float getItemHorizontalSpace() {
        return mItemHorizontalSpace;
    }

    public void setItemHoriatontalSpace(float itemHoriatontalSpace) {
        mItemHorizontalSpace = itemHoriatontalSpace;
    }

    public float getItemVerticalSpace() {
        return mItemVerticalSpace;
    }

    public void setItemVerticalSpace(float itemVerticalSpace) {
        mItemVerticalSpace = itemVerticalSpace;
    }

    public TextFlowLayout(Context context) {
        this(context, null);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取XML中声明的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowTextStyle);
        mItemHorizontalSpace = ta.getDimension(R.styleable.FlowTextStyle_horizontalSpace, DEFAULT_SPACE);
        mItemVerticalSpace = ta.getDimension(R.styleable.FlowTextStyle_verticalSpace, DEFAULT_SPACE);
        ta.recycle();
        LogUtils.d(this, "mItemHorizontalSpace --> " + mItemHorizontalSpace + " mItemVerticalSpace --> "
                + mItemVerticalSpace);
    }

    /**
     * 返回数据源的集合size
     * @return
     */
    public int getContentSize() {
        return mTextList == null ? 0 : mTextList.size();
    }

    /**
     * 供外界设置数据源的方法
     * @param textList
     */
    public void setTextList(List<String> textList) {
        // 先移除之前的所有子view
        removeAllViews();
        // 先清除避免重复添加
        mTextList.clear();
        // 数据源可以为null
        if (textList == null) {
            return;
        }
        mTextList.addAll(textList);
        // 发转集合后搜索的放前面方便用户查看
        Collections.reverse(mTextList);
        // 遍历mTextList内容再创建
        for (String text : mTextList) {
            // 根据数据源添加子view root:此时要填写this attachToRoot:true表示将layout布局添加到root布局中
            // LayoutInflater.from(getContext()).inflate(R.layout.flow_text, this, true);

            // ↑等价于↓
            TextView item = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.flow_text, this,
                    false);
            item.setText(text);
            // 给每个item设置点击事件
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onFlowItemClick(text);
                    }
                }
            });

            addView(item);
        }
    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 如果没有子控件直接返回
        if (getChildCount() == 0) {
            return;
        }

        // 这时描述单行
        List<View> line = null;
        // 先清空一下集合
        lines.clear();

        // 自己的宽度 = 自己的宽度 - Padding
        mSelfWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        LogUtils.d(this, " selfWidth --> " + mSelfWidth);
        // 测量
        LogUtils.d(this, " onMeasure --> " + getChildCount());

        // 测量子控件
        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            if (itemView.getVisibility() != VISIBLE) {
                // 视图不显示则不需要测量
                continue;
            }
            // 测量前
            LogUtils.d(this, "before itemView height-->" + itemView.getMeasuredHeight());
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec);
            // 测量后
            LogUtils.d(this, "after itemView height-->" + itemView.getMeasuredHeight());

            if (line == null) {
                // 说明当前行为空可以添加
                line = createNewLine(itemView);
            } else {
                // 判断是否可以再添加一行
                if (canBeAdd(itemView, line)) {
                    // 原来的一行还可以添加
                    line.add(itemView);
                } else {
                    // 新创建一行
                    line = createNewLine(itemView);
                }
            }
        }

        mItemHeight = getChildAt(0).getMeasuredHeight();
        // 最后加0.5是为了四舍五入
        int selfHeight = (int) (lines.size() * mItemHeight + mItemVerticalSpace * (lines.size() + 1) + 0.5f);

        // 测量自己
        setMeasuredDimension(mSelfWidth, selfHeight);
    }

    /**
     * 创建新的一行
     * @param itemView
     */
    private List<View> createNewLine(View itemView) {
        List<View> line = new ArrayList<>();
        line.add(itemView);
        lines.add(line);
        return line;
    }

    /**
     * 判断是否可以再继续添加数据
     * @param itemView
     * @param line
     * @return
     */
    private boolean canBeAdd(View itemView, List<View> line) {
        // 所有已经添加了的子View宽度相加 + (line.size() + 1) * mItemHorizontalSpace + itemView.getMeasuredWidth()
        // 条件如果小于或等于当前控件的宽度，则可以添加，否则不能添加
        int totalWidth = itemView.getMeasuredWidth();
        for (View view : line) {
            // 叠加所有已经添加控件的宽度
            totalWidth += view.getMeasuredWidth();
        }

        // +水平间距的宽度
        totalWidth += (line.size() + 1) * mItemHorizontalSpace;
        LogUtils.d(this, " totalWidth --> " + totalWidth + " mSelfWidth --> " + mSelfWidth);

        // 如果小于或等于当前控件的宽度，则可以添加，否则不能添加
        return totalWidth <= mSelfWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放(子)控件
        LogUtils.d(this, "onLayout --> " + getChildCount());

        int topOffset = (int) mItemHorizontalSpace;
        for (List<View> views : lines) {
            // views是每一行
            int leftOffset = (int) mItemHorizontalSpace;
            for (View view : views) {
                // 每一行里的每一个item
                view.layout(leftOffset, topOffset, leftOffset + view.getMeasuredWidth(), topOffset
                        + view.getMeasuredHeight());
                // 左边的间距随之增加
                leftOffset += view.getMeasuredWidth() + mItemHorizontalSpace;
            }
            // 上边间距随之增加
            topOffset += mItemHeight + mItemHorizontalSpace;
        }
    }

    /**
     * 供外界点击事件的设置
     * @param listener
     */
    public void setOnFlowTextItemClickListener(OnFlowTextItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * 点击事件传递接口
     */
    public interface OnFlowTextItemClickListener {
        void onFlowItemClick(String text);
    }
}
