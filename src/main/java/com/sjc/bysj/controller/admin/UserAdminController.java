package com.sjc.bysj.controller.admin;

import com.sjc.bysj.entity.User;
import com.sjc.bysj.req.UserReq;
import com.sjc.bysj.service.UserService;
import com.sjc.bysj.util.Consts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    /**
     * 根据条件分页查询用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    @RequiresPermissions(value="分页查询用户信息")
    public Map<String,Object> list(UserReq userReq){
        Map<String,Object> map = new HashMap<>();
        map.put("data",userService.list(userReq));
        map.put("total",userService.getCount(userReq));
        map.put("errorNo",0);
        return map;
    }

    /**
     * 修改用户VIP状态
     */
    @ResponseBody
    @RequestMapping(value = "/updateVipState")
    @RequiresPermissions(value="修改用户VIP状态")
    public Map<String,Object> updateVipState(Integer userId,boolean isVip){
        User oldUser = userService.getById(userId);
        oldUser.setIsVip(isVip);
        userService.update(oldUser);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    /**
     * 修改用户VIP状态
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserState")
    @RequiresPermissions(value="修改用户状态")
    public Map<String,Object> updateUserState(Integer userId,boolean isOff){
        User oldUser = userService.getById(userId);
        oldUser.setIsOff(isOff);
        userService.update(oldUser);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    /**
     * 重置用户密码
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword")
    @RequiresPermissions(value="重置用户密码")
    public Map<String,Object> resetPassword(Integer userId){
        User oldUser = userService.getById(userId);
        oldUser.setPassword(Consts.PASSWORD);          //重置密码为123456
        userService.update(oldUser);
        Map<String,Object> map = new HashMap<>();
        map.put("errorNo",0);
        return map;
    }

    /**
     * 用户vip等级修改
     */
    @ResponseBody
    @RequestMapping(value = "/updateVipGrade")
    @RequiresPermissions(value="用户vip等级修改")
    public Map<String,Object> updateVipGrade(User user){
        Map<String,Object> map = new HashMap<>();
        User oldUser = userService.getById(user.getUserId());
        if(!oldUser.getIsVip()){
            map.put("errorNo",1);
            map.put("errorInfo","该用户不是vip,不可修改");
            return map;
        }
        oldUser.setVipGrade(user.getVipGrade());
        userService.update(oldUser);
        map.put("errorNo",0);
        return map;
    }

    /**
     * 用户积分充值
     */
    @ResponseBody
    @RequestMapping(value = "/addPoints")
    @RequiresPermissions(value="用户积分充值")
    public Map<String,Object> addPoints(User user){
        User oldUser = userService.getById(user.getUserId());
        oldUser.setPoints(oldUser.getPoints()+user.getPoints());
        userService.update(oldUser);
        Map<String,Object> map = new HashMap<>();
        map.put("errorNo",0);
        return map;
    }

    /**
     * 管理员自己的修改密码
     */
    @ResponseBody
    @RequiresPermissions(value = "修改管理员密码")
    @PostMapping("/modifyPassword")
    public Map<String,Object> modifyPassword(String oldPassword, String newPassword, HttpSession session){
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        Map<String,Object> map = new HashMap<>();
        if(!user.getPassword().equals(oldPassword)){
            map.put("success",false);
            map.put("errorInfo","原密码错误！");
            return map;
        }
        User oldUser = userService.getById(user.getUserId());
        oldUser.setPassword(newPassword);
        userService.update(oldUser);
        map.put("success",true);
        return map;
    }

    /**
     * 安全退出
     */
    @GetMapping("/logout")
    @RequiresPermissions(value = "安全退出")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/admin/login.html";
    }
}
