//日期格式化
var utils = {
    formatDate:function(date, fmt){
            date=date || new Date();
            fmt=fmt || 'yyyy-MM-dd hh:mm:ss';
            if(Object.prototype.toString.call(date).slice(8,-1)!=='Date'){
                date=new Date(date)
            }
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
            }
            let o = {
                'M+': date.getMonth() + 1,
                'd+': date.getDate(),
                'h+': date.getHours(),
                'm+': date.getMinutes(),
                's+': date.getSeconds()
            };
            for (let k in o) {
                if (new RegExp(`(${k})`).test(fmt)) {
                    let str = o[k] + '';
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : this.padLeftZero(str))
                }
            }
            return fmt
        },
        padLeftZero:function(str) {
            return ('00' + str).substr(str.length)
        }
};
