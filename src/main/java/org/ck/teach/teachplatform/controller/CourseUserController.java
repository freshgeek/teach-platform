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
import org.ck.teach.teachplatform.service.CourseUserService;
import org.ck.teach.teachplatform.entity.CourseUser;

import java.io.Serializable;

/**
* <p>
* 用户课程报名 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "用户课程报名")
@RequestMapping("/admin/api/courseUser")
 public class CourseUserController extends BaseController {

    @Autowired
    private CourseUserService courseUserService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(CourseUser courseUser){
        IPage page = courseUserService.page(courseUser.convertPage(),new QueryWrapper<CourseUser>(courseUser));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(CourseUser courseUser){
        courseUserService.save(courseUser);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(CourseUser  courseUser){
        courseUserService.removeById(courseUser.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(CourseUser  courseUser){
        boolean update = courseUserService.updateById(courseUser);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(CourseUser  courseUser){
        return Response.success(courseUserService.list(new QueryWrapper<CourseUser>(courseUser)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(courseUserService.getById(id));
    }

}
