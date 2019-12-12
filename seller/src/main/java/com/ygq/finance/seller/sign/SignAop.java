package com.ygq.finance.seller.sign;

import com.ygq.finance.seller.service.SignService;
import com.ygq.util.RsaUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author ythawed
 * @date 2019/12/9 0009
 * 切面类，用于在此拦截订单方法，进行rsa验签，通过后才进行对下单方法放行
 */
@Component
@Aspect
public class SignAop {


    @Autowired
    private SignService signService;

    /**
     * 申购订单的验签
     *          * com.ygq.finance.seller.controller.*.*(..)
     *   表达式：com.ygq.finance.seller.controller包下的所有方法
     *          args(authId,sign,text,..)
     *   表达式：指定更具体的方法参数：参数名称依次为authId，sign，text
     * @param authId 验签id，对应明文text
     * @param sign 密文
     * @param text json明文
     */
    @Before(value = "execution(* com.ygq.finance.seller.controller.*.*(..)) " +
            "&& args(authId,sign,text,..)")
    public void verify(String authId, String sign, SignText text) {
        String publicKey = signService.getPublicKey(authId);
        //明文
        String plainText = text.toText();
        Assert.isTrue(RsaUtil.verify(plainText, sign, publicKey), "验签失败！");

    }
}
