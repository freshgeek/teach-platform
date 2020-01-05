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
import org.ck.teach.teachplatform.service.SysInfoTypeService;
import org.ck.teach.teachplatform.entity.SysInfoType;

import java.io.Serializable;

/**
* <p>
* 资讯类型 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "资讯类型")
@RequestMapping("/admin/api/sysInfoType")
 public class SysInfoTypeController extends BaseController {

    @Autowired
    private SysInfoTypeService sysInfoTypeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(SysInfoType sysInfoType){
        IPage page = sysInfoTypeService.page(sysInfoType.convertPage(),new QueryWrapper<SysInfoType>(sysInfoType));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(SysInfoType sysInfoType){
        sysInfoTypeService.save(sysInfoType);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(SysInfoType  sysInfoType){
        sysInfoTypeService.removeById(sysInfoType.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(SysInfoType  sysInfoType){
        boolean update = sysInfoTypeService.updateById(sysInfoType);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(SysInfoType  sysInfoType){
        return Response.success(sysInfoTypeService.list(new QueryWrapper<SysInfoType>(sysInfoType)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(sysInfoTypeService.getById(id));
    }

}
