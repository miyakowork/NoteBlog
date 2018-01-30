layui.use(['element', 'form', 'upload'], function () {
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;
    element.render();
    form.render();

    //监听提交
    form.on('submit(profileForm)', function (data) {
        var pass = data.field.password1;
        if (pass !== null && pass !== "") {
            data.field.password1 = md5(data.field.password1);
            data.field.password2 = md5(data.field.password2);
        }
        BMY.ajax(BMY.url.prefix + "/profile/edit", data.field, function (json) {
            BMY.okMsgHandle(json);
        });
        return false;
    });

    upload.render({
        elem: '#avatar' //绑定元素
        , url: BMY.url.prefix + '/profile/upload/' //上传接口
        , done: function (res) {
            if (res.code === 0) {
                $("#avatar").find("img").attr("src", res.data.src);
            }
            layer.msg(res.msg);
        }
        , error: function () {
            layer.msg("上传失败！");
        }
    });
});


