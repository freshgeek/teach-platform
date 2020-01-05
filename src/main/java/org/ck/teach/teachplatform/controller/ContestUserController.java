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
import org.ck.teach.teachplatform.service.ContestUserService;
import org.ck.teach.teachplatform.entity.ContestUser;

import java.io.Serializable;

/**
* <p>
* 竞赛参与 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "竞赛参与")
@RequestMapping("/admin/api/contestUser")
 public class ContestUserController extends BaseController {

    @Autowired
    private ContestUserService contestUserService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(ContestUser contestUser){
        IPage page = contestUserService.page(contestUser.convertPage(),new QueryWrapper<ContestUser>(contestUser));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(ContestUser contestUser){
        contestUserService.save(contestUser);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(ContestUser  contestUser){
        contestUserService.removeById(contestUser.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(ContestUser  contestUser){
        boolean update = contestUserService.updateById(contestUser);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(ContestUser  contestUser){
        return Response.success(contestUserService.list(new QueryWrapper<ContestUser>(contestUser)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(contestUserService.getById(id));
    }

}
