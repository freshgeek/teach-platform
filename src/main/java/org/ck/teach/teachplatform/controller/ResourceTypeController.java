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
import org.ck.teach.teachplatform.service.ResourceTypeService;
import org.ck.teach.teachplatform.entity.ResourceType;

import java.io.Serializable;

/**
* <p>
* 资源分类 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "资源分类")
@RequestMapping("/admin/api/resourceType")
 public class ResourceTypeController extends BaseController {

    @Autowired
    private ResourceTypeService resourceTypeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(ResourceType resourceType){
        IPage page = resourceTypeService.page(resourceType.convertPage(),new QueryWrapper<ResourceType>(resourceType));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(ResourceType resourceType){
        resourceTypeService.save(resourceType);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(ResourceType  resourceType){
        resourceTypeService.removeById(resourceType.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(ResourceType  resourceType){
        boolean update = resourceTypeService.updateById(resourceType);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(ResourceType  resourceType){
        return Response.success(resourceTypeService.list(new QueryWrapper<ResourceType>(resourceType)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(resourceTypeService.getById(id));
    }

}
