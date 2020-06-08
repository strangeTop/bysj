package com.sjc.bysj.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sjc.bysj.entity.ArticleType;
import com.sjc.bysj.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserRes implements Serializable {

    private Integer userId;  //用户id

    private String nickname;    //昵称

    private String userName;    //用户名

    private String email;    //邮箱地址

    private String headPortrait;    //用户头像

    private String sex;    //性别

    private Integer points;    //积分

    private Boolean isVip ;      //是否vip

    private Integer vipGrade;       //vip等级

    private Boolean isOff;      //是否被封禁

    private String latelyLoginTime;      //最近的登陆时间
}
