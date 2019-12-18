package com.ygq.finance.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * 将ProductRpcRequestParams的四个属性用于json序列化
 * 最终目的是因为json不能解析复杂对象
 *
 * @author ythawed
 * @date 2019/12/18 0018
 */
@JsonDeserialize(as = ProductRpcRequestParams.class)
public interface ParamInf {
    /**
     * 四个需要被json解析的属性
     */
    List<String> getIdList();
    BigDecimal getMinRewardRate();
    BigDecimal getMaxRewardRate();
    List<String> getStatusList();
}
