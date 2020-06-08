package com.sjc.bysj.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRes implements Serializable {

    private String publishDate;//时间

    private String content;//内容

    private String cause;//失败原因

    private boolean isSee;//是否未读

}
