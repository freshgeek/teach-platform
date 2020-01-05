package org.ck.teach.teachplatform.web.controller;

import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Tie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/25 20:05
 * @description
 */
@Controller
public class PageTalkController extends BaseController {

    @GetMapping("/talk")
    public ModelAndView talkList(ModelAndView modelAndView, Tie tie){
        modelAndView.setViewName("view/talk");
        return modelAndView;
    }


}
