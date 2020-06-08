package com.sjc.bysj.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageReq implements Serializable {
    private Integer page;

    private Integer pageSize;

    private Integer userId;
}
