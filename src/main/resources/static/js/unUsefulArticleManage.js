layui.use('table', function(){
    var table = layui.table;

    var tableIns = table.render({
        elem: '#test'
        ,url:'/user/articleList?useful=false'
        ,toolbar: '#toolbarDemo'
        ,title: '失效资源数据表'
        ,cols: [[
            {field:'name', title:'资源名称', width:580, fixed: 'left',templet: "#nameTpl"}
            ,{field:'publishDate', title:'发布日期', width:177, sort: true}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,id: 'testReload'
        ,page: true
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'edit'){
            layer.open({
                type: 2,
                title: '修改百度云分享链接',
                area: ['700px', '200px'],
                content: '/user/modifyShareLink.html?id='+data.articleId //iframe的url
            });
        }
    });
});

function toAddArticle(){
    window.location.href = '/user/toAddArticle';
}