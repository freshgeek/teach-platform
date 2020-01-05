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
import org.ck.teach.teachplatform.service.SysNoticeService;
import org.ck.teach.teachplatform.entity.SysNotice;

import java.io.Serializable;

/**
* <p>
* 公告 前端控制器
* </p>
*
* @author 
* @since 2019-12-21
*/

@RestController
@Api(tags = "公告")
@RequestMapping("/admin/api/sysNotice")
 public class SysNoticeController extends BaseController {

    @Autowired
    private SysNoticeService sysNoticeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(SysNotice sysNotice){
        IPage page = sysNoticeService.page(sysNotice.convertPage(),new QueryWrapper<SysNotice>(sysNotice));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(SysNotice sysNotice){
        sysNoticeService.save(sysNotice);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(SysNotice  sysNotice){
        sysNoticeService.removeById(sysNotice.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(SysNotice  sysNotice){
        boolean update = sysNoticeService.updateById(sysNotice);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(SysNotice  sysNotice){
        return Response.success(sysNoticeService.list(new QueryWrapper<SysNotice>(sysNotice)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(sysNoticeService.getById(id));
    }

}
