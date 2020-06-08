package com.sjc.bysj.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sjc.bysj.entity.Article;
import com.sjc.bysj.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentRes implements Serializable {
    private Integer commentId;          //评论id

    private String commentDate;           //评论时间

    private String content;             //评论内容

    private Integer state;              //评论状态：0未审核1审核通过2审核驳回

    private Article article;

    private User user;
}
