package com.sjc.bysj.service;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.req.ArcTypeReq;

import java.util.List;

public interface ArcTypeService {
    List<ArticleType> getAll();

    int getCount();

    /**
     * 分页查询
     * @return
     */
    List<ArticleType> list(ArcTypeReq arcTypeReq);

    /**
     * 添加或修改
     * @param arcType
     * @return
     */
    int save(ArticleType arcType);

    ArticleType getById(Integer arcTypeId);

    /**
     * 批量删除
     * @param id
     * @return
     */
    int delete(int id);
}
