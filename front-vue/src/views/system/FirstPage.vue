<template>
  <div id="box" class="container">
    <div class = "header">
      <p>综和信息分析</p>
    </div>
    <div>
        <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <div class="grid-cont-right">
              <div class="grid-num">{{userCount}}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-2">
            <div class="grid-cont-right">
              <div class="grid-num">{{songCount}}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-3">
            <div class="grid-cont-right">
              <div class="grid-num">{{singerCount}}</div>
              <div>歌手数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-4">
            <div class="grid-cont-right">
              <div class="grid-num">{{songListCount}}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    </div>
  <div style="display:flex; width: 100%;margin: 10px;">
     <div class="userbox" style="flex:1;width: 100%;" >
      <div style="text-align: center;">用户信息统计</div>
      <br/>
      <div id="allUser" style="width: 100%;height:400px;"></div>
    </div>

    <div class="online" style="flex:1;width: 100%;">
      <div style="text-align: center; ">歌单类型统计</div> <br/>
      <div id="online" style="width: 100%;height:400px;"></div>
    </div>
  </div>
    
  </div>
</template>

<script>
// import {statisticMobile,statisticOrigin,statisticHour} from '../api/index'
import {getTotalUsers,getUsersex,getTotalSongs,getTotalSingers,getTotalSongList,getCategoryNumber} from  '../../api/system/statistic'
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
          originNumber: '',    // 用户性别总的数量
           userCount: 0,   // 用户总的数量
          songCount: 0,    // 歌曲总数量
          singerCount: 0,  // 歌手的总数量
          songListCount: 0  // 歌单的总数量
      }
   },

   created(){
        this.getTotalUser(),
        this.getTotalSong(),
        this.getTotalSinger(),
        this.getTotalist()
   },
  methods: {
    
    // 得到总的用户数量
    getTotalUser(){
         getTotalUsers().then(res =>{
             this.userCount = res;
         })
    },

    getTotalSong(){
       getTotalSongs().then(res =>{
           this.songCount = res;
       })
    },

    getTotalSinger(){
       getTotalSingers().then(res =>{
           this.singerCount = res;
       })
    },

     getTotalist(){
       getTotalSongList().then(res =>{
           this.songListCount = res;
       })
    },

   // 用户的性别比例
    drawChart() {

      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("allUser"));   
      // 最简单的饼图的初始值
      let option = {
           tooltip: {},
            series: [{
            type: 'pie',
            data: [],
            }]
       };

        // 第一次的初始化
      myChart.setOption(option);

       // 配置个性化的使用
      let bgColor = '#fff';
      let title = '总量';
      let color = ['#0E7CE2', '#FF8352', '#E271DE', '#F8456B', '#00FFFF', '#4AEAB0'];
       
       // 测试数据
      let echartData = [{
              name: "男",
              value: "3720"
          },
          {
              name: "女",
              value: "2920"
          },
          {
              name: "组合",
              value: "2200"
          },
          {
              name: "未知",
              value: "1420"
          }
      ];
      
      // 计算数据的总数
      let formatNumber = function(num) {
          let reg = /(?=(\B)(\d{3})+$)/g;
          return num.toString().replace(reg, ',');
      }

        let total = this.originData.reduce((a, b) => {
            return a + b.value * 1
        }, 0);

        let option1 = {
            backgroundColor: bgColor,
            color: color,
            title: [{
                // text: '{name|' + title + '}\n{val|' + formatNumber(total) + '}',
                text: '用户性别统计',
                top: 'center',
                left: 'center',
                textStyle: {
                    rich: {
                        name: {
                            fontSize: 14,
                            fontWeight: 'normal',
                            color: '#666666',
                            padding: [10, 0]
                        },
                        val: {
                            fontSize: 32,
                            fontWeight: 'bold',
                            color: '#333333',
                        }
                    }
                }
            },{
                text: '单位：人数',
                top: 0,
                left: 20,
                textStyle: {
                    fontSize: 14,
                    color:'#666666',
                    fontWeight: 400
                }
            }],
            series: [{
                type: 'pie',
                radius: ['45%', '60%'],
                center: ['50%', '50%'],
               //data: echartData,    
               data:this.originData,
                hoverAnimation: false,
                itemStyle: {
                    normal: {
                        borderColor: bgColor,
                        borderWidth: 2
                    }
                },
                labelLine: {
                    normal: {
                        length: 20,
                        length2: 120,
                        lineStyle: {
                            color: '#e6e6e6'
                        }
                    }
                },
                label: {
                    normal: {
                        formatter: params => {
                            return (
                                '{icon|●}{name|' + params.name + '}{value|' +
                                formatNumber(params.value) + '}'
                            );
                        },
                        padding: [0 , -100, 25, -100],
                        rich: {
                            icon: {
                                fontSize: 16
                            },
                            name: {
                                fontSize: 14,
                                padding: [0, 10, 0, 4],
                                color: '#666666'
                            },
                            value: {
                                fontSize: 18,
                                fontWeight: 'bold',
                                color: '#333333'
                            }
                        }
                    }
                },
            }]
        };

        // 实现数据的绑定
        getUsersex().then(res =>{
            //数据库的数据进行绑定
                let num = 0;
            for(let i = 0 ; i < res.length; i++){
                    this.originData[i] = res[i];
                    num += res[i].value;                 
            }
                
                console.log(num);
                this.originNumber = num;
                myChart.setOption(option1);
                window.addEventListener('resize', () => {
                    if (myChart) {
                        myChart.resize();
                    }
                });
        })
        //  myChart.setOption(option1);
        //   window.addEventListener('resize', () => {
        // 			if (myChart) {
        // 				myChart.resize();
        // 			}
        // 		});
    },

    // 用户新增人数
    lineChart() {
      // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById("online"));
        
        // 测试数据
       let option = {
           tooltip: {},
            series: [{
            type: 'pie',
            data: [],
            }]
       };

        // 第一次的初始化
      myChart.setOption(option);

       // 配置个性化的使用
      let bgColor = '#fff';
      let title = '总量';
      let color = ['#0E7CE2', '#FF8352', '#E271DE', '#F8456B', '#00FFFF', '#4AEAB0'];
       
       // 测试数据
      let echartData = [{
              name: "男",
              value: "3720"
          },
          {
              name: "女",
              value: "2920"
          },
          {
              name: "组合",
              value: "2200"
          },
          {
              name: "未知",
              value: "1420"
          }
      ];
      
      // 计算数据的总数
      let formatNumber = function(num) {
          let reg = /(?=(\B)(\d{3})+$)/g;
          return num.toString().replace(reg, ',');
      }

        let total = this.originData.reduce((a, b) => {
            return a + b.value * 1
        }, 0);

        let option1 = {
            backgroundColor: bgColor,
            color: color,
            title: [{
                // text: '{name|' + title + '}\n{val|' + formatNumber(total) + '}',
                text: '歌单类型统计',
                top: 'center',
                left: 'center',
                textStyle: {
                    rich: {
                        name: {
                            fontSize: 14,
                            fontWeight: 'normal',
                            color: '#666666',
                            padding: [10, 0]
                        },
                        val: {
                            fontSize: 32,
                            fontWeight: 'bold',
                            color: '#333333',
                        }
                    }
                }
            },{
                text: '单位：数量',
                top: 0,
                left: 20,
                textStyle: {
                    fontSize: 14,
                    color:'#666666',
                    fontWeight: 400
                }
            }],
            series: [{
                type: 'pie',
                radius: ['45%', '60%'],
                center: ['50%', '50%'],
               //data: echartData,    
               data:this.originData,
                hoverAnimation: false,
                itemStyle: {
                    normal: {
                        borderColor: bgColor,
                        borderWidth: 2
                    }
                },
                labelLine: {
                    normal: {
                        length: 20,
                        length2: 120,
                        lineStyle: {
                            color: '#e6e6e6'
                        }
                    }
                },
                label: {
                    normal: {
                        formatter: params => {
                            return (
                                '{icon|●}{name|' + params.name + '}{value|' +
                                formatNumber(params.value) + '}'
                            );
                        },
                        padding: [0 , -100, 25, -100],
                        rich: {
                            icon: {
                                fontSize: 16
                            },
                            name: {
                                fontSize: 14,
                                padding: [0, 10, 0, 4],
                                color: '#666666'
                            },
                            value: {
                                fontSize: 18,
                                fontWeight: 'bold',
                                color: '#333333'
                            }
                        }
                    }
                },
            }]
        };

        // 实现数据的绑定
        getCategoryNumber().then(res =>{
            //数据库的数据进行绑定
                let num = 0;
            for(let i = 0 ; i < res.length; i++){
                    this.originData[i] = res[i];
                    num += res[i].value;                 
            }
                
                console.log(num);
                this.originNumber = num;
                myChart.setOption(option1);
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
 .grid-content {
    display: flex;
    align-items: center;
    height: 100px;
  }

  .grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
  }

  .grid-num {
    font-size: 30px;
    font-weight: bold;
  }
</style>