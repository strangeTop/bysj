package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.User;
import com.sjc.bysj.mapper.UserMapper;
import com.sjc.bysj.req.UserReq;
import com.sjc.bysj.res.UserRes;
import com.sjc.bysj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
        User user=new User();
        user.setUserName(userName);
        return userMapper.selectOne(user);
    }

    @Override
    public User findByEmail(String email) {
        User user=new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }

    @Override
    public int save(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(User currentUser) {
        return userMapper.updateByPrimaryKeySelective(currentUser);
    }

    @Override
    public User getById(Integer arcTypeId) {
        return userMapper.selectByPrimaryKey(arcTypeId);
    }

    @Override
    public List<UserRes> list(UserReq userReq) {
        return userMapper.list(userReq);
    }

    @Override
    public int getCount(UserReq userReq) {
        return userMapper.getCount(userReq);
    }
}
