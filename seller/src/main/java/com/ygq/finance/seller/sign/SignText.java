package com.ygq.finance.seller.sign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ygq.util.JsonUtil;

/**
 * @author ythawed
 * @date 2019/12/8 0008
 * RSA验证文本接口
 *  1. 待加密的文本包含的字符都是非空
 *  2. 开启属性按照名称字典排序
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public interface SignText {
    /**
     * JDK1.8的新特性，接口方法可以有默认实现方法
     * 将属性转化成文本
     */
    default String toText() {
        return JsonUtil.toJson(this);
    }

}
