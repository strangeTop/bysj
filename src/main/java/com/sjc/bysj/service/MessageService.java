package com.sjc.bysj.service;

import com.sjc.bysj.entity.Message;
import com.sjc.bysj.req.MessageReq;
import com.sjc.bysj.res.MessageRes;

import java.util.List;

public interface MessageService {
    /**
     * 获取当前用户未读消息数
     * @param messageReq
     * @return
     */
    Integer getMessageCount(MessageReq messageReq);

    List<MessageRes> list(MessageReq messageReq);

    int count(MessageReq messageReq);

    void setRead(MessageReq messageReq);

    int save(Message message);
}
