package org.ck.teach.teachplatform.service.impl;

import org.ck.teach.teachplatform.entity.User;
import org.ck.teach.teachplatform.entity.UserTip;
import org.ck.teach.teachplatform.mapper.UserDao;
import org.ck.teach.teachplatform.mapper.UserTipDao;
import org.ck.teach.teachplatform.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ck.teach.teachplatform.service.UserTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    @Autowired
    private UserTipDao userTipDao;

    @Async
    @Override
    public void addPoint(Integer userId,Integer addPoint,String content){
        if (baseMapper.updateUserPoint(userId, addPoint)){
            UserTip build = UserTip.build(userId, content);
            userTipDao.insert(build);
        }
    }
}
