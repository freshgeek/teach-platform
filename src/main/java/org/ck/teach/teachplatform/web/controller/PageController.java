package org.ck.teach.teachplatform.web.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.ck.teach.teachplatform.common.AppConst;
import org.ck.teach.teachplatform.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/21 21:22
 * @description
 */
@Slf4j
@Controller
public class PageController extends BaseController {

    @Autowired
    private Producer producer;

    @GetMapping("/imageCodeServlet")
    public void getCodeImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType(AppConst.REQ_CONTENT_TYPE_PNG);
        String capText = producer.createText();
        log.info("******************验证码是: " + capText + "******************");
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        try {
            ImageIO.write(bi, "png", out);
            out.flush();
        } finally {
            out.close();
        }
    }

    @GetMapping("/{path}/{name}.html")
    public ModelAndView pageAction(@PathVariable("path") String path, @PathVariable("name") String name, ModelAndView modelAndView) {
        modelAndView.setViewName(String.format("view/%s/%s", path, name));
        return modelAndView;
    }

    @GetMapping(value = {"/{name}.html", "/"})
    public ModelAndView pageAction2(@PathVariable(value = "name", required = false) String name, ModelAndView modelAndView) {
        if (name == null) {
            name = "index";
        }
        modelAndView.setViewName(String.format("view/%s", name));
        return modelAndView;
    }

    @GetMapping("/sys/detail/{id}")
    public ModelAndView infoDetail(@PathVariable("id") Integer id, ModelAndView modelAndView) {
        modelAndView.setViewName("view/notify-detail");
        modelAndView.addObject("detail", sysInfoService.getById(id));
        return modelAndView;
    }
}
