package org.yangxin.security.securityuserapi.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yangxin
 * 2021/4/10 下午7:06
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

    /**
     * 通过姓名，查找相关记录
     *
     * @param username 姓名
     * @return 同一姓名的相关记录
     */
    User findByUsername(String username);
}
