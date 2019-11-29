new Vue({
    el:'#container',
    data:{
        tableData_single:[{facName:'南岸木洞',idcName:'110kV专变',collectTime:'2019-11-26 至 2019-11-28',errorNum:'22'}],
        tableData_error_dev:[],
        tableData_dev_detail:[],
        date1:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date2:"",
        date3:utils.formatDate(new Date(),'yyyy-MM-dd'),
        date4:""

    },
    created(){
        let date_tmp = new Date();
        this.date2 = utils.formatDate(date_tmp.setTime(date_tmp.getTime() + 1000*60*60*24),'yyyy-MM-dd');
        this.date4 = utils.formatDate(date_tmp.setTime(date_tmp.getTime() + 1000*60*60*24),'yyyy-MM-dd');
    },
    mounted(){

    },
    methods:{
        /*查询单独合格率*/
        query_single(){
            let url = 'queryDataCountSingle';
            let param = {
                start:this.date1,
                end:this.date2
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                console.log(res);
            }).catch(err =>{
                console.error(err);
            })
        },
        /*导出单独合格率表*/
        download_single(){
            let url = 'queryDataCountSingle';
            let param = {
                start:this.date1,
                end:this.date2
            };
            ajaxUtil.ajaxQuery(url,param).then(res=>{
                console.log(res);
            }).catch(err =>{
                console.error(err);
            })
        },
        /*弹出错误设备统计界面*/
        openErrorDevCountPage(){
            let this_ = this;
            //TODO 查询数据
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.open({
                    type: 1,
                    area: ['500px', '600px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:this_.date1 + "至" + this_.date2 + "时间段错误设备统计",
                    content: $('#error_dev_count') //这里content是一个普通的String
                });
            });
        },
        queryDetail(index,row){
            let this_ = this;
            //TODO 查询数据
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.open({
                    type: 1,
                    area: ['700px', '600px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:"查看详情",
                    content: $('#dev_detail') //这里content是一个普通的String
                });
            });
        }
    }
});