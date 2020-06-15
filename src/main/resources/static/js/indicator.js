new Vue({
    el:'#container',
    data:{
        tableData_bdz:[],
        tableData_kgbw:[],
        tableData_yxyc:[],
        tableData_jfdl:[],
        tableData_sgfz:[],
        tableData_ztgj:[],
        pageSize:20,
        pageNum_bdz:1,
        pageNum_kgbw:1,
        pageNum_yxyc:1,
        pageNum_jfdl:1,
        pageNum_sgfz:1,
        pageNum_ztgj:1,
        total_bdz:1,
        total_kgbw:1,
        total_yxyc:1,
        total_jfdl:1,
        total_sgfz:1,
        total_ztgj:1,
        date1:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date2:"",
        date3:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date4:"",
        date5:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date6:"",
        date7:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date8:"",
        date9:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date10:"",
        date11:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date12:""
    },
    created(){
        let date_tmp = new Date();
        this.date2 = utils.formatDate(date_tmp.setTime(date_tmp.getTime() + 1000*60*60*24),'yyyy-MM-dd');
        this.date4 = utils.formatDate(date_tmp,'yyyy-MM-dd');
        this.date6 = utils.formatDate(date_tmp,'yyyy-MM-dd');
        this.date8 = utils.formatDate(date_tmp,'yyyy-MM-dd');
        this.date10 = utils.formatDate(date_tmp,'yyyy-MM-dd');
        this.date12 = utils.formatDate(date_tmp,'yyyy-MM-dd');
    },
    mounted(){
        this.querydata_ztgj();
        this.querydata_bdz();
        this.querydata_kgbw();
        this.querydata_yxyc();
        this.querydata_jfdl();
        this.querydata_sgfz();
    },
    methods:{
        /*查询数据*/
        querydata_ztgj(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date11,
                end:this.date12,
                type:1006,
                pageNum:this.pageNum_ztgj,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_ztgj = res.list;
                this.pageNum_ztgj = res.pageNum;
                this.total_ztgj  = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        querydata_bdz(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date1,
                end:this.date2,
                type:1001,
                pageNum:this.pageNum_bdz,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_bdz = res.list;
                this.pageNum_bdz = res.pageNum;
                this.total_bdz = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        querydata_kgbw(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date3,
                end:this.date4,
                type:1002,
                pageNum:this.pageNum_kgbw,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_kgbw = res.list;
                this.pageNum_kgbw = res.pageNum;
                this.total_kgbw = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        querydata_yxyc(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date5,
                end:this.date6,
                type:1003,
                pageNum:this.pageNum_yxyc,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_yxyc = res.list;
                this.pageNum_yxyc = res.pageNum;
                this.total_yxyc = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        querydata_jfdl(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date7,
                end:this.date8,
                type:1004,
                pageNum:this.pageNum_jfdl,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_jfdl = res.list;
                this.pageNum_jfdl = res.pageNum;
                this.total_jfdl = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        querydata_sgfz(){
            let url = 'queryIndicatorHisData';
            let param = {
                start:this.date9,
                end:this.date10,
                type:1005,
                pageNum:this.pageNum_sgfz,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData_sgfz = res.list;
                this.pageNum_sgfz = res.pageNum;
                this.total_sgfz = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        current_change_bdz:function(currentPage){
            this.pageNum_bdz = currentPage;
            this.querydata_bdz();
        },
        current_change_jfdl:function(currentPage){
            this.pageNum_jfdl = currentPage;
            this.querydata_jfdl();
        },
        current_change_kgbw:function(currentPage){
            this.pageNum_kgbw = currentPage;
            this.querydata_kgbw();
        },
        current_change_yxyc:function(currentPage){
            this.pageNum_yxyc = currentPage;
            this.querydata_yxyc();
        },
        current_change_sgfz:function(currentPage){
            this.pageNum_sgfz = currentPage;
            this.querydata_sgfz();
        },
        current_change_ztgj:function(currentPage){
            this.pageNum_ztgj = currentPage;
            this.querydata_ztgj();
        },

        /*导出单独合格率表*/
        download_single(){
        },
        queryDetail(index,row,type){
           let idpId = row.id;
           let pageName = "indicator_detail_"+type;
            let this_ = this;
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.open({
                    type: 2,
                    area: ['900px', '600px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:"查看详情",
                    content: `../html/`+pageName+`.html?idpId=`+idpId+`&type=`+row.type, //这里content是一个普通的String
                });
            });
        }
    }
});