package org.yangxin.security.securityuserapi.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author yangxin
 * 2021/4/14 下午8:44
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(), 32768, 8, 1));

        userRepository.save(user);

        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserInfo get(Long id) {
        return userRepository.findById(id)
                .map(User::buildUserInfo)
                .orElse(null);
    }

    @Override
    public List<UserInfo> query(String name) {
        return null;
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        UserInfo result = null;
        User user = userRepository.findByUsername(userInfo.getUsername());
        if (user != null && SCryptUtil.check(userInfo.getPassword(), user.getPassword())) {
            result = user.buildUserInfo();
        }
        return result;
    }
}
