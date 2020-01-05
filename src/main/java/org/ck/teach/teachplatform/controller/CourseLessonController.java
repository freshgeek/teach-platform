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
import org.ck.teach.teachplatform.service.CourseLessonService;
import org.ck.teach.teachplatform.entity.CourseLesson;

import java.io.Serializable;

/**
* <p>
* 课程小节 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "课程小节")
@RequestMapping("/admin/api/courseLesson")
 public class CourseLessonController extends BaseController {

    @Autowired
    private CourseLessonService courseLessonService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(CourseLesson courseLesson){
        IPage page = courseLessonService.page(courseLesson.convertPage(),new QueryWrapper<CourseLesson>(courseLesson));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(CourseLesson courseLesson){
        courseLessonService.save(courseLesson);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(CourseLesson  courseLesson){
        courseLessonService.removeById(courseLesson.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(CourseLesson  courseLesson){
        boolean update = courseLessonService.updateById(courseLesson);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(CourseLesson  courseLesson){
        return Response.success(courseLessonService.list(new QueryWrapper<CourseLesson>(courseLesson)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(courseLessonService.getById(id));
    }

}
