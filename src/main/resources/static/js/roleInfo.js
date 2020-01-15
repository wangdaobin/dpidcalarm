var vue = new Vue({
    el:'#container',
    data:{
        roleInfo:{}
    },
    mounted(){
        this.roleInfo =  window.parent.vue.$data.roleInfo;
        if(this.roleInfo != null){
            if(this.roleInfo.sysadm == 1){
                this.roleInfo.sysadm = true;
            }else{
                this.roleInfo.sysadm = false;
            }
            if(this.roleInfo.memadm == 1){
                this.roleInfo.memadm = true;
            }else{
                this.roleInfo.memadm = false;
            }
            if(this.roleInfo.collectadm == 1){
                this.roleInfo.collectadm = true;
            }else{
                this.roleInfo.collectadm = false;
            }
            if(this.roleInfo.alarmadm == 1){
                this.roleInfo.alarmadm = true;
            }else{
                this.roleInfo.alarmadm = false;
            }
        }
    },
    methods:{
        submit:function () {
            let this_ = this;
            let url = 'role/addRole';
            if("add" == window.parent.vue.$data.operateType){
                url = 'role/addRole';
            }else{
                url = 'role/modifyRole';
            }
            if(this.roleInfo.sysadm){
                this.roleInfo.sysadm = 1;
            }else{
                this.roleInfo.sysadm = 0;
            }
            if(this.roleInfo.memadm ){
                this.roleInfo.memadm = 1;
            }else{
                this.roleInfo.memadm = 0;
            }
            if(this.roleInfo.collectadm){
                this.roleInfo.collectadm = 1;
            }else{
                this.roleInfo.collectadm = 0;
            }
            if(this.roleInfo.alarmadm){
                this.roleInfo.alarmadm = 1;
            }else{
                this.roleInfo.alarmadm = 0;
            }
            let param = JSON.stringify(this.roleInfo);
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
                window.parent.vue.queryRoles();
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