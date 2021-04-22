package org.yangxin.security.securitygatewayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关一般不做细粒度限流处理，原因有下：
 * 1 如果要对微服务的限流做细粒度的业务限流处理，则将限流功能与网关高度耦合
 * 2 网关限流没法处理微服务之间的限流要求
 *
 * @author yangxin
 * 2021/04/20 20:38
 */
@SpringBootApplication
@EnableZuulProxy
public class SecurityGatewayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityGatewayApiApplication.class, args);
    }

}
