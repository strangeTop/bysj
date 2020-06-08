package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.Message;
import com.sjc.bysj.mapper.MessageMapper;
import com.sjc.bysj.req.MessageReq;
import com.sjc.bysj.res.MessageRes;
import com.sjc.bysj.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public Integer getMessageCount(MessageReq messageReq) {
        return messageMapper.getMessageCount(messageReq);
    }

    @Override
    public List<MessageRes> list(MessageReq messageReq) {
        return messageMapper.list(messageReq);
    }

    @Override
    public int count(MessageReq messageReq) {
        return messageMapper.count(messageReq);
    }

    @Override
    public void setRead(MessageReq messageReq) {
        Message message=new Message();
        message.setUserId(messageReq.getUserId());
        message.setIsSee(false);
        List<Message> messageList=messageMapper.select(message);
        for(Message message1:messageList){
            message1.setIsSee(true);
            messageMapper.updateByPrimaryKeySelective(message1);
        }
    }

    @Override
    public int save(Message message) {
        return messageMapper.insertSelective(message);
    }
}
