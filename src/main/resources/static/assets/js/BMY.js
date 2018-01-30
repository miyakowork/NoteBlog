var BMY = {
    $: layui.$

    , status: {
        ok: 200
    }

    , url: {
        prefix: "/management"
        , manage_index: "/management/index"
    }

    , ajax: function (url, data, success) {
        layui.$.ajax({
            type: 'post'
            , dataType: 'json'
            , url: url
            , data: data
            , success: success
            , error: function () {
                layer.msg("发生未知错误！");
            }
        })
    }

    , okHandle: function (json, index, tableId, ok) {
        if (json.code === BMY.status.ok) {
            var okMsg = ok !== undefined ? ok : json.message;
            layer.msg(okMsg);
            if (index)
                layer.close(index);
            if (tableId)
                layui.table.reload(tableId);
        } else {
            layer.msg(json.message);
        }
    }

    , okMsgHandle: function (json, ok) {
        if (json.code === BMY.status.ok) {
            var okMsg = ok !== undefined ? ok : json.message;
            layer.msg(okMsg);
        } else {
            layer.msg(json.message);
        }
    }

    , dateFormatter: function (date) {
        var resD = '';
        if (date instanceof Object) {
            resD = date.year + "-"
                + (date.monthValue < 10 ? ("0" + date.monthValue) : date.monthValue) + "-"
                + (date.dayOfMonth < 10 ? ("0" + date.dayOfMonth) : date.dayOfMonth) + " ";
            if (date.hour >= 0 && date.minute >= 0 && date.second >= 0) {
                resD += (date.hour < 10 ? ("0" + date.hour) : date.hour) + ":"
                    + (date.minute < 10 ? ("0" + date.minute) : date.minute) + ":"
                    + (date.second < 10 ? ("0" + date.second) : date.second);
            }
            return resD;
        }
        else if (date !== null && date !== "") {
            var d = new Date();
            d.setTime(date);
            return d.toLocaleString()
        }
        return "不合法的日期";
    }

    , hashChange: function ($) {
        var hash = location.hash;
        var restIndex = hash.indexOf("!!");
        if (restIndex > -1) {
            hash = hash.substring(0, restIndex);
        }
        var $layuiThis = $("li.layui-nav-item a[href=" + hash + "]");
        if ($layuiThis !== undefined && $layuiThis.length > 0) {
            var $layItem = $layuiThis.last();
            $layItem.addClass("layui-this").css("color", "#ffffff");
            $(".layui-nav-itemed").removeClass("layui-nav-itemed");
            $layItem.parents("li.layui-nav-item").addClass("layui-nav-itemed");
            $layItem.addClass("layui-this")
        }
        $("li.layui-nav-item").click(function () {
            $(this).siblings("li").removeClass("layui-nav-itemed");
        })
    }

    , coffee4Me: function () {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: ['640px', '400px'],
            shade: 0.8,
            id: 'BMY_PAY',
            resize: false,
            shadeClose: true,
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 60px; background-color: #5b6275; color: #fff; font-weight: 300;" class="layui-row">' +
            '<div class="layui-col-md6">' +
            '   <img src="/static/assets/img/alipay.png" style="width: 250px;height: 250px;">' +
            '   <p style="text-align: center;" class="layui-admin-mt10">支付宝</p>' +
            '</div> ' +
            '<div class="layui-col-md6">' +
            '   <img src="/static/assets/img/wecaht.png" style="width: 250px;height: 250px;">' +
            '   <p style="text-align: center;" class="layui-admin-mt10">微信</p>' +
            '</div>' +
            '</div>'
        });
    }
};

// window.alert = function (message) {
//     layui.layer.msg(message)
// }