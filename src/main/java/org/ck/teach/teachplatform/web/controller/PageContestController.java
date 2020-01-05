package org.ck.teach.teachplatform.web.controller;

import org.ck.teach.teachplatform.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
        modelAndView.addObject("detail", contestService.getById(id));
        modelAndView.setViewName("view/contest-detail");
        return modelAndView;
    }

    @GetMapping("/contest-list")
    public ModelAndView contest(ModelAndView modelAndView) {
        modelAndView.setViewName("view/contest-list");
        modelAndView.addObject("contest_list", contestService.list());
        return modelAndView;
    }


}
