package com.ygq.finance.api.events;

import com.ygq.finance.entity.enums.ProductStatus;

import java.io.Serializable;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 * 产品状态 事件 （mq中传递的消息对象)
 */
public class ProductStatusEvent implements Serializable {

    private String id;
    private ProductStatus status;

    @Override
    public String toString() {
        return "ProductStatusEvent{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }

    public ProductStatusEvent(String id, ProductStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
