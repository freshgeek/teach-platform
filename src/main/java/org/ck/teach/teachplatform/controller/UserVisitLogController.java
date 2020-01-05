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
import org.ck.teach.teachplatform.service.UserVisitLogService;
import org.ck.teach.teachplatform.entity.UserVisitLog;

import java.io.Serializable;

/**
* <p>
* 访问足迹 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "访问足迹")
@RequestMapping("/admin/api/userVisitLog")
 public class UserVisitLogController extends BaseController {

    @Autowired
    private UserVisitLogService userVisitLogService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(UserVisitLog userVisitLog){
        IPage page = userVisitLogService.page(userVisitLog.convertPage(),new QueryWrapper<UserVisitLog>(userVisitLog));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(UserVisitLog userVisitLog){
        userVisitLogService.save(userVisitLog);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(UserVisitLog  userVisitLog){
        userVisitLogService.removeById(userVisitLog.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(UserVisitLog  userVisitLog){
        boolean update = userVisitLogService.updateById(userVisitLog);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(UserVisitLog  userVisitLog){
        return Response.success(userVisitLogService.list(new QueryWrapper<UserVisitLog>(userVisitLog)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(userVisitLogService.getById(id));
    }

}
