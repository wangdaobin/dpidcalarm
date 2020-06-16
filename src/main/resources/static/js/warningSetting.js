new Vue({
    el:"#container",
    data:{
        userName:"",
        form_workday:{
            indicatorType:'',
            gzOption:1,
            gzValue:'',
            sendType:1,
            messageInterval:30,
            sendStartTime:'',
            sendEndTime:'',
            nightSetting:0,
            nightStartTime:'',
            nightEndTime:'',
            nightSendInterval:120
        },
        indicators:[],
        options:[{label:'大于',value:1},{label:'小于',value:2},{label:'等于',value:3}],
        sendTypes:[{label:'触发',value:1},{label:'定时',value:2}],
        tableData_users:[]
    },
    created(){

    },
    mounted(){
        this.queryUsers();
        this.queryIndicators();
    },
    methods:{
        /*查询用户*/
        queryUsers(){
            let url = 'user/queryAllUsers';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_users = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        queryIndicators(){
            let url = 'indicatorMgr/queryIndicator';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.indicators = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        /*提交*/
        onSubmit(){

        },
        /*保存指标接收人*/
        saveMessageUser(){
            let selectUsers = this.$refs.usersTable.selection;
            if(this.form_workday.indicatorType == null ||this.form_workday.indicatorType == ''){
                layui.use('layer', function(){
                    layer.msg("请选择要设置的指标！");
                });
                return;
            }
            if(selectUsers == null || selectUsers.length == 0){
                layui.use('layer', function(){
                    layer.msg("请选择短信接收人！");
                });

            }else{
                let url = 'messageLogSetting/saveSendUsers';
                let param = {
                    indicatorType:this.form_workday.indicatorType,
                    users:selectUsers
                };
                ajaxUtil.ajaxQueryJson(url,JSON.stringify(param)).then(res=>{

                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
});