package ${package.Controller};
<#assign argService=table.entityName?uncap_first+'Service' >
<#assign argEntity=table.entityName?uncap_first >

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.*;
<#else>
import org.springframework.stereotype.Controller;
</#if>

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#if superEntityClassPackage??>
import ${superEntityClassPackage};
</#if>
<#if cfg.rspPackage??>
import ${cfg.rspPackage};
</#if>
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};

import java.io.Serializable;

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/

<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Api(tags = "${table.comment!}")
@RequestMapping("/admin/api<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
 public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
 public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${argService};


    @GetMapping("/page")
    @ApiOperation("分页")
    public Response getPages(${table.entityName} ${argEntity}){
        IPage page = ${argService}.page(${argEntity}.convertPage(),new QueryWrapper<${table.entityName}>(${argEntity}));
        return Response.parse(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public Response add(${table.entityName} ${argEntity}){
        ${argService}.save(${argEntity});
        return Response.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public Response delete(${table.entityName}  ${argEntity}){
        ${argService}.removeById(${argEntity}.getId());
        return Response.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Response update(${table.entityName}  ${argEntity}){
        boolean update = ${argService}.updateById(${argEntity});
        return update ? Response.success() : Response.exception("更新失败");
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public Response list(${table.entityName}  ${argEntity}){
        return Response.success(${argService}.list(new QueryWrapper<${table.entityName}>(${argEntity})));
    }

    @GetMapping("/find/{id}")
    @ApiOperation("通过id查询")
    public Response find(@PathVariable("id") Serializable id){
        return new Response(${argService}.getById(id));
    }

}
</#if>
