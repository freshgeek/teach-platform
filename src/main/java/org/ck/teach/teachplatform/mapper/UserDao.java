package org.ck.teach.teachplatform.mapper;

import org.apache.ibatis.annotations.Update;
import org.ck.teach.teachplatform.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
public interface UserDao extends BaseMapper<User> {

    Map selectInfoCount(Integer id);

    @Update("update t_user set point = point + #{addPoint} where id = #{userId}")
    boolean updateUserPoint(Integer userId, Integer addPoint);
}
