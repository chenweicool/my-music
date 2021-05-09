<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(avator) alt="出现网络问题">
      </div>
      <ul class="album-info">
        <li><div class="album-title">个性签名: {{introduction}}</div></li>
      </ul>
    </div>
    <div class="album-content">
      <div class="songs-body">
        <album-content :songList="songList">
          <template slot="title">我的最近浏览</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
import {getUserHistory,getHistorySong} from '../api/system/song'
import * as dateUtils from  '../api/data'
import qs from	'qs'

export default {
  name: 'my-music',
  components: {
    AlbumContent
  },
  mixins: [mixin],
  data () {
    return {
      userId:'',
      avator: '',
      introduction: '',
      type: 1,
      songIds: [] , // 浏览历史歌曲id 
      songList:[],  //浏览历史的歌曲
    }
  },
  computed: {
    ...mapGetters([
     // 'userId',
      'id',
      'listOfSongs' // 存放的音乐
    ])
  },
  created () {
    this.getMsg()
    this.getUserHistory(this.userId)
  //  this.getHistortSong()
  },
  methods: {
    getMsg () {
         this.userId = localStorage.getItem("userId")
         this.username = localStorage.getItem("username");
         this.avator = localStorage.getItem("avator");
         this.location = localStorage.getItem("location");
         this.introduction = localStorage.getItem("introduction")
    },

    // 查询用户的历史歌曲的id 信息
    getUserHistory (userId) {
      getUserHistory(this.userId)
        .then(res => {
          console.log(res)
          for(var i =  res.length-1 ;i >= 0 ;i-- ){
              this.songIds[i] = res[i].songId;
          }
          console.log(this.songIds)
            // 查询具体的歌曲信息
           getHistorySong(this.songIds).then(res=>{
             this.songList = res.data;
             
             this.$store.commit('setListOfSongs', this.songList)
            console.log(this.songList)
         })

        })
        .catch(err => {
          console.log(err)
        })
    },

    // 改变日期的类的信息
    changeBirth(val){
       let date = new Date(val);
       this.birth =  dateUtils.formatDate(date,'yyyy-MM-dd')
     },

     
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>
