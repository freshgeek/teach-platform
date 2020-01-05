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
import org.ck.teach.teachplatform.service.ResourceService;
import org.ck.teach.teachplatform.entity.Resource;

import java.io.Serializable;

/**
* <p>
* 创客资源 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "创客资源")
@RequestMapping("/admin/api/resource")
 public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(Resource resource){
        IPage page = resourceService.page(resource.convertPage(),new QueryWrapper<Resource>(resource));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(Resource resource){
        resourceService.save(resource);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(Resource  resource){
        resourceService.removeById(resource.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(Resource  resource){
        boolean update = resourceService.updateById(resource);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(Resource  resource){
        return Response.success(resourceService.list(new QueryWrapper<Resource>(resource)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(resourceService.getById(id));
    }

}
