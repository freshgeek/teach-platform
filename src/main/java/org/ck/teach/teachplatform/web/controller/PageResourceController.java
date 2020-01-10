package org.ck.teach.teachplatform.web.controller;

import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.Resource;
import org.ck.teach.teachplatform.entity.UserVisitLog;
import org.ck.teach.teachplatform.util.AppUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/30 18:03
 * @description
 */
@Controller
public class PageResourceController extends BaseController {

    @GetMapping("/resource/detail/{type}/{id}")
    public ModelAndView toDetail(ModelAndView modelAndView,
                                 @PathVariable(value = "type", required = false) Integer typeId,
                                 @PathVariable(value = "id", required = false) Integer id,
                                 Resource resource
    ) {
        if (typeId != null) {
            resource.setTypeId(typeId);
        }
        if (id != null) {
            resource.setId(id);
        }
        Resource byId = resourceService.getById(id);
        modelAndView.addObject("resource", byId);
        userVisitLogService.logVisitService("访问资源[" + byId.getName() + "]详情",
                "/resource/detail/" + resource.getTypeId() + "/" + resource.getId());

        modelAndView.setViewName("view/resource-detail");
        return modelAndView;
    }

}
