package com.ygq.finance.entity.enums;

/**
 * 订单类型的枚举
 * 1. 申购
 * 2. 赎回
 */
public enum OrderType {

    APPLY("申购"),
    REDEEM("赎回");

    private String desc;

    OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
