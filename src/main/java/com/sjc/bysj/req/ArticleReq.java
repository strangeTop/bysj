package com.sjc.bysj.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleReq implements Serializable {
    private Integer page;

    private Integer pageSize;

    private String name;

    private Integer state;

    private Integer arcType;

    private Integer userId;

    private String start;

    private String end;

    private String field;//根据什么排序

    private String order;//排序规则

    private String searchWord;//搜索词
}
