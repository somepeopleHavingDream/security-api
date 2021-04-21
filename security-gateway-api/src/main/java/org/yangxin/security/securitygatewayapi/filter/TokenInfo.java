package org.yangxin.security.securitygatewayapi.filter;

import lombok.Data;

import java.util.Date;

/**
 * @author yangxin
 * 2021/4/21 下午9:00
 */
@Data
public class TokenInfo {

    private Boolean active;

    private String clientId;

    private String[] scope;

    private String username;

    private String[] aud;

    private Date exp;

    private String[] authorities;
}
