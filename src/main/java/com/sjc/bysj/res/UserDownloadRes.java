package com.sjc.bysj.res;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDownloadRes implements Serializable {
    private Integer userDownloadId;          //id

    private String downloadDate;           //时间

    private Article article;
}
