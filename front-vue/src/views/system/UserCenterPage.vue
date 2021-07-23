<template>
    <div>
     
              <el-button type="primary" 
                       plain  @click="myCollection()"  >用户收藏歌单</el-button>
              <el-button type="primary"  plain @click="myComment()" >我的评论</el-button>
              <el-button type="primary"  plain @click="myHistory()" >我的浏览历史</el-button>
              <el-button type="primary"  plain @click="myCreateSongList()">我的创建歌单</el-button>
              <!-- <el-button type="primary"  plain @click="myListenStatistic()">我的听歌统计</el-button> -->
    
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
      this.userId = this.$route.query.userId;
      console.log(this.userId)
    },

    methods:{

      //  我的收藏
        myCollection(){
              this.$router.push({path: '/home/myCollection', query: {userId: this.userId}})
        },
        
        // 我的评论
        myComment(){
             this.$router.push({path: '/home/myComment', query: {userId: this.userId}})
        },

        // 我创建的歌单
         myCreateSongList(){
            this.$router.push({path: '/home/myCreateSongList', query: {userId: this.userId}})
        },

        // 我浏览历史
        myHistory(){
           this.$router.push({path: '/home/myHistory', query: {userId: this.userId}})
        },
       
       // 我的歌曲统计信息
        myListenStatistic(){
             this.$router.push({path: '/home/myListenStatistic', query: {userId: this.userId}})
        }

    }
}
</script>