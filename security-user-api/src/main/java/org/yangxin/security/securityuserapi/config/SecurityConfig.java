package org.yangxin.security.securityuserapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yangxin.security.securityuserapi.interceptor.AclInterceptor;
import org.yangxin.security.securityuserapi.interceptor.AuditLogInterceptor;
import org.yangxin.security.securityuserapi.user.UserInfo;

import java.util.Objects;
import java.util.Optional;

/**
 * @author yangxin
 * 2021/4/15 下午9:55
 */
@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    private final AuditLogInterceptor auditLogInterceptor;
    private final AclInterceptor aclInterceptor;

    @Autowired
    public SecurityConfig(AuditLogInterceptor auditLogInterceptor, AclInterceptor aclInterceptor) {
        this.auditLogInterceptor = auditLogInterceptor;
        this.aclInterceptor = aclInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            UserInfo userInfo = (UserInfo) Objects.requireNonNull(servletRequestAttributes)
                    .getRequest()
                    .getSession()
                    .getAttribute("user");

            String username = null;
            if (userInfo != null) {
                username = userInfo.getUsername();
            }

            return Optional.ofNullable(username);
        };
    }
}
