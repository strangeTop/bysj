package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.UserReq;
import com.sjc.bysj.res.UserRes;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {
    List<UserRes> list(UserReq userReq);

    int getCount(UserReq userReq);
}
