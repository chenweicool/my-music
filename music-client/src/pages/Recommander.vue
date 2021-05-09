<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(avator) alt="出现网络问题">
      </div>
       <div class="album-info">
        <span>
          一纸寒凉，一纸秋水,<br>
          一纸悲凉
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="songs-body">
        <album-content :songList="songList">
          <template slot="title">今日推荐</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
import {getRecommendSong} from '../api/system/song'
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
     let params = new URLSearchParams()
     params.append("userId",this.userId)
      getRecommendSong(params)
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
