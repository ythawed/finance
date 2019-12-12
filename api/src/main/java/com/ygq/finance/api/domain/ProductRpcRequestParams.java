package com.ygq.finance.api.domain;

import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/5 0005
 * <p>
 * 封装请求参数，使得请求参数更灵活被使用,本类在ProductRpc中的query(params)中使用
 */
public class ProductRpcRequestParams {

    private List<String> idList;
    private BigDecimal minRewardRate;

    private BigDecimal maxRewardRate;

    private List<String> statusList;

    /**
     * 下面四个属性是原来的pageable属性，因为jsonrp不能转换复杂对象，所以将复杂对象拆分
     */
    private int pageNum;
    private int pageSize;
    private Sort.Direction orderDirection;
    private String orderBy;



    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public BigDecimal getMinRewardRate() {
        return minRewardRate;
    }

    public void setMinRewardRate(BigDecimal minRewardRate) {
        this.minRewardRate = minRewardRate;
    }

    public BigDecimal getMaxRewardRate() {
        return maxRewardRate;
    }

    public void setMaxRewardRate(BigDecimal maxRewardRate) {
        this.maxRewardRate = maxRewardRate;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(Sort.Direction orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
