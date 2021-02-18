package com.bobo.union.model.doman;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/1/31 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class SelectedContent {


    /**
     * success : true
     * code : 10000
     * message : 获取精选内容成功.
     * data : {"tbk_dg_optimus_material_response":{"result_list":{"favoriteId":0,"map_data":[{"click_url":
     * "https://s.click.taobao.com/t?e=m%3D2%26s%3DQqgvXVwF4QAcQipKwQzePOeEDrYVVa64yK8Cckff7TXjf2vlNIV67lbA7kV
     * j6DOjxeoNewupcd6YzwUb1bbXppZwHxYkBYTUeP8T%2BYUOOiuIOBqu0asPv%2F0jCkSDmD6DKe3%2FbelnU7chre9gvq5P3zKYVV1BI
     * d1rfp5HoTJX0vWplyh2W4e4BPxr1c6V5LsG1hjz2dNwkcRt%2ByMzJ8mpM%2Fe7B5ZkP8EjSdChf3U3iXY%2B5QowgvHJPA%3D%3D&u
     * nid=19902751&union_lens=lensId:0b012096_0c74_16ec657c76b_e230","coupon_click_url":"https://uland.taobao.
     * com/coupon/edetail?e=TBIJTibAhO4GQASttHIRqQA9fN4MifAG7tS89T6GqASOeUcSvy%2FHYmF5qFnaO9996DeX2ucVKhvHjZlr
     * d41oqDEL25HyNw0xJ4FRquMv%2FTtFRuNP9Tp9RQH%2FlwNDrBX3XYsE5g93lTYZao0HO%2FvX0w%3D%3D&union_lens=lensId:0b
     * 012096_0c74_16ec657c76b_e230","coupon_end_time":"2019-12-07","coupon_info":"满7元减5元","coupon_remain_c
     * ount":99635,"coupon_start_time":"2019-12-01","coupon_total_count":100000,"event_end_time":"1970-01-01 0
     * 0:00:00","event_start_time":"1970-01-01 00:00:00","item_url":"https://item.taobao.com/item.htm?id=59518
     * 4094985","num_iid":595184094985,"pict_url":"https://img.alicdn.com/tfscom/i1/1744991291/O1CN01J58x6I1LP
     * JqtM61gh_!!0-item_pic.jpg","reserve_price":"45.00","status":1,"title":"纳雷特原装正品适用 OPPO数据线闪充R9
     * R15 R11s R11 R11s plus R9s R7安卓手机原厂快充短充电器头线2米加长","tk_rate":"9.00","type":4,"user_type":1,"
     * volume":11450,"zk_final_price":"10.90","zk_final_price_wap":"10.90"}]},"total_result_list":26,"request_id":
     * "sa9tsfbr1uco"}}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SelectedContent{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {

        /**
         * tbk_dg_optimus_material_response : {"result_list":{"favoriteId":0,"map_data":[{"click_url":"https://s.
         * click.taobao.com/t?e=m%3D2%26s%3DQqgvXVwF4QAcQipKwQzePOeEDrYVVa64yK8Cckff7TXjf2vlNIV67lbA7kVj6DOjxeoNewupc
         * d6YzwUb1bbXppZwHxYkBYTUeP8T%2BYUOOiuIOBqu0asPv%2F0jCkSDmD6DKe3%2FbelnU7chre9gvq5P3zKYVV1BId1rfp5HoTJX0vWpl
         * yh2W4e4BPxr1c6V5LsG1hjz2dNwkcRt%2ByMzJ8mpM%2Fe7B5ZkP8EjSdChf3U3iXY%2B5QowgvHJPA%3D%3D&unid=19902751&union_l
         * ens=lensId:0b012096_0c74_16ec657c76b_e230","coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=TB
         * IJTibAhO4GQASttHIRqQA9fN4MifAG7tS89T6GqASOeUcSvy%2FHYmF5qFnaO9996DeX2ucVKhvHjZlrd41oqDEL25HyNw0xJ4FRquMv%2F
         * TtFRuNP9Tp9RQH%2FlwNDrBX3XYsE5g93lTYZao0HO%2FvX0w%3D%3D&union_lens=lensId:0b012096_0c74_16ec657c76b_e230",
         * "coupon_end_time":"2019-12-07","coupon_info":"满7元减5元","coupon_remain_count":99635,"coupon_start_time":"2
         * 019-12-01","coupon_total_count":100000,"event_end_time":"1970-01-01 00:00:00","event_start_time":"1970-01-01
         * 00:00:00","item_url":"https://item.taobao.com/item.htm?id=595184094985","num_iid":595184094985,"pict_url":
         * "https://img.alicdn.com/tfscom/i1/1744991291/O1CN01J58x6I1LPJqtM61gh_!!0-item_pic.jpg","reserve_price":"45.
         * 00","status":1,"title":"纳雷特原装正品适用 OPPO数据线闪充R9 R15 R11s R11 R11s plus R9s R7安卓手机原厂快充短充电器
         * 头线2米加长","tk_rate":"9.00","type":4,"user_type":1,"volume":11450,"zk_final_price":"10.90","zk_final_price_
         * wap":"10.90"}]},"total_result_list":26,"request_id":"sa9tsfbr1uco"}
         */

        // tbk_dg_optimus_material_response
        private TbkUatmFavoritesItemGetResponseBean tbk_dg_optimus_material_response;

        public TbkUatmFavoritesItemGetResponseBean gettbk_dg_optimus_material_response() {
            return tbk_dg_optimus_material_response;
        }

        public void settbk_dg_optimus_material_response(TbkUatmFavoritesItemGetResponseBean tbk_dg_optimus_material_response) {
            this.tbk_dg_optimus_material_response = tbk_dg_optimus_material_response;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "tbk_dg_optimus_material_response=" + tbk_dg_optimus_material_response +
                    '}';
        }

        public static class TbkUatmFavoritesItemGetResponseBean {
            /**
             * result_list : {"favoriteId":0,"map_data":[{"click_url":"https://s.click.taobao.com/t?e=m%3D2%26s%3DQqgv
             * XVwF4QAcQipKwQzePOeEDrYVVa64yK8Cckff7TXjf2vlNIV67lbA7kVj6DOjxeoNewupcd6YzwUb1bbXppZwHxYkBYTUeP8T%2BYUOOi
             * uIOBqu0asPv%2F0jCkSDmD6DKe3%2FbelnU7chre9gvq5P3zKYVV1BId1rfp5HoTJX0vWplyh2W4e4BPxr1c6V5LsG1hjz2dNwkcRt%2
             * ByMzJ8mpM%2Fe7B5ZkP8EjSdChf3U3iXY%2B5QowgvHJPA%3D%3D&unid=19902751&union_lens=lensId:0b012096_0c74_16ec6
             * 57c76b_e230","coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=TBIJTibAhO4GQASttHIRqQA9fN4Mi
             * fAG7tS89T6GqASOeUcSvy%2FHYmF5qFnaO9996DeX2ucVKhvHjZlrd41oqDEL25HyNw0xJ4FRquMv%2FTtFRuNP9Tp9RQH%2FlwNDrBX
             * 3XYsE5g93lTYZao0HO%2FvX0w%3D%3D&union_lens=lensId:0b012096_0c74_16ec657c76b_e230","coupon_end_time":"2019
             * -12-07","coupon_info":"满7元减5元","coupon_remain_count":99635,"coupon_start_time":"2019-12-01","coupon_t
             * otal_count":100000,"event_end_time":"1970-01-01 00:00:00","event_start_time":"1970-01-01 00:00:00","item_
             * url":"https://item.taobao.com/item.htm?id=595184094985","num_iid":595184094985,"pict_url":"https://img.al
             * icdn.com/tfscom/i1/1744991291/O1CN01J58x6I1LPJqtM61gh_!!0-item_pic.jpg","reserve_price":"45.00","status"
             * :1,"title":"纳雷特原装正品适用 OPPO数据线闪充R9 R15 R11s R11 R11s plus R9s R7安卓手机原厂快充短充电器头线2米加长
             * ","tk_rate":"9.00","type":4,"user_type":1,"volume":11450,"zk_final_price":"10.90","zk_final_price_wap":"
             * 10.90"}]}
             * total_result_list : 26
             * request_id : sa9tsfbr1uco
             */

            private result_listBean result_list;
            private int total_result_list;
            private String request_id;

            public result_listBean getresult_list() {
                return result_list;
            }

            public void setresult_list(result_listBean result_list) {
                this.result_list = result_list;
            }

            public int getTotal_result_list() {
                return total_result_list;
            }

            public void setTotal_result_list(int total_result_list) {
                this.total_result_list = total_result_list;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            @Override
            public String toString() {
                return "TbkUatmFavoritesItemGetResponseBean{" +
                        "result_list=" + result_list +
                        ", total_result_list=" + total_result_list +
                        ", request_id='" + request_id + '\'' +
                        '}';
            }

            public static class result_listBean {
                /**
                 * favoriteId : 0
                 * map_data : [{"click_url":"https://s.click.taobao.com/t?e=m%3D2%26s%3DQqgvXVwF4QAcQipKwQzePOeEDr
                 * YVVa64yK8Cckff7TXjf2vlNIV67lbA7kVj6DOjxeoNewupcd6YzwUb1bbXppZwHxYkBYTUeP8T%2BYUOOiuIOBqu0asPv%2F0jCk
                 * SDmD6DKe3%2FbelnU7chre9gvq5P3zKYVV1BId1rfp5HoTJX0vWplyh2W4e4BPxr1c6V5LsG1hjz2dNwkcRt%2ByMzJ8mpM%2Fe7
                 * B5ZkP8EjSdChf3U3iXY%2B5QowgvHJPA%3D%3D&unid=19902751&union_lens=lensId:0b012096_0c74_16ec657c76b_e23
                 * 0","coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=TBIJTibAhO4GQASttHIRqQA9fN4MifAG7tS
                 * 89T6GqASOeUcSvy%2FHYmF5qFnaO9996DeX2ucVKhvHjZlrd41oqDEL25HyNw0xJ4FRquMv%2FTtFRuNP9Tp9RQH%2FlwNDrBX3X
                 * YsE5g93lTYZao0HO%2FvX0w%3D%3D&union_lens=lensId:0b012096_0c74_16ec657c76b_e230","coupon_end_time":"2
                 * 019-12-07","coupon_info":"满7元减5元","coupon_remain_count":99635,"coupon_start_time":"2019-12-01","c
                 * oupon_total_count":100000,"event_end_time":"1970-01-01 00:00:00","event_start_time":"1970-01-01 00:0
                 * 0:00","item_url":"https://item.taobao.com/item.htm?id=595184094985","num_iid":595184094985,"pict_url
                 * ":"https://img.alicdn.com/tfscom/i1/1744991291/O1CN01J58x6I1LPJqtM61gh_!!0-item_pic.jpg","reserve_pr
                 * ice":"45.00","status":1,"title":"纳雷特原装正品适用 OPPO数据线闪充R9 R15 R11s R11 R11s plus R9s R7安卓手
                 * 机原厂快充短充电器头线2米加长","tk_rate":"9.00","type":4,"user_type":1,"volume":11450,"zk_final_price":
                 * "10.90","zk_final_price_wap":"10.90"}]
                 */

                private int favoriteId;
                private List<UatmTbkItemBean> map_data;

                public int getFavoriteId() {
                    return favoriteId;
                }

                public void setFavoriteId(int favoriteId) {
                    this.favoriteId = favoriteId;
                }

                public List<UatmTbkItemBean> getmap_data() {
                    return map_data;
                }

                public void setmap_data(List<UatmTbkItemBean> map_data) {
                    this.map_data = map_data;
                }

                @Override
                public String toString() {
                    return "result_listBean{" +
                            "favoriteId=" + favoriteId +
                            ", map_data=" + map_data +
                            '}';
                }

                public static class UatmTbkItemBean implements IBaseInfo{
                    /**
                     * click_url : https://s.click.taobao.com/t?e=m%3D2%26s%3DQqgvXVwF4QAcQipKwQzePOeEDrYVVa64yK8Cckff7
                     * TXjf2vlNIV67lbA7kVj6DOjxeoNewupcd6YzwUb1bbXppZwHxYkBYTUeP8T%2BYUOOiuIOBqu0asPv%2F0jCkSDmD6DKe3%2
                     * FbelnU7chre9gvq5P3zKYVV1BId1rfp5HoTJX0vWplyh2W4e4BPxr1c6V5LsG1hjz2dNwkcRt%2ByMzJ8mpM%2Fe7B5ZkP8E
                     * jSdChf3U3iXY%2B5QowgvHJPA%3D%3D&unid=19902751&union_lens=lensId:0b012096_0c74_16ec657c76b_e230
                     * coupon_click_url : https://uland.taobao.com/coupon/edetail?e=TBIJTibAhO4GQASttHIRqQA9fN4MifAG7tS
                     * 89T6GqASOeUcSvy%2FHYmF5qFnaO9996DeX2ucVKhvHjZlrd41oqDEL25HyNw0xJ4FRquMv%2FTtFRuNP9Tp9RQH%2FlwNDr
                     * BX3XYsE5g93lTYZao0HO%2FvX0w%3D%3D&union_lens=lensId:0b012096_0c74_16ec657c76b_e230
                     * coupon_end_time : 2019-12-07
                     * coupon_info : 满7元减5元
                     * coupon_remain_count : 99635
                     * coupon_start_time : 2019-12-01
                     * coupon_total_count : 100000
                     * event_end_time : 1970-01-01 00:00:00
                     * event_start_time : 1970-01-01 00:00:00
                     * item_url : https://item.taobao.com/item.htm?id=595184094985
                     * num_iid : 595184094985
                     * pict_url : https://img.alicdn.com/tfscom/i1/1744991291/O1CN01J58x6I1LPJqtM61gh_!!0-item_pic.jpg
                     * reserve_price : 45.00
                     * status : 1
                     * title : 纳雷特原装正品适用 OPPO数据线闪充R9 R15 R11s R11 R11s plus R9s R7安卓手机原厂快充短充电器头线2
                     * 米加长
                     * tk_rate : 9.00
                     * type : 4
                     * user_type : 1
                     * volume : 11450
                     * zk_final_price : 10.90
                     * zk_final_price_wap : 10.90
                     */

                    private String click_url;
                    private String coupon_click_url;
                    private String coupon_end_time;
                    private String coupon_info;
                    private int coupon_remain_count;
                    private String coupon_start_time;
                    private int coupon_total_count;
                    private String event_end_time;
                    private String event_start_time;
                    private String item_url;
                    private long num_iid;
                    private String pict_url;
                    private String reserve_price;
                    private int status;
                    private String title;
                    private String tk_rate;
                    private int type;
                    private int user_type;
                    private int volume;
                    private String zk_final_price;
                    private String zk_final_price_wap;

                    public String getClick_url() {
                        return click_url;
                    }

                    public void setClick_url(String click_url) {
                        this.click_url = click_url;
                    }

                    public String getCoupon_click_url() {
                        return coupon_click_url;
                    }

                    public void setCoupon_click_url(String coupon_click_url) {
                        this.coupon_click_url = coupon_click_url;
                    }

                    public String getCoupon_end_time() {
                        return coupon_end_time;
                    }

                    public void setCoupon_end_time(String coupon_end_time) {
                        this.coupon_end_time = coupon_end_time;
                    }

                    public String getCoupon_info() {
                        return coupon_info;
                    }

                    public void setCoupon_info(String coupon_info) {
                        this.coupon_info = coupon_info;
                    }

                    public int getCoupon_remain_count() {
                        return coupon_remain_count;
                    }

                    public void setCoupon_remain_count(int coupon_remain_count) {
                        this.coupon_remain_count = coupon_remain_count;
                    }

                    public String getCoupon_start_time() {
                        return coupon_start_time;
                    }

                    public void setCoupon_start_time(String coupon_start_time) {
                        this.coupon_start_time = coupon_start_time;
                    }

                    public int getCoupon_total_count() {
                        return coupon_total_count;
                    }

                    public void setCoupon_total_count(int coupon_total_count) {
                        this.coupon_total_count = coupon_total_count;
                    }

                    public String getEvent_end_time() {
                        return event_end_time;
                    }

                    public void setEvent_end_time(String event_end_time) {
                        this.event_end_time = event_end_time;
                    }

                    public String getEvent_start_time() {
                        return event_start_time;
                    }

                    public void setEvent_start_time(String event_start_time) {
                        this.event_start_time = event_start_time;
                    }

                    public String getItem_url() {
                        return item_url;
                    }

                    public void setItem_url(String item_url) {
                        this.item_url = item_url;
                    }

                    public long getNum_iid() {
                        return num_iid;
                    }

                    public void setNum_iid(long num_iid) {
                        this.num_iid = num_iid;
                    }

                    public String getPict_url() {
                        return pict_url;
                    }

                    public void setPict_url(String pict_url) {
                        this.pict_url = pict_url;
                    }

                    public String getReserve_price() {
                        return reserve_price;
                    }

                    public void setReserve_price(String reserve_price) {
                        this.reserve_price = reserve_price;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    @Override
                    public String getCover() {
                        return pict_url;
                    }

                    public String getTitle() {
                        return title;
                    }

                    @Override
                    public String getUrl() {
                        // coupon_click_url:领券页url
                        // click_url：商品页url
                        return coupon_click_url == null ? click_url : coupon_click_url;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getTk_rate() {
                        return tk_rate;
                    }

                    public void setTk_rate(String tk_rate) {
                        this.tk_rate = tk_rate;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public int getUser_type() {
                        return user_type;
                    }

                    public void setUser_type(int user_type) {
                        this.user_type = user_type;
                    }

                    public int getVolume() {
                        return volume;
                    }

                    public void setVolume(int volume) {
                        this.volume = volume;
                    }

                    public String getZk_final_price() {
                        return zk_final_price;
                    }

                    public void setZk_final_price(String zk_final_price) {
                        this.zk_final_price = zk_final_price;
                    }

                    public String getZk_final_price_wap() {
                        return zk_final_price_wap;
                    }

                    public void setZk_final_price_wap(String zk_final_price_wap) {
                        this.zk_final_price_wap = zk_final_price_wap;
                    }

                    @Override
                    public String toString() {
                        return "UatmTbkItemBean{" +
                                "click_url='" + click_url + '\'' +
                                ", coupon_click_url='" + coupon_click_url + '\'' +
                                ", coupon_end_time='" + coupon_end_time + '\'' +
                                ", coupon_info='" + coupon_info + '\'' +
                                ", coupon_remain_count=" + coupon_remain_count +
                                ", coupon_start_time='" + coupon_start_time + '\'' +
                                ", coupon_total_count=" + coupon_total_count +
                                ", event_end_time='" + event_end_time + '\'' +
                                ", event_start_time='" + event_start_time + '\'' +
                                ", item_url='" + item_url + '\'' +
                                ", num_iid=" + num_iid +
                                ", pict_url='" + pict_url + '\'' +
                                ", reserve_price='" + reserve_price + '\'' +
                                ", status=" + status +
                                ", title='" + title + '\'' +
                                ", tk_rate='" + tk_rate + '\'' +
                                ", type=" + type +
                                ", user_type=" + user_type +
                                ", volume=" + volume +
                                ", zk_final_price='" + zk_final_price + '\'' +
                                ", zk_final_price_wap='" + zk_final_price_wap + '\'' +
                                '}';
                    }
                }
            }
        }
    }
}
