package com.bobo.union.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.union.R;
import com.bobo.union.base.BaseActivity;
import com.bobo.union.model.doman.TicketResult;
import com.bobo.union.presenter.ITikcetPresenter;
import com.bobo.union.ui.custom.LoadingView;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.PresenterManager;
import com.bobo.union.utils.ToastUtil;
import com.bobo.union.utils.UrlUtils;
import com.bobo.union.view.ITicketPagerCallback;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * Created by 公众号：IT波 on 2021/1/16 Copyright ? Leon. All rights reserved.
 * Functions: 淘口令页面
 * Ticket          票
 * plus            加
 * practice        实践
 * triangle        三角形
 * skirt           裙子
 * speak           说话
 * stop            停止
 * crow            乌鸦
 * price           价格
 * stars           星星
 * sports          体育
 * sky             天空
 * train           火车
 * plant           植物
 * hundred         一百
 * thousand        千
 * million        百万
 * billion        十亿
 * half           一半
 * three-quarter 四分之三
 */
public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private ITikcetPresenter mTicketPresenter;
    private boolean mHasTaobaoApp = false;

    @BindView(R.id.ticket_cover)
    public ImageView mCover;

    @BindView(R.id.ticket_back_press)
    public ImageView backPress;

    @BindView(R.id.ticket_code)
    public EditText mTicketCode;

    @BindView(R.id.ticket_copy_or_open_btn)
    public TextView mOpenOrCopyBtn;

    @BindView(R.id.ticket_cover_loading)
    public LoadingView loadingView;

    @BindView(R.id.ticket_load_retry)
    public TextView retryLoadText;

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresenterManager.getInstance().getTicketPressenter();
        if (mTicketPresenter != null) {
            mTicketPresenter.registerViewCallback(this);
        }
        // 判断用户手机是否有安装淘宝 com.taobao.taobao
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao",
                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
            mHasTaobaoApp = packageInfo != null;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mHasTaobaoApp = false;
        }
        // LogUtils.d(this, "mHasTaobaoApp --> " + mHasTaobaoApp);
        // 根据用户手机里是否有淘宝app显示不同的文本
        mOpenOrCopyBtn.setText(mHasTaobaoApp ? "打开淘宝领券" : "复制淘口令");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mOpenOrCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 复制淘口令
                String ticketCode = mTicketCode.getText().toString().trim();
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 复制到粘贴板
                ClipData clipData = ClipData.newPlainText("leon_taobao_ticket_code", ticketCode);
                cm.setPrimaryClip(clipData);

                // 判断用户手机中是否有淘宝
                if (mHasTaobaoApp) {
                    // 有淘宝就打开淘宝
                    Intent taobaoIntent = new Intent();
                    // taobaoIntent.setAction("android.intent.action.Main");
                    // taobaoIntent.addCategory("Android.intent.catrgory.LAUNCHER");
                    ComponentName componentName = new ComponentName("com.taobao.taobao",
                            "com.taobao.tao.TBMainActivity");
                    taobaoIntent.setComponent(componentName);
                    startActivity(taobaoIntent);
                } else {
                    // 没有淘宝仅仅提示一下用户就可以了
                    ToastUtil.showToast("已复制,粘贴分享或打开淘宝");
                }

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {

        // 设置图片
        if (mCover != null && !TextUtils.isEmpty(cover)) {
            mCover.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = mCover.getLayoutParams();
            int width = layoutParams.width;
            int height = layoutParams.height;
            LogUtils.d(this, "cover width --> " + width + " cover height --> " + height);
            String coverPath = UrlUtils.getCoverPath(cover, 300);
            Glide.with(this).load(coverPath).placeholder(R.mipmap.bobo_launch).into(mCover);
        } else {
            if (mCover != null) {
                mCover.setVisibility(View.VISIBLE);
                mCover.setImageResource(R.mipmap.no_image);
            }
        }

        // 设置淘口令
        if (result != null && result.getData().getTbk_tpwd_create_response() != null) {
            mTicketCode.setText(result.getData().getTbk_tpwd_create_response().getData().getModel());
        }

        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.VISIBLE);
            // retryLoadText.setOnClickListener();
        }
    }

    @Override
    public void onLoading() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEmpty() {
        // 这里没有做没有此回调
        // if (loadingView != null) {
        //     loadingView.setVisibility(View.GONE);
        // }
        // if (retryLoadText != null) {
        //     retryLoadText.setVisibility(View.GONE);
        // }
    }

    @Override
    protected void release() {
        if (mTicketPresenter != null) {
            // 解绑presenter
            mTicketPresenter.unregisterViewCallback(this);
        }
    }
}
