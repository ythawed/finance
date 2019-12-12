package com.ygq.finance.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.ygq.finance.api.domain.ProductRpcRequestParams;
import com.ygq.finance.entity.Product;

import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/5 0005
 *
 * 产品相关的rpc服务
 * "/products"为服务的调用地址
 */
@JsonRpcService("rpc/products")
public interface ProductRpc {
    /**
     * 复杂条件的分页查询
     * @param params 将请求参数封装，灵活性更高com.ygq.manager.service.ProductService#query(*)
     */
    List<Product> query(ProductRpcRequestParams params);

    /**
     * 查询单个产品
     */
    Product findProductById(String id);
}
