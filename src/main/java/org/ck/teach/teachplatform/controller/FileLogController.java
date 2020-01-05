package org.ck.teach.teachplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.web.bind.annotation.*;

import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.service.FileLogService;
import org.ck.teach.teachplatform.entity.FileLog;

import java.io.Serializable;

/**
* <p>
* 文件记录 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "文件记录")
@RequestMapping("/admin/api/fileLog")
 public class FileLogController extends BaseController {

    @Autowired
    private FileLogService fileLogService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(FileLog fileLog){
        IPage page = fileLogService.page(fileLog.convertPage(),new QueryWrapper<FileLog>(fileLog));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(FileLog fileLog){
        fileLogService.save(fileLog);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(FileLog  fileLog){
        fileLogService.removeById(fileLog.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(FileLog  fileLog){
        boolean update = fileLogService.updateById(fileLog);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(FileLog  fileLog){
        return Response.success(fileLogService.list(new QueryWrapper<FileLog>(fileLog)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(fileLogService.getById(id));
    }

}
