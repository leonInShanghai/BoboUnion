package com.bobo.union.model;

import com.bobo.union.model.doman.Categories;
import com.bobo.union.model.doman.HomePagerContent;
import com.bobo.union.model.doman.OnSellContent;
import com.bobo.union.model.doman.SearchRecommend;
import com.bobo.union.model.doman.SearchResult;
import com.bobo.union.model.doman.SelectedContent;
import com.bobo.union.model.doman.SelectedPageCategory;
import com.bobo.union.model.doman.TicketParams;
import com.bobo.union.model.doman.TicketResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Leon on 2020-08-22 Copyright © Leon. All rights reserved.
 * Functions: 网络请求api文档： https://www.sunofbeach.net/a/1201366916766224384
 */
public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    // 非固定式url 写法
    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);

    @POST("tpwd")
    Call<TicketResult> getTicket(@Body TicketParams ticketParams);

    @GET("recommend/categories")
    Call<SelectedPageCategory> getSelectedPageCategories();

    @GET
    Call<SelectedContent> getSelectedPageContent(@Url String url);

    @GET
    Call<OnSellContent> getOnSellPageContent(@Url String url);

    @GET("search/recommend")
    Call<SearchRecommend> getRecommendWords();

    @GET("search")
    Call<SearchResult> doSourch(@Query("page") int page, @Query("keyword") String keyword);
}


/**
 * What is This
 * what is this? this is a bowl.
 * what is this? this is a spoon
 * what is that? that is a blanket.
 * what is that? that is a phone.
 * what are these? these are flower.
 * what are these? these are photos.
 * what are those? those are gloves.
 * what are those? those are snacks.
 *
 * o'clock == of the clock
 */
