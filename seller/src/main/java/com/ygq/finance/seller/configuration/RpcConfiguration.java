package com.ygq.finance.seller.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.ygq.finance.api.ProductRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author ythawed
 * @date 2019/12/6 0006
 * rpc 客户端代理
 *
 */
@Configuration
public class RpcConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(RpcConfiguration.class);

    /**
     *
     * @return rpc客户端代理
     */
    @Bean
    public AutoJsonRpcClientProxyCreator jsonRpcClientProxyCreator(@Value("${rpc.manager.url}")String url) {


        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            //rpc服务端地址
            creator.setBaseUrl(new URL(url));
        } catch (MalformedURLException e) {
            LOG.error("调用rpc服务的url错误", e);
        }
        // rpc服务类扫描
        creator.setScanPackage(ProductRpc.class.getPackage().getName());
        return creator;
    }
}
