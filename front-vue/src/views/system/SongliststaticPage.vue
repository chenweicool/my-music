<template >
  <div id="box" class="container">
    <div class = "header">
      <p>歌曲信息分析</p>
    </div>
    
  <div style="display:flex; width: 100%;margin: 20px ">
      <div class="mobile" style="flex:1;width: 100%; margin-right:50px;">
        <div style="text-align: center;">歌曲播放统计</div>
        <div id="main" style="width: 100%; height:400px;margin-top:20px;"></div>
     </div>
     <div class="online" style="flex:1;width: 100%;">
      <div style="text-align: center;">歌曲评论统计</div>
      <div id="song" style="width: 100%;height:450px;"></div>
    </div>
   </div>

    
  </div>
</template>

<script>
// import {statisticUser} from '../api/index'
import {getMaxPlaySongs,getMaxComment,getUserLocation} from  '../../api/system/statistic'
import echarts from 'echarts'

export default {
    
   name: "test",
   data (){
      return{
         onlineXarr: [],    //存放用户在线时长的纵坐标
         onlineSarr:[] ,    // 用户在线时长的每个坐标值

         songXarr:[],   // 用户登陆的时间的纵坐标
         songSarr:[],   // 登陆时间点的具体坐标
      }
   },
//       created (){
//         this.getLocation();
//    },
  methods: {

   drawChart() {
      // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById("main"));
        //指定图表的配置项和数据
        var salvProName =["安徽省","河南省","浙江省","湖北省","贵州省","江西省","江苏省","四川省","云南省","湖南省"];
        var salvProValue =[239,181,154,144,135,117,74,72,67,55];
        var salvProMax =[];//背景按最大值
        for (let i = 0; i < salvProValue.length; i++) {
            salvProMax.push(salvProValue[0])
        }
      // 测试数据
        let bgColor = '#fff';
        let data = {
            xData: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11','12'],
            yData: [100, 200, 300, 300, 500, 600, 700, 800, 900, 900, 1000,1300]
        }
        let option1 = {
            tooltip: {},
            xAxis: {
                data: [],
            },
            yAxis: {},
            series: [{
                type: 'bar',
                data: [],
            }]
        };

         // 第一次最简单的初始化ehars图
        myChart.setOption(option1);

        let option = {
            backgroundColor:"#003366",
            grid: {
                left: '2%',
                right: '2%',
                bottom: '2%',
                top: '2%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'none'
                },
                formatter: function(params) {
                    return params[0].name  + ' : ' + params[0].value
                }
            },
            xAxis: {
                show: false,
                type: 'value'
            },
            yAxis: [{
                type: 'category',
                inverse: true,
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    },
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                // data: salvProName
                data: this.onlineXarr
            }, {
                type: 'category',
                inverse: true,
                axisTick: 'none',
                axisLine: 'none',
                show: true,
                axisLabel: {
                    textStyle: {
                        color: '#ffffff',
                        fontSize: '12'
                    },
                },
                data:this.onlineSarr
            }],
            series: [{
                    name: '值',
                    type: 'bar',
                    zlevel: 1,
                    itemStyle: {
                        normal: {
                            barBorderRadius: 30,
                            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                                offset: 0,
                                color: 'rgb(57,89,255,1)'
                            }, {
                                offset: 1,
                                color: 'rgb(46,200,207,1)'
                            }]),
                        },
                    },
                    barWidth: 20,
                    data: this.onlineSarr
                },
                {
                    name: '背景',
                    type: 'bar',
                    barWidth: 20,
                    barGap: '-100%',
                    data: salvProMax,
                    itemStyle: {
                        normal: {
                            color: 'rgba(24,31,68,1)',
                            barBorderRadius: 30,
                        }
                    },
                },
            ]
        };
        myChart.setOption(option);
        getMaxPlaySongs().then(res =>{
                    console.log(res)
                    for(let i = 0; i<res.length; i++){
                        this.onlineXarr[i] = res[i].name;
                        this.onlineSarr[i] = res[i].value;
                    }
                    myChart.setOption(option);
                    window.addEventListener('resize', () => {
                        if (myChart) {
                            myChart.resize();
                        }
                    }); 
                })
            
        },
    songChart() {
      // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById("song"));
        
        // 测试数据
        let bgColor = '#fff';
        let data = {
            xData: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11','12'],
            yData: [100, 200, 300, 300, 500, 600, 700, 800, 900, 900, 1000,1300]
        }
        let option1 = {
            tooltip: {},
            xAxis: {
                data: [],
            },
            yAxis: {},
            series: [{
                type: 'bar',
                data: [],
            }]
        };

         // 第一次最简单的初始化ehars图
        myChart.setOption(option1);

        // 个性化图表的样式
        let option = {
                backgroundColor: bgColor,
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: [
                    {
                        top: 30,
                        bottom: 70,
                        left: 30
                    },
                    {
                        height: 30,
                        bottom: 0
                    }
                ],
                xAxis: [{
                    nameLocation: 'middle',
                    type: 'category',
                    //data: data.xData,
                    data: this.songXarr,
                    gridIndex: 0,
                    axisLabel: {
                        interval: 0,
                         color: '#333',
                        formatter: function (value) {
                        //x轴的文字改为竖版显示
                        var str = value.split("");
                        return str.join("\n");
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#e7e7e7'
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: '#e7e7e7'
                        }
                    },
                    zlevel: 2
                },
                {
                    type: 'category',
                    gridIndex: 1,
                    axisLine: {
                        show: false
                    },
                    zlevel: 1
                } 
                ],
                yAxis: [{
                    name:'评论次数',
                    type: 'value',
                    gridIndex: 0,
                    minInterval: 1,  // 显示整数
                    axisLabel: {
                        color: '#333'
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dashed'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },{
                    type: 'value',
                    gridIndex: 1,
                    axisLabel: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    }
                }],
                series: [{
                   // data: data.yData,
                   data: this.songSarr,
                    type: 'bar',
                    barWidth: 35,
                    label: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#555'
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: (params) => {
                                let colors = ['#4150d8', '#28bf7e', '#ed7c2f', '#f2a93b', '#f9cf36', '#4a5bdc', '#4cd698', '#f4914e', '#fcb75b', '#ffe180', '#b6c2ff', '#96edc1']
                                return colors[params.dataIndex]
                            }
                        }
                    },
                    xAxisIndex: 0,
                    yAxisIndex: 0
                },
                ]
            };
           
           // 动态渲染数据
            getMaxComment().then(res =>{
                console.log(res)
                for(let i = 0; i<res.length; i++){
                     this.songXarr[i] = res[i].name;
                     this.songSarr[i] = res[i].value;
                }
                myChart.setOption(option);
                window.addEventListener('resize', () => {
                    if (myChart) {
                        myChart.resize();
                    }
                }); 
            })
           
   },
   
},
 
  // 视图渲染完毕
  mounted() {
    this.drawChart();
    this.songChart();
  },

}
</script>

<style scoped>
.container{
  background-color: '#041730'
}
.header{
  text-align: center; /*让div内部文字居中*/
	border-radius: 20px;
	height: 50px;
	margin: 20px;
	top: 10px;
	left: 0;
	right: 0;
	bottom: 0;
}
</style>