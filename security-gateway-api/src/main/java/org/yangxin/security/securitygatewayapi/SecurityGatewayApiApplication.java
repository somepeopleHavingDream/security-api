package org.yangxin.security.securitygatewayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
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
