package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.Comment;
import com.sjc.bysj.req.CommentReq;
import com.sjc.bysj.res.CommentRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentMapper extends Mapper<Comment> {
    List<CommentRes> list(CommentReq comment);

    int count(CommentReq commentReq);

    int deleteByArticleId(int articleId);
}
