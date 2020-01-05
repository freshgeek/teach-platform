package org.ck.teach.teachplatform.util;

import org.ck.teach.teachplatform.common.AppConst;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/27 10:22
 * @description
 */
public class WebUtils {

    /***
     * 设置session 属性值
     * @param key
     * @param value
     */
    public static void setSeeionAttr( String key, Object value) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return;
        }
        if (request.getSession() == null) {
            return;
        }
        request.getSession().setAttribute(key, value);
    }
    /***
     * 设置session 属性值
     * @param value
     */
    public static void setLoginSessionUser(Object value) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return;
        }
        if (request.getSession() == null) {
            return;
        }
        request.getSession().setAttribute(AppConst.USER_LOGIN_SESSION, value);
    }
    /***
     * 获取session 属性值
     * @param key
     * @return
     */
    public static <T> T getSessionAttr( String key) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }
        if (request.getSession() == null) {
            return null;
        }
        return (T) request.getSession().getAttribute(key);
    }



    public static <T> T getLoginSessionUser() {
        return (T) getSessionAttr(getHttpServletRequest(), AppConst.USER_LOGIN_SESSION);
    }
    public static HttpServletRequest getHttpServletRequest(){
        return  servletRequestAttributesInheritable().getRequest();
    }
    public static HttpServletResponse getHttpServletResponse(){
        return  servletRequestAttributesInheritable().getResponse();
    }
    /***
     *
     @SpringBootConfiguration
     public class ListenerConfig {


     @Bean
     public RequestContextListener requestContextListener(){
     return new RequestContextListener();
     }
     }

     获取 httpservletrequest 前提需要配置listener
      * @return
     */
    private static ServletRequestAttributes servletRequestAttributesInheritable(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes,true);
        return (ServletRequestAttributes) requestAttributes;
    }



    /***
     * 设置session 属性值
     * @param key
     * @param value
     */
    public static void setSeeionAttr(HttpServletRequest request , String key, Object value) {
        if (request == null) {
            return;
        }
        if (request.getSession() == null) {
            return;
        }
        request.getSession().setAttribute(key, value);
    }
    /***
     * 获取session 属性值
     * @param request
     * @param key
     * @return
     */
    @Deprecated
    public static Object getSessionAttr(HttpServletRequest request, String key) {
        if (request == null) {
            return null;
        }
        if (request.getSession() == null) {
            return null;
        }
        return request.getSession().getAttribute(key);
    }
}
