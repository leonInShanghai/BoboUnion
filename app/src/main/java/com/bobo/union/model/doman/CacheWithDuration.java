package com.bobo.union.model.doman;

/**
 * Created by 公众号：IT波 on 2021/2/21 Copyright © Leon. All rights reserved.
 * Functions: 用户搜索历史对象
 */
public class CacheWithDuration {

    private long duration;
    private String cahce;

    public CacheWithDuration(long duration, String cahce) {
        this.duration = duration;
        this.cahce = cahce;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCahce() {
        return cahce;
    }

    public void setCahce(String cahce) {
        this.cahce = cahce;
    }
}
