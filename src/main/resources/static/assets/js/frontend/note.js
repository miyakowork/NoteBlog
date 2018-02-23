layui.define(function (exports) {
    exports('note', function (cover, page, next, laytpl) {
        return nextShare(cover, page, next, laytpl)
    });
});

var shares =
    "{{# layui.each(d.result, function(index, item){ }}" +
    "<li class=\"layui-timeline-item animated slideInUp\">" +
    "   <i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>" +
    "   <div class=\"layui-timeline-content layui-text\">" +
    "       <h3 class=\"layui-timeline-title\">{{ noteTitle(item) }}</h3>" +
    "       <div class=\"timeline-body\"'>" +
    "           {{item.content}}" +
    "       </div>" +
    "   </div>" +
    "</li>" +
    "{{# }); }}";

var shareEnds =
    "<li class=\"layui-timeline-item layui-note-cover\">" +
    "   <i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>" +
    "   <div class=\"layui-timeline-content layui-text\">" +
    "       <h3 class=\"layui-timeline-title\">笔记封面</h3>" +
    "   </div>" +
    "</li>";

function nextShare(cover, page, next, tpl) { //执行下一页的回调
    var s = BMY.getParam("t");
    $.post("/note/next", {
        pageNo: page,
        t: s,
        cc: s,
    }, function (json) {
        if (json.code === BMY.status.ok) {
            tpl(shares).render(json.data, function (html) {
                cover.slideUp();
                next(html + shareEnds, json.data.hasNext)
            });
            if (s !== "" && s !== undefined && s !== null) {
                BMY.noteVm.quote.showSearch = true;
                BMY.noteVm.quote.searchCount = json.data.totalCount;
            }
            $(window).resize();
        }
    });
}

function noteTitle(note) {
    return BMY.dateFormatter(note.post, "/", false) + "&nbsp;<i class=\"fa fa-hand-grab-o \"></i> " + note.title;
}