package com.sjc.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.scene.shape.ArcType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "article")
public class Article implements Serializable {
    @Id
    @Column(name = "article_id")
    private Integer articleId;          //资源id

    @NotEmpty(message = "资源名称不能为空！")
    @Column(name = "name")
    private String name;         //资源名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "publish_date")
    private Date publishDate;      //发布时间

    @Column(name="user_id")
    private Integer userId;                  //所属用户

    @Column(name="arc_type_id")
    private Integer arcTypeId;                  //所属资源类型

    @Column(name = "is_free")
    private Boolean isFree;             //是否免费资源   true 是  false 否

    @Column(name = "points")
    private Integer points;             //积分

    @Column(name = "content")
    private String content;             //资源内容

    @Transient
    private String contentNoTag;        //资源内容 网页标签 lucene分词用到

    @Column(name = "download")
    private String download;               //百度云地址

    @Column(name = "password")
    private String password;               //密码

    @Column(name = "is_hot")
    private Boolean isHot;             //是否热门资源   true 是  false 否

    @Column(name = "state")
    private Integer state;              //状态：1未审核2审核通过3审核驳回

    @Column(name = "reason")
    private String reason;              //审核未通过原因

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "check_date")
    private Date checkDate;      //审核时间

    @Column(name = "click")
    private Integer click;          //点击数

    @Column(name = "keywords")
    private String keywords;        //关键字

    @Column(name = "description")
    private String description;     //描述

    @Column(name = "is_useful")
    private Boolean isUseful;             //资源链接是否有效   true 是  false 否
}
