package org.ck.teach.teachplatform.web.controller;

import org.ck.teach.teachplatform.annotation.VisitLog;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Contest;
import org.ck.teach.teachplatform.entity.UserVisitLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/26 17:35
 * @description
 */
@Controller
public class PageContestController extends BaseController {

    @GetMapping("/contest/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id, ModelAndView modelAndView) {
        Contest byId = contestService.getById(id);
        modelAndView.addObject("detail", byId);
        modelAndView.setViewName("view/contest-detail");
        userVisitLogService.logVisitService("访问竞赛["+byId.getName()+"]详情","/contest/detail/"+id);

        return modelAndView;
    }

    @GetMapping("/contest-list")
    public ModelAndView contest(ModelAndView modelAndView) {
        modelAndView.setViewName("view/contest-list");
        modelAndView.addObject("contest_list", contestService.list());
        return modelAndView;
    }


}
