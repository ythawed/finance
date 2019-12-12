package com.ygq.manager.service.mq;

import com.ygq.finance.api.events.ProductStatusEvent;
import com.ygq.finance.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 *
 * 产品服务 消息生产者
 */
@Component
public class ProductProducer {

    static final String MQ_DESTINATION = "VirtualTopic.PRODUCT_STATUS";

    private static final Logger LOG = LoggerFactory.getLogger(ProductProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 一条改变商品状态的消息
     */
    public void changeStatus(String id, ProductStatus status) {

        ProductStatusEvent event = new ProductStatusEvent(id, status);
        LOG.info("MQ---生产消息,message={}", event);
        jmsTemplate.convertAndSend(MQ_DESTINATION, event);
    }

    /**
     * 自启动测试方法
     */
    //@PostConstruct
    public void init() {
        changeStatus("T001",ProductStatus.IN_SELL);
    }

}
