package org.yangxin.security.securitygatewayapi.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.yangxin.security.securitygatewayapi.other.TokenInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangxin
 * 2021/4/21 下午9:39
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if (log.isInfoEnabled()) {
            log.info("authorization start");
        }

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if (tokenInfo != null && tokenInfo.isActive()) {
                if (!hasPermission(tokenInfo, request)) {
                    if (log.isInfoEnabled()) {
                        log.info("audit log update fail 401");
                        handleError(403, requestContext);
                    }
                }

                requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
            } else {
                final String tokenRequestPrefix = "/token";
                if (!StringUtils.startsWith(request.getRequestURI(), tokenRequestPrefix)) {
                    if (log.isInfoEnabled()) {
                        log.info("audit log update fail 401");
                        handleError(401, requestContext);
                    }
                }
            }
        }

        return null;
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return true;
//        return RandomUtils.nextInt(10) > 2;
    }

    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody("{\"message\": \"auth fail\"}");
        requestContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
