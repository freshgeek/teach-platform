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
import org.ck.teach.teachplatform.service.UserService;
import org.ck.teach.teachplatform.entity.User;

import java.io.Serializable;

/**
* <p>
* 用户表 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "用户表")
@RequestMapping("/admin/api/user")
 public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(User user){
        IPage page = userService.page(user.convertPage(),new QueryWrapper<User>(user));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(User user){
        userService.save(user);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(User  user){
        userService.removeById(user.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(User  user){
        boolean update = userService.updateById(user);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(User  user){
        return Response.success(userService.list(new QueryWrapper<User>(user)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userService.getById(id));
    }

}
