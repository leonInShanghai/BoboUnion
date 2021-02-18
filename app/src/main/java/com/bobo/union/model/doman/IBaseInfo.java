package com.bobo.union.model.doman;

/**
 * Created by 公众号：IT波 on 2021/2/18 Copyright © Leon. All rights reserved.
 * Functions: 跳转到淘口令界面所必须的参数
 * 需要原来的java bean对象实现这个接口 例如HomePagerFragment： DataBean implements IBaseInfo
 */
public interface IBaseInfo {

    /**
     * 获取商品的封面 url
     * @return
     */
    String getCover();

    /**
     * 商品的标题
     * @return
     */
    String getTitle();

    /**
     * 商品的淘口令Url
     * @return
     */
    String getUrl();
}
