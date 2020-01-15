var vue = new Vue({
    el:'#container',
    data:{
        indicatorInfos:[],
        indicatorInfoAttr:{}
    },
    mounted(){
        this.indicatorInfoAttr =  window.parent.vue.$data.indicatorInfoAttr;
        this.indicatorInfos = window.parent.vue.$data.tableData_indicator;
    },
    methods:{
        submit:function () {
            let this_ = this;
            let url = 'indicatorMgr/addIndicatorAttr';
            if("add" == window.parent.vue.$data.operateType){
                url = 'indicatorMgr/addIndicatorAttr';
            }else{
                url = 'indicatorMgr/modifyIndicatorAttr';
            }
            let param = JSON.stringify(this.indicatorInfoAttr);
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
                window.parent.vue.queryIndicatorAttr();
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