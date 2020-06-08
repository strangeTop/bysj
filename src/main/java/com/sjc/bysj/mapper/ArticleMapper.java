package com.sjc.bysj.mapper;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.res.ArticleRes;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ArticleMapper extends Mapper<Article> {
    /**
     * 分页查询列表
     * @param articleReq
     * @return
     */
    List<ArticleRes> list(ArticleReq articleReq);

    /**
     * 查询总数
     * @param articleReq
     * @return
     */
    Integer getCount(ArticleReq articleReq);
}
