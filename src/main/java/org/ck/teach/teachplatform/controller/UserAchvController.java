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
import org.ck.teach.teachplatform.service.UserAchvService;
import org.ck.teach.teachplatform.entity.UserAchv;

import java.io.Serializable;

/**
* <p>
* 成果作品 前端控制器
* </p>
*
* @author 
* @since 2019-12-29
*/

@RestController
@Api(tags = "成果作品")
@RequestMapping("/admin/api/userAchv")
 public class UserAchvController extends BaseController {

    @Autowired
    private UserAchvService userAchvService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserAchv userAchv){
        IPage page = userAchvService.page(userAchv.convertPage(),new QueryWrapper<UserAchv>(userAchv));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserAchv userAchv){
        userAchv.setUserId(getSessionUser().getId());
        userAchvService.save(userAchv);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserAchv  userAchv){
        userAchvService.removeById(userAchv.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserAchv  userAchv){
        boolean update = userAchvService.updateById(userAchv);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserAchv  userAchv){
        return Response.success(userAchvService.list(new QueryWrapper<UserAchv>(userAchv)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userAchvService.getById(id));
    }

}
