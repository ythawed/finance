package com.ygq.manager.service;

import com.ygq.finance.entity.Product;
import com.ygq.finance.entity.enums.ProductStatus;
import com.ygq.manager.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/3
 */
@Service
public class ProductService {

    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    /**
     * 复杂条件查询
     * @param idList 多个产品id
     * @param minRewardRate 最小的收益率
     * @param maxRewardRate 最大的收益率
     * @param statusList 多个产品状态
     * @param pageable 分页条件
     * @return 符合条件的产品集合
     */
    public Page<Product> query(List<String> idList,
                               BigDecimal minRewardRate,
                               BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable) {
        LOG.debug("查询产品,idList={},minRewardRate={},maxRewardRate={},statusList={},pageable={}",
                idList, minRewardRate, maxRewardRate, statusList, pageable);
        // 条件封装
        Specification<Product> specification=new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                List<Predicate> predicates = new ArrayList<>();

                if (idList != null && idList.size() > 0) {
                    //将产品编号设置为查询条件
                    predicates.add(idCol.in(idList));
                }
                if (minRewardRate != null && BigDecimal.ZERO.compareTo(minRewardRate) < 0) {
                    //当前收益率与最小收益率比较
                    // >
                    predicates.add(cb.ge(rewardRateCol, minRewardRate));
                }
                if (maxRewardRate != null && BigDecimal.ZERO.compareTo(maxRewardRate) < 0) {
                    //当前收益率与最大收益率比较
                    //  <
                    predicates.add(cb.le(rewardRateCol, maxRewardRate));
                }
                if (statusList != null && statusList.size() > 0) {
                    //状态
                    predicates.add(statusCol.in(statusList));
                }
                //查询
                query.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };

        Page<Product> page = repository.findAll(specification,pageable);

        LOG.debug("查询产品,结果={}", page);
        return page;
    }

    /**
     * 通过产品id查询产品
     * 1. id非空校验
     */
    public Product findProductById(String id) {
        LOG.debug("查询单个产品，id={}", id);

        Assert.notNull(id, "产品id不能为空");
        Product product = repository.findOne(id);

        LOG.debug("查询单个产品，结果={}", product);

        return product;
    }


    /**
     * 添加产品
     * 1. 校验提交的产品参数是否齐全
     * 2. 可以为空的参数设置对应的默认值
     */
    public Product addProduct(Product product) {
        LOG.debug("创建产品，参数={}", product);

        checkProduct(product);
        setDefault(product);
        //返回添加结果
        Product result = repository.save(product);

        LOG.debug("创建产品，结果={}", result);

        return result;
    }

    /**
     * 可以为空的参数设置对应的默认值
     *    创建时间
     *    更新时间
     *    投资步长
     *    锁定期
     *    状态
     */
    private void setDefault(Product product) {
        if (product.getCreateTime() == null) {
            product.setCreateTime(new Date());
        }
        if (product.getCreateTime() == null) {
            product.setUpdateTime(new Date());
        }
        if (product.getStepAmount() == null) {
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getLockTerm() == null) {
            product.setLockTerm(0);
        }
        if (product.getStatus() == null) {
            //.name()表示获取获取枚举中的内容（如：审核中）
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 校验产品参数是否符合规则
     *  产品编号不能为空
     *  校验某些参数如收益率等是否符合对应的范围值
     *  投资步长为整数
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(), "产品编号不能为空");
        // 收益率为0<rate<=30
        BigDecimal rewardRate = product.getRewardRate();
        Assert.isTrue(BigDecimal.ZERO.compareTo(rewardRate) < 0
                && BigDecimal.valueOf(30).compareTo(rewardRate) >= 0, "收益率范围应该为0<rate<=30");
        //投资步长为整数
        BigDecimal stepAmount = product.getStepAmount();
        Assert.isTrue(BigDecimal.valueOf(stepAmount.longValue()).compareTo(stepAmount) == 0, "投资步长应为整数");
    }
}
