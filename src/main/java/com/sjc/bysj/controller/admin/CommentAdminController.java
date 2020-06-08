package com.sjc.bysj.controller.admin;

import com.sjc.bysj.entity.Comment;
import com.sjc.bysj.req.CommentReq;
import com.sjc.bysj.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/comment")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据条件分页查询评论信息
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = "分页查询评论信息")
    public Map<String,Object> list(CommentReq commentReq){
        Map<String, Object> resultMap = new HashMap<>();
        int count = commentService.count(commentReq);
        if(commentReq.getPage()!=null){
            commentReq.setPage((commentReq.getPage()-1)*commentReq.getPageSize());
        }
        if("commentDate".equals(commentReq.getField())){
            commentReq.setField("comment_date");
        }
        resultMap.put("data",commentService.list(commentReq));
        resultMap.put("total",count);
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 修改评论状态
     */
    @RequestMapping(value = "/updateState")
    @RequiresPermissions(value = "修改评论状态")
    public Map<String,Object> updateState(Integer commentId,boolean state){
        Comment comment = commentService.get(commentId);
        if(state){      //审核通过
            comment.setState(1);
        }else {         //审核不通过
            comment.setState(2);
        }
        commentService.update(comment);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 删除评论
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = "删除评论")
    public Map<String,Object> delete(@RequestParam(value = "commentId") String ids){
        Map<String,Object> resultMap = new HashMap<>();
        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        resultMap.put("errorNo",0);
        return resultMap;
    }
}
