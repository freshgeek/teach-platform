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
import org.ck.teach.teachplatform.service.CourseService;
import org.ck.teach.teachplatform.entity.Course;

import java.io.Serializable;

/**
* <p>
* 课程 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "课程")
@RequestMapping("/admin/api/course")
 public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(Course course){
        IPage page = courseService.page(course.convertPage(),new QueryWrapper<Course>(course));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(Course course){
        courseService.save(course);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(Course  course){
        courseService.removeById(course.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(Course  course){
        boolean update = courseService.updateById(course);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(Course  course){
        return Response.success(courseService.list(new QueryWrapper<Course>(course)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(courseService.getById(id));
    }

}
