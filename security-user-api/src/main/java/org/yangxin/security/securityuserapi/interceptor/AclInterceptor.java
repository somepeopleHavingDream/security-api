package org.yangxin.security.securityuserapi.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yangxin.security.securityuserapi.user.User;
import org.yangxin.security.securityuserapi.user.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangxin
 * 2021/4/17 上午11:45
 */
@SuppressWarnings("NullableProblems")
@Component
public class AclInterceptor implements HandlerInterceptor {

    private final String[] permitUrlArray = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;

        if (!ArrayUtils.contains(permitUrlArray, request.getRequestURI())) {
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
            if (userInfo == null) {
                response.setContentType("text/plain");
                response.getWriter().write("need authentication.");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = false;
            } else {
                String method = request.getMethod();
                if (!userInfo.hasPermission(method)) {
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden.");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;
                }
            }
        }

        return result;
    }
}
