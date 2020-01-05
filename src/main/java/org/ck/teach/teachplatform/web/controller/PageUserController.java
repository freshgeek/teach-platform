package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Course;
import org.ck.teach.teachplatform.entity.CourseUser;
import org.ck.teach.teachplatform.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/user/other/info/{id}")
    public ModelAndView info(ModelAndView modelAndView,@PathVariable("id") Integer id){
        modelAndView.setViewName("view/student/otherInfo");
        setSessionUser(userService.getById(id));
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
