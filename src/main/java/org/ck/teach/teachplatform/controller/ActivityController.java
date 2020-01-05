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
import org.ck.teach.teachplatform.service.ActivityService;
import org.ck.teach.teachplatform.entity.Activity;

import java.io.Serializable;

/**
* <p>
* 项目活动 前端控制器
* </p>
*
* @author 
* @since 2019-12-31
*/

@RestController
@Api(tags = "项目活动")
@RequestMapping("/admin/api/activity")
 public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(Activity activity){
        IPage page = activityService.page(activity.convertPage(),new QueryWrapper<Activity>(activity));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(Activity activity){
        activityService.save(activity);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(Activity  activity){
        activityService.removeById(activity.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(Activity  activity){
        boolean update = activityService.updateById(activity);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(Activity  activity){
        return Response.success(activityService.list(new QueryWrapper<Activity>(activity)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(activityService.getById(id));
    }

}
