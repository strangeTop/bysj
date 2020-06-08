package com.sjc.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_download")
public class UserDownload implements Serializable {

    @Id
    @Column(name = "user_download_id")
    private Integer userDownloadId;          //用户下载id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "download_date")
    private Date downloadDate;              //下载时间

    @Column(name = "article_id")
    private Integer articleId;                //下载资源

    @Column(name = "user_id")
    private Integer userId;                  //下载用户
}
