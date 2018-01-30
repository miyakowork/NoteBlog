layui.use(['element', 'layer', 'form'], function () {
    var element = layui.element;
    var layer = layui.layer;
    var form = layui.form;

    element.render();
    form.render();

    form.on('submit(simpleArticlePost)', function (data) {
        BMY.ajax(BMY.url.prefix + "/simple/post/article", data.field, function (json) {
            BMY.okMsgHandle(json, "保存草稿成功！");
        });
        return false;
    });

    form.on('submit(simpleNotePost)', function (data) {
        BMY.ajax(BMY.url.prefix + "/simple/post/note", data.field, function (json) {
            BMY.okMsgHandle(json, "发布随笔成功！");
        });
        return false;
    });

});






