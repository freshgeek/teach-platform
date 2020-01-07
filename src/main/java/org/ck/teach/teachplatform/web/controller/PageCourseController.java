package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Course;
import org.ck.teach.teachplatform.entity.CourseLesson;
import org.ck.teach.teachplatform.entity.CourseType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/25 9:30
 * @description
 */
@Controller
public class PageCourseController extends BaseController {

    @GetMapping(value = {"/course/{type}", "/course"})
    public ModelAndView course(ModelAndView modelAndView, Course course, @PathVariable(value = "type", required = false) Integer type) {
        if (type!=null){
            course.setTypeId(type);
        }
        modelAndView.addObject("course_type",
                courseTypeService.list(new QueryWrapper<CourseType>().orderByAsc(SORT_FILED)));
        modelAndView.addObject("course",
                course
        );
        modelAndView.addObject("course_page",
                courseService.page(course.convertPage(), new QueryWrapper<>(course))
        );
        modelAndView.setViewName("view/course");
        return modelAndView;
    }

    @GetMapping("/courseDetail/{id}")
    public ModelAndView course(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Course byId = courseService.getById(id);
        modelAndView.addObject("course", byId);
        modelAndView.addObject("course_type", courseTypeService.getById(byId.getTypeId()));

        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setCourseId(id);
        List<CourseLesson> courseLessons = courseLessonService.list(new QueryWrapper<>(courseLesson));
        modelAndView.addObject("lessons", courseLessons);

        modelAndView.setViewName("view/courseDetail");
        return modelAndView;
    }

    @GetMapping("/course/study/{id}")
    public ModelAndView courseStudy(ModelAndView modelAndView, @PathVariable("id") Integer id) {
        Course byId = courseService.getById(id);
        modelAndView.addObject("course", byId);
        modelAndView.addObject("course_type", courseTypeService.getById(byId.getTypeId()));
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setCourseId(id);
        List<CourseLesson> courseLessons = courseLessonService.list(new QueryWrapper<>(courseLesson));
        modelAndView.addObject("lessons", courseLessons);
        modelAndView.setViewName("view/videoStudy");

        return modelAndView;
    }


}
