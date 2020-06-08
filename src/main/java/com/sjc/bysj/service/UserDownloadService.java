package com.sjc.bysj.service;

import com.sjc.bysj.entity.UserDownload;
import com.sjc.bysj.req.HaveDownloadReq;
import com.sjc.bysj.res.UserDownloadRes;

import java.util.List;

public interface UserDownloadService {
    Integer getCountByUserIdAndArticleId(Integer userId, Integer articleId);

    Integer save(UserDownload userDownload);

    List<UserDownloadRes> list(HaveDownloadReq userId);

    int count(HaveDownloadReq haveDownloadReq);
}
