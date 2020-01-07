package org.ck.teach.teachplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.ck.teach.teachplatform.annotation.TipLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.web.bind.annotation.*;

import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.service.UserAchvFavService;
import org.ck.teach.teachplatform.entity.UserAchvFav;

import java.io.Serializable;

/**
* <p>
* 作品收藏 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "作品收藏")
@RequestMapping("/admin/api/userAchvFav")
 public class UserAchvFavController extends BaseController {

    @Autowired
    private UserAchvFavService userAchvFavService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserAchvFav userAchvFav){
        IPage page = userAchvFavService.page(userAchvFav.convertPage(),new QueryWrapper<UserAchvFav>(userAchvFav));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserAchvFav userAchvFav){
        userAchvFavService.save(userAchvFav);
        return Response.success(userAchvFav);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserAchvFav  userAchvFav){
        userAchvFavService.removeById(userAchvFav.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserAchvFav  userAchvFav){
        boolean update = userAchvFavService.updateById(userAchvFav);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserAchvFav  userAchvFav){
        return Response.success(userAchvFavService.list(new QueryWrapper<UserAchvFav>(userAchvFav)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userAchvFavService.getById(id));
    }

}
