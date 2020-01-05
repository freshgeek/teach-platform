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
import org.ck.teach.teachplatform.service.UserAchvLikeService;
import org.ck.teach.teachplatform.entity.UserAchvLike;

import java.io.Serializable;

/**
* <p>
* 作品点赞 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "作品点赞")
@RequestMapping("/admin/api/userAchvLike")
 public class UserAchvLikeController extends BaseController {

    @Autowired
    private UserAchvLikeService userAchvLikeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserAchvLike userAchvLike){
        IPage page = userAchvLikeService.page(userAchvLike.convertPage(),new QueryWrapper<UserAchvLike>(userAchvLike));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserAchvLike userAchvLike){
        userAchvLikeService.save(userAchvLike);
        return Response.success(userAchvLike);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserAchvLike  userAchvLike){
        userAchvLikeService.removeById(userAchvLike.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserAchvLike  userAchvLike){
        boolean update = userAchvLikeService.updateById(userAchvLike);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserAchvLike  userAchvLike){
        return Response.success(userAchvLikeService.list(new QueryWrapper<UserAchvLike>(userAchvLike)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userAchvLikeService.getById(id));
    }

}
