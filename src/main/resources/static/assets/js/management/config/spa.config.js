$(function () {
    var prefix = '/static/assets/js/management';
    vipspa.start({
        view: '#admin-body',
        router: {
            'blog': {
                templateUrl: 'blog',
                controller: prefix + '/blog.js'
            },
            'blog_edit': {
                templateUrl: 'blog/edit',
                controller: prefix + '/blog_edit.js'
            },
            'blogs': {
                templateUrl: 'blog/index',
                controller: prefix + '/blogs.js'
            },
            'note': {
                templateUrl: 'note',
                controller: prefix + '/note.js'
            },
            'notes': {
                templateUrl: 'note/index',
                controller: prefix + '/notes.js'
            },
            'note_edit': {
                templateUrl: 'note/edit',
                controller: prefix + '/note_edit.js'
            },
            'cate': {
                templateUrl: 'cate',
                controller: prefix + '/cate.js'
            },
            'tag': {
                templateUrl: 'tag',
                controller: prefix + '/tag.js'
            },
            'about': {
                templateUrl: 'about',
                controller: prefix + '/about.js'
            },
            'keyword': {
                templateUrl: 'keyword',
                controller: prefix + '/keyword.js'
            },
            'users': {
                templateUrl: 'user',
                controller: prefix + '/user.js'
            },
            'settings': {
                templateUrl: 'settings',
                controller: prefix + '/settings.js'
            },
            'defaults': 'home' //默认路由
        }
    });

});
