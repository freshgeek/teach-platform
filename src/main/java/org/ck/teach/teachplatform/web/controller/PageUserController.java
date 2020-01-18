package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.*;
import org.ck.teach.teachplatform.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/27 9:35
 * @description
 */
@Controller
public class PageUserController extends BaseController {

    @GetMapping("/user/info")
    public ModelAndView info(ModelAndView modelAndView){
        modelAndView.setViewName("view/student/userInfo");
        setSessionUser(userService.getById(getSessionUser()==null?0:getSessionUser().getId()));
        return modelAndView;
    }

    @Autowired
    private UserDao userDao;

    @GetMapping("/user/other/info/{id}")
    public ModelAndView info(ModelAndView modelAndView,@PathVariable("id") Integer id){
        modelAndView.setViewName("view/student/otherInfo");

        UserTip userTip = UserTip.build(id,"用户访问了你的主页");
        userTipService.save(userTip);
        //刷新当前登录人
        setSessionUser(userService.getById(getSessionUser().getId()));
        //查询人
        modelAndView.addObject("other_user",userService.getById(id));
        //作品
        modelAndView.addObject("other_achv_list",userAchvService.list(new QueryWrapper<UserAchv>().eq("user_id", id)
                        .orderByDesc("create_time").last("limit 10"))
                );
        //动态
        List<UserTip> list = userTipService.list(new QueryWrapper<UserTip>()
                .orderByDesc("create_time").last("limit 10"));
        if (getSessionUser()==null|| !getSessionUser().getId().equals(id)){
            list.forEach(t->{
                t.setContent(t.getContent().replaceAll("[你]","他"));
            });
        }

        modelAndView.addObject("other_user_tip",list   );
        //数据
        modelAndView.addObject("count_num",userDao.selectInfoCount(id));
        return modelAndView;
    }

    @GetMapping(value = {"/user/course/{type}","/user/course"})
    public ModelAndView userCourse(ModelAndView modelAndView, CourseUser courseUser, @PathVariable(value = "type" ,required = false) Integer type){
        if (type!=null){
           // course.setTypeId(type);
        }
        courseUser.setUserId(getSessionUser().getId());
        modelAndView.setViewName("view/student/myCourse");
        // 手写分页
        modelAndView.addObject("user_course_page",courseUserService.page(courseUser.convertPage(),new QueryWrapper<>(courseUser)));
        return modelAndView;
    }
}
