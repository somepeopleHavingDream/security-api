package org.yangxin.security.securityorderapi.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.yangxin.security.securityorderapi.price.PriceInfo;
import org.yangxin.security.securityorderapi.server.resource.User;

/**
 * @author yangxin
 * 2021/4/17 下午11:22
 */
@SuppressWarnings({"AlibabaRemoveCommentedCode"})
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:9060/prices/";

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo orderInfo, @AuthenticationPrincipal User user) {
        if (log.isInfoEnabled()) {
            log.info("userId is [{}]", user.getId());
        }

        return orderInfo;
    }

    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("id: [{}]", id);
        }

        return new OrderInfo();
    }
}
