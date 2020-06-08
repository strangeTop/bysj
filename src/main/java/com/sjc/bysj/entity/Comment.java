package com.sjc.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @Column(name = "comment_id")
    private Integer commentId;          //评论id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "comment_date")
    private Date commentDate;           //评论时间

    @Column(name = "content")
    private String content;             //评论内容

    @Column(name = "state")
    private Integer state;              //评论状态：0未审核1审核通过2审核驳回

    @Column(name = "article_id")
    private Integer articleId;            //所属资源

    @Column(name = "user_id")
    private Integer userId;                  //评论人

}
