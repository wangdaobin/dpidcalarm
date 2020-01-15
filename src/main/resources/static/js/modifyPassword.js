new Vue({
    el:'#container',
    data:{
        password_old:'',
        password_new:''
    },
    methods:{
        closeLayer:function () {
            window.parent.vue.closeLayer();
        },
        modifyPassword:function () {
            let url = "user/modifyPassword";
            let param = {
                userName:window.parent.vue.userInfo.name,
                password_old:this.password_old,
                password_new:this.password_new
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                layui.use('layer', function(){
                    let layer = layui.layer;
                    layer.msg(res);
                })
            }).catch(err =>{
                console.error(err);
            })
        }
    }
});