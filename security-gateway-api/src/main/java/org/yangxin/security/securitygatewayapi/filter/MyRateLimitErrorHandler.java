package org.yangxin.security.securitygatewayapi.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

/**
 * @author yangxin
 * 2021/4/22 下午9:48
 */
@Component
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }
}
