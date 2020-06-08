package com.sjc.bysj.controller.admin;

import com.sjc.bysj.service.ArticleService;
import com.sjc.bysj.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到管理员主页面
     */
    @RequiresPermissions(value="进入管理员主页")
    @RequestMapping("/toAdminUserCenterPage")
    public String toAdminUserCenterPage(){
        return "admin/index";
    }

    /**
     * 跳转到管理员主页面
     */
    @RequiresPermissions(value="进入管理员主页")
    @RequestMapping("/defaultIndex")
    public String defaultIndex(){
        return "admin/default";
    }
}
