package com.sjc.bysj.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Table(name = "arc_type")
public class ArticleType implements Serializable {
    @Id
    @Column(name = "arc_type_id")
    private Integer arcTypeId;          //资源类型id

    @NotEmpty(message = "资源类型名称不能为空！")
    @Column(name = "arc_type_name")
    private String arcTypeName;         //资源类型名称

    @Column(name = "remark")
    private String remark;              //描述

    @Column(name = "sort")
    private Integer sort;               //排序
}
