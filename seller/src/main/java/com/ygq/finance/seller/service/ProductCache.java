package com.ygq.finance.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.ygq.finance.api.ProductRpc;
import com.ygq.finance.api.domain.ProductRpcRequestParams;
import com.ygq.finance.entity.Product;
import com.ygq.finance.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ythawed
 * @date 2019/12/7 0007
 * 产品类的相关缓存方法集中处理类
 */
@Component
public class ProductCache {

    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "FINANCE_PRODUCT";

    private static Logger LOG = LoggerFactory.getLogger(ProductCache.class);

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired(required = false)
    private ProductRpc productRpc;

    /**
     * 读取缓存
     * 取代的方法是findProductById
     */
    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id) {
        LOG.info("查询单个产品详情，id={}", id);
        Product product = productRpc.findProductById(id);
        LOG.info("查询单个产品详情，结果={}", product);
        return product;
    }

    /**
     * 更新缓存
     *
     * @param product 执行对象
     *                指定key为product的id
     *                value为product
     */
    @CachePut(cacheNames = CACHE_NAME, key = "#product.id")
    public Product putCache(Product product) {
        return product;
    }

    /**
     * 根据id清除缓存
     */
    @CacheEvict(cacheNames = CACHE_NAME)
    public void removeCache(String id) {

    }

    /**
     * 获取所有缓存产品
     */
    public List<Product> readAllCache() {
        //从缓存中获取缓存
        Map map = hazelcastInstance.getMap(CACHE_NAME);
        List<Product> productList = null;
        //判断获取结果
        if (map.size() > 0) {
            productList = new ArrayList<>();
            productList.addAll(map.values());
        } else {
            productList = findAll();
        }
        return productList;
    }

    public List<Product> findAll() {

        ProductRpcRequestParams params = new ProductRpcRequestParams();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());

        params.setStatusList(status);
        //设置"Pageable"参数,第0页，每页1000条数据，按收益率逆序，


        LOG.info("查询所有在售产品，参数={}",params);
        List<Product> query = productRpc.query(params);
        LOG.info("查询所有在售产品，结果={}",query);
        return query;
    }

}
