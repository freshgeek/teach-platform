package org.ck.teach.teachplatform.service.impl;

import org.ck.teach.teachplatform.entity.User;
import org.ck.teach.teachplatform.mapper.UserDao;
import org.ck.teach.teachplatform.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
