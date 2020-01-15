new Vue({
    el:'#container',
    data:{
        roles:[],
        personInfo:{
            name:"",
            roleId:"",
            phone:"",
            passwd:"",
            areaId:"",
            desc:""
        }
    },
    mounted(){
        //从父页面获取数据
        this.roles = window.parent.vue.$data.roles;
        this.personInfo = window.parent.vue.$data.personInfo;
    },
    methods:{
        submit:function () {
            let this_ = this;
            let url = 'user/addUser';
            if("add" == window.parent.vue.$data.operateType){
                url = 'user/addUser';
            }else{
                url = 'user/modifyUser';
            }

            let param = JSON.stringify(this.personInfo);
            ajaxUtil.ajaxQueryJson(url,param).then(res=>{
                if(res > 0){
                    layui.use('layer', function() {
                        let layer = layui.layer;
                        layer.msg("操作成功");
                    })
                }else{
                    layui.use('layer', function() {
                        let layer = layui.layer;
                        layer.msg("操作失败");
                    })
                }
                window.parent.vue.queryUsers();
                setTimeout(function () {
                    this_.closeDialog();
                },500);

            }).catch(err =>{
                console.error(err);
            })
        },
        closeDialog:function () {
            window.parent.vue.closeDialog();
        }
    }
});