layui.use('table', function(){
    var table = layui.table;

    var tableIns = table.render({
        elem: '#test'
        ,url:'/user/articleList'
        ,toolbar: '#toolbarDemo'
        ,title: '资源数据表'
        ,cols: [[
            {field:'name', title:'资源名称', width:200, fixed: 'left',templet: "#nameTpl"}
            ,{field:'type', title:'所属类别', width:150, templet: "#arcTypeTpl"}
            ,{field:'points', title:'积分', width:80, sort: true, templet: "#pointsTpl"}
            ,{field:'publishDate', title:'发布日期', width:177, sort: true}
            ,{field:'state', title:'审核状态', width:100, templet: "#stateTpl"}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,id: 'testReload'
        ,page: true
        ,request: {
            pageName: 'page',   // 页码的参数名称，默认：page
            limitName: 'pageSize'   // 每页数据量的参数名，默认：limit
        }
    });

    var $ = layui.$, active = {
        reload: function(){
            var name = $('#name');
            var state = $('#state');
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name: name.val()
                    ,state:state.val()
                }
            });
        }
    };
    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('确定要删除该资源吗？', function(index){
                var name = $('#name');
                var state = $('#state');
                $.post("/user/articleDelete",{"articleId":data.articleId},function(result){
                    if(result.success){
                        layer.msg("删除成功！");
                        table.reload("testReload",{
                            where: {
                                name: name.val()
                                ,state:state.val()
                            }
                        });
                    }else if(result.errorInfo){
                        layer.msg(result.errorInfo);
                    }else{
                        layer.msg("删除失败，请联系管理员！");
                    }
                },"json");
                //layer.close(index);
            });
        } else if(obj.event === 'edit'){
            $.post("/user/checkArticleUser",{"articleId":data.articleId},function(result){
                if(result.success){
                    window.location.href = '/user/toEditArticle/' + data.articleId;
                }else{
                    layer.msg(result.errorInfo);
                }
            },"json");
        }
    });
});

function toAddArticle(){
    window.location.href = '/user/toAddArticle';
}