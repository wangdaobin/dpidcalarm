var vue = new Vue({
    el:'#container',
    data:{
        name:'',
        tableData:[],
        pageNum:1,
        pageSize:20,
        total:0,
        mytimer:null,
        roles:[],
        title:"",
        operateType:"",//modify add
        dialogIndex:0,
        personInfo:{
            name:"",
            roleId:"",
            phone:"",
            passwd:"",
            areaId:"",
            desc:""
        }
    },
    created(){
        this.queryRoles();
    },
    mounted(){
        this.queryUsers();
    },
    methods:{
        queryUsers:function(){
            let url = 'user/queryUsers';
            let param = {
                userName:this.name,
                pageNum:this.pageNum,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData = res.list;
                this.pageNum = res.pageNum;
                this.total = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        queryInfo(){
            if(this.mytimer != null){
                window.clearTimeout(this.mytimer);
            }
            let this_ = this;
            this.mytimer = setTimeout(function () {
                this_.queryUsers();
            },200)
        },
        addUserInfo(){
            this.title = "添加人员信息";
            this.operateType = "add";
            this.personInfo.name = '';
            this.personInfo.roleId = '';
            this.personInfo.phone = '';
            this.personInfo.passwd = '';
            this.personInfo.areaId = '';
            this.personInfo.desc = '';
            this.openDialog();
        },
        deleteUser(index,rowData){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.confirm('您确定要删除该条记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    let url = 'user/deleteUser';
                    let param = {
                        userId:rowData.id
                    };
                    ajaxUtil.ajaxQuery(url,param).then(res=>{
                        this_.queryUsers();
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
        modify(index,rowData){
            this.title = "修改人员信息";
            this.operateType = "modify";
            this.personInfo = JSON.parse(JSON.stringify(rowData));
            this.openDialog();
        },
        openDialog(){
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                this_.dialogIndex=layer.open({
                    type: 2,
                    area: ['400px', '550px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:this_.title,
                    content: "/dpidcalarm/html/userInfo.html"
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
        current_change:function () {
            this.queryUsers();
        },
        queryRoles:function () {
            let this_ = this;
            let url = 'role/queryRoles';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this_.roles = res;
            }).catch(err =>{
                console.error(err);
            })
        },

    }
});