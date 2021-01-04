package com.bobo.union.utils;

/**
 * Created by Leon on 2020-08-30 Copyright © Leon. All rights reserved.
 * Functions: 这是一个专门用于拼接字符串的工具类
 */
public class UrlUtils {

    /**
     * 拼接首页下个各个子页面请求的url
     * @param materiaId
     * @param page
     * @return
     */
    public static String createHomePagerUrl(int materiaId, int page) {
        return "discovery/" + materiaId + "/" + page;
    }

    /**
     * 拼接商品图片url 网络请求回来的如下：
     * //gw.alicdn.com/bao/uploaded/i1/2200812029962/O1CN01C6QZ1a2NSeCN64glY_!!0-item_pic.jpg
     * 要加工才能用
     * @param pict_url
     * @return
     */
    public static String getCoverPath(String pict_url) {
        return "https:" + pict_url;
    }
}
