package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.UserDownload;
import com.sjc.bysj.req.HaveDownloadReq;
import com.sjc.bysj.res.UserDownloadRes;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDownloadMapper extends Mapper<UserDownload> {
    List<UserDownloadRes> list(HaveDownloadReq userId);

    int count(HaveDownloadReq haveDownloadReq);
}
