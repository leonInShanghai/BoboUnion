package com.bobo.union.presenter.impl;

import com.bobo.union.model.Api;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.presenter.ICategoryPagerPresenter;
import com.bobo.union.utils.LogUtils;
import com.bobo.union.utils.RetrofitManage;
import com.bobo.union.utils.UrlUtils;
import com.bobo.union.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Leon on 2020-08-30 Copyright © Leon. All rights reserved.
 * Functions: 首页下的各个子页面（其实是同一个碎片new了多次）presenter实现 impl 实现的意识
 */
public class CategoryPagePresenterImpl implements ICategoryPagerPresenter {

    // 这个Presenter会被多个地方引用 回调集合
    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();

    // 根据当前id获取和保存对应的 page
    private Map<Integer, Integer> pagesInfo = new HashMap<>();
    
    // 请求分页默认是1
    public static final int DEFAULT_PAGE = 1;
    
    public static CategoryPagePresenterImpl sInstance = null;

    /**
     * 单例标配 私有的构造方法
     */
    private CategoryPagePresenterImpl() {

    }

    /**
     * 懒汉式单例 不到万不得已不实例化自己
     * @return
     */
    public static CategoryPagePresenterImpl getInstance() {

        // 注意加锁避免线程安全风险
        if (sInstance == null) {
            synchronized (CategoryPagePresenterImpl.class) {
                if (sInstance == null) {
                    sInstance = new CategoryPagePresenterImpl();
                }
            }

        }

        return sInstance;
    }

    @Override
    public void getContentByCategoryId(final int categoryId) {

        // 开始请求网络此时状态是loading
        for (ICategoryPagerCallback callback : callbacks) {
            // 判断是否是发起请求的页面是才回调
            if (callback.getCategoryId() == categoryId) {
                callback.onLoading();
            }
        }

        // 根据分类id去加载对应的内容
        Retrofit retrofit = RetrofitManage.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        
        Integer targetPage = pagesInfo.get(categoryId);
        
        if (targetPage == null){
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId, targetPage);
        }
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(this, "homePageUrl == " + homePagerUrl);

        Call<HomePagerContent> task = api.getHomePagerContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(CategoryPagePresenterImpl.this, "code --> " + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContent pageContent = response.body();
                    LogUtils.d(CategoryPagePresenterImpl.this, "pageConnect --> " + pageContent.toString());
                    // 把数据给到UI更新
                    handleHomePageContentResult(pageContent, categoryId);
                } else {
                    LogUtils.d(CategoryPagePresenterImpl.this, "errorCode --> " + code);

                    // 通知ui网络错误
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LogUtils.d(CategoryPagePresenterImpl.this, "errorMessage --> " + t.toString());
                // 通知ui网络错误
                handleNetworkError(categoryId);
            }
        });
    }

    /**
     * 通知ui网络错误
     */
    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            // 判断是否是发起请求的页面是才回调
            if (callback.getCategoryId() == categoryId) {
                callback.onError();
            }
        }
    }

    /**
     * 把数据给到UI更新
     * @param pageContent
     */
    private void handleHomePageContentResult(HomePagerContent pageContent, int categoryId) {

        List<HomePagerContent.DataBean> data = pageContent.getData();
        // 通知ui层更新数据
        for (ICategoryPagerCallback callback : callbacks) {
            // 判断是否是发起请求的页面是才回调
            if (callback.getCategoryId() == categoryId) {

                if (pageContent == null || pageContent.getData() == null ||
                        pageContent.getData().size() == 0) {
                    // 加载成功但是内容为空
                    callback.onEmpty();
                } else {

                    List<HomePagerContent.DataBean> looperData = data.subList(data.size() - 5,
                            data.size());
                    callback.onLooperListLoaded(looperData);
                    // 加载成功并且有数据
                    callback.onContentLoaded(data);
                }

            }
        }
    }

    @Override
    public void loaderMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }


    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {
        // 先判断是否存在
        if (!callbacks.contains(callback)) {
            // 如果集合中没有就添加到集合中
            callbacks.add(callback);
        }
    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {
        // 判断预防空指针
        if (callback != null) {
            // 移除回调
            callbacks.remove(callback);
        }
    }
}
