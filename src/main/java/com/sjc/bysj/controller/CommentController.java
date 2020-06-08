package com.sjc.bysj.controller;

import com.sjc.bysj.entity.Comment;
import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.CommentReq;
import com.sjc.bysj.res.CommentRes;
import com.sjc.bysj.service.CommentService;
import com.sjc.bysj.util.Consts;
import com.sjc.bysj.util.HTMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 前端提交保存评论信息
     * @param comment
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public Map<String,Object> add(Comment comment, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String content=comment.getContent().replace("<","&lt;").replace(">","&gt;");
        comment.setContent(content);
        comment.setCommentDate(new Date());
        comment.setState(0);
        User user=(User)session.getAttribute(Consts.CURRENT_USER);
        comment.setUserId(user.getUserId());
        commentService.save(comment);
        map.put("success",true);
        return map;
    }

    /**
     * 分页查询某个资源的评论信息
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public Map<String,Object> list(CommentReq comment, @RequestParam(value = "page",defaultValue = "1")Integer page){
        comment.setPage((page-1)*Consts.COM_PAGE_SIZE);
        comment.setPageSize(Consts.COM_PAGE_SIZE);
        int count=commentService.count(comment);
        List<CommentRes> commentList= commentService.list(comment);
        Map<String,Object> map = new HashMap<>();
        map.put("data", HTMLUtil.getCommentPageStr(commentList));          //评论的HTML代码
        int total=count/Consts.COM_PAGE_SIZE==0?count/Consts.COM_PAGE_SIZE:(count/Consts.COM_PAGE_SIZE)+1;
        map.put("total",total);                                   //总页数
        return map;
    }
}
