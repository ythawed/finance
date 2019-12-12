package com.ygq.finance.seller.controller;

import com.ygq.finance.entity.Order;
import com.ygq.finance.seller.rsa.OrderParams;
import com.ygq.finance.seller.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 申购产品
     * @param authId 验收编号
     * @param sign 密文
     * @param params 请求参数
     * @return
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public Order apply(@RequestHeader String authId, @RequestHeader String sign, @RequestBody OrderParams params) {

        LOG.info("申购订单，请求参数={}", params);

        Order order = new Order();
        BeanUtils.copyProperties(params, order);

        order = orderService.apply(order);
        LOG.info("申购订单，响应结果={}", order);

        return order;

    }


}
