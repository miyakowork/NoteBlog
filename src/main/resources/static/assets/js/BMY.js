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

};

// window.alert = function (message) {
//     layui.layer.msg(message)
// }