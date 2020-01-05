package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Activity;
import org.ck.teach.teachplatform.entity.ActivityLog;
import org.ck.teach.teachplatform.entity.ActivityUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/29 19:58
 * @description
 */
@Controller
public class PageActivityController extends BaseController {

    @GetMapping("/activity/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id,ModelAndView modelAndView){
        Activity activity = activityService.getById(id);

        modelAndView.addObject("detail",activity);
        modelAndView.addObject("user_list",activityUserService.list(
                new QueryWrapper<ActivityUser>().eq("atv_id",activity.getId())
        ));
        modelAndView.addObject("teach",userService.getById(activity.getUserId()));
        modelAndView.addObject("log_list",activityLogService.list(
           new QueryWrapper<ActivityLog>().eq("atv_id",activity.getId())
        ));
        modelAndView.setViewName("view/act-detail");
        return modelAndView;
    }

}
