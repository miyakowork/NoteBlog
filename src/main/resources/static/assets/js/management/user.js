layui.use(['form', 'table', 'element'], function () {
    var table = layui.table
        , element = layui.element
        , form = layui.form;
    element.render();

    var userTable = table.render({
        elem: '#user-table'
        , url: BMY.url.prefix + '/user/list'
        , page: true
        , limit: 10
        , height: 'full'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'id', width: 80, title: 'ID', sort: true}
            , {
                field: 'username', title: '账号', templet: function (d) {
                    return d.username === '' || d.username === null ? 'QQ第三方注册' : d.username;
                }
            }
            ,{field: 'nickname',title: 'QQ昵称'}
            , {
                field: 'password', title: '密码', templet: function (d) {
                    return d.password === '' || d.password === null ? 'QQ第三方注册' : d.password;
                }
            }
            , {
                field: 'create', title: '创建日期', sort: true, templet: function (d) {
                    return BMY.dateFormatter(d.create);
                }
            }
            , {title: '状态', width: 90, align: 'center', toolbar: '#enableTpl'}
        ]]
    });


    form.on('switch(enable)', function (obj) {
        BMY.ajax(BMY.url.prefix + "/user/edit/enable", {id: this.value, enable: obj.elem.checked}, function (json) {
            BMY.okMsgHandle(json);
            layer.tips('用户状态：' + ((obj.elem.checked) ? "正常" : "锁定"), obj.othis);
        });
    });

    table.on('sort(user)', function (obj) {
        userTable.reload({
            initSort: obj
            , where: {
                sort: obj.field
                , order: obj.type
            }
        });
    });


});







