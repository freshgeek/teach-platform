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
import org.ck.teach.teachplatform.service.TieService;
import org.ck.teach.teachplatform.entity.Tie;

import java.io.Serializable;

/**
* <p>
* 讨论帖子 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "讨论帖子")
@RequestMapping("/admin/api/tie")
 public class TieController extends BaseController {

    @Autowired
    private TieService tieService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(Tie tie){
        IPage page = tieService.page(tie.convertPage(),new QueryWrapper<Tie>(tie).orderByDesc("create_time"));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(Tie tie){
        tie.setUserId(getSessionUser().getId());
        tieService.save(tie);
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(Tie  tie){
        tieService.removeById(tie.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(Tie  tie){
        boolean update = tieService.updateById(tie);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(Tie  tie){
        return Response.success(tieService.list(new QueryWrapper<Tie>(tie)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(tieService.getById(id));
    }

}
