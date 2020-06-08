package com.sjc.bysj.service.impl;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.mapper.ArcTypeMapper;
import com.sjc.bysj.req.ArcTypeReq;
import com.sjc.bysj.service.ArcTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArcTypeServiceImpl implements ArcTypeService {
    @Autowired
    ArcTypeMapper arcTypeMapper;

    @Override
    public List<ArticleType> getAll() {
        return arcTypeMapper.selectAll();
    }

    @Override
    public int getCount() {

        return arcTypeMapper.getCount();
    }

    @Override
    public List<ArticleType> list(ArcTypeReq arcTypeReq) {
        return arcTypeMapper.list(arcTypeReq);
    }

    @Override
    public int save(ArticleType arcType) {
        if(arcType.getArcTypeId()==null){
            //增加
            return arcTypeMapper.insertSelective(arcType);
        }else{
            //修改
            return arcTypeMapper.updateByPrimaryKeySelective(arcType);
        }
    }

    @Override
    public ArticleType getById(Integer arcTypeId) {
        return arcTypeMapper.selectByPrimaryKey(arcTypeId);
    }

    @Override
    public int delete(int id) {
        return arcTypeMapper.deleteByPrimaryKey(id);
    }
}
