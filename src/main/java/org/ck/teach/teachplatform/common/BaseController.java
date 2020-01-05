package org.ck.teach.teachplatform.common;

import com.google.code.kaptcha.Constants;
import org.ck.teach.teachplatform.controller.ContestController;
import org.ck.teach.teachplatform.entity.User;
import org.ck.teach.teachplatform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/26 18:10
 * @description
 */
@Controller
public class BaseController {
    protected static String SORT_FILED = "sort_field";

    protected static String MODEL_USER = "login_user";

    @Autowired
    protected SysInfoService sysInfoService;

    @Autowired
    protected ActivityService activityService;

    @Autowired
    protected ActivityUserService activityUserService;

    @Autowired
    protected ActivityLogService activityLogService;

    @Autowired
    protected UserTipService userTipService;
    @Autowired
    protected UserVisitLogService userVisitLogService;

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserSignService userSignService;

    @Autowired
    protected UserAchvService userAchvService;
    @Autowired
    protected UserAchvCmtService userAchvCmtService;
    @Autowired
    protected UserAchvFavService userAchvFavService;
    @Autowired
    protected UserAchvLikeService userAchvLikeService;
    @Autowired
    protected UserAchvTypeService userAchvTypeService;

    @Autowired
    protected ResourceService resourceService;
    @Autowired
    protected ResourceCmtService resourceCmtService;
    @Autowired
    protected ResourceTypeService resourceTypeService;

    @Autowired
    protected ContestService contestService;
    @Autowired
    protected ContestUserService contestUserService;


    @Autowired
    protected CourseLessonService courseLessonService;
    @Autowired
    protected CourseService courseService;
    @Autowired
    protected CourseTypeService courseTypeService;
    @Autowired
    protected CourseUserService courseUserService;

    @Autowired
    protected TieCmtService tieCmtService;
    @Autowired
    protected TieService tieService;

    protected File staticDir;

    @Value("${staticFilePath}")
    public void setStaticDir(String dir) {

        File file = new File(dir);

        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        staticDir = file;
    }

    /**
     * 获取request对象
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取Response对象
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取Session对象
     */
    protected HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    protected User getSessionUser() {
        return (User) getSession().getAttribute(AppConst.USER_LOGIN_SESSION);
    }

    protected void setSessionUser(Object user) {
        getSession().setAttribute(AppConst.USER_LOGIN_SESSION, user);
    }

    protected String getKaptchaCode() {
        return (String) getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
    }

}


