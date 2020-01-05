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
import org.ck.teach.teachplatform.service.UserAchvTypeService;
import org.ck.teach.teachplatform.entity.UserAchvType;

import java.io.Serializable;

/**
* <p>
* 成果分类 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "成果分类")
@RequestMapping("/admin/api/userAchvType")
 public class UserAchvTypeController extends BaseController {

    @Autowired
    private UserAchvTypeService userAchvTypeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserAchvType userAchvType){
        IPage page = userAchvTypeService.page(userAchvType.convertPage(),new QueryWrapper<UserAchvType>(userAchvType));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserAchvType userAchvType){
        userAchvTypeService.save(userAchvType);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserAchvType  userAchvType){
        userAchvTypeService.removeById(userAchvType.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserAchvType  userAchvType){
        boolean update = userAchvTypeService.updateById(userAchvType);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserAchvType  userAchvType){
        return Response.success(userAchvTypeService.list(new QueryWrapper<UserAchvType>(userAchvType)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userAchvTypeService.getById(id));
    }

}
