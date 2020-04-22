new Vue({
    el:"#container",
    data:{
        userName:"",
        form_workday:{
            startTime:'',
            endTime:'',
            gzOption:'1',
            gzValue:'',
            messageInterval:'',
            sendStartTime:'',
            sendEndTime:'',
            nightStartTime:'',
            nightEndTime:'',
            nightRecUser:'',
            messageModel:''
        },
        options:[{label:'大于',value:'1'},{label:'小于',value:'2'},{label:'等于',value:'3'}],
        option_users:[{label:'张三',value:'1'}],
        tableData_users:[{name:'张三'},{name:'王麻子'}]
    },
    mounted(){

    },
    methods:{
        /*查询用户*/
        queryUsers(){
            let url = 'user/queryAllUsers';
            let param = {
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                tableData_users = res;
            }).catch(err =>{
                console.error(err);
            })
        },
        /*提交*/
        onSubmit(){

        }
    }
});