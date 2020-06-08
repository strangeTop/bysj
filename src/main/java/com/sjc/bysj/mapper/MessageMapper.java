package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.entity.Message;
import com.sjc.bysj.req.ArcTypeReq;
import com.sjc.bysj.req.MessageReq;
import com.sjc.bysj.res.MessageRes;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MessageMapper extends Mapper<Message> {
    Integer getMessageCount(MessageReq messageReq);

    List<MessageRes> list(MessageReq messageReq);

    int count(MessageReq messageReq);
}
