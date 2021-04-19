package org.yangxin.security.securityuserapi.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author yangxin
 * 2021/4/14 下午8:40
 */
@Data
public class UserInfo {

    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String permissions;

    public boolean hasPermission(String method) {
        return StringUtils.equalsIgnoreCase("get", method)
                ? StringUtils.contains(permissions, "r")
                : StringUtils.contains(permissions, "w");
    }
}
