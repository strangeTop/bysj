package com.sjc.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "message")
public class Message implements Serializable {

    @Id
    @Column(name = "message_id")
    private Integer messageId;          //消息id

    @Column(name = "content")
    private String content;             //消息内容

    @Column(name = "cause")
    private String cause;             //原因

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "publish_date")
    private Date publishDate;      //发布时间

    @Column(name = "user_id")
    private Integer userId;                  //所属用户

    @Column(name = "is_see")
    private Boolean isSee;      //消息是否被查看 true 是 false 否

}
