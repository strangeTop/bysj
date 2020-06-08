package com.sjc.bysj.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "link")
public class Link implements Serializable {

    @Id
    @Column(name = "link_id")
    private Integer linkId;          //链接id

    @Column(name = "link_name")
    private String linkName;        //链接名称

    @Column(name = "link_url")
    private String linkUrl;         //链接地址

    @Column(name = "link_email")
    private String linkEmail;       //联系人邮箱

    @Column(name = "sort")
    private Integer sort;           //排序
}
