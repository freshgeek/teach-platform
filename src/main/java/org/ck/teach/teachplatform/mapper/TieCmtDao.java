package org.ck.teach.teachplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ck.teach.teachplatform.entity.Tie;
import org.ck.teach.teachplatform.entity.TieCmt;

import java.util.List;

/**
 * <p>
 * 帖子回复 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
public interface TieCmtDao extends BaseMapper<TieCmt> {

    List<TieCmt> listsByCmtId(Integer cmtId);

    List<TieCmt> pageByCmtList(IPage page, Tie tie);

}
