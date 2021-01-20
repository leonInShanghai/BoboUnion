package com.bobo.union.model.doman;

/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class TicketResult {

    /**
     * success : true
     * code : 10000
     * message : 淘宝口令构建成功!
     * data : {"tbk_tpwd_create_response":{"data":{"model":"￥xhQoYC66sMX￥"},"request_id":"64jzpdn6m026"}}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TicketResult{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBeanX {
        /**
         * tbk_tpwd_create_response : {"data":{"model":"￥xhQoYC66sMX￥"},"request_id":"64jzpdn6m026"}
         */

        private TbkTpwdCreateResponseBean tbk_tpwd_create_response;

        public TbkTpwdCreateResponseBean getTbk_tpwd_create_response() {
            return tbk_tpwd_create_response;
        }

        public void setTbk_tpwd_create_response(TbkTpwdCreateResponseBean tbk_tpwd_create_response) {
            this.tbk_tpwd_create_response = tbk_tpwd_create_response;
        }

        @Override
        public String toString() {
            return "DataBeanX{" +
                    "tbk_tpwd_create_response=" + tbk_tpwd_create_response +
                    '}';
        }

        public static class TbkTpwdCreateResponseBean {
            /**
             * data : {"model":"￥xhQoYC66sMX￥"}
             * request_id : 64jzpdn6m026
             */

            private DataBean data;
            private String request_id;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            @Override
            public String toString() {
                return "TbkTpwdCreateResponseBean{" +
                        "data=" + data +
                        ", request_id='" + request_id + '\'' +
                        '}';
            }

            public static class DataBean {
                /**
                 * model : ￥xhQoYC66sMX￥
                 *
                 * 2021-1-17 我发现我开始写的时候要自己处理一下 截取字符串
                 * model='9.0￥ru9xcGFZe9y￥~.taӪ宝或點这里url链 https://m.tb.cn/h.4RwgEEb 至浏览er
                 * 【欧诺洁6D微晶炫白牙贴温和不酸牙14对28贴】'
                 */

                private String model;

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                @Override
                public String toString() {
                    return "DataBean{" +
                            "model='" + model + '\'' +
                            '}';
                }
            }
        }
    }
}
