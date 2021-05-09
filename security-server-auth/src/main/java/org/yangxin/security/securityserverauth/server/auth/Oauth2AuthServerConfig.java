package org.yangxin.security.securityserverauth.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import javax.sql.DataSource;

/**
 * EnableJdbcHttpSession
 * EnableJdbcHttpSession注解创建了一个实现了Filter名为springSessionRepositoryFilter的Bean。
 * 过滤器负责使用Spring Session支持的一个实现去替换HttpSession，
 * 这个实例中Spring Session由关系型数据库支持。
 *
 * EnableAuthorizationServer
 * 使用这个注解的时候会触发3个重要的配置类，
 * ClientDetailsServiceConfigurer用于配置客户端信息，
 * AuthorizationServerSecurityConfigurer用于配置授权服务器端点的安全过滤拦截，
 * AuthorizationServerEndpointsConfigurer用于配置端点。
 *
 * AuthorizationServerConfigurerAdapter
 * AuthorizationServerConfigurerAdapter只是一个提供给开发配置ClientDetailsServiceConfigurer、
 * AuthorizationServerEndpointsConfigurer、AuthorizationServerSecurityConfigurer空壳类，
 * 并没有持有以上三个配置Bean对象。
 * 由初始化时调用AuthorizationServerConfigurerAdapter.configure(xxxConfigure)注入配置。
 * ClientDetailServiceConfigurer：clientDetailsService注入，决定clientDetails信息的处理服务。
 * AuthorizationServerEndpointsConfigurer：访问端点配置，tokenStore、tokenEnhancer服务。
 * AuthorizationServerSecurityConfigurer：访问安全配置。
 *
 * @author yangxin
 * 2021/4/18 下午2:29
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
@EnableJdbcHttpSession
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;

    @Autowired
    public Oauth2AuthServerConfig(AuthenticationManager authenticationManager, DataSource dataSource, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    private JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey("123456");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("security.key"),
                "123456".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("yangxin"));
        return jwtAccessTokenConverter;
    }

    /**
     * AuthorizationServerEndpointsConfigurer其实是一个装载类，
     * 装载Endpoints所有相关的类配置（AuthorizationServer、TokenServices、TokenStore、ClientDetailsService、UserDetailsService）
     *
     * @param authorizationServerEndpointsConfigurer  授权服务终端配置器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) {
        authorizationServerEndpointsConfigurer.userDetailsService(userDetailsService)
                // token的保存方式（设置AccessToken的存储介质tokenService）
                .tokenStore(tokenStore())
                // token里加点信息
                .tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager);
    }

    /**
     * 配置从哪里获取ClientDetails信息，
     * 在client_credentials授权下，只要这个ClientDetails信息
     *
     * @param clientDetailsServiceConfigurer 客户端细节服务配置器
     * @throws Exception 异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        // 认证信息从数据库中获取
        clientDetailsServiceConfigurer.jdbc(dataSource);
    }

    /**
     * AuthorizationServerSecurityConfigurer继承SecurityConfigurerAdapter，
     * 也就是一个Spring Security安全配置提供给AuthorizationServer去配置AuthorizationServer的端点（/oauth/***）的安全访问规则、过滤器Filter
     *
     * @param authorizationServerSecurityConfigurer 授权服务安全配置器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) {
        authorizationServerSecurityConfigurer.tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()");
    }
}
