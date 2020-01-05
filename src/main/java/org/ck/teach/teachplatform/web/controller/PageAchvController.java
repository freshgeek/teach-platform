package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.UserAchv;
import org.ck.teach.teachplatform.entity.UserAchvFav;
import org.ck.teach.teachplatform.entity.UserAchvLike;
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
public class PageAchvController extends BaseController {


    @GetMapping("/achv/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id,ModelAndView modelAndView){
        modelAndView.addObject("detail",userAchvService.getById(id));
        if (getSessionUser()!=null){
            UserAchvLike like = new UserAchvLike();
            like.setUserId(getSessionUser().getId());
            like.setAchvId(id);
            modelAndView.addObject("like",userAchvLikeService.getOne(new QueryWrapper<>(like)));
            UserAchvFav fav = new UserAchvFav();
            fav.setAchvId(id);
            fav.setUserId(getSessionUser().getId());
            modelAndView.addObject("fav",userAchvFavService.getOne(new QueryWrapper<>(fav)));
        }
        modelAndView.setViewName("view/achv-detail");
        return modelAndView;
    }

}
