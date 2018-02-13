layui.use(['form', 'table', 'element'], function () {
    var table = layui.table
        , form = layui.form
        , element = layui.element;

    element.render();
    var noteTable = table.render({
        elem: '#note-table'
        , height: 'full'
        , url: BMY.url.prefix + "/note/list"
        , cellMinWidth: 90
        , limit: 10
        , size: 'lg'
        , method: "post"
        , where: {
            order: 'desc'
            , sort: 'post'
        }
        , initSort: {
            field: 'post'
            , type: 'desc'
        }
        , cols: [[
            {type: 'numbers'}
            , {
                field: 'title', title: '笔记标题', sort: true, templet: function (d) {
                    return '<a href="javascript:;" class="layui-blue">' + d.title + '</a>';
                }
            }
            , {
                field: 'post', title: '发布时间', sort: true, templet: function (d) {
                    return BMY.dateFormatter(d.post);
                }
            }
            , {title: '状态', width: 90, align: 'center', toolbar: '#showTpl'}
            , {field: 'top', title: '置顶', width: 110, templet: '#topTpl'}
            , {title: '操作', width: 200, align: 'center', toolbar: '#noteBar'}
        ]]
        , page: true
    });

    var $ = layui.$, active = {
        reload: function () {
            var noteSearch = $('#note-search');
            //执行重载
            table.reload('note-table', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    title: noteSearch.val()
                }
            });
        }
    };

    $('#note-table-search').find('.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

//监听工具条
    table.on('tool(note)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            location.hash = vipspa.stringifyPattern("note_edit", [data.id]);
        } else if (obj.event === 'del') {
            layer.confirm('确认删除吗？', function (index) {
                obj.del();
                BMY.ajax(BMY.url.prefix + "/note/delete/" + data.id, {}, function (json) {
                    BMY.okMsgHandle(json);
                });
                layer.close(index);
            });
        }
    });

    table.on('sort(note)', function (obj) {
        noteTable.reload({
            initSort: obj
            , where: {
                sort: obj.field
                , order: obj.type
            }
        });
    });

    form.on('switch(show)', function (obj) {
        BMY.ajax(BMY.url.prefix + "/note/edit/show/" + this.value, {show: obj.elem.checked}, function (json) {
            BMY.okMsgHandle(json);
            layer.tips('显示：' + ((obj.elem.checked) ? "正常" : "隐藏"), obj.othis);
        });
    });


    form.on('switch(top)', function (obj) {
        BMY.ajax(BMY.url.prefix + "/note/edit/top/" + this.value, {top: obj.elem.checked}, function (json) {
            BMY.okMsgHandle(json);
            layer.tips(((obj.elem.checked) ? "已置顶" : "取消置顶"), obj.othis);
        });
    });


});







