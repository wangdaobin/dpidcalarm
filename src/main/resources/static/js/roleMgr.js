var vue = new Vue({
    el:'#container',
    data:{
        name:'',
        tableData:[],
        title:'',
        operateType:'',
        dialogIndex:0,
        roleInfo:{

        }
    },
    mounted(){
        this.queryRoles();
    },
    methods:{
        queryRoles:function () {
            let url = 'role/queryRoles';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        deleteRole:function (index,rowData) {
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.confirm('您确定要删除该条记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    let url = 'role/deleteRole';
                    let param = {
                        roleId:rowData.id
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
        modify:function (index,rowData) {
            this.title = "修改角色信息";
            this.operateType = "modify";
            this.roleInfo = JSON.parse(JSON.stringify(rowData));
            this.openDialog();
        },
        addRoleInfo:function () {
            this.title = "添加角色信息";
            this.operateType = "add";
            this.roleInfo = {};
            this.openDialog();
        },
        openDialog(){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                this_.dialogIndex=layer.open({
                    type: 2,
                    area: ['400px', '450px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:this_.title,
                    content: "/dpidcalarm/html/roleInfo.html"
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