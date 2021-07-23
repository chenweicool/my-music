<template>
  <div id="box" class="container">
    <div class = "header">
      <p>歌手信息分析</p>
    </div>
    
  <div style="display:flex; width: 100%;margin: 10px ">
      <div class="mobile" style="flex:1;width: 100%;' '">
      <div style="text-align: center;">歌手组合分析</div>

      <div id="singer" style="width: 100%;height:400px;"></div>
    </div>

    <div class="online" style="flex:1;width: 100%;">
      <div style="text-align: center;">歌手拥有歌曲统计</div>
      <div id="online" style="width: 100%;height:400px;"></div>
    </div>
   </div>
    
    
  </div>
</template>

<script>
import {getSexSingers,getMaxSongOfSingers} from  '../../api/system/statistic'
import echarts from 'echarts'
export default {
   name: "infoPage",
   data (){
      return{
          onlineXarr: [],    //存放用户在线时长的纵坐标
          onlineSarr:[] ,    // 用户在线时长的每个坐标值
          
          loginXarr:[],   // 用户登陆的时间的纵坐标
          loginSarr:[],   // 登陆时间点的具体坐标

          pieData:[],   // 用户的手机类型
          originData:[] , // 用户的数据来源的图形
          originNumber: '',    // 下载商品的用户的总的数量
      }
   },
  methods: {
 
   // 歌手性别情况
    mobileChart() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("singer"));
           
      let option = {
            tooltip: {},
            series: [{
                type: 'pie',
                data: [],
            }]
        };
      // 初始化ehcars
      myChart.setOption(option);
  
      // 进行数据的渲染
      getSexSingers().then(res =>{
            for(let i = 0; i < res.length;i++){
                 this.pieData[i] = res[i];
                console.log(this.pieData[i]['value']);
            }
          // console.log(this.pieData);
           myChart.setOption(option1);
           window.addEventListener('resize', () => {
				if (myChart) {
					myChart.resize();
				}
			});
        });

       // 设置新的值
      let index = 0;
      let bgColor = '#fff';
      var colorList = ['#73DDFF', '#73ACFF', '#FDD56A', '#FDB36A', '#FD866A', '#9E87FF', '#58D5FF']
      function fun() {
        var timer = setInterval(function() {
         myChart.dispatchAction({
            type: 'hideTip',
            seriesIndex: 0,
            dataIndex: index
        });
        // 显示提示框
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: index
        });
        // 取消高亮指定的数据图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: index == 0? 5 : index -1
        });
         myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: index
        });
        index++;
        if (index > 5) {
            index = 0;
        }
      },3000)
     }

      fun()
      setTimeout(function() {fun()}, 5000);

      let option1 = {
      backgroundColor: bgColor,
      title: {
          text: '歌手类别',
          x: 'center',
          y: 'center',
          textStyle: {
              fontSize: 17
          }
      },
      tooltip: {
          trigger: 'item'
      },
      series: [{
          type: 'pie',
          radius: ['45%', '60%'],
          center: ['50%', '50%'],
          clockwise: true,
          avoidLabelOverlap: true,
          hoverOffset: 15,
          itemStyle: {
              normal: {
                  color: function(params) {
                      return colorList[params.dataIndex]
                  }
              }
          },
          label: {
              show: true,
              position: 'outside',
              formatter: '{a|{b}：{d}%}\n{hr|}',
              rich: {
                  hr: {
                      backgroundColor: 't',
                      borderRadius: 3,
                      width: 3,
                      height: 3,
                      padding: [3, 3, 0, -12]
                  },
                  a: {
                      padding: [-30, 15, -20, 15]
                  }
              }
          },
          labelLine: {
              normal: {
                  length: 20,
                  length2: 30,
                  lineStyle: {
                      width: 1
                  }
              }
          },
          data: this.pieData,
       }]
      };
   },

  // 歌手拥有歌曲数量显示
  lineChart() {
      // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById("online"));
        
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
                    data: this.onlineXarr,
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
                    name:'歌曲数量',
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
                   data: this.onlineSarr,
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
            getMaxSongOfSingers().then(res =>{
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

   },
  // 视图渲染完毕
  mounted() {
    this.mobileChart();
    this.lineChart();
  },
}
</script>

<style scoped>
.container{
  background-color: '#fff'
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