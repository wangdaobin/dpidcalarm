var vue = new Vue({
    el:'#container',
    data:{
        indicatorInfo:{}
    },
    mounted(){
        this.indicatorInfo =  window.parent.vue.$data.indicatorInfo;
    },
    methods:{
        submit:function () {
            let this_ = this;
            let url = 'indicatorMgr/addIndicator';
            if("add" == window.parent.vue.$data.operateType){
                url = 'indicatorMgr/addIndicator';
            }else{
                url = 'indicatorMgr/modifyIndicator';
            }
            let param = JSON.stringify(this.indicatorInfo);
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
                window.parent.vue.queryIndicator();
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