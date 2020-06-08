package com.sjc.bysj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer userId;  //用户id

    @Column(name = "open_id")
    private String openId;   //qq用户唯一标识

    @NotEmpty(message = "昵称不能为空！")
    @Column(name = "nickname")
    private String nickname;    //昵称

    @NotEmpty(message = "用户名不能为空！")
    @Column(name = "user_name")
    private String userName;    //用户名

    @NotEmpty(message = "密码不能为空！")
    @Column(name = "password")
    private String password;    //密码

    @Email(message = "邮箱地址格式有误！")
    @NotEmpty(message = "请输入邮箱地址！")
    @Column(name = "email")
    private String email;    //邮箱地址

    @Column(name = "head_portrait")
    private String headPortrait;    //用户头像

    @Column(name = "sex")
    private String sex;    //性别

    @Column(name = "points")
    private Integer points;    //积分

    @Column(name = "is_vip")
    private Boolean isVip ;      //是否vip

    @Column(name = "vip_grade")
    private Integer vipGrade;       //vip等级

    @Column(name = "is_off")
    private Boolean isOff;      //是否被封禁

    @Column(name = "role_name")
    private String roleName;     //角色名称：管理员、会员

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "registration_date")
    private Date registrationDate;      //注册时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lately_login_time")
    private Date latelyLoginTime;      //最近的登陆时间
}
