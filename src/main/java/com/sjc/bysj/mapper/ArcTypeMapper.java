package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.req.ArcTypeReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ArcTypeMapper extends Mapper<ArticleType> {
    /**
     * 总数
     * @return
     */
    int getCount();

    /**
     * 分页查询
     * @return
     */
    List<ArticleType> list(ArcTypeReq arcTypeReq);
}
