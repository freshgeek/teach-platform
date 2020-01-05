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
import org.ck.teach.teachplatform.service.SysInfoService;
import org.ck.teach.teachplatform.entity.SysInfo;

import java.io.Serializable;

/**
* <p>
* 创客资讯 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "创客资讯")
@RequestMapping("/admin/api/sysInfo")
 public class SysInfoController extends BaseController {

    @Autowired
    private SysInfoService sysInfoService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(SysInfo sysInfo){
        IPage page = sysInfoService.page(sysInfo.convertPage(),new QueryWrapper<SysInfo>(sysInfo));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(SysInfo sysInfo){
        sysInfoService.save(sysInfo);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(SysInfo  sysInfo){
        sysInfoService.removeById(sysInfo.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(SysInfo  sysInfo){
        boolean update = sysInfoService.updateById(sysInfo);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(SysInfo  sysInfo){
        return Response.success(sysInfoService.list(new QueryWrapper<SysInfo>(sysInfo)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(sysInfoService.getById(id));
    }

}
