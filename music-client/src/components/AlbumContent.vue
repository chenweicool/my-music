<template>
  <div class="content">
    <h1 class="title">
      <slot name="title"></slot>
    </h1>
    <hr>
    <ul>
      <li class="list-title">
        <div class="song-item">
          <span class="item-index"></span>
          <span class="item-title">歌曲名</span>
          <span class="item-name">艺人</span>
          <span class="item-intro">专辑</span>
        </div>
      </li>
      <li class="list-content" v-for="(item, index) in songList" :key="index">
         <div style="float:right;">
           <el-button type="success" size="mini">添加收藏</el-button>
        </div>
        <div class="song-item" :class="{'is-play': id === item.id}"  @click="toplay(item.id, item.url, item.pic, index, item.songName, item.lyric)">
          <span class="item-index">
            <span v-if="id !== item.id">{{index + 1}}</span>
            <svg v-if="id === item.id" class="icon" aria-hidden="true">
              <use xlink:href="#icon-yinliang"></use>
            </svg>
          </span>
          <span class="item-title">{{replaceFName(item.songName)}}</span>
          <span class="item-name">{{replaceLName(item.songName)}}</span>
          <span class="item-intro">{{item.introduction}}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'

export default {
  name: 'album-content',
  mixins: [mixin],
  props: [
    'songList'
  ],
  computed: {
    ...mapGetters([
      'id' // 音乐ID
    ])
  },

   data(){
     return{
         mySongListArr:[],
         mysongList:{
           id:'',
           title:'',
         }
     }
  },
  created(){
   // console.log( JSON.parse(localStorage.getItem("mySongList")))
    this.getmysongList()
  }, 
   
   //添加歌曲的方法
  methods: {
    getmysongList(){
        this.mySongListArr = JSON.parse(localStorage.getItem("mySongList"))
        console.log(this.mySongListArr[0].id);
     },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
</style>
