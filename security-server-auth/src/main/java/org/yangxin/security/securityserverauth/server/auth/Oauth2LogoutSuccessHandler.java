package org.yangxin.security.securityserverauth.server.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangxin
 * 2021/5/3 下午1:07
 */
@Component
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        String redirectUri = httpServletRequest.getParameter("redirect_uri");
        if (StringUtils.isNotBlank(redirectUri)) {
            httpServletResponse.sendRedirect(redirectUri);
        }
    }
}
