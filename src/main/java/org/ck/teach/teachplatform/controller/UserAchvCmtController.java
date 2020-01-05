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
import org.ck.teach.teachplatform.service.UserAchvCmtService;
import org.ck.teach.teachplatform.entity.UserAchvCmt;

import java.io.Serializable;

/**
* <p>
* 作品评论 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "作品评论")
@RequestMapping("/admin/api/userAchvCmt")
 public class UserAchvCmtController extends BaseController {

    @Autowired
    private UserAchvCmtService userAchvCmtService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserAchvCmt userAchvCmt){
        IPage page = userAchvCmtService.page(userAchvCmt.convertPage(),new QueryWrapper<UserAchvCmt>(userAchvCmt));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserAchvCmt userAchvCmt){
        userAchvCmtService.save(userAchvCmt);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserAchvCmt  userAchvCmt){
        userAchvCmtService.removeById(userAchvCmt.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserAchvCmt  userAchvCmt){
        boolean update = userAchvCmtService.updateById(userAchvCmt);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserAchvCmt  userAchvCmt){
        return Response.success(userAchvCmtService.list(new QueryWrapper<UserAchvCmt>(userAchvCmt)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userAchvCmtService.getById(id));
    }

}
