package org.yangxin.security.securityserverauth.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * EnableWebSecurity注解有两个作用：
 * 1 加载了WebSecurityConfiguration配置类，配置安全认证策略
 * 2 加载了AuthenticationConfiguration，配置了认证信息
 *
 * @author yangxin
 * 2021/4/18 下午2:41
 */
@Configuration
@EnableWebSecurity
public class Oauth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public void setLogoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 1 根据需求设置相应属性
     * 2 根据需要应用相应的认证安全配置器SecurityConfigurer（可以应用多个，也可以一个都不应用）
     * 3 可以再提供一个自定义的UserDetailsService（也可以不提供）
     * 4 可以提供若干个自定义的AuthenticationProvider（也可以不提供）
     * 5 调用#build方法构建目标AuthenticationManager供使用方使用
     *
     * 这里配置的是认证信息，我们只需要向这个类中配置用户信息，就能生成对应的AuthenticationManager，
     * 这个类也提过，是用户身份的管理者，是认证的入口，因此，我们需要通过这个配置，向security提供真实的用户身份
     *
     * @param auth 认证管理建造者
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置Spring Security的认证策略，每个模块配置使用and结尾。
     * authorizeRequests()配置路径拦截，表明路径访问所对应的权限、角色、认证信息。
     * formLogin()对应表单认证相关的配置。
     * logout()对应了注销相关的配置。
     * httpBasic()可以配置basic登录
     *
     * @param http http安全配置
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and()
                .formLogin().and()
                .httpBasic().and()
                .logout().logoutSuccessHandler(logoutSuccessHandler);

    }
}
