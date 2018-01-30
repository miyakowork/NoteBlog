layui.use('form', function () {
    var form = layui.form;

    form.verify({
        username: function (value) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
            if (value.length < 4 || value.length > 12) {
                return "用户名必须4~12位，且不能包含空格";
            }
        }
        , pass: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
    });

    form.on('submit(bmySubmit)', function (data) {
        data.field.bmyPass = md5(data.field.bmyPass);
        BMY.ajax("/login", data.field, function (resp) {
                if (resp.code === BMY.status.ok) {
                    layer.msg("登录成功！");
                    setTimeout(function () {
                        location.href = BMY.url.manage_index;
                    }, 1000);
                } else {
                    layer.msg("登录失败！");
                }
            }
        );
        return false;
    });

});