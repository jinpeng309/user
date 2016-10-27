package com.capslock.rpc.service.user;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by capslock1874.
 */
@Configuration
public class RpcClientConfig {
    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.capslock.rpc.service.user");
        return motanAnnotationBean;
    }

    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig() {
        final String protocolName = "motan";
        final ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName(protocolName);
        config.setMaxContentLength(1048576);
        return config;
    }

    @Bean(name = "registryConfig")
    public RegistryConfigBean registryConfig() {
        final String regProtocol = "zookeeper";
        final String address = "139.162.57.135";
        final RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol(regProtocol);
        config.setAddress(address);
        return config;
    }

    @Bean(name = "basicConfig")
    public BasicRefererConfigBean baseReferConfig() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setRegistry("registryConfig");
        config.setProtocol("motan");
        config.setCheck(false);
        config.setAccessLog(true);
        config.setRetries(2);
        config.setThrowException(true);
        return config;
    }
}
