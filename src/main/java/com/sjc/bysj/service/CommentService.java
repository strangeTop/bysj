package com.sjc.bysj.service;

import com.sjc.bysj.entity.Comment;
import com.sjc.bysj.req.CommentReq;
import com.sjc.bysj.res.CommentRes;

import java.util.List;

public interface CommentService {
    int save(Comment comment);

    /**
     * 前台分页查询
     * @param comment
     * @return
     */
    List<CommentRes> list(CommentReq comment);

    int count(CommentReq commentReq);

    int update(Comment comment);

    Comment get(Integer commentId);

    int delete(int commentId);

    int deleteByArticleId(int articleId);
}
