/*历史数据查询js*/
var echarts_year;
var echarts_month;
var echarts_day;
jQuery(document).ready(function() {
    //初始化日期控件
    layui.use('laydate', function(){
        let laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#year', //指定元素
            type: 'year',
            done: function(value, date, endDate){
                addData_year(value)
            }
        });
        laydate.render({
            elem: '#month', //指定元素
            type: 'month',
            done: function(value, date, endDate){
                addData_mounth(value);
            }
        });
        laydate.render({
            elem: '#day', //指定元素
            type: 'date',
            done: function(value, date, endDate){
                addData_day(value);
            }
        });
    });
    //初始化echarts
    let xAxis_year = [];
    for(let i = 1;i<13;i++){
        xAxis_year.push(i+"月");
    }
    let xAxis_month = [];
    for(let i = 1;i<32;i++){
        xAxis_month.push(i+"日");
    }
    let xAxis_day = [];
    for(let i = 1;i<25;i++){
        xAxis_day.push(i+"时");
    }
    echarts_year = initCharts("charts_year",xAxis_year);
    //TODO 默认查询当年
    addData_year('2019年');
    addData_year('2018年');
    echarts_month = initCharts("charts_month",xAxis_month);
    echarts_day = initCharts("charts_day",xAxis_day);
});
/*年份切换数据添加*/
function addData_year(name) {
    //TODO 查询数据
    let value = [1, 3, 5, 2, 6, 12, 4,8,7,10,9,''];
    let series = {
        name:name,
        data:value ,
        type: 'line',
        smooth: true
    };
    let option = echarts_year.getOption();
    option.series.push(series);
    option.legend[0].data.push(series.name);
    echarts_year.setOption(option);
}
function removeData_year(name) {
    let option = echarts_year.getOption();

}
/*月份份切换数据添加*/
function addData_mounth(value) {

}
/*日期切换数据添加*/
function addData_day(value) {

}

function initCharts(el,xAxis_year) {
    let echarts_temp = echarts.init(document.getElementById(el));
    let option = {
        grid:{
            left:50,
            top:10
        },
        tooltip: {},
        legend: {
            orient:'vertical',
            top:20,
            right:20,
            data:[]
        },
        xAxis: {
            data:xAxis_year
        },
        yAxis: {},
        series: []
    };
    echarts_temp.setOption(option);
    echarts_temp.on('legendselectchanged', function (evt) {
    //这里通过obj获取信息，设定option之后,重新载入图表
        Object.keys(evt.selected).forEach((key)=>{
            if(evt.selected[key] == false){
                //删除对比
                let option_tmp = echarts_temp.getOption();
                //图例删除
                option_tmp.legend[0].data = option_tmp.legend[0].data.filter(function(val){
                    return val != key;
                });
                //数据删除
                option_tmp.series = option_tmp.series.filter(function(val){
                    return val.name != key;
                });
                echarts_temp.setOption(option_tmp,true);
                console.log(echarts_temp.getOption());
            }
        });

    });
    return echarts_temp;
}