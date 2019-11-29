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
    echarts_year = initCharts("year");
    echarts_year = initCharts("year");
    echarts_year = initCharts("year");
});
/*年份切换数据添加*/
function addData_year(value) {

}
/*月份份切换数据添加*/
function addData_mounth(value) {

}
/*日期切换数据添加*/
function addData_day(value) {

}

function initCharts(el) {
    let echarts_temp = echarts.init(document.getElementById(el));
    return echarts_temp;
}