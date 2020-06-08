package com.sjc.bysj.service;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.res.ArticleRes;

import java.util.List;

public interface ArticleService {
    /**
     * 查询资源列表
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

    /**
     * 更新或修改资源
     * @param article
     * @return
     */
    int save(Article article);

    /**
     * 根据id获得article
     * @param articleId
     * @return
     */
    Article getById(Integer articleId);

    /**
     * 批量删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 点击
     * @param articleId
     */
    int updateClick(int articleId);
}
