package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.Article;
import com.sjc.bysj.mapper.ArticleMapper;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.res.ArticleRes;
import com.sjc.bysj.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<ArticleRes> list(ArticleReq articleReq) {
        return articleMapper.list(articleReq);
    }

    @Override
    public Integer getCount(ArticleReq articleReq) {
        return articleMapper.getCount(articleReq);
    }

    @Override
    public int save(Article article) {
        if(article.getArticleId()==null){
            return articleMapper.insertSelective(article);
        }else{
            return articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    @Override
    public Article getById(Integer articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public int delete(int id) {

        return articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateClick(int articleId) {
        Article article=articleMapper.selectByPrimaryKey(articleId);
        article.setClick(article.getClick()+1);
        return articleMapper.updateByPrimaryKeySelective(article);
    }
}
