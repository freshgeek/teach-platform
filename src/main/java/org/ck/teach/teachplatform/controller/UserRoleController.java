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
import org.ck.teach.teachplatform.service.UserRoleService;
import org.ck.teach.teachplatform.entity.UserRole;

import java.io.Serializable;

/**
* <p>
* 用户角色 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "用户角色")
@RequestMapping("/admin/api/userRole")
 public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserRole userRole){
        IPage page = userRoleService.page(userRole.convertPage(),new QueryWrapper<UserRole>(userRole));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserRole userRole){
        userRoleService.save(userRole);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserRole  userRole){
        userRoleService.removeById(userRole.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserRole  userRole){
        boolean update = userRoleService.updateById(userRole);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserRole  userRole){
        return Response.success(userRoleService.list(new QueryWrapper<UserRole>(userRole)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userRoleService.getById(id));
    }

}
