package org.ck.teach.teachplatform.common;

import lombok.extern.slf4j.Slf4j;
import org.ck.teach.teachplatform.util.AppUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/28 10:03
 * @description
 */
@Slf4j
@Configuration
public class UserWebMvcConfigurer implements WebMvcConfigurer {


    public HandlerInterceptorAdapter getUserInterceptorAdapter(){
        return new HandlerInterceptorAdapter() {
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                if (modelAndView!=null){
                    modelAndView.addObject(BaseController.MODEL_USER,request.getSession().getAttribute(AppConst.USER_LOGIN_SESSION));
                }
                super.postHandle(request, response, handler, modelAndView);
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserInterceptorAdapter()).addPathPatterns("/**");
    }

}
