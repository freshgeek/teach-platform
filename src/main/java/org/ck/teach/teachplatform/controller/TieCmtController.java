package org.ck.teach.teachplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.ck.teach.teachplatform.entity.Tie;
import org.ck.teach.teachplatform.entity.UserTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.web.bind.annotation.*;

import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.service.TieCmtService;
import org.ck.teach.teachplatform.entity.TieCmt;

import java.io.Serializable;

/**
* <p>
* 帖子回复 前端控制器
* </p>
*
* @author 
* @since 2019-12-26
*/

@RestController
@Api(tags = "帖子回复")
@RequestMapping("/admin/api/tieCmt")
 public class TieCmtController extends BaseController {

    @Autowired
    private TieCmtService tieCmtService;


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(TieCmt tieCmt){
        IPage page = tieCmtService.page(tieCmt.convertPage(),new QueryWrapper<TieCmt>(tieCmt));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(TieCmt tieCmt){
        tieCmt.setUserId(getSessionUser().getId());

        tieCmtService.save(tieCmt);

        Tie byId = tieService.getById(tieCmt.getTieId());
        UserTip userTip = UserTip.build(byId.getUserId(),"有用户给你评论了");
        userTipService.save(userTip);

        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(TieCmt  tieCmt){
        tieCmtService.removeById(tieCmt.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(TieCmt  tieCmt){
        boolean update = tieCmtService.updateById(tieCmt);
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(TieCmt  tieCmt){
        return Response.success(tieCmtService.list(new QueryWrapper<TieCmt>(tieCmt)));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(tieCmtService.getById(id));
    }

}
