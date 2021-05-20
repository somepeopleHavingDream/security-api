package org.yangxin.security.securitygatewayapi.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.yangxin.security.securitygatewayapi.other.TokenInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证
 *
 * @author yangxin
 * 2021/4/21 下午8:41
 */
@Component
@Slf4j
public class OauthFilter extends ZuulFilter {

    private final String AUTH_HEADER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("oauth start");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        final String tokenRequestPrefix = "/token";
        if (StringUtils.startsWith(request.getRequestURI(), tokenRequestPrefix)) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        if (!StringUtils.startsWithIgnoreCase(authHeader, AUTH_HEADER_PREFIX)) {
            return null;
        }

        try {
            TokenInfo tokenInfo = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", tokenInfo);
        } catch (Exception e) {
            log.error("get token info fail", e);
        }

        return null;
    }

    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, AUTH_HEADER_PREFIX);
        String oauthServiceUrl = "http://localhost:9090/oauth/check_token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, httpHeaders);

        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl,
                HttpMethod.POST, entity, TokenInfo.class);
        TokenInfo tokenInfo = response.getBody();
        if (log.isInfoEnabled()) {
            log.info("token info: [{}]", tokenInfo);
        }

        return tokenInfo;
    }
}
