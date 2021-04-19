package org.yangxin.security.securityuserapi.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流控
 *
 * @author yangxin
 * 2021/4/10 下午8:38
 */
@SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("too many request!");
            httpServletResponse.getWriter().flush();
        }
    }
}
