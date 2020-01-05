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
import org.ck.teach.teachplatform.service.ResourceCmtService;
import org.ck.teach.teachplatform.entity.ResourceCmt;

import java.io.Serializable;

/**
* <p>
* 资源评论 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "资源评论")
@RequestMapping("/admin/api/resourceCmt")
 public class ResourceCmtController extends BaseController {

    @Autowired
    private ResourceCmtService resourceCmtService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(ResourceCmt resourceCmt){
        IPage page = resourceCmtService.page(resourceCmt.convertPage(),new QueryWrapper<ResourceCmt>(resourceCmt));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(ResourceCmt resourceCmt){
        resourceCmtService.save(resourceCmt);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(ResourceCmt  resourceCmt){
        resourceCmtService.removeById(resourceCmt.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(ResourceCmt  resourceCmt){
        boolean update = resourceCmtService.updateById(resourceCmt);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(ResourceCmt  resourceCmt){
        return Response.success(resourceCmtService.list(new QueryWrapper<ResourceCmt>(resourceCmt)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(resourceCmtService.getById(id));
    }

}
