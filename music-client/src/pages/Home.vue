<template>
  <div class="home">
    <!--轮播图-->
    <swiper/>
    <!--热门歌单/歌手-->
    <div class="section" v-for="(item, index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from '../components/Swiper'
import ContentList from '../components/ContentList'
import { getSongListByHot } from '../api/system/songlist'
import { getSingerHot } from '../api/system/singer'
import { getUserInfo } from '../api/system/sysuser'
export default {
  name: 'home',
  components: {
    Swiper,
    ContentList
  },
  data () {
    return {
      songsList: [
        {name: '歌单', list: []},
        {name: '歌手', list: []}
      ],
      username: '',
      avator:'',

    }
  },
  created () {
    // 获取歌单列表
    this.getSongList('songList')
    // 获取歌手列表
    this.getSinger('singer')
   
   // 获取用户信息，并存储
   this.getUser()
  },
  methods: {
    getSongList (path) {
      getSongListByHot()
        .then(res => {
        //  console.log(res)
          this.songsList[0].list = res.slice(0, 10)
        })
        .catch(err => {
          console.log(err)
        })
    },

    getSinger () {
      getSingerHot().then(res => {
        this.songsList[1].list = res.slice(0, 10)
      })
        .catch(err => {
          console.log(err)
        })
    },
     // 根据用户名反查用户的详细信息并存储。
     getUser(){
       this.username = localStorage.getItem("username")
       if(this.username != null){
           getUserInfo().then(res =>{
         this.setdata(res) 
      })
    }
    },

    setdata(item){
      
      this.$store.commit('setUserId', item.id)
      this.$store.commit('setUsername', item.username)
      this.$store.commit('setAvator', item.avator)

      // 存储用户信息
      console.log(item)
      localStorage.setItem("avator",item.data.avator)
      localStorage.setItem("sex",item.data.sex)
      localStorage.setItem("birth",item.data.birth)
      localStorage.setItem("introduction",item.data.introduction)
      localStorage.setItem("location",item.data.location)
      localStorage.setItem("userId",item.data.id)
   },
    
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
