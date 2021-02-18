package com.bobo.union.utils;

/**
 * Created by Leon on 2020-08-30 Copyright © Leon. All rights reserved.
 * Functions: 这是一个专门用于拼接字符串的工具类
 */
public class UrlUtils {

    /**
     * 拼接首页下个各个子页面请求的url
     *
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
     *
     * @param pict_url
     * @return http://gw.alicdn.com/bao/uploaded/i1/2200812029962/O1CN01C6QZ1a2NSeCN64glY_!!0-item_
     * pic.jpg_140x140.jpg
     */
    public static String getCoverPath(String pict_url, int size) {
        if (pict_url.startsWith("http") || pict_url.startsWith("https")) {
            return pict_url + "_" + size + "x" + size + ".jpg";
        } else {
            return "https:" + pict_url + "_" + size + "x" + size + ".jpg";
        }
    }

    /**
     * FIXME:
     * 全路径获取图片Url 这里获取到的图片比较大 尽量使用getCoverPath(String pict_url, int size)
     * @param pict_url
     * @return
     */
    public static String getCoverPath(String pict_url) {
        if (pict_url.startsWith("http") || pict_url.startsWith("https")) {
            return pict_url;
        } else {
            return "https:" + pict_url;
        }
    }

    public static String getTicketUrl(String url) {
        if (url.startsWith("http") || url.startsWith("https")) {
            return url;
        } else {
            return "https:" + url;
        }
    }

    /**
     * 拼接精选详情内容URL
     * @param categoryId
     */
    public static String getSelectedPageContentUrl(int categoryId) {
        return "recommend/" + categoryId;
    }

    /**
     * 拼接特惠请求url 加载更多时要分页的
     * @param currentPage
     */
    public static String getOnSellPageUrl(int currentPage) {
        return "onSell/" + currentPage;
    }
}

