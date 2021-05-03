// 处理日期的的中间类
export function formatDate(date,fmt) {
    if(/(y+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1,(date.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    let o ={
        'M+': date.getMonth()+1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o){
        if(new RegExp(`(${k})`).test(fmt))
        {
            let str = o[k] + '';
            fmt=fmt.replace(RegExp.$1,(RegExp.$1.length===1)?str:padLeftZero(str))
        }

    }
    return fmt
}
function padLeftZero(str){
    return ('00' + str).substr(str.length)
}

export function changeSex(value){
        if (value === 0) {
          return '女'
        } else if (value === 1) {
          return '男'
        } else if (value === 2) {
          return '组合'
        } else if (value === 3) {
          return '不明'
        } else if (value === '男' || value === '女') {
          return value
        }
}

// 改变用户歌单类型
export function changeSongListType(value){
    if(value == 0){
        return '系统生成'
    }else if(value == 1){
      return '用户自定义'
    }else if(value == 2){
      return '系统推送'
    }
}

// 评论的状态信息
export function changeCommit(value){
    if(value == 0){
        return '审核中'
    }else if(value == 1){
        return '审核通过'
    }else if(value == 2){
        return '审核不通过'
    }
}