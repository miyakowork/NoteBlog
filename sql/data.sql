----------------------------------------------------
-- 关于表初始设置值
-- -------------------------------------------------
INSERT INTO `T_ABOUT` VALUES ('1', '关于「博客系统」', 'about_blog', '  <div class="layui-row layui-col-space20 layui-mt10">
        <div class="layui-col-md7">
            <ul class="layui-timeline">
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2018年1月8号（使用<em>Spring Boot</em>的第二版），<em>v3.0.basic</em></h3>
                        <p><em>layui 2.x</em>就是这版本的更新动力（自从出2.x就蠢蠢欲动，17年12月下旬开始搞的吧，历时1个半月）。</p>
                        <p><i class="fa fa-asterisk"></i> <em>UI</em>相对上一版前后台都由<em>Bootstrap3</em>改为<em>layui 2.x</em>（纯手工制作，天然成品）。
                        </p>
                        <p><i class="fa fa-asterisk"></i> <em>Java</em>版本升至<em>JDK8</em>。</p>
                        <p><i class="fa fa-asterisk"></i> 由于界面变化太大，重构了后端代码，<em>ORM</em>依然使用<em><a
                                href="https://github.com/miyakowork/template-boot-modules/tree/master/template-modules-jpa"
                                target="_blank">template-modules-jpa</a></em>。</p>
                        <p><i class="fa fa-asterisk"></i> 业务逻辑层优化：使用<em><a
                                href="https://github.com/miyakowork/template-boot-modules/tree/master/template-modules-repository"
                                target="_blank">template-modules-repository</a></em>取代繁琐的业务代码以及<em>sql</em>操作。</p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2016年10月13号（使用<em>Spring Boot</em>的第一版），<em>v2.0</em></h3>
                        <p><em>v2.0</em>版本应该是我用的最久的一个版本了，一年多没动过，稳定性还不错。</p>
                        <p><i class="fa fa-asterisk"></i> 由<em>spring</em>转向<em>Spring
                            Boot</em>，相关配置升级优化，模板引擎由<em>jsp</em>转向<em>Thymeleaf</em>。</p>
                        <p><i class="fa fa-asterisk"></i> 压缩了代码量，尤其是配置文件。以及<em>ORM</em>层的操作。</p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2016年6月10号（使用<em>Spring</em>的最终版），<em>v1.3</em></h3>
                        <p><i class="fa fa-asterisk"></i> <em>ORM</em>由此前的<em>spring-mybatis</em>改用<em><a
                                href="https://github.com/miyakowork/template-boot-modules/tree/master/template-modules-jpa"
                                target="_blank">template-modules-jpa</a></em>。</p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2016年1月26号（使用<em>Spring</em>的第三版），<em>v1.2</em></h3>
                        <p><i class="fa fa-asterisk"></i> 升级了相关依赖的版本，譬如主要的spring（由 3.0.2.RELEASE 升级至 4.1.3.RELEASE）。</p>
                        <p><i class="fa fa-asterisk"></i> 前端改用<em>Bootstrap3</em>代替 ，搞了一套UI，后端而改用<em>AdminLTE</em>。</p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2015年11月13号（使用<em>Spring</em>的第二版），<em>v1.1</em></h3>
                        <p><i class="fa fa-asterisk"></i> <em>ORM</em>改用spring-mybatis了，也算个小升级吧。</p>
                        <p><i class="fa fa-asterisk"></i> 代码太混乱，重构一遍而已（增加了一个小功能 - <b class="layui-blue">笔记</b>）。前端并没有变化~
                        </p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2015年9月28日（使用<em>Spring</em>的第一版），<em>v1.0</em></h3>
                        <p>问：为啥我记得这么清楚？</p>
                        <p>答：因为我找到了数据库备份，数据库名就叫<em>myblog150928</em>。<i class="layui-icon"></i></p>
                        <p><i class="fa fa-asterisk"></i> <em>ORM</em>改用了<em>Spring</em>自带的<em>jdbcTemplate</em>，感觉还挺好用的（这是铺垫）。
                        </p>
                        <p><i class="fa fa-asterisk"></i> <em>UI</em>这时候还是我自己写的，只不过比上个<em>jsp</em>的版本好多了（︿(￣︶￣)︿）</p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">2014年X月Y日（已经不记得具体日期了），<em>beta</em></h3>
                        <p>朦胧了写一个自己的博客站的想法。 同一年（应该是年末几个月），诞生了第一版本。</p>
                        <p>
                            这时还是简单的<em>html/css+jsp/servlet</em>版本，数据库连接还是可怜的<em>Class.forName("com.mysql.jdbc.Driver")</em>
                        </p>
                        <p><i class="fa fa-asterisk"></i> 这个版本页面比较简陋，数据结构也简单。功能就仅仅文章浏览、发布等些许功能。</p>
                        <p><i class="fa fa-asterisk"></i> 其余的我也记不起了。 <i class="layui-icon"></i></p>
                    </div>
                </li>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <div class="layui-timeline-title">起始点（你找不到我~）</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="layui-col-md5">
            <div class="layui-text">
                <ul>
                    <li>支持响应式，支持全设备访问</li>
                    <li>兼容人类目前正在使用的所有浏览器（IE6/7除外，其余版IE稍有瑕疵）</li>
                    <li>目前暂定：每45天一个小版本，每180天一个稳定版本</li>
                    <li>版本更新请持续关注我的主页：<a href="//wuwenbin.me" target="_blank">BMY''s Blog</a></li>
                    <li>将来会有一个<em>node</em>语言版本的分支</li>
                    <li>将来也会有一个非<em>layui</em>的版本分支</li>
                    <li>后续会支持统计的功能（待<em>echarts</em>模块搞完）</li>
                    <li>后续会支持<em>markdown</em>功能</li>
                    <li>将来会支持多主题（这个可能时间比较久）</li>
                    <li>将来会支持插件扩展（这已经是很后面的事了）</li>
                    <li>等等...</li>
                    <li>我会持续的优化，欢迎志同道合的朋友加入！</li>
                </ul>
                <p class="layui-admin-mt20">欢迎交流/加群反馈：
                    <a target="_blank"
                       href="//shang.qq.com/wpa/qunwpa?idkey=6038ff36576f69325a84260aaacde20ad945c31f06f8365897589f8484fc43ea">
                        <img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="笔记博客 ~ 交流反馈"
                             title="笔记博客 ~ 交流反馈">
                    </a>
                    <a href="javascript:BMY.coffee4Me();" style="color: #1E9FFF;">支持我.</a>
                </p>
            </div>
        </div>
    </div>');
INSERT INTO `T_ABOUT` VALUES ('2', '关于「我」', 'about_me', '<p>此处可去后台 内容管理-> 关于内容 处设置</p>');
INSERT INTO `T_ABOUT` VALUES ('3', '关于「网站」', 'about_website', '<p>此处可去后台 内容管理-> 关于内容 处设置</p>');

----------------------------------------------------
-- 参数表初始预设值
-- -------------------------------------------------
INSERT INTO `T_PARAM` VALUES ('1', 'all_comment_open', '0', '是否全局开放评论');
INSERT INTO `T_PARAM` VALUES ('2', 'website_title', '笔记博客', '网站标题的文字');
INSERT INTO `T_PARAM` VALUES ('3', 'footer_words', '', '页脚的文字');
INSERT INTO `T_PARAM` VALUES ('4', 'index_top_words', '', '首页置顶文字');
INSERT INTO `T_PARAM` VALUES ('5', 'menu_home', '主页', '导航菜单_首页');
INSERT INTO `T_PARAM` VALUES ('6', 'menu_note', '笔记', '导航菜单_笔记');
INSERT INTO `T_PARAM` VALUES ('7', 'menu_link', '代码', '导航菜单_额外的链接');
INSERT INTO `T_PARAM` VALUES ('8', 'menu_link_icon', '', '导航菜单_额外的链接的字体图标logo');
INSERT INTO `T_PARAM` VALUES ('9', 'menu_link_href', '', '导航菜单_额外的链接url');
INSERT INTO `T_PARAM` VALUES ('10', 'menu_mine', '关于', '导航菜单_关于我');
INSERT INTO `T_PARAM` VALUES ('11', 'menu_link_show', '0', '是否显示额外的导航链接（譬如github）');
INSERT INTO `T_PARAM` VALUES ('12', 'wechat_pay', '/static/assets/img/noqrcode.jpg', '微信付款码');
INSERT INTO `T_PARAM` VALUES ('13', 'alipay', '/static/assets/img/noqrcode.jpg', '支付宝付款码');
INSERT INTO `T_PARAM` VALUES ('14', 'app_id', '', 'qq登录API的app_id');
INSERT INTO `T_PARAM` VALUES ('15', 'app_key', '', 'qq登录API的app_key');
INSERT INTO `T_PARAM` VALUES ('16', 'qq_login', '0', '是否开放qq登录');
INSERT INTO `T_PARAM` VALUES ('17', 'statistics_code', '', '统计代码');
INSERT INTO `T_PARAM` VALUES ('18', 'info_label', '<p>此处可去后台 偏好设置->网站设置->信息板内容 处自定义文案</p>', '信息板内容');
INSERT INTO `T_PARAM` VALUES ('19', 'menu_search', '搜索', '导航菜单_搜索');
INSERT INTO `T_PARAM` VALUES ('20', 'website_logo_words', '笔记博客', '网站logo的文字');
INSERT INTO `T_PARAM`
VALUES ('21', 'comment_notice', '<span style=\"color:#FF5722;\">遵守国家法律法规，请勿回复无意义内容，请不要回复嵌套过多的楼层！</span>', '评论置顶公告');
INSERT INTO `T_PARAM` VALUES ('22', 'is_set_master', '0', '是否设置了网站管理员');
INSERT INTO `T_PARAM` VALUES ('23', 'is_open_message', '0', '是否开启留言功能');
INSERT INTO `T_PARAM` VALUES ('24', 'message_panel_words', '此处的文字可以去后台设置', '留言板的提示信息文字');
INSERT INTO `T_PARAM` VALUES ('25', 'info_panel_order', '1', '网站信息和会员中心显示顺序，1表示网站信息显示在首要位置');

----------------------------------------------------
-- 角色表初始预设值
-- -------------------------------------------------
INSERT INTO `T_ROLE` VALUES ('1', 'sa', '超级管理员');
INSERT INTO `T_ROLE` VALUES ('2', 'user', '网站用户');