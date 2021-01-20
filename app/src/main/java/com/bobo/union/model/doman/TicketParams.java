package com.bobo.union.model.doman;

/**
 * Created by 公众号：IT波 on 2021/1/17 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class TicketParams {

    private String url;
    private String title;

    public TicketParams(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
