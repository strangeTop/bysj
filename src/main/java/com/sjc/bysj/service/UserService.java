package com.sjc.bysj.service;

import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.UserReq;
import com.sjc.bysj.res.UserRes;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 增
     * @param user
     * @return
     */
    int save(User user);

    /**
     * 改
     * @param currentUser
     * @return
     */
    int update(User currentUser);

    /**
     * 根据id
     * @param arcTypeId
     * @return
     */
    User getById(Integer arcTypeId);

    /**
     * 分页查询用户
     * @return
     */
    List<UserRes> list(UserReq userReq);

    int getCount(UserReq userReq);

}
