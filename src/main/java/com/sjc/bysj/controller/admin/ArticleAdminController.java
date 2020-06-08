package com.sjc.bysj.controller.admin;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.entity.Message;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.service.ArticleService;
import com.sjc.bysj.service.CommentService;
import com.sjc.bysj.service.MessageService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/article")
public class ArticleAdminController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    MessageService messageService;

    /**
     * 分页查询资源
     * @return
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = "分页查询资源信息列表")
    public Map<String,Object> list(ArticleReq articleReq,
                                   @RequestParam(value = "publishDates",required =false) String publishDates){
        Map<String,Object> resultMap = new HashMap<>();
        String start = null;       //开始时间
        String end = null;       //结束时间
        if(StringUtil.isNotEmpty(publishDates)){
            String[] str = publishDates.split(" - ");       //拆分时间段
            start = str[0];
            end = str[1];
        }
        articleReq.setStart(start);
        articleReq.setEnd(end);
        if(articleReq.getPage()!=null){
            articleReq.setPage((articleReq.getPage()-1)*articleReq.getPageSize());
        }
        if("publishDate".equals(articleReq.getField())){
            articleReq.setField("publish_date");
        }
        resultMap.put("data",articleService.list(articleReq));
        resultMap.put("total",articleService.getCount(articleReq));
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 批量删除资源
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = "删除资源")
    public Map<String,Object> delete(@RequestParam(value = "articleId") String ids){
        Map<String,Object> resultMap = new HashMap<>();
        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            //删除评论
            commentService.deleteByArticleId(Integer.parseInt(idsStr[i]));
            articleService.delete(Integer.parseInt(idsStr[i]));           //删除帖子
        }
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 查看或审核资源页面所需要的数据
     */
    @RequestMapping("/findById")
    @RequiresPermissions(value = "查看资源")
    public Map<String,Object> toModifyArticlePage(Integer articleId){
        Article article = articleService.getById(articleId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("data",article);
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 审核资源
     */
    @RequestMapping("/updateState")
    @RequiresPermissions(value = "审核资源")
    public Map<String,Object> updateState(Article article, HttpSession session){
        Article oldArticle = articleService.getById(article.getArticleId());
        Message message = new Message();
        message.setUserId(oldArticle.getUserId());
        message.setPublishDate(new Date());
        message.setIsSee(false);
        oldArticle.setCheckDate(new Date());
        if(article.getState()==2){
            message.setContent("【<font color='#00ff7f'>审核成功</font>】您发布的【"+oldArticle.getName()+"】资源审核成功！");
            oldArticle.setState(2);
        }else if(article.getState()==3){
            message.setContent("【<font color='#af0000'>审核失败</font>】您发布的【"+oldArticle.getName()+"】资源审核未成功！");
            message.setCause(article.getReason());
            oldArticle.setState(3);
            oldArticle.setReason(article.getReason());
        }
        messageService.save(message);
        articleService.save(oldArticle);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("errorNo",0);
        return resultMap;
    }
}
