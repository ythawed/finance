package com.ygq.finance.seller.service.mq;

import com.ygq.finance.api.ProductRpc;
import com.ygq.finance.api.events.ProductStatusEvent;
import com.ygq.finance.entity.enums.ProductStatus;
import com.ygq.finance.seller.configuration.RpcConfiguration;
import com.ygq.finance.seller.service.ProductCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 */
@Component
public class ProductConsumer {

    private static Logger LOG = LoggerFactory.getLogger(RpcConfiguration.class);

    /**
     * 消息队列地址
     */
    private static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";


    @Autowired(required = false)
    private ProductRpc productRpc;

    @Autowired
    private ProductCache productCache;

    /**
     * MQ消费者
     */
    @JmsListener(destination = MQ_DESTINATION)
    void updateCache(ProductStatusEvent event) {

        LOG.info("MQ-----接收到消息，message={}",event);

        productCache.removeCache(event.getId());
        if (ProductStatus.IN_SELL == event.getStatus()) {
            productCache.readCache(event.getId());
        }
    }
}
