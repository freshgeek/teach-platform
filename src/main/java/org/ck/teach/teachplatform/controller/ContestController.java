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
import org.ck.teach.teachplatform.service.ContestService;
import org.ck.teach.teachplatform.entity.Contest;

import java.io.Serializable;

/**
* <p>
* 擂台竞赛 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "擂台竞赛")
@RequestMapping("/admin/api/contest")
 public class ContestController extends BaseController {

    @Autowired
    private ContestService contestService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(Contest contest){
        IPage page = contestService.page(contest.convertPage(),new QueryWrapper<Contest>(contest));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(Contest contest){
        contestService.save(contest);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(Contest  contest){
        contestService.removeById(contest.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(Contest  contest){
        boolean update = contestService.updateById(contest);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(Contest  contest){
        return Response.success(contestService.list(new QueryWrapper<Contest>(contest)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(contestService.getById(id));
    }

}
