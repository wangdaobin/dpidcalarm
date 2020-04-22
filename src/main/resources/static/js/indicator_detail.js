new Vue({
    el: '#container',
    data:{
        tableData:[],
        pageSize:20,
        pageNum:1,
        total:0,
        type:0,
        idpId:""
    },
    created(){
        this.type = this.getQueryString("type");
        this.idpId = this.getQueryString("idpId");
    },
    mounted(){
        this.queryDetail();
    },
    methods:{
        queryDetail(){
            let url = "queryIndicatorDetail";
            let param = {
                type:this.type,
                idpId:this.idpId,
                pageNum:this.pageNum,
                pageSize:this.pageSize
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                this.tableData = res.list;
                console.log(this.tableData);
                this.pageNum = res.pageNum;
                this.total = res.total;
            }).catch(err =>{
                console.error(err);
            })
        },
        current_change:function(currentPage){
            this.pageNum_sgfz = currentPage;
            this.queryDetail();
        },
        // 获取参数地址
        getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        }
    }
});