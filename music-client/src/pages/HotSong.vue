<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(avator) alt="出现网络问题">
      </div>
       <div class="album-info">
        <span>
         最困难的事情就是认识<br>
         自己。
         <br>——希腊
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="songs-body">
        <album-content :songList="songList">
          <template slot="title">热门歌曲</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
import {getHotSong} from '../api/system/song'
import * as dateUtils from  '../api/data'
import { Loading } from 'element-ui'

export default {
  name: 'my-music',
  components: {
    AlbumContent
  },
  mixins: [mixin],
  data () {
    return {
      userId:'',
      avator:'',
      songList: [] ,// 推荐的歌曲
    }
  },
  computed: {
    ...mapGetters([
      'id',
      'listOfSongs' // 存放的音乐
    ])
  },
  created () {
    this.getCollection(this.userId)
  },
  methods: {

    // 收藏的歌曲ID
    getCollection () {
     this.userId = localStorage.getItem("userId")
     this.avator = localStorage.getItem("avator")
      getHotSong()
        .then(res => {
         // console.log(res)
          this.songList = res
          this.$store.commit('setListOfSongs', this.songList)
        })
        .catch(err => {
          console.log(err)
        })
    },
    
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>
