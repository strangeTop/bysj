package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.Comment;
import com.sjc.bysj.mapper.CommentMapper;
import com.sjc.bysj.req.CommentReq;
import com.sjc.bysj.res.CommentRes;
import com.sjc.bysj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int save(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    @Override
    public List<CommentRes> list(CommentReq comment) {
        return commentMapper.list(comment);
    }

    @Override
    public int count(CommentReq commentReq) {

        return commentMapper.count(commentReq);
    }

    @Override
    public int update(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public Comment get(Integer commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int delete(int commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public int deleteByArticleId(int articleId) {

        return commentMapper.deleteByArticleId(articleId);
    }

}
