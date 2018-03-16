layui.use(['form', 'element'], function () {
    var element = layui.element
        , form = layui.form;
    element.render();
    form.render();

    form.on('switch(commentSwitch)', function (data) {
        var s = data.elem.checked;
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'all_comment_open', value: s ? 1 : 0}, function (json) {
            if (json.code === BMY.status.ok) {
                layer.tips('全站评论功能：' + ((s) ? '开启' : '关闭'), data.othis);
            }
        })
    });

    form.on('switch(messageSwitch)', function (data) {
        var s = data.elem.checked;
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'is_open_message', value: s ? 1 : 0}, function (json) {
            if (json.code === BMY.status.ok) {
                layer.tips('网友留言功能：' + ((s) ? '开启' : '关闭'), data.othis);
            }
        })
    });

    form.on('switch(infoLabelOrderSwitch)', function (data) {
        var s = data.elem.checked;
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'info_panel_order', value: s ? 1 : 0}, function (json) {
            if (json.code === BMY.status.ok) {
                layer.tips('修改信息板位置为：' + ((s) ? '首要' : '次要'), data.othis);
            }
        })
    });

    form.on('switch(linkSwitch)', function (data) {
        var s = data.elem.checked;
        if (s) {
            $("#extra-link").removeClass("layui-hide");
        } else {
            $("#extra-link").addClass("layui-hide");
        }
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'menu_link_show', value: s ? 1 : 0}, function (json) {
            if (json.code === BMY.status.ok) {
                layer.tips('额外链接按钮：' + ((data.elem.checked) ? '开启' : '关闭'), data.othis);
            }
        })
    });

    form.on('switch(qqLoginSwitch)', function (data) {
        var s = data.elem.checked;
        if (s) {
            $("#qq-login-id,#qq-login-key").removeClass("layui-hide");
        } else {
            $("#qq-login-id,#qq-login-key").addClass("layui-hide");
        }
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'qq_login', value: s ? 1 : 0}, function (json) {
            if (json.code === BMY.status.ok) {
                layer.tips('qq快捷登录：' + ((data.elem.checked) ? '开启' : '关闭'), data.othis);
            }
        })
    });

    form.on('submit(settings)', function (data) {
        var $this = $(data.elem);
        var $data = $this.parents(".layui-inline").prev(".layui-inline").find("input[type=text]");
        var name = $data.attr("name");
        var value = $data.val();
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: name, value: value}, function (json) {
            BMY.okMsgHandle(json);
        });
        return false;
    })

    form.on('submit(link)', function () {
        var text = $("input[name=linkText]").val();
        var icon = $("input[name=linkIcon]").val();
        var url = $("input[name=linkUrl]").val();
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'menu_link', value: text}, function (json1) {
            if (json1.code === BMY.status.ok) {
                BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'menu_link_icon', value: icon}, function (json2) {
                    if (json2.code === BMY.status.ok) {
                        BMY.ajax(BMY.url.prefix + "/settings/edit", {
                            name: 'menu_link_href',
                            value: url
                        }, function (json3) {
                            if (json3.code === BMY.status.ok) {
                                BMY.ajax(BMY.url.prefix + "/settings/edit", {
                                    name: 'menu_link_href',
                                    value: url
                                }, function (json3) {
                                    BMY.okMsgHandle(json3);
                                });
                            } else {
                                layer.msg("修改链接失败！");
                            }
                        });
                    } else {
                        layer.msg("修改图标失败！");
                    }
                });
            } else {
                layer.msg("修改链接文本失败！");
            }
        });
        return false;
    });

    form.on('submit(statistics)', function () {
        var statisticsCode = $("textarea[name=statisticsCode]").val();
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'statistics_code', value: statisticsCode}, function (json1) {
            BMY.okMsgHandle(json1)
        });
        return false;
    });

    form.on('submit(infoLabel)', function () {
        var infoLabel = $("textarea[name=infoLabel]").val();
        BMY.ajax(BMY.url.prefix + "/settings/edit", {name: 'info_label', value: infoLabel}, function (json1) {
            BMY.okMsgHandle(json1)
        });
        return false;
    });

});







