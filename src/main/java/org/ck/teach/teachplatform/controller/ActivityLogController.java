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
import org.ck.teach.teachplatform.service.ActivityLogService;
import org.ck.teach.teachplatform.entity.ActivityLog;

import java.io.Serializable;

/**
* <p>
* 项目记录 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "项目记录")
@RequestMapping("/admin/api/activityLog")
 public class ActivityLogController extends BaseController {

    @Autowired
    private ActivityLogService activityLogService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(ActivityLog activityLog){
        IPage page = activityLogService.page(activityLog.convertPage(),new QueryWrapper<ActivityLog>(activityLog));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(ActivityLog activityLog){
        activityLogService.save(activityLog);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(ActivityLog  activityLog){
        activityLogService.removeById(activityLog.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(ActivityLog  activityLog){
        boolean update = activityLogService.updateById(activityLog);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(ActivityLog  activityLog){
        return Response.success(activityLogService.list(new QueryWrapper<ActivityLog>(activityLog)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(activityLogService.getById(id));
    }

}
