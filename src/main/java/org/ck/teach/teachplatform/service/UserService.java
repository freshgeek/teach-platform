package org.ck.teach.teachplatform.service;

import org.ck.teach.teachplatform.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
public interface UserService extends IService<User> {

    @Async
    void addPoint(Integer userId, Integer addPoint, String content);
}
