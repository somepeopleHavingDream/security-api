package org.yangxin.security.securitygatewayapi.other;

import lombok.Data;

import java.util.Date;

/**
 * @author yangxin
 * 2021/4/21 下午9:00
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Data
public class TokenInfo {

    private boolean active;

    private String client_id;

    private String[] scope;

    private String user_name;

    private String[] aud;

    private Date exp;

    private String[] authorities;
}
