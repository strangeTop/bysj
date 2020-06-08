package com.sjc.bysj.controller.admin;

import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.req.ArcTypeReq;
import com.sjc.bysj.service.ArcTypeService;
import com.sjc.bysj.util.Consts;
import javafx.scene.shape.ArcType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/arcType")
public class ArcTypeAdminController {
    @Autowired
    ArcTypeService arcTypeService;

    @RequestMapping("/list")
    public Map<String,Object> list(ArcTypeReq arcTypeReq){
        Map<String, Object> resultMap = new HashMap<>();
        int count = arcTypeService.getCount();
        if(arcTypeReq.getPage()!=null){
            arcTypeReq.setPage((arcTypeReq.getPage()-1)*arcTypeReq.getPageSize());
        }
        if("arcTypeName".equals(arcTypeReq.getField())){
            arcTypeReq.setField("arc_type_name");
        }
        resultMap.put("data",arcTypeService.list(arcTypeReq));
        resultMap.put("total",count);
        resultMap.put("errorNo",0);
        return resultMap;
    }
    /**
     * 根据id查询资源类型实体
     */
    @RequestMapping("/findById")
    @RequiresPermissions(value = "根据id查询资源类型实体")
    public Map<String,Object> findById(Integer arcTypeId){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data",arcTypeService.getById(arcTypeId));
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 添加或修改资源类型信息
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "添加或修改资源类型信息")
    public Map<String,Object> save(ArticleType arcType){
        Map<String, Object> resultMap = new HashMap<>();
        arcTypeService.save(arcType);
        resultMap.put("errorNo",0);
        return resultMap;
    }

    /**
     * 批量删除资源类型
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "删除资源类型信息")
    public Map<String,Object> delete(@RequestParam(value = "arcTypeId") String ids){
        Map<String, Object> resultMap = new HashMap<>();
        String[] idsStr = ids.split(",");
        for(int i =0;i<idsStr.length;i++){
            arcTypeService.delete(Integer.parseInt(idsStr[i]));
        }
        resultMap.put("errorNo",0);
        return resultMap;
    }
}
