package com.ygq.finance.api.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ythawed
 * @date 2019/12/5 0005
 * <p>
 * 封装请求参数，使得请求参数更灵活被使用,本类在ProductRpc中的query(params)中使用
 *
 * implements ParamInf是为了能将这些属性被json解析，因为json不能解析复杂对象
 *
 */
public class ProductRpcRequestParams implements ParamInf{

    private List<String> idList;
    private BigDecimal minRewardRate;

    private BigDecimal maxRewardRate;

    private List<String> statusList;

    @Override
    public String toString() {
        return "ProductRpcRequestParams{" +
                "idList=" + idList +
                ", minRewardRate=" + minRewardRate +
                ", maxRewardRate=" + maxRewardRate +
                ", statusList=" + statusList +
                '}';
    }


    @Override
    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    @Override
    public BigDecimal getMinRewardRate() {
        return minRewardRate;
    }

    public void setMinRewardRate(BigDecimal minRewardRate) {
        this.minRewardRate = minRewardRate;
    }

    @Override
    public BigDecimal getMaxRewardRate() {
        return maxRewardRate;
    }

    public void setMaxRewardRate(BigDecimal maxRewardRate) {
        this.maxRewardRate = maxRewardRate;
    }

    @Override
    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}
