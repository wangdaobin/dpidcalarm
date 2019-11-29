new Vue({
    el:'#container',
    data:{
        name:'',
        tableData:[],
        personInfo:{
            name:"",
            sex:"",
            phone:""
        }
    },
    methods:{
        addUserInfo(){
            this.personInfo.name = '';
            this.personInfo.sex = '';
            this.personInfo.phone = '';
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.open({
                    type: 1,
                    area: ['400px', '400px'],
                    skin: 'layui-layer-rim', //加上边框
                    title:"添加人员信息",
                    content: $('#userInfo') //这里content是一个普通的String
                });
            });
        }

    }
});