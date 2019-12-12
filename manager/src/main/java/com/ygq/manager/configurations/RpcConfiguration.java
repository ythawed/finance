package com.ygq.manager.configurations;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ythawed
 * @date 2019/12/5 0005
 * 将rpc包下的rpc服务端交给spring启动
 */
@Configuration
public class RpcConfiguration {

    @Bean
    public AutoJsonRpcServiceImplExporter exporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

}
