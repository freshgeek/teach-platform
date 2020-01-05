package org.ck.teach.teachplatform.service.impl;

import org.ck.teach.teachplatform.entity.File;
import org.ck.teach.teachplatform.mapper.FileDao;
import org.ck.teach.teachplatform.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件记录 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-23
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

}
