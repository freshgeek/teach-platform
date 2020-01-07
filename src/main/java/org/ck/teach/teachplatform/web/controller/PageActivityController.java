package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Activity;
import org.ck.teach.teachplatform.entity.ActivityLog;
import org.ck.teach.teachplatform.entity.ActivityUser;
import org.ck.teach.teachplatform.entity.UserVisitLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/29 19:58
 * @description
 */
@Controller
public class PageActivityController extends BaseController {

    @GetMapping("/activity/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id, ModelAndView modelAndView) {
        Activity activity = activityService.getById(id);

        modelAndView.addObject("detail", activity);
        List<ActivityUser> userList = activityUserService.list(
                new QueryWrapper<ActivityUser>().eq("atv_id", activity.getId())
        );

        UserVisitLog visitLog = UserVisitLog.build("访问项目["+activity.getName()+"]详情","/activity/detail/"+id);
        userVisitLogService.save(visitLog);

        modelAndView.addObject("user_list", userList);
        modelAndView.addObject("hasJoin", activityUserService.getOne(new QueryWrapper<ActivityUser>()
                .eq("atv_id", id)
                .eq("user_id",
                        getSessionUser() == null ?
                                0 : getSessionUser().getId())));

        modelAndView.addObject("teach", userService.getById(activity.getUserId()));
        modelAndView.addObject("log_list", activityLogService.list(
                new QueryWrapper<ActivityLog>().eq("atv_id", activity.getId()).orderByDesc("create_time")
        ));
        modelAndView.setViewName("view/act-detail");
        return modelAndView;
    }

}
