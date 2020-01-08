package org.ck.teach.teachplatform.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import freemarker.template.utility.DateUtil;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.entity.User;
import org.ck.teach.teachplatform.entity.UserSign;
import org.ck.teach.teachplatform.entity.UserTip;
import org.ck.teach.teachplatform.entity.UserVisitLog;
import org.ck.teach.teachplatform.entity.form.UserLoginForm;
import org.ck.teach.teachplatform.service.UserService;
import org.ck.teach.teachplatform.util.AppUtils;
import org.ck.teach.teachplatform.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/23 15:35
 * @description
 */
@RestController
public class UserLoginController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public void logout() throws IOException {
        getSession().invalidate();
        getResponse().sendRedirect("/");
    }

    @PostMapping("/login")
    public Response login(UserLoginForm userLoginForm) {
        User user = BeanUtils.copyProperties(userLoginForm, User.class);
        String kaptchaCode = getKaptchaCode();
        if (!kaptchaCode.equals(userLoginForm.getCode())) {
            return Response.exception("验证码错误");
        }

        User one = userService.getOne(new QueryWrapper<>(user));
        if (one == null) {
            return Response.exception("账号或密码错误");
        }
        setSessionUser(one);
        UserVisitLog visitLog = UserVisitLog.build("用户登录","#");
        userVisitLogService.save(visitLog);
        return Response.success(one);
    }

    @PostMapping("/register")
    public Response register(User user) {
        User one = userService.getOne(new QueryWrapper<User>().eq("phone", user.getPhone()));
        if (one != null) {
            return Response.exception("手机号已存在,找回密码?");
        }
        user.setPoint(0);
        userService.save(user);
        return Response.success();
    }

    @PostMapping("/forgetPassword")
    public Response forgetPassword(User user) {
        User one = userService.getOne(new QueryWrapper<>(user).eq("phone", user.getPhone()));
        if (one != null) {
            return Response.exception("手机号已存在,找回密码?");
        }
        userService.save(user);
        return Response.success();
    }

    @GetMapping("/loadLoginUser")
    public Response loadLoginUser() {
        User sessionUser = getSessionUser();
        if (sessionUser == null) {
            return Response.exception("请登录");
        }
        User byId = userService.getById(sessionUser.getId());
        return Response.success(byId);
    }

    @GetMapping("/getNotify")
    public Response getNotify() {
        UserTip userTip = new UserTip();
        userTip.setUserId(getSessionUser().getId());
        userTip.setReaded("0");
        return Response.success(userTipService.count(new QueryWrapper<>(userTip)));
    }

    @GetMapping("/getTodaySign")
    public Response getTodaySign() {
        User sessionUser = getSessionUser();
        return Response.success(userSignService.getOne(new QueryWrapper<UserSign>().eq("user_id", sessionUser.getId())
                .ge("create_time", AppUtils.toDay())
        ));
    }

    @PostMapping("/user/sign")
    public Response sign() {
        Response todaySign = getTodaySign();
        if (todaySign.getBody() != null) {
            return Response.exception("已经签到过了");
        }
        User sessionUser = getSessionUser();
        UserSign userSign = new UserSign();
        userSign.setUserId(sessionUser.getId());
        userSign.setCreateTime(new Date());
        userSign.setSignDate(new Date());
        userSignService.save(userSign);
        addPoint(sessionUser.getId());

        UserVisitLog visitLog = UserVisitLog.build("用户签到","#");
        userVisitLogService.save(visitLog);

        return Response.success();
    }

    private void addPoint(Integer userId) {

        List<UserSign> list = userSignService.list(new QueryWrapper<UserSign>().eq("user_id", userId)
                .ge("create_time", AppUtils.getBeforeDay(6)).orderByDesc("create_time"));
        int point = 1;

        Set<UserSign> collect = list.stream().collect(Collectors.toSet());

        for (UserSign userSign : collect) {
            if (AppUtils.dateStep(userSign.getSignDate(), point, new Date())) {
                point++;
            } else {
                break;
            }
        }

        boolean id = userService.update(new UpdateWrapper<User>().setSql(" point = point + " + point)
                .eq("id", userId));
    }

}
