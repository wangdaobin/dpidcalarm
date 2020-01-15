
jQuery(document).ready(function() {
    window.addEventListener('resize', function() {
        setViewportScale();
    });
    //根据当前浏览器窗口大小修改放缩比例
    function setViewportScale(){
        let scroll = window.scroll.width;
        let clentWidth =   document.body.offsetWidth;
        let clentHeight = document.body.offsetHeight;
        let scale_w = clentWidth/1920;
        let scale_h = clentHeight/969;
        let scale = "scale("+scale_w+","+scale_h+")";
        document.getElementById("viewport").style.transform = scale;
    }
    var qcloud = {};
    $('[_t_nav]').hover(function() {
        var _nav = $(this).attr('_t_nav');
        clearTimeout(qcloud[_nav + '_timer']);
        qcloud[_nav + '_timer'] = setTimeout(function() {
            $('[_t_nav]').each(function() {
                $(this)[_nav == $(this).attr('_t_nav') ? 'addClass' : 'removeClass']('nav-up-selected');
            });
            $('#' + _nav).stop(true, true).slideDown(200);
        }, 150);
    }, function() {
        var _nav = $(this).attr('_t_nav');
        clearTimeout(qcloud[_nav + '_timer']);
        qcloud[_nav + '_timer'] = setTimeout(function() {
            $('[_t_nav]').removeClass('nav-up-selected');
            $('#' + _nav).stop(true, true).slideUp(200);
        }, 150);
    });
    //二级菜单点击
    $(".nav-down-menu").click(function () {
        let id = $(this).attr("id");
        vue.$data.selectedNav = id;
    });
    //点击首页
    $(".navigation-up li[_t_nav='home']").click(function (){
        vue.$data.selectedNav = 'home';
        vue.$data.breadcrumbList = ['首页'];
        vue.$data.currentSrc = 'index.html';
    })
});

var vue = new Vue({
    el: "#viewport",
    data: {
        selectedNav:'home',
        currentSrc:'index.html',
        userInfo:{},
        password_old:'',
        password_new:'',
        layerIndex:0,
        /* 导航标题 */
        nevigatelist: [{
            id: '1',
            nav: 'home',
            label: '首页'
        }, {
            id: '2',
            nav: 'statisticData',
            label: '数据指标'
        }, {
            id: '3',
            nav: 'warning',
            label: '短信告警'
        },{
            id: '5',
            nav: 'sysManager',
            label: '系统管理'
        }],
        /* 导航内明细列表 */
        navdetails: [{
            id: 'statisticData',
            p_name:'数据指标',
            /* 明细列表左移距离 */
            leftlength : '260px',
            /* 明细列表标题下内容 */
            childrenlist: [{
                id: '1',
                /* 列表标题 */
                label: '',
                /* 标题下内容列表 */
                hreflist: [{
                    label: '指标统计数据',
                    href: 'indicator.html',
                }]
            },{
                id: '2',
                /* 列表标题 */
                label: '',
                /* 标题下内容列表 */
                hreflist: [{
                    label: '历史数据查询',
                    href: 'historyData_all.html',
                }]
            }]

        },{
            id: 'warning',
            p_name:'短信告警',
            leftlength : '390px',
            childrenlist: [{
                id: '1',
                /* 列表标题 */
                label: '',
                /* 标题下内容列表 */
                hreflist: [{
                    label: '监控告警设置',
                    href: 'warningSetting.html'
                } ]
            },{
                id: '2',
                /* 列表标题 */
                label: '',
                /* 标题下内容列表 */
                hreflist: [{
                    label: '已发短信查询',
                    href: 'send_message.html'
                } ]
            }
            ]
        },{
            id:'sysManager',
            leftlength : '520px',
            p_name:'系统管理',
            childrenlist : [
                {
                    id: '1',
                    /* 列表标题 */
                    label: '',
                    /* 标题下内容列表 */
                    hreflist: [{
                        label: '用户管理',
                        href: 'personInfoMgr.html'
                    } ]
                },{
                    id: '2',
                    /* 列表标题 */
                    label: '',
                    /* 标题下内容列表 */
                    hreflist: [{
                        label: '角色管理',
                        href: 'roleMgr.html'
                    } ]
                },{
                    id: '3',
                    /* 列表标题 */
                    label: '',
                    /* 标题下内容列表 */
                    hreflist: [{
                        label: '指标管理',
                        href: 'indicatorMgr.html'
                    } ]
                }
            ]
        }],
        breadcrumbList:['首页']
    },
    mounted: function() {
        this.queryCurrentUser();
    },
    methods: {
        openPage(e){
            let href = $(e.target).data("href");
            this.currentSrc = href;
            //二级菜单 可能不存在
            let menu_two_title = $(e.target).closest("dt").html();
            //获取父级类型
            let target_name = $(e.target).html();
            let parent_type = $(e.target).closest(".nav-down-menu").attr("name");
            this.breadcrumbList = [];
            this.breadcrumbList.push(parent_type);
            if(menu_two_title){
                this.breadcrumbList.push(menu_two_title);
            }
            this.breadcrumbList.push(target_name);
        },
        queryCurrentUser(){
            let url = "user/queryCurrentUser";
            let param = {};
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.userInfo = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        logout(){
            window.location.href="/dpidcalarm/logout";
        },
        modifyPassword(){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                this_.layerIndex = layer.open({
                    type: 2,
                    area: ['380px', '350px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:"修改密码",
                    content: "/dpidcalarm/html/modifyPassword.html" //这里content是一个普通的String
                });
            });
        },
        closeLayer(){
            let this_ = this;
            layui.use('layer', function() {
                let layer = layui.layer;
                layer.close(this_.layerIndex)
            });
        }
    }
});

