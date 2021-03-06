package com.bobo.union.model.doman;

/**
 * Created by 公众号：IT波 on 2021/3/5 Copyright © Leon. All rights reserved.
 * Functions: 为了使得 搜索页和首页 重构 不同的数据bean抽取接口
 */
public interface ILinearItemInfo extends IBaseInfo {

    /**
     * 获取原价
     * @return
     */
    String getFinalPrice();

    /**
     * 获取优惠力度(优惠价格)
     * @return
     */
    long getCouponAmount();

    /**
     * 获取销量
     * @return
     */
    long getVolume();
}
