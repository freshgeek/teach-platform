package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.io.Files;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.PlatException;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.entity.*;
import org.ck.teach.teachplatform.mapper.TieCmtDao;
import org.ck.teach.teachplatform.service.FileLogService;
import org.ck.teach.teachplatform.service.ResourceService;
import org.ck.teach.teachplatform.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/21 21:21
 * @description
 */
@RestController
public class ApiController extends BaseController {

    @Autowired
    private FileLogService fileLogService;

    @GetMapping("/api/tieCmt/page")
    public Response tieCmtPage(Tie tie){
        IPage tie_id = tieCmtService.page(tie.convertPage(), new QueryWrapper<TieCmt>()
                .eq("tie_id", tie.getId())
        .eq("cmt_id",0));

        List<TieCmt> records = tie_id.getRecords();

        records.forEach(s->{
            List<TieCmt> id = tieCmtService.list(new QueryWrapper<TieCmt>().eq("cmt_id", s.getId()));
            s.setSubTieCmts(id);
        });

        return Response.parse(tie_id);
    }

    @GetMapping("/api/resource/visit/{id}")
    public Response visitId(@PathVariable("id") Integer id){
        resourceService.update(new UpdateWrapper<Resource>().setSql("visit_num = visit_num + 1").eq("id",id));
        return Response.success();
    }

    @PostMapping("/student/api/addTag")
    public Response addTag(@RequestParam Map param) {
        User userId = userService.getById(AppUtils.getMapStr(param, "userId"));
        if (StringUtils.isEmpty(userId.getUserTag())){
            userId.setUserTag(AppUtils.getMapStr(param,"tag"));
        }else{
            userId.setUserTag(userId.getUserTag()+","+AppUtils.getMapStr(param,"tag"));
        }
        userService.update(new UpdateWrapper<User>().set("user_tag",userId.getUserTag()).eq("id",userId.getId()));
        return Response.success();
    }


    @GetMapping("/student/api/readTip/{id}")
    public Response readTip(@PathVariable("id") Integer id) {
        boolean update = userTipService.update(new UpdateWrapper<UserTip>().set("readed", "1").eq("id", id));
        return update ? Response.success() : Response.exception();
    }

    @GetMapping("/student/api/timer")
    public Response studentTimer() {
        Integer id = getSessionUser().getId();
        return Response.success();
    }

    @PostMapping("/teacher/api/activity/note")
    public Response noteAct(ActivityLog activityLog) {
        ActivityLog byId = activityLogService.getById(activityLog.getId());
        byId.setComment(activityLog.getComment());
        byId.setCommentTime(new Date());
        activityLogService.updateById(byId);
        return Response.success();
    }

    @GetMapping("/teacher/api/tie/del/{id}")
    public Response delTie(@PathVariable("id") Integer id){
        tieService.removeById(id);
        return Response.success();
    }

    @GetMapping("/teacher/api/tieCmt/del/{id}")
    public Response delTieCmt(@PathVariable("id") Integer id){
        tieCmtService.removeById(id);
        return Response.success();
    }

    @GetMapping("/student/api/achv/list")
    public Response achvList() {
        UserAchv userAchv = new UserAchv();
        userAchv.setUserId(getSessionUser().getId());
        return Response.success(userAchvService.list(new QueryWrapper<>(userAchv)));
    }

    @GetMapping("/student/api/userTip/page")
    public Response userTipPage(UserTip userTip) {
        userTip.setUserId(getSessionUser().getId());
        return Response.parse(userTipService.page(userTip.convertPage(), new QueryWrapper<>(userTip)));
    }

    @GetMapping("/student/api/userVisitLog/page")
    public Response userTipPage(UserVisitLog userVisitLog) {
        userVisitLog.setUserId(getSessionUser().getId());
        return Response.parse(userVisitLogService.page(userVisitLog.convertPage(),
                new QueryWrapper<>(userVisitLog).orderByDesc("create_time")));
    }

    @GetMapping("/student/achv/page")
    public Response achvPage(UserAchv userAchv) {
        return Response.parse(userAchvService.page(userAchv.convertPage(), new QueryWrapper<>(userAchv)));
    }

    @GetMapping("/student/achvFav/page")
    public Response achvPage(UserAchvFav userAchvFav) {
        userAchvFav.setUserId(getSessionUser().getId());
        IPage page = userAchvFavService.page(userAchvFav.convertPage(), new QueryWrapper<>(userAchvFav));
        page.getRecords().forEach(e -> {
            UserAchvFav fav = (UserAchvFav) e;
            UserAchv byId = userAchvService.getById(fav.getAchvId());
            fav.setAchv(byId);
        });
        return Response.parse(page);
    }

    @GetMapping("/student/resource/page")
    public Response resourcePage(Resource resource) {
        resource.setUserId(getSessionUser().getId());
        return Response.parse(resourceService.page(resource.convertPage(), new QueryWrapper<>(resource)));
    }

    @PostMapping("/student/api/resource/add")
    public Response addResource(Resource resource) {
        resource.setUserId(getSessionUser().getId());
        resourceService.save(resource);
        return Response.success();
    }

    @PostMapping("/uploadEditor")
    public Map uploadEditor(MultipartFile file) {
        Response response = uploadFile(file);
        Map re = new HashMap();
        re.put("code", 1);
        if (AppUtils.isSuccess(response)) {
            FileLog fileLog = (FileLog) response.getBody();
            HashMap inner = new HashMap();
            re.put("code", 0);
            inner.put("src", fileLog.getPath());
            inner.put("title", fileLog.getName());
            re.put("data", inner);
        }
        return re;
    }

    @PostMapping("/upload")
    public Response uploadFile(MultipartFile file) {
        String fileExtension = Files.getFileExtension(file.getOriginalFilename());
        File local = new File(staticDir, String.format("%s_%s.%s", System.currentTimeMillis(),
                UUID.randomUUID().toString(),
                fileExtension
        ));
        User sessionUser = getSessionUser();
        FileLog fileLog = new FileLog();
        try {
            fileLog.setCreateTime(new Date());
            fileLog.setName(file.getOriginalFilename());
            fileLog.setPath(String.format("/%s", local.getName()));
            fileLog.setSize(Long.valueOf(file.getSize()).intValue());
            fileLog.setUserId(sessionUser == null ? 0 : sessionUser.getId());
            fileLogService.save(fileLog);

            file.transferTo(local);
        } catch (IOException e) {
            throw new PlatException("文件上传失败");
        }

        return Response.success(fileLog);

    }

}
