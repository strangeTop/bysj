package com.sjc.bysj.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReq implements Serializable {
    private Integer page;

    private Integer pageSize;

    private String field;//根据什么排序

    private String order;//排序规则

    private Integer articleId;
}
