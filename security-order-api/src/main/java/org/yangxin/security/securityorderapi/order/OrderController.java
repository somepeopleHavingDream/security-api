package org.yangxin.security.securityorderapi.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public OrderInfo create(@RequestBody OrderInfo orderInfo, @RequestHeader String username) {
        if (log.isInfoEnabled()) {
            log.info("username is [{}]", username);
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
