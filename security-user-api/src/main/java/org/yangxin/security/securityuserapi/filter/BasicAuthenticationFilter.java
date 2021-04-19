package org.yangxin.security.securityuserapi.filter;

import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yangxin.security.securityuserapi.user.User;
import org.yangxin.security.securityuserapi.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author yangxin
 * 2021/4/14 下午9:29
 */
@SuppressWarnings("NullableProblems")
@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private static final String SESSION_KEY_TMP = "temp";

    @Autowired
    public BasicAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)) {
            String token64 = StringUtils.substringAfter(authHeader, "Basic ");
            String token = new String(Base64Utils.decodeFromString(token64));

            String[] itemArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");
            String username = itemArray[0];
            String password = itemArray[1];

            User user = userRepository.findByUsername(username);
            if (user != null && SCryptUtil.check(password, user.getPassword())) {
                httpServletRequest.getSession().setAttribute("user", user.buildUserInfo());
                httpServletRequest.getSession().setAttribute(SESSION_KEY_TMP, "yes");
            }
        }

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            HttpSession session = httpServletRequest.getSession();
            if (session.getAttribute(SESSION_KEY_TMP) != null) {
                session.invalidate();
            }
        }
    }
}
