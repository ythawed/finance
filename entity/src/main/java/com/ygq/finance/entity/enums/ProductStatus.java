package com.ygq.finance.entity.enums;

/**
 * 产品状态枚举
 * 1. 审核中Auditing
 * 2. 正在出售 in_sell
 * 3. 暂停销售 Locked
 * 4. 已结束 finished
 *
 * *********注意最后一个例子要用分号结束
 */
public enum  ProductStatus{

    AUDITING("审核中"),
    IN_SELL("销售中"),
    LOCKED("暂停销售"),
//    用 ; 结束
    FINISHED("已结束");

    private String desc;

    ProductStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
