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
import org.ck.teach.teachplatform.service.UserTipService;
import org.ck.teach.teachplatform.entity.UserTip;

import java.io.Serializable;

/**
* <p>
* 用户动态 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "用户动态")
@RequestMapping("/admin/api/userTip")
 public class UserTipController extends BaseController {

    @Autowired
    private UserTipService userTipService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserTip userTip){
        IPage page = userTipService.page(userTip.convertPage(),new QueryWrapper<UserTip>(userTip));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserTip userTip){
        userTipService.save(userTip);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserTip  userTip){
        userTipService.removeById(userTip.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserTip  userTip){
        boolean update = userTipService.updateById(userTip);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserTip  userTip){
        return Response.success(userTipService.list(new QueryWrapper<UserTip>(userTip)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userTipService.getById(id));
    }

}
