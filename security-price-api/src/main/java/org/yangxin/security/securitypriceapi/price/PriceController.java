package org.yangxin.security.securitypriceapi.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author yangxin
 * 2021/4/17 下午11:28
 */
@RestController
@RequestMapping("/prices")
@Slf4j
public class PriceController {

    @GetMapping("/{id}")
    public PriceInfo getPrice(@PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("productId is [{}]", id);
        }

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setId(id);
        priceInfo.setPrice(new BigDecimal(100));

        return priceInfo;
    }
}
