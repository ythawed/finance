package com.ygq.manager.controller;

import com.ygq.finance.entity.Product;
import com.ygq.manager.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/3
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    /**
     * 添加产品
     * @param product 接收请求参数
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Product addProduct(@RequestBody Product product) {
        LOG.info("创建产品，请求参数:{}", product);

        Product result= service.addProduct(product);

        LOG.info("创建产品，执行结果:{}", result);

        return result;
    }

    /**
     * REST API形式，
     * 参数从url中取，注意这里的写法
     * 此方法是从url中取id查询
     */
    @ApiOperation(value = "查询",notes = "根据id查询单个产品")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product findProductById(@PathVariable String id) {

        LOG.info("查询单个产品，id={}",id);

        Product product = service.findProductById(id);

        LOG.info("查询单个产品，结果={}",product);
        return product;
    }

    /**
     *
     * @param pageNum 设置默认页数为0
     * @param pageSize 一页10条
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<Product> query(String ids,
                               BigDecimal minRewardRate,
                               BigDecimal maxRewardRate,
                               String status,
                               @RequestParam(defaultValue = "0") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize) {

        LOG.info("条件查询产品，ids={},minRewardRate={},maxRewardRate={},status={},pageNum={},pageSize={}");

        //将参数进行切分
        List<String> idList = null;
        List<String> statusList = null;
        if (StringUtils.isNotEmpty(ids)) {
            idList = Arrays.asList(ids.split(","));
        }
        if (StringUtils.isNotEmpty(status)) {
            statusList=Arrays.asList(status.split(","));
        }

        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<Product> query = service.query(idList, minRewardRate, maxRewardRate, statusList, pageable);
        LOG.info("条件查询产品，结果={}", query);
        return query;
    }

}
