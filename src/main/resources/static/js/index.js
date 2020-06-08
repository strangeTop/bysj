function checkRole() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg('你还不是会员，联系站长！');
    });
}

//登陆弹窗
function login() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type : 2,
            title : false,
            area : [ '450px', '370px' ], //宽高
            closeBtn : 0, //不显示关闭按钮
            scrollbar: false, //禁止浏览器出现滚动条
            resize: false, //禁止拉伸
            move : false, //禁止拖拽
            shade : 0.8, //遮罩

            shadeClose : true, //开启遮罩关闭
            content : '/user/login.html', //这里content是一个Url
        });
    });
}

//注册弹窗
function register() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type : 2,
            title : false,
            area : [ '450px', '480px' ], //宽高
            closeBtn : 0, //不显示关闭按钮
            scrollbar: false, //禁止浏览器出现滚动条
            resize: false, //禁止拉伸
            move : false, //禁止拖拽
            shade : 0.8, //遮罩
            shadeClose : true, //开启遮罩关闭
            content : '/user/register.html', //这里content是一个Url
        });
    });
}

$(function () {
    
    $("#loginLi").hover(function (){
        $(".layui-nav-child").show();
    },function (){
       $(".layui-nav-child").hide();
    });

    //用户中心菜单切换
    $("#LAY_mine li").click(function (obj) {
        $(this).addClass("layui-this");
        $(this).siblings().removeClass("layui-this");
        var id = $(this).attr("lay-id");
        $("#"+id).addClass("layui-show");
        $("#"+id).siblings().removeClass("layui-show");
    });

});

function mzsm() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg("本站所有资源都是由网友分享，仅供学习和交流使用，如果侵权了您的合法权益，请发送邮件至2896543728@qq.com 我们立即处理。", {
            time: 30000, //30s后自动关闭
            btn: ['明白了']
        });
    });
}

function sjvip() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg("成为本站VIP,享受全站资源免积分下载！请联系管理员：2896543728@qq.com (QQ同号)", {
            time: 30000, //30s后自动关闭
            btn: ['明白了']
        });
    });
}

function jfcz() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg("充值积分，请联系管理员：2896543728@qq.com (QQ同号)", {
            time: 20000, //20s后自动关闭
            btn: ['明白了']
        });
    });
}

//登出
function userLogout() {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.confirm('您确定要退出登录吗?', {
            icon : 3,
            title : '系统提示'
        }, function() {
            window.location.href = '/user/logout';
        });
    });
}
