package com.sjc.bysj.res;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleRes implements Serializable {
    private Integer articleId;

    private String name;

    private Integer points;

    private String publishDate;

    private Integer state;

    private Boolean isHot;

    private Boolean isFree;

    private String reason;

    private Integer click;

    private Boolean isUseful;

    private String content;

    private User user;

    private ArticleType articleType;
}
