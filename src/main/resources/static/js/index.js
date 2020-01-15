var charts_bdz;
var charts_jfdl;
var charts_kgbw;
var charts_yxyc;
var charts_sgfz;
jQuery(document).ready(function() {
    charts_ztgj = echarts.init(document.getElementById("ztgj"));

    charts_bdz = echarts.init(document.getElementById("bdz"));

    charts_jfdl = echarts.init(document.getElementById("jfdl"));

    charts_kgbw = echarts.init(document.getElementById("kgbw"));

    charts_yxyc = echarts.init(document.getElementById("yxyc"));

    charts_sgfz = echarts.init(document.getElementById("sgfz"));
    refreshData();
    //定时刷新
    setInterval(refreshData,1000*60);
});
function refreshData() {
    queryData(charts_ztgj,1006,"状态估计");
    queryData(charts_bdz,1001,"变电站遥测刷新率");
    queryData(charts_jfdl,1004,"积分电量误差率");
    queryData(charts_kgbw,1002,"开关频繁变位");
    queryData(charts_yxyc,1003,"遥信遥测状态不一致");
    queryData(charts_sgfz,1005,"事故分闸");
}
function queryData(charts,type,title) {
    let url = "queryRealTimeData";
    let param = {
        type:type
    };
    ajaxUtil.ajaxQuery(url,param).then(res=>{
        initCharts(charts,res,title);
    }).catch(err =>{
        console.error(err);
    })
}
function initCharts(charts,value,title){
    let option = {
        backgroundColor: "#ffffff",
        color: ["#37A2DA", "#32C5E9", "#67E0E3"],
        series: [{
            name: '业务指标',
            type: 'gauge',
            detail: {
                formatter: '{value}%'
            },
            axisLine: {
                show: true,
                lineStyle: {
                    width: 30,
                    shadowBlur: 0,
                    color: [
                        [0.3, '#fd666d'],
                        [0.7, '#37a2da'],
                        [1, '#67e0e3']
                    ]
                }
            },
            data: [{
                value: value,
                name: title,
            }]

        }]
    };
    charts.setOption(option);
}
