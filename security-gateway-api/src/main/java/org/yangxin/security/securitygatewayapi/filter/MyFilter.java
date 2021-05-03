package org.yangxin.security.securitygatewayapi.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangxin
 * 2021/5/3 下午2:07
 */
@Component
public class MyFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return StringUtils.equals(request.getRequestURI(), "/user/me");
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String user = requestContext.getZuulRequestHeaders().get("username");
        if (StringUtils.isNotBlank(user)) {
            requestContext.setResponseBody("{\"username\":\""+user+"\"}");
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(200);
        requestContext.getResponse().setContentType("application/json");
        return null;
    }
}
