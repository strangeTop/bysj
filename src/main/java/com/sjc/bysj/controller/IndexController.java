package com.sjc.bysj.controller;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.service.ArcTypeService;
import com.sjc.bysj.service.ArticleService;
import com.sjc.bysj.util.Consts;
import com.sjc.bysj.util.HTMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ArcTypeService arcTypeService;
    @Autowired
    ArticleService articleService;

    /**
     * 首页
     */
    @RequestMapping("/")
    public String index(Model model){
        return "redirect:/article/all/publish_date/1";
    }

    /**
     * 购买VIP
     */
    @RequestMapping("/buyVIP")
    public String buyVIP(){
        return "buyVIP";
    }

    /**
     * 发布资源赚积分
     */
    @RequestMapping("/fbzyzjf")
    public String fbzyzjf(){
        return "fbzyzjf";
    }
}
