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
import org.ck.teach.teachplatform.service.ActivityUserService;
import org.ck.teach.teachplatform.entity.ActivityUser;

import java.io.Serializable;

/**
* <p>
* 项目参与 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "项目参与")
@RequestMapping("/admin/api/activityUser")
 public class ActivityUserController extends BaseController {

    @Autowired
    private ActivityUserService activityUserService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(ActivityUser activityUser){
        IPage page = activityUserService.page(activityUser.convertPage(),new QueryWrapper<ActivityUser>(activityUser));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(ActivityUser activityUser){
        activityUserService.save(activityUser);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(ActivityUser  activityUser){
        activityUserService.removeById(activityUser.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(ActivityUser  activityUser){
        boolean update = activityUserService.updateById(activityUser);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(ActivityUser  activityUser){
        return Response.success(activityUserService.list(new QueryWrapper<ActivityUser>(activityUser)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(activityUserService.getById(id));
    }

}
