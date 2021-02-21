package com.bobo.union.model.doman;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/2/21 Copyright © Leon. All rights reserved.
 * Functions: 本地持久化保存数据 二次封装 之前已经有一个bean类了
 */
public class Histories {

    private List<String> histories;

    public List<String> getHistories() {
        return histories;
    }

    public void setHistories(List<String> histories) {
        this.histories = histories;
    }
}
