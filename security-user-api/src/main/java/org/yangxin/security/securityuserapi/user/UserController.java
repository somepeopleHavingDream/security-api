package org.yangxin.security.securityuserapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author yangxin
 * 2021/4/10 下午4:33
 */
@SuppressWarnings("SqlResolve")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public void login(@Validated UserInfo userInfo, HttpServletRequest request) {
        UserInfo info = userService.login(userInfo);

        // session固定攻击防护
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.getSession(true).setAttribute("user", info);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo userInfo) {
        return userService.create(userInfo);
    }

    @PutMapping("{id}")
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userService.update(userInfo);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo == null || !Objects.equals(userInfo.getId(), id)) {
            throw new RuntimeException("身份认证信息异常，获取用户信息失败！");
        }

        return userService.get(id);
    }

    @GetMapping
    public List<UserInfo> query(String name) {
        return userService.query(name);
    }
}
