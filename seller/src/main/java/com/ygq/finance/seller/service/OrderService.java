package com.ygq.finance.seller.service;

import com.ygq.finance.entity.Order;
import com.ygq.finance.entity.Product;
import com.ygq.finance.entity.enums.OrderStatus;
import com.ygq.finance.entity.enums.OrderType;
import com.ygq.finance.seller.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRpcService productRpcService;

    /**
     * 申购订单
     */
    public Order apply(Order order) {
        //1. 校验订单基本数据
        checkOrder(order);
        //2. 补充订单其他数据
        completeOrder(order);
        //3. 写入数据库，将order返回
        return orderRepository.saveAndFlush(order);
    }

    /**
     *  补充订单数据
     */
    private void completeOrder(Order order) {
        //1. 设置订单编号，去掉UUID中的'-'
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
        //2. 设置订单类型(此处被apply方法调用，所以为"申购"类型)
        order.setOrderType(OrderType.APPLY.name());
        //3. 设置订单状态,本订单没有其它后续处理，所以设置为成功
        order.setOrderStatus(OrderStatus.SUCCESS.name());
        //4. 订单更新时间,即当前更新
        order.setUpdateTime(new Date());

    }

    /**
     * 校验订单的正确性
     */
    private void checkOrder(Order order) {
        //1. 产品的存在性检测
        Product product = productRpcService.findProductById(order.getProductId());
        Assert.notNull(product, "产品不存在！");
        //2. 订单的必填项
        Assert.notNull(order.getProductId(),"需要产品编号！");
        Assert.notNull(order.getChanId(),"需要渠道编号！");
        Assert.notNull(order.getChanUserId(),"需要用户编号！");
        Assert.notNull(order.getAmount(),"需要产品金额！");
        Assert.notNull(order.getCreateTime(),"需要下单时间！");
        Assert.notNull(order.getOuterOrderId(),"需要外部订单号！");
        //3. 金额的正确性
        //3.1 订单金额需大于起投金额
        Assert.isTrue(order.getAmount().compareTo(product.getThresholdAmount()) >= 0, "订单金额需大于起投金额");
        //3.2 金额多出部分需为步长的整数倍

    }
}
