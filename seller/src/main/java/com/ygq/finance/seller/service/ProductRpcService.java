package com.ygq.finance.seller.service;

import com.ygq.finance.api.ProductRpc;
import com.ygq.finance.api.events.ProductStatusEvent;
import com.ygq.finance.entity.Product;
import com.ygq.finance.entity.enums.ProductStatus;
import com.ygq.finance.seller.configuration.RpcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/6 0006
 */
@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 消息队列地址
     */
    private static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";

    /**
     * 系统启动监听：用于在系统启动时，将产品全部设置为缓存，放置读取时读取不全
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //TODO findAll()方法里面的rpc类型为复杂对象，不能转为json调用，需要修改
//        List<Product> all = findAll();
//        System.out.println(all.size());
//        if (all != null) {
//            all.forEach(product -> {
//                productCache.putCache(product);
//            });
//        }
    }

    private static Logger LOG = LoggerFactory.getLogger(RpcConfiguration.class);

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


    /**
     * 查询单个产品
     * 对结果进行缓存
     */
    public Product findProductById(String id) {
//        LOG.info("查询单个产品详情，id={}",id);
//        Product product = productRpc.findProductById(id);
//        LOG.info("查询单个产品详情，结果={}",product);
//        return product;
        //上述方法失效，改用读取缓存.在本类注入
        Product product = productCache.readCache(id);
        //可能读出空缓存，当读出空时，需要删除该空缓存
        //如 T009 : com.hazelcast.spring.cache.HazelcastCache$NullDataSerializable@0
        if (product == null) {
            productCache.removeCache(id);
        }
        return product;
    }

    /**
     * 查询"正在出售"的所有产品，结果按收益率倒序排序
     */
    public List<Product> findAll() {
//        此处由于读取所有缓存会存在“读取不全”的情况，所以设置监听器，在系统启动时，就将所有产品全部写入缓存
        return productCache.readAllCache();
    }


}
