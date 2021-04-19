package org.yangxin.security.securityuserapi.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangxin
 * 2021/4/10 下午4:30
 */
@Data
@Entity(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 3247760470265919178L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为null")
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    private String permissions;

    public UserInfo buildUserInfo() {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this, userInfo);
        return userInfo;
    }
}
