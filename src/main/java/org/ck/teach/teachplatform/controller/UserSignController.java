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
import org.ck.teach.teachplatform.service.UserSignService;
import org.ck.teach.teachplatform.entity.UserSign;

import java.io.Serializable;

/**
* <p>
* 用户签到 前端控制器
* </p>
*
* @author 
* @since 2019-12-27
*/

@RestController
@Api(tags = "用户签到")
@RequestMapping("/admin/api/userSign")
 public class UserSignController extends BaseController {

    @Autowired
    private UserSignService userSignService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserSign userSign){
        IPage page = userSignService.page(userSign.convertPage(),new QueryWrapper<UserSign>(userSign));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserSign userSign){
        userSignService.save(userSign);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserSign  userSign){
        userSignService.removeById(userSign.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserSign  userSign){
        boolean update = userSignService.updateById(userSign);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserSign  userSign){
        return Response.success(userSignService.list(new QueryWrapper<UserSign>(userSign)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userSignService.getById(id));
    }

}
