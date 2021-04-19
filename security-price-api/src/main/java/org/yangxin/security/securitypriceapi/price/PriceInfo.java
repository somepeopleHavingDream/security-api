package org.yangxin.security.securitypriceapi.price;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yangxin
 * 2021/4/17 下午11:29
 */
@Data
public class PriceInfo {

    private Long id;

    private BigDecimal price;
}
