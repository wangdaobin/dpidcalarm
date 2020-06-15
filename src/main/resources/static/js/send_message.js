new Vue({
    el:"#container",
    data:{
        date1:"",
        date2:"",
        options:[
            {label:"单独合格率",value:"1"},
            {label:"业务数据传输可用率",value:"2"}
        ],
        typeValue:"1",
        content:"",
        tableData:[],
        currentPage:1,
        pageSize:20,
        total:0,
        timer:null
    },
    created(){
        this.date1 = utils.formatDate(new Date(),'yyyy-MM-dd');
        let date_tmp = new Date();
        this.date2 = utils.formatDate(date_tmp.setTime(date_tmp.getTime() + 1000*60*60*24),'yyyy-MM-dd');
    },
    mounted(){
        this.queryMessageLogs();
    },
    methods:{
        queryMessageLogs:function () {
            let url = 'messageLog/queryMsgLog';
            let param = {
                beginTime:this.date1,
                endTime:this.date2,
                type:'',//暂未使用
                content:this.content,
                pageNum:this.currentPage,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData = res.list;
                this.currentPage = res.pageNum;
                this.total = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        queryHandle(){
            this.queryMessageLogs();
        },
        contentChangeHandle(){
            let this_ = this;
            if(this.timer != null){
                clearTimeout(this.timer)
            }
            this.timer = setTimeout(function () {
                console.log("查询");
                this_.queryMessageLogs();
            },100)
        },
        currentChangeHandle:function (currentPage) {
            this.currentPage = currentPage;
            this.queryMessageLogs();
        },
        statusFormatter(row,column){
            if(row.status == 0){
                return "发送失败"
            }else if(row.status == 1){
                return "发送成功"
            }
        }

    }
});