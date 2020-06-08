package com.sjc.bysj.controller;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.req.MessageReq;
import com.sjc.bysj.res.ArticleRes;
import com.sjc.bysj.service.ArcTypeService;
import com.sjc.bysj.service.ArticleService;
import com.sjc.bysj.service.MessageService;
import com.sjc.bysj.service.UserService;
import com.sjc.bysj.util.Consts;
import com.sjc.bysj.util.HTMLUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    ArcTypeService arcTypeService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    /**
     * 按资源类型分页查询资源列表
     * @param type
     * @param currentPage
     * @return
     */
    @RequestMapping("/{type}/{field}/{currentPage}")
    public String type(@PathVariable(value = "type",required = false) String type,
                       @PathVariable(value = "currentPage",required = false)Integer currentPage,
                       @PathVariable(value = "field",required = false) String field,
                       @RequestParam(value = "searchWord",required = false) String searchWord,
                       Model model,
                       HttpSession session){
        //获取未读消息
        User currentUser =(User)session.getAttribute(Consts.CURRENT_USER);
        if(currentUser!=null){
            MessageReq messageReq=new MessageReq();
            messageReq.setUserId(currentUser.getUserId());
            Integer messageCount=messageService.getMessageCount(messageReq);
            session.setAttribute("messageCount",messageCount);
        }
        //类型
        List<ArticleType> articleTypeList=arcTypeService.getAll();
        model.addAttribute("arcTypeStr", HTMLUtil.getArcTypeStr(type,articleTypeList));
        //资源列表
        ArticleReq articleReq=new ArticleReq();
        articleReq.setPage((currentPage-1)*Consts.PAGE_SIZE);
        articleReq.setPageSize(Consts.PAGE_SIZE);
        articleReq.setField(field);
        articleReq.setState(2);
        articleReq.setSearchWord(searchWord);
        if(!"is_free".equals(field)){
            articleReq.setOrder("desc");
        }
        if(!"all".equals(type)) {
            articleReq.setArcType(Integer.parseInt(type));
        }
        model.addAttribute("articleList",articleService.list(articleReq));
        model.addAttribute("searchWord",searchWord);
        String href=searchWord==null?"1":"1?searchWord="+searchWord;
        model.addAttribute("href",href);
        //分页html代码
        Integer count=articleService.getCount(articleReq);
        searchWord=searchWord==null?"":"?searchWord="+searchWord;
        model.addAttribute("pageStr",HTMLUtil.getPagation("/article/"+type+"/"+field,count,currentPage,searchWord,"该分类还没有数据..."));
        model.addAttribute("field",field);//选项卡
        ArticleReq articleReq1=new ArticleReq();
        //热搜排行
        articleReq1.setOrder("desc");
        articleReq1.setField("click");
        articleReq1.setPage(0);
        articleReq1.setPageSize(15);
        articleReq1.setState(2);
        model.addAttribute("hotArticle",articleService.list(articleReq1));
        //最新发布
        articleReq1.setOrder("desc");
        articleReq1.setField("publish_date");
        model.addAttribute("newArticle",articleService.list(articleReq1));
        return "index";
    }

    /**
     * 资源详情
     */
    @RequestMapping("/detail/{articleId}")
    public String detail(@PathVariable(value = "articleId",required = false) String articleId,
                         Model model){
        //封装返回的数据
        articleService.updateClick(Integer.parseInt(articleId));
        Article article1 = articleService.getById(Integer.parseInt(articleId));
        ArticleType articleType=arcTypeService.getById(article1.getArcTypeId());
        User user = userService.getById(article1.getUserId());
        ArticleRes article=new ArticleRes();
        BeanUtils.copyProperties(article1,article);
        article.setArticleType(articleType);
        article.setUser(user);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        article.setPublishDate(ft.format(article1.getPublishDate()));
        if(article.getState()!=2){
            return null;
        }
        model.addAttribute("article",article);
        //类型的html代码
        List<ArticleType> articleTypeList=arcTypeService.getAll();
        model.addAttribute("arcTypeStr", HTMLUtil.getArcTypeStr(article.getArticleType().getArcTypeId().toString(),articleTypeList));
        ArticleReq articleReq1=new ArticleReq();
        //热搜排行
        articleReq1.setOrder("desc");
        articleReq1.setField("click");
        articleReq1.setPage(0);
        articleReq1.setPageSize(15);
        articleReq1.setState(2);
        model.addAttribute("hotArticle",articleService.list(articleReq1));
        //最新发布
        articleReq1.setOrder("desc");
        articleReq1.setField("publish_date");
        model.addAttribute("newArticle",articleService.list(articleReq1));
        return "detail";
    }

    /**
     * 判断资源是否免费
     */
    @ResponseBody
    @RequestMapping("/isFree")
    public boolean isFree(Integer articleId){
        Article article = articleService.getById(articleId);
        return article.getIsFree();
    }
}
