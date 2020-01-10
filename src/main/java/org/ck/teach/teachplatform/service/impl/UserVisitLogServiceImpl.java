package org.ck.teach.teachplatform.service.impl;

import org.ck.teach.teachplatform.entity.UserVisitLog;
import org.ck.teach.teachplatform.mapper.UserVisitLogDao;
import org.ck.teach.teachplatform.service.UserVisitLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访问足迹 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Service
public class UserVisitLogServiceImpl extends ServiceImpl<UserVisitLogDao, UserVisitLog> implements UserVisitLogService {

    @Override
    @Async
    public void logVisitService(String name, String url) {
        try {
            UserVisitLog visitLog = UserVisitLog.build(name,url);
            baseMapper.insert(visitLog);
        }catch (Exception e){

        }
    }
}
