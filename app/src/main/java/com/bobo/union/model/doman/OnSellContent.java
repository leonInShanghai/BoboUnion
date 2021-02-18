package com.bobo.union.model.doman;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/2/15 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class OnSellContent {

    /**
     * success : true
     * code : 10000
     * message : 获取特惠成功.
     * data : {"tbk_dg_optimus_material_response":{"is_default":"false","result_list":{"map_data":[{"category_id":
     * 50009211,"category_name":null,"click_url":"//s.click.taobao.com/t?e=m%3D2%26s%3D9biiuNCEs2lw4vFB6t2Z2ueEDrYVVa64
     * \Dne87AjQPk9yINtkUhsv0AwIMIR498vZNnGHDv9u8g5m5nS0VR3oT0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j5
     * QNerBM5mSXVLdYGR5XowAWssShUjNLx%2BdusJxCFYEy%2FnzFmH8P00Bp%2FSdChf3U3iXY%2B5QowgvHJPA%3D%3D&scm=1007.15348.11505
     * 8.0_3756&pvid=7a2d1a71-4355-4f43-8481-a11efc88d87c&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3
     * 756;originalFloorId:3756;pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c;app_pvid:59590_11.132.118.137_598_160778478929
     * 3&union_lens=lensId%3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401","commission_rat
     * e":"9.0","coupon_amount":10,"coupon_click_url":"//uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2jJN
     * KOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgGZ9ZhjSjHmMHp
     * NfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%3D&&app_pvid=59590_1
     * 1.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.137_598_1607784789293;tpp_pvid:7a2d1a7
     * 1-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128
     * 169764%401","coupon_end_time":"1608220799000","coupon_info":null,"coupon_remain_count":49477,"coupon_share_url":
     * "//uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2jJNKOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEA
     * vHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgGZ9ZhjSjHmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSB
     * jM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756
     * ;app_pvid:59590_11.132.118.137_598_1607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%
     * 3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401","coupon_start_fee":"39.0","coupon_st
     * art_time":"1607616000000","coupon_total_count":50000,"item_description":"智能数显 大容量 快输出 小巧便携 可上飞机","
     * item_id":587128169764,"level_one_category_id":50008090,"level_one_category_name":"3C数码配件","nick":"耐时旗舰店",
     * "pict_url":"//img.alicdn.com/bao/uploaded/i1/1620417126/O1CN01qprPxB22Vl0WCYqdL_!!0-item_pic.jpg","seller_id":16
     * 20417126,"shop_title":null,"small_images":{"string":["//img.alicdn.com/i4/1620417126/O1CN01UgLSwk22VkxmlyCVR_!!0
     * -item_pic.jpg","//img.alicdn.com/i2/1620417126/O1CN0162lfIS22VkrSKYDXg_!!1620417126.jpg","//img.alicdn.com/i1/16
     * 20417126/O1CN01c69sps22VkxPxWAGO_!!1620417126.jpg","//img.alicdn.com/i4/1620417126/O1CN01OkQzU522VkrWiL9PW_!!162
     * 0417126.jpg"]},"title":"耐时充电宝超薄小巧便携快充闪充适用于vivo华为oppo苹果充电宝","user_type":1,"volume":1754,"zk_fi
     * nal_price":"39.9"}]},"request_id":"5nypm8p8ex8z"}}
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

    public static class DataBean {
        /**
         * tbk_dg_optimus_material_response : {"is_default":"false","result_list":{"map_data":[{"category_id":50009211,
         * "category_name":null,"click_url":"//s.click.taobao.com/t?e=m%3D2%26s%3D9biiuNCEs2lw4vFB6t2Z2ueEDrYVVa64Dne
         * 87AjQPk9yINtkUhsv0AwIMIR498vZNnGHDv9u8g5m5nS0VR3oT0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j
         * 5QNerBM5mSXVLdYGR5XowAWssShUjNLx%2BdusJxCFYEy%2FnzFmH8P00Bp%2FSdChf3U3iXY%2B5QowgvHJPA%3D%3D&scm=1007.15348.
         * 115058.0_3756&pvid=7a2d1a71-4355-4f43-8481-a11efc88d87c&app_pvid=59590_11.132.118.137_598_1607784789293&ptl
         * floorId:3756;originalFloorId:3756;pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c;app_pvid:59590_11.132.118.137_59
         * 8_1607784789293&union_lens=lensId%3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401
         * ","commission_rate":"9.0","coupon_amount":10,"coupon_click_url":"//uland.taobao.com/coupon/edetail?e=LAAI0BUR
         * fKYNfLV8niU3R5TgU2jJNKOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0
         * MDY5xlXkOeMFgGZ9ZhjSjHmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJH
         * cLCJg%3D%3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.137_
         * 598_1607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401607784789%407a2d1
         * a71-4355-4f43-8481-a11efc88d87c_587128169764%401","coupon_end_time":"1608220799000","coupon_info":null,"coup
         * on_remain_count":49477,"coupon_share_url":"//uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2jJN
         * KOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgGZ9ZhjSj
         * HmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%3D&&app_pvi
         * d=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.137_598_1607784789293;tp
         * p_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-
         * a11efc88d87c_587128169764%401","coupon_start_fee":"39.0","coupon_start_time":"1607616000000","coupon_total_c
         * ount":50000,"item_description":"智能数显 大容量 快输出 小巧便携 可上飞机","item_id":587128169764,"level_one_cate
         * gory_id":50008090,"level_one_category_name":"3C数码配件","nick":"耐时旗舰店","pict_url":"//img.alicdn.com/bao/
         * uploaded/i1/1620417126/O1CN01qprPxB22Vl0WCYqdL_!!0-item_pic.jpg","seller_id":1620417126,"shop_title":null,"s
         * mall_images":{"string":["//img.alicdn.com/i4/1620417126/O1CN01UgLSwk22VkxmlyCVR_!!0-item_pic.jpg","//img.ali
         * cdn.com/i2/1620417126/O1CN0162lfIS22VkrSKYDXg_!!1620417126.jpg","//img.alicdn.com/i1/1620417126/O1CN01c69sps
         * 22VkxPxWAGO_!!1620417126.jpg","//img.alicdn.com/i4/1620417126/O1CN01OkQzU522VkrWiL9PW_!!1620417126.jpg"]},"t
         * itle":"耐时充电宝超薄小巧便携快充闪充适用于vivo华为oppo苹果充电宝","user_type":1,"volume":1754,"zk_final_price":"3
         * 9.9"}]},"request_id":"5nypm8p8ex8z"}
         */

        private TbkDgOptimusMaterialResponseBean tbk_dg_optimus_material_response;

        public TbkDgOptimusMaterialResponseBean getTbk_dg_optimus_material_response() {
            return tbk_dg_optimus_material_response;
        }

        public void setTbk_dg_optimus_material_response(TbkDgOptimusMaterialResponseBean tbk_dg_optimus_material_response) {
            this.tbk_dg_optimus_material_response = tbk_dg_optimus_material_response;
        }

        public static class TbkDgOptimusMaterialResponseBean {
            /**
             * is_default : false
             * result_list : {"map_data":[{"category_id":50009211,"category_name":null,"click_url":"//s.click.taobao.c
             * om/t?e=m%3D2%26s%3D9biiuNCEs2lw4vFB6t2Z2ueEDrYVVa64Dne87AjQPk9yINtkUhsv0AwIMIR498vZNnGHDv9u8g5m5nS0VR3o
             * T0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j5QNerBM5mSXVLdYGR5XowAWssShUjNLx%2BdusJxCFYEy
             * %2FnzFmH8P00Bp%2FSdChf3U3iXY%2B5QowgvHJPA%3D%3D&scm=1007.15348.115058.0_3756&pvid=7a2d1a71-4355-4f43-84
             * 81-a11efc88d87c&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;originalFloorId:3756;p
             * vid:7a2d1a71-4355-4f43-8481-a11efc88d87c;app_pvid:59590_11.132.118.137_598_1607784789293&union_lens=len
             * sId%3AMAPI%401607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401","commission_rate":"9.0
             * ","coupon_amount":10,"coupon_click_url":"//uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2
             * jJNKOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgG
             * Z9ZhjSjHmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%
             * 3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.137_598_1
             * 607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401607784789%407a2d
             * 1a71-4355-4f43-8481-a11efc88d87c_587128169764%401","coupon_end_time":"1608220799000","coupon_info":null,
             * "coupon_remain_count":49477,"coupon_share_url":"//uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3
             * R5TgU2jJNKOfNNtsjZw%2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkO
             * eMFgGZ9ZhjSjHmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg
             * %3D%3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.137_5
             * 98_1607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401607784789%407
             * a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401","coupon_start_fee":"39.0","coupon_start_time":"1607
             * 616000000","coupon_total_count":50000,"item_description":"智能数显 大容量 快输出 小巧便携 可上飞机","item_i
             * d":587128169764,"level_one_category_id":50008090,"level_one_category_name":"3C数码配件","nick":"耐时旗舰
             * 店","pict_url":"//img.alicdn.com/bao/uploaded/i1/1620417126/O1CN01qprPxB22Vl0WCYqdL_!!0-item_pic.jpg","
             * seller_id":1620417126,"shop_title":null,"small_images":{"string":["//img.alicdn.com/i4/1620417126/O1CN0
             * 1UgLSwk22VkxmlyCVR_!!0-item_pic.jpg","//img.alicdn.com/i2/1620417126/O1CN0162lfIS22VkrSKYDXg_!!1620417126
             * .jpg","//img.alicdn.com/i1/1620417126/O1CN01c69sps22VkxPxWAGO_!!1620417126.jpg","//img.alicdn.com/i4/1620
             * 417126/O1CN01OkQzU522VkrWiL9PW_!!1620417126.jpg"]},"title":"耐时充电宝超薄小巧便携快充闪充适用于vivo华为oppo苹
             * 果充电宝","user_type":1,"volume":1754,"zk_final_price":"39.9"}]}
             * request_id : 5nypm8p8ex8z
             */

            private String is_default;
            private ResultListBean result_list;
            private String request_id;

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public ResultListBean getResult_list() {
                return result_list;
            }

            public void setResult_list(ResultListBean result_list) {
                this.result_list = result_list;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public static class ResultListBean {
                private List<MapDataBean> map_data;

                public List<MapDataBean> getMap_data() {
                    return map_data;
                }

                public void setMap_data(List<MapDataBean> map_data) {
                    this.map_data = map_data;
                }

                public static class MapDataBean implements IBaseInfo {
                    /**
                     * category_id : 50009211
                     * category_name : null
                     * click_url : //s.click.taobao.com/t?e=m%3D2%26s%3D9biiuNCEs2lw4vFB6t2Z2ueEDrYVVa64Dne87AjQPk9yINt
                     * kUhsv0AwIMIR498vZNnGHDv9u8g5m5nS0VR3oT0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j5
                     * QNerBM5mSXVLdYGR5XowAWssShUjNLx%2BdusJxCFYEy%2FnzFmH8P00Bp%2FSdChf3U3iXY%2B5QowgvHJPA%3D%3D&scm=
                     * 1007.15348.115058.0_3756&pvid=7a2d1a71-4355-4f43-8481-a11efc88d87c&app_pvid=59590_11.132.118.137
                     * _598_1607784789293&ptl=floorId:3756;originalFloorId:3756;pvid:7a2d1a71-4355-4f43-8481-a11efc88d8
                     * 7c;app_pvid:59590_11.132.118.137_598_1607784789293&union_lens=lensId%3AMAPI%401607784789%407a2d1
                     * a71-4355-4f43-8481-a11efc88d87c_587128169764%401
                     * commission_rate : 9.0
                     * coupon_amount : 10
                     * coupon_click_url : //uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2jJNKOfNNtsjZw%
                     * 2F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgGZ9Zhj
                     * SjHmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%
                     * 3D%3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.11
                     * 8.137_598_1607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%4
                     * 01607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401
                     * coupon_end_time : 1608220799000
                     * coupon_info : null
                     * coupon_remain_count : 49477
                     * coupon_share_url : //uland.taobao.com/coupon/edetail?e=LAAI0BURfKYNfLV8niU3R5TgU2jJNKOfNNtsjZw%2
                     * F%2FoLlBy0%2FNOvWo9LDJQhMgKWk%2BIEAvHjYUeRU%2BGq5uvbHnsuRTiT9oEhVZV8pr6FWc0MDY5xlXkOeMFgGZ9ZhjSj
                     * HmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D
                     * %3D&&app_pvid=59590_11.132.118.137_598_1607784789293&ptl=floorId:3756;app_pvid:59590_11.132.118.
                     * 137_598_1607784789293;tpp_pvid:7a2d1a71-4355-4f43-8481-a11efc88d87c&union_lens=lensId%3AMAPI%401
                     * 607784789%407a2d1a71-4355-4f43-8481-a11efc88d87c_587128169764%401
                     * coupon_start_fee : 39.0
                     * coupon_start_time : 1607616000000
                     * coupon_total_count : 50000
                     * item_description : 智能数显 大容量 快输出 小巧便携 可上飞机
                     * item_id : 587128169764
                     * level_one_category_id : 50008090
                     * level_one_category_name : 3C数码配件
                     * nick : 耐时旗舰店
                     * pict_url : //img.alicdn.com/bao/uploaded/i1/1620417126/O1CN01qprPxB22Vl0WCYqdL_!!0-item_pic.jpg
                     * seller_id : 1620417126
                     * shop_title : null
                     * small_images : {"string":["//img.alicdn.com/i4/1620417126/O1CN01UgLSwk22VkxmlyCVR_!!0-item_pic.j
                     * pg","//img.alicdn.com/i2/1620417126/O1CN0162lfIS22VkrSKYDXg_!!1620417126.jpg","//img.alicdn.com/
                     * i1/1620417126/O1CN01c69sps22VkxPxWAGO_!!1620417126.jpg","//img.alicdn.com/i4/1620417126/O1CN01Ok
                     * QzU522VkrWiL9PW_!!1620417126.jpg"]}
                     * title : 耐时充电宝超薄小巧便携快充闪充适用于vivo华为oppo苹果充电宝
                     * user_type : 1
                     * volume : 1754
                     * zk_final_price : 39.9
                     */

                    private int category_id;
                    private Object category_name;
                    private String click_url;
                    private String commission_rate;
                    private int coupon_amount;
                    private String coupon_click_url;
                    private String coupon_end_time;
                    private Object coupon_info;
                    private int coupon_remain_count;
                    private String coupon_share_url;
                    private String coupon_start_fee;
                    private String coupon_start_time;
                    private int coupon_total_count;
                    private String item_description;
                    private long item_id;
                    private int level_one_category_id;
                    private String level_one_category_name;
                    private String nick;
                    private String pict_url;
                    private long seller_id;
                    private Object shop_title;
                    private SmallImagesBean small_images;
                    private String title;
                    private int user_type;
                    private int volume;
                    private String zk_final_price;

                    public int getCategory_id() {
                        return category_id;
                    }

                    public void setCategory_id(int category_id) {
                        this.category_id = category_id;
                    }

                    public Object getCategory_name() {
                        return category_name;
                    }

                    public void setCategory_name(Object category_name) {
                        this.category_name = category_name;
                    }

                    public String getClick_url() {
                        return click_url;
                    }

                    public void setClick_url(String click_url) {
                        this.click_url = click_url;
                    }

                    public String getCommission_rate() {
                        return commission_rate;
                    }

                    public void setCommission_rate(String commission_rate) {
                        this.commission_rate = commission_rate;
                    }

                    public int getCoupon_amount() {
                        return coupon_amount;
                    }

                    public void setCoupon_amount(int coupon_amount) {
                        this.coupon_amount = coupon_amount;
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

                    public Object getCoupon_info() {
                        return coupon_info;
                    }

                    public void setCoupon_info(Object coupon_info) {
                        this.coupon_info = coupon_info;
                    }

                    public int getCoupon_remain_count() {
                        return coupon_remain_count;
                    }

                    public void setCoupon_remain_count(int coupon_remain_count) {
                        this.coupon_remain_count = coupon_remain_count;
                    }

                    public String getCoupon_share_url() {
                        return coupon_share_url;
                    }

                    public void setCoupon_share_url(String coupon_share_url) {
                        this.coupon_share_url = coupon_share_url;
                    }

                    public String getCoupon_start_fee() {
                        return coupon_start_fee;
                    }

                    public void setCoupon_start_fee(String coupon_start_fee) {
                        this.coupon_start_fee = coupon_start_fee;
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

                    public String getItem_description() {
                        return item_description;
                    }

                    public void setItem_description(String item_description) {
                        this.item_description = item_description;
                    }

                    public long getItem_id() {
                        return item_id;
                    }

                    public void setItem_id(long item_id) {
                        this.item_id = item_id;
                    }

                    public int getLevel_one_category_id() {
                        return level_one_category_id;
                    }

                    public void setLevel_one_category_id(int level_one_category_id) {
                        this.level_one_category_id = level_one_category_id;
                    }

                    public String getLevel_one_category_name() {
                        return level_one_category_name;
                    }

                    public void setLevel_one_category_name(String level_one_category_name) {
                        this.level_one_category_name = level_one_category_name;
                    }

                    public String getNick() {
                        return nick;
                    }

                    public void setNick(String nick) {
                        this.nick = nick;
                    }

                    public String getPict_url() {
                        return pict_url;
                    }

                    public void setPict_url(String pict_url) {
                        this.pict_url = pict_url;
                    }

                    public long getSeller_id() {
                        return seller_id;
                    }

                    public void setSeller_id(long seller_id) {
                        this.seller_id = seller_id;
                    }

                    public Object getShop_title() {
                        return shop_title;
                    }

                    public void setShop_title(Object shop_title) {
                        this.shop_title = shop_title;
                    }

                    public SmallImagesBean getSmall_images() {
                        return small_images;
                    }

                    public void setSmall_images(SmallImagesBean small_images) {
                        this.small_images = small_images;
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

                    public static class SmallImagesBean {
                        private List<String> string;

                        public List<String> getString() {
                            return string;
                        }

                        public void setString(List<String> string) {
                            this.string = string;
                        }
                    }
                }
            }
        }
    }
}
