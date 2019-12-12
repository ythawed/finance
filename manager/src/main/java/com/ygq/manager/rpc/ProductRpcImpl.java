package com.ygq.manager.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.ygq.finance.api.ProductRpc;
import com.ygq.finance.api.domain.ProductRpcRequestParams;
import com.ygq.finance.entity.Product;
import com.ygq.manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/5 0005
 * rpc服务实现类
 */
@AutoJsonRpcServiceImpl
@Service
public class ProductRpcImpl implements ProductRpc{

    @Autowired
    private ProductService productService;

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcImpl.class);

    @Override
    public List<Product> query(ProductRpcRequestParams params) {

        LOG.info("查询多个条件的产品,请求参数={}", params);

        //设置Pageable对象
        Pageable pageable = new PageRequest(params.getPageNum(),
                params.getPageSize(),
                params.getOrderDirection(),
                params.getOrderBy());

        Page<Product> result = productService.query(params.getIdList(),
                params.getMinRewardRate(),
                params.getMaxRewardRate(),
                params.getStatusList(),
                pageable);
        LOG.info("查询多个条件的产品，查询结果={}", result);
        return result.getContent();
    }

    @Override
    public Product findProductById(String id) {

        LOG.info("查询单个产品详情，参数id={}", id);
        Product result = productService.findProductById(id);
        LOG.info("查询单个产品详情，结果={}", result);
        return result;
    }
}
