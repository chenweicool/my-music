<template>
    <div>
     
     
    
              <el-button type="primary" 
                       plain  @click="myCollection()"  >用户收藏歌单</el-button>
              <el-button type="primary"  plain @click="myComment()" >我的评论</el-button>
              <el-button type="primary"  plain @click="myHistory()" >我的浏览历史</el-button>
              <el-button type="primary"  plain @click="myCreateSongList()">我的创建歌单</el-button>
              <el-button type="primary"  plain @click="myListenStatistic()">我的听歌统计</el-button>
    
    </div>
</template>

<script>

export default {

    data(){
        return{
          userId:'',
        }
    },

    created(){
      this.userId = this.$router.query.userId;
    },

    methods:{
        myCollection(){
             
        },
        
        myComment(){

        },
        myHistory(){

        },
        myCreateSongList(){

        },

        myListenStatistic(){
            
        }

    }
}
</script>