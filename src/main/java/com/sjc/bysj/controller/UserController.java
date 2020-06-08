package com.sjc.bysj.controller;

import com.sjc.bysj.entity.*;
import com.sjc.bysj.req.ArticleReq;
import com.sjc.bysj.req.HaveDownloadReq;
import com.sjc.bysj.req.MessageReq;
import com.sjc.bysj.res.MessageRes;
import com.sjc.bysj.res.UserDownloadRes;
import com.sjc.bysj.service.*;
import com.sjc.bysj.util.Consts;
import com.sjc.bysj.util.HTMLUtil;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArcTypeService arcTypeService;
    @Autowired
    UserDownloadService userDownloadService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;
    @Value("${imgFilePath}")
    private String imgFilePath;         //图片上传路径

    /**
     * 用户注册
     */
    @ResponseBody
    @PostMapping("/register")
    public Map<String,Object> register(@Valid User user, BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            map.put("success",false);
            map.put("errorInfo",bindingResult.getFieldError().getDefaultMessage());
        }else if(userService.findByUserName(user.getUserName())!=null){
            map.put("success",false);
            map.put("errorInfo","用户名已存在，请更换！");
        }else if(userService.findByEmail(user.getEmail())!=null){
            map.put("success",false);
            map.put("errorInfo","邮箱已存在，请更换！");
        }else{
            user.setRegistrationDate(new Date());
            user.setLatelyLoginTime(new Date());
            user.setIsOff(false);
            user.setIsVip(false);
            user.setRoleName("会员");
            user.setPoints(0);
            user.setVipGrade(0);
            user.setHeadPortrait("tou.jpg");
            userService.save(user);
            map.put("success",true);
        }
        return map;
    }

    /**
     * 用户登录
     */
    @ResponseBody
    @PostMapping("/login")
    public Map<String,Object> login(User user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(user.getUserName())){
            map.put("success",false);
            map.put("errorInfo","请输入用户名！");
        }else if(StringUtil.isEmpty(user.getPassword())){
            map.put("success",false);
            map.put("errorInfo","请输入密码！");
        }else{
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
            try {
                subject.login(token);       //登录验证
                String userName = (String) SecurityUtils.getSubject().getPrincipal();
                User currentUser = userService.findByUserName(userName);
                if (currentUser.getIsOff()) {
                    map.put("adminSuccess", false);
                    map.put("success", false);
                    map.put("errorInfo", "该用户已封禁，请联系管理员！");
                    map.put("adminErrorInfo", "该用户已封禁，请联系管理员！");
                    subject.logout();
                } else {
                    currentUser.setLatelyLoginTime(new Date());
                    userService.update(currentUser);
//                    //未读消息数放到session
                    MessageReq messageReq=new MessageReq();
                    messageReq.setUserId(currentUser.getUserId());
                    Integer messageCount=messageService.getMessageCount(messageReq);
                    session.setAttribute("messageCount",messageCount);
                    session.setAttribute(Consts.CURRENT_USER,currentUser);
                    map.put("success", true);
                    map.put("adminSuccess", true);
                    if(subject.isPermitted("进入管理员主页")){
                        map.put("adminSuccess", true);
                    }else {
                        map.put("adminSuccess", false);
                        map.put("adminErrorInfo", "该用户没有权限");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                map.put("success", false);
                map.put("adminSuccess", false);
                map.put("errorInfo", "用户名或密码错误！");
                map.put("adminErrorInfo", "用户名或密码错误！");
            }
        }
        return map;
    }

    /**
     * 资源管理
     */
    @GetMapping("/articleManage")
    public String articleManage(){
        return "user/articleManage";
    }

    @ResponseBody
    @RequestMapping("/articleList")
    public Map<String,Object> articleList(ArticleReq articleReq, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User currentUser = (User) session.getAttribute(Consts.CURRENT_USER);
        articleReq.setUserId(currentUser.getUserId());
        articleReq.setPage((articleReq.getPage()-1)*articleReq.getPageSize());
        map.put("data",articleService.list(articleReq));
        map.put("count",articleService.getCount(articleReq));     //总计录数
        map.put("code",0);
        return map;
    }

    /**
     * 进入资源发布页面
     */
    @GetMapping("toAddArticle")
    public String toAddArticle(Model model){
        List<ArticleType> articleTypeList=new ArrayList<>();
        articleTypeList=arcTypeService.getAll();
        model.addAttribute(articleTypeList);
        return "user/addArticle";
    }

    /**
     * 添加资源
     */
    @ResponseBody
    @PostMapping("/saveArticle")
    public Map<String,Object> saveArticle(Article article,HttpSession session) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        if(article.getPoints()<0||article.getPoints()>10){
            resultMap.put("success",false);
            resultMap.put("erroInfo","积分超出正常区间！");
            return resultMap;
        }
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        if(article.getArticleId()==null){               //添加资源
            article.setPublishDate(new Date());
            article.setUserId(currentUser.getUserId());
            if(article.getPoints()==0){         //积分为0时，设置为免费资源
                article.setIsFree(true);
            }else{
                article.setIsFree(false);
            }
            article.setIsHot(false);
            article.setIsUseful(true);
            article.setState(1);                //未审核状态
            article.setClick(0);         //设置点击数;
            articleService.save(article);
            resultMap.put("success",true);
        }else{                                          //修改资源
            Article oldArticle=articleService.getById(article.getArticleId());
            article.setUserId(oldArticle.getUserId());
            article.setState(oldArticle.getState());
            if(article.getUserId().intValue()==currentUser.getUserId().intValue()){        //只能修改自己的资源
                if(article.getState()==3){           //假如原先是未通过，用户点击修改，则重新审核，状态变成未审核
                    article.setState(1);
                }
                articleService.save(article);
                resultMap.put("success",true);
            }
        }

        return resultMap;
    }

    /**
     * 删除资源
     * @param articleId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/articleDelete")
    public Map<String,Object> articleDelete(Integer articleId,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        Article article = articleService.getById(articleId);
        if(article.getUserId().intValue() == currentUser.getUserId().intValue()){
            //需要先删除评论
            commentService.deleteByArticleId(articleId);
            articleService.delete(articleId);
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
            resultMap.put("erroInfo","您不是资源所有者，不能删除！");
        }
        return resultMap;
    }

    /**
     * 验证资源的发布者
     * @param articleId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkArticleUser")
    public Map<String,Object> checkArticleUser(Integer articleId,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        Article article = articleService.getById(articleId);
        if(article.getUserId().intValue() == currentUser.getUserId().intValue()){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
            resultMap.put("erroInfo","您不是资源所有者，不能修改！");
        }
        return resultMap;
    }

    /**
     * 进入修改资源页面
     * @param articleId
     * @return
     */
    @GetMapping("/toEditArticle/{articleId}")
    public String toEditArticle(@PathVariable(value="articleId",required = true) Integer articleId,Model model){
        Article article = articleService.getById(articleId);
        List<ArticleType> articleTypeList=new ArrayList<>();
        articleTypeList=arcTypeService.getAll();
        model.addAttribute(articleTypeList);
        model.addAttribute("article",article);
        return "user/editArticle";
    }

    /**
     * 判断某资源是否被当前用户下载过
     */
    @ResponseBody
    @RequestMapping("/userDownloadExist")
    public boolean userDownloadExist(Integer articleId,HttpSession session){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        Integer count = userDownloadService.getCountByUserIdAndArticleId(currentUser.getUserId(),articleId);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 跳转到资源下载页面
     */
    @RequestMapping("/toDownloadPage/{articleId}")
    public String toDownloadPage(@PathVariable("articleId")Integer articleId,HttpSession session,Model model){
        Article article = articleService.getById(articleId);
        //查不到或者没审核通过，直接返回
        if(article==null||article.getState().intValue()!=2){
            return null;
        }
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        //判断当前用户是否下载过
        int count = userDownloadService.getCountByUserIdAndArticleId(currentUser.getUserId(),articleId);
        if(count==0){           //没下载过
            if(!article.getIsFree()){      //非免费资源
                if(currentUser.getPoints()-article.getPoints()<0){      //积分不够
                    return null;
                }
                //扣除积分后保存到数据库
                currentUser.setPoints(currentUser.getPoints()-article.getPoints());
                userService.update(currentUser);
                //资源分享人加积分
                User articleUser=userService.getById(article.getUserId());
                article.setPoints(articleUser.getPoints()+article.getPoints());
                userService.update(articleUser);
            }

            //保存用户下载
            UserDownload userDownload = new UserDownload();
            userDownload.setArticleId(article.getArticleId());
            userDownload.setUserId(currentUser.getUserId());
            userDownload.setDownloadDate(new Date());
            userDownloadService.save(userDownload);
        }
        model.addAttribute("article",article);
        return "article/downloadPage";
    }

    /**
     * 判断用户积分是否足够下载本资源
     */
    @ResponseBody
    @RequestMapping("/userDownloadEnough")
    public boolean userDownloadEnough(Integer points,HttpSession session){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        if(currentUser.getPoints()>points){         //积分足够
            return true;
        }else{                                   // 积分不够
            return false;
        }
    }

    /**
     * vip跳转到资源下载页面
     */
    @RequestMapping("/toVipDownloadPage/{articleId}")
    public String toVipDownloadPage(@PathVariable("articleId")Integer articleId,HttpSession session,Model model){
        Article article = articleService.getById(articleId);
        //查不到或者没审核通过，直接返回
        if(article==null||article.getState().intValue()!=2){
            return null;
        }
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        if(!currentUser.getIsVip()){
            return null;
        }
        //判断当前用户是否下载过
        int count = userDownloadService.getCountByUserIdAndArticleId(currentUser.getUserId(),articleId);
        if(count==0){           //没下载过
            //保存用户下载
            UserDownload userDownload = new UserDownload();
            userDownload.setArticleId(article.getArticleId());
            userDownload.setUserId(currentUser.getUserId());
            userDownload.setDownloadDate(new Date());
            userDownloadService.save(userDownload);
        }
        model.addAttribute("article",article);
        return "article/downloadPage";
    }

    /**
     * 获取当前用户是否vip
     */
    @ResponseBody
    @PostMapping("/isVip")
    public boolean isVip(HttpSession session){
        User user = (User)session.getAttribute(Consts.CURRENT_USER);
        return user.getIsVip();
    }

    /**
     * 进入已下载资源页面
     */
    @GetMapping("/toHaveDownloaded/{currentPage}")
    public String toHaveDownloaded(@PathVariable(value = "currentPage",required = false)Integer currentPage, HttpSession session,Model model){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        HaveDownloadReq haveDownloadReq=new HaveDownloadReq();
        haveDownloadReq.setPage((currentPage-1)*Consts.PAGE_SIZE);
        haveDownloadReq.setPageSize(Consts.PAGE_SIZE);
        haveDownloadReq.setUserId(currentUser.getUserId());
        //已下载资源的列表
        List<UserDownloadRes> userDownloadList = userDownloadService.list(haveDownloadReq);
        int count =userDownloadService.count(haveDownloadReq);
        model.addAttribute("userDownloadList",userDownloadList);
        //分页html代码
        model.addAttribute("pageStr", HTMLUtil.getPagation("/user/toHaveDownloaded",count,currentPage,"","没有下载任何资源。。。"));
        return "user/haveDownloaded";
    }

    /**
     * 我的消息
     */
    @GetMapping("/userMessage/{currentPage}")
    public String userMessage(@PathVariable(value = "currentPage",required = false)Integer currentPage, HttpSession session,Model model){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        MessageReq messageReq=new MessageReq();
        messageReq.setUserId(currentUser.getUserId());
        messageReq.setPage((currentPage-1)*Consts.PAGE_SIZE);
        messageReq.setPageSize(Consts.PAGE_SIZE);
        Integer messageCount=(Integer)session.getAttribute("messageCount");
        //进入页面就认为用户阅读了所有消息
        if(messageCount==null||messageCount>0){
            messageCount=0;                 //设置当前用户未读消息数为0
        }
        session.setAttribute("messageCount",messageCount);
        //所有当前用户的消息列表
        List<MessageRes> messageList = messageService.list(messageReq);
        int count =messageService.count(messageReq);
        model.addAttribute("messageList",messageList);
        //将未读消息设为已读
        messageService.setRead(messageReq);
        //分页html代码
        model.addAttribute("pageStr", HTMLUtil.getPagation("/user/userMessage",count,currentPage,"","没有任何系统消息。。。"));
        return "user/userMessage";
    }

    /**
     * 我的主页
     */
    @GetMapping("/home")
    public String home(HttpSession session,Model model){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        HaveDownloadReq haveDownloadReq=new HaveDownloadReq();
        haveDownloadReq.setUserId(currentUser.getUserId());
        haveDownloadReq.setPage(0);
        haveDownloadReq.setPageSize(10);
        List<UserDownloadRes> userDownloadList = userDownloadService.list(haveDownloadReq);
        if(userDownloadList.size()>0){
            model.addAttribute("userDownloadList",userDownloadList);                //用户最近下载的n条资源帖子
        }
        MessageReq messageReq=new MessageReq();
        messageReq.setUserId(currentUser.getUserId());
        messageReq.setPage(0);
        messageReq.setPageSize(10);
        List<MessageRes> messageList = messageService.list(messageReq);
        if(messageList.size()>0){
            model.addAttribute("messageList",messageList);                          //用户最近的n条系统消息
        }
        return "user/home";
    }

    /**
     * 用户中心
     */
    @GetMapping("/userCenter")
    public String userCenter(HttpSession session,Model model){
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        model.addAttribute("currentUser",currentUser);
        return "user/userCenter";
    }

    /**
     * 修改基本信息
     */
    @ResponseBody
    @RequestMapping("/userUpdate")
    private Map<String,Object> userUpdate(User user,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        currentUser.setNickname(user.getNickname());
        currentUser.setSex(user.getSex());
        userService.update(currentUser);
        session.setAttribute(Consts.CURRENT_USER,currentUser);
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 修改密码
     */
    @ResponseBody
    @PostMapping("/updatePassword")
    public Map<String,Object> updatePassword(String oldPassword,String newPassword,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
        if(currentUser.getPassword().equals(oldPassword)){
            User oldUser = userService.getById(currentUser.getUserId());
            oldUser.setPassword(newPassword);
            userService.update(oldUser);
            session.setAttribute(Consts.CURRENT_USER,oldUser);
            resultMap.put("success",true);
        }else{
            resultMap.put("errorInfo","原密码错误！");
        }
        return resultMap;
    }

    /**
     * 上传头像
     */
    @ResponseBody
    @RequestMapping("/updateHeadPortrait")
    public Map<String,Object> updateHeadPortrait(MultipartFile file, HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        if(!file.isEmpty()){
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //新文件名
            String newFileName = System.currentTimeMillis()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(imgFilePath+newFileName));
            map.put("success",true);
            map.put("imgName",newFileName);
            //把头像放到session和数据库
            User currentUser = (User)session.getAttribute(Consts.CURRENT_USER);
            currentUser.setHeadPortrait(newFileName);
            userService.update(currentUser);
            session.setAttribute(Consts.CURRENT_USER,currentUser);
        }
        return map;
    }
}
