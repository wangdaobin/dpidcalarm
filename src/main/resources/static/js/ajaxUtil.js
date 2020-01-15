/*对各种类型的ajax请求封装*/
var baseUrl = "/dpidcalarm"
var ajaxUtil =  {
        /*ajax请求封装 get查询数据 只需要返回data时使用*/
        ajaxQuery:function(url, param){
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: baseUrl+"/"+url,
                    type: "GET",
                    data: param,
                    success: (res) => {
                        resolve(res)
                    },
                    error:(res) =>{
                        reject(res);
                    }
                })
            })
        },
        /*ajax请求封装 用于修改 删除等操作 返回数据需要message 和data 时*/
        ajaxPost:function(url, param){
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: baseUrl+"/"+url,
                    type: "POST",
                    data: param,
                    success: (res) => {
                        resolve(res)
                    },
                    error:(res) =>{
                        reject(res);
                    }
                })
            })
        },
        /*前端使用json字符串数组 后台需转换为对象时使用*/
        ajaxQueryJson:function(url,param) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: baseUrl+"/"+url,
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json", // 指定这个协议很重要
                    data: param,
                    success: (res) => {
                        resolve(res)
                    },
                    error:(res) =>{
                        reject(res);
                    }
                })
            })
        }

};
