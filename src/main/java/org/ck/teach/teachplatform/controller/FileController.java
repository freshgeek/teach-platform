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
import org.ck.teach.teachplatform.service.FileService;
import org.ck.teach.teachplatform.entity.File;

import java.io.Serializable;

/**
* <p>
* 文件记录 前端控制器
* </p>
*
* @author 
* @since 2019-12-23
*/

@RestController
@Api(tags = "文件记录")
@RequestMapping("/admin/api/file")
 public class FileController extends BaseController {

    @Autowired
    private FileService fileService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(File file){
        IPage page = fileService.page(file.convertPage(),new QueryWrapper<File>(file));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(File file){
        fileService.save(file);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(File  file){
        fileService.removeById(file.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(File  file){
        boolean update = fileService.updateById(file);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(File  file){
        return Response.success(fileService.list(new QueryWrapper<File>(file)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(fileService.getById(id));
    }

}
