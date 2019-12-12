package com.ygq.finance.entity.enums;

/**
 * 订单状态枚举
 * 1. 初始化
 * 2. 处理中
 * 3. 成功
 * 4. 失败
 * @author Administrator
 */
public enum OrderStatus {

    INIT("初始化"),
    PROCESS("处理中"),
    SUCCESS("成功"),
    FAILURE("失败");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    OrderStatus(String desc) {
        this.desc = desc;
    }
}
