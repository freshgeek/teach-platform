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
import org.ck.teach.teachplatform.service.CourseTypeService;
import org.ck.teach.teachplatform.entity.CourseType;

import java.io.Serializable;

/**
* <p>
* 课程类别 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "课程类别")
@RequestMapping("/admin/api/courseType")
 public class CourseTypeController extends BaseController {

    @Autowired
    private CourseTypeService courseTypeService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(CourseType courseType){
        IPage page = courseTypeService.page(courseType.convertPage(),new QueryWrapper<CourseType>(courseType));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(CourseType courseType){
        courseTypeService.save(courseType);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(CourseType  courseType){
        courseTypeService.removeById(courseType.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(CourseType  courseType){
        boolean update = courseTypeService.updateById(courseType);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(CourseType  courseType){
        return Response.success(courseTypeService.list(new QueryWrapper<CourseType>(courseType)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(courseTypeService.getById(id));
    }

}
