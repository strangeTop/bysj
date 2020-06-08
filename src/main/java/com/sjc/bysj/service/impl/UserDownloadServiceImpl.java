package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.UserDownload;
import com.sjc.bysj.mapper.UserDownloadMapper;
import com.sjc.bysj.req.HaveDownloadReq;
import com.sjc.bysj.res.UserDownloadRes;
import com.sjc.bysj.service.UserDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDownloadServiceImpl implements UserDownloadService {
    @Autowired
    UserDownloadMapper userDownloadMapper;

    @Override
    public Integer getCountByUserIdAndArticleId(Integer userId, Integer articleId) {
        UserDownload userDownload=new UserDownload();
        userDownload.setUserId(userId);
        userDownload.setArticleId(articleId);
        return userDownloadMapper.selectCount(userDownload);
    }

    @Override
    public Integer save(UserDownload userDownload) {
        return userDownloadMapper.insertSelective(userDownload);
    }

    @Override
    public List<UserDownloadRes> list(HaveDownloadReq haveDownloadReq) {

        return userDownloadMapper.list(haveDownloadReq);
    }

    @Override
    public int count(HaveDownloadReq haveDownloadReq) {
        return userDownloadMapper.count(haveDownloadReq);
    }
}
