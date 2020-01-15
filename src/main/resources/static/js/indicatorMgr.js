var vue = new Vue({
    el:'#container',
    data:{
        name:'',
        tableData_indicator:[],
        tableData_indicatorAttr:[],
        title:'',
        operateType:'',
        src:'',
        dialogIndex:0,
        indicatorInfo:{},
        indicatorInfoAttr:{}
    },
    create(){},
    mounted(){
        this.queryIndicator();
        this.queryIndicatorAttr();
    },
    methods:{
        /*查询所有指标*/
        queryIndicator:function () {
            let url = 'indicatorMgr/queryIndicator';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_indicator = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        queryIndicatorAttr:function(){
            let url = 'indicatorMgr/queryIndicatorAttr';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_indicatorAttr = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        addIndicatorInfo:function(){
            this.title = "添加指标信息";
            this.operateType = "add";
            this.indicatorInfo = {};
            this.src = "/dpidcalarm/html/indicatorInfo.html";
            this.openDialog('400px','430px');
        },
        modifyIndicator:function (index,rowData) {
            this.title = "修改指标信息";
            this.operateType = "modify";
            this.indicatorInfo = JSON.parse(JSON.stringify(rowData));
            this.src = "/dpidcalarm/html/indicatorInfo.html";
            this.openDialog('400px','430px');
        },
        deleteIndicator:function (index,rowData) {
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.confirm('您确定要删除该条记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    let url = 'indicatorMgr/deleteIndicator';
                    let param = {
                        id:rowData.id
                    };
                    ajaxUtil.ajaxQuery(url,param).then(res=>{
                        this_.queryRoles();
                        if(res != 0){
                            layer.msg("删除成功！")
                        }else{
                            layer.msg("删除失败！");
                        }
                    }).catch(err =>{
                        console.error(err);
                    })
                }, function(){

                });
            })
        },
        addIndicatorCollectInfo:function () {
            this.title = "添加指标采集信息";
            this.operateType = "add";
            this.indicatorInfoAttr = {};
            this.src = "/dpidcalarm/html/indicatorAttrInfo.html";
            this.openDialog('800px','460px');
        },
        modifyIndicatorCollectInfo:function (index,rowData) {
            this.title = "修改指标采集信息";
            this.operateType = "modify";
            this.indicatorInfoAttr = JSON.parse(JSON.stringify(rowData));
            this.src = "/dpidcalarm/html/indicatorAttrInfo.html";
            this.openDialog('800px','460px');
        },
        deleteIndicatorCollectInfo:function (index,rowData) {
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.confirm('您确定要删除该条记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    let url = 'indicatorMgr/deleteIndicatorAttr';
                    let param = {
                        id:rowData.id
                    };
                    ajaxUtil.ajaxQuery(url,param).then(res=>{
                        this_.queryRoles();
                        if(res != 0){
                            layer.msg("删除成功！")
                        }else{
                            layer.msg("删除失败！");
                        }
                    }).catch(err =>{
                        console.error(err);
                    })
                }, function(){

                });
            })
        },
        openDialog(width,height){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                this_.dialogIndex=layer.open({
                    type: 2,
                    area: [width, height],
                    skin: 'layui-layer-rim', //加上边框
                    title:this_.title,
                    content: this_.src
                });
            });
        },
        closeDialog(){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.close(this_.dialogIndex)
            })
        },
    }
});