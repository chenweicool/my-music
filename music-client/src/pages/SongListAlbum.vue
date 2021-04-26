<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(singers.pic) alt="">
      </div>
      <div class="album-info">
        <h2>简介：</h2>
        <span>
          {{singers.introduction}}
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <p>{{singers.title}}</p>
      </div>
      <!--评分-->
      <div class="album-score">
        <div>
          <h3>歌单评分：</h3>
          <div>
            <el-rate v-model="value5" disabled></el-rate>
          </div>
        </div>
        <span>{{value5 * 2}}</span>
        <div>
          <h3>评价：</h3>
          <div @click="pushValue()">
            <el-rate v-model="value3" show-text allow-half></el-rate>
          </div>
        </div>
      </div>
      <!--歌曲-->
      <div class="songs-body">
        <album-content :songList="listOfSongs">
          <template slot="title">歌单</template>
        </album-content>
        <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            layout="total, sizes, prev, pager, next, jumper"
            :current-page="pagination.pageNum"
            :page-size="pagination.pageSize"
            :total="pagination.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageNumChange"
            background
            style="float: right;margin-bottom: 10px">
          </el-pagination>
        <!-- <comment :playId="songListId" :type="1"></comment> -->
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
//import Comment from '../components/Comment'
import { getRankOfSongListId, setRank, getSongOfId, } from '../api/index'
import { getSongOfSongListId} from '../api/system/song'

export default {
  name: 'song-list-album',
  components: {
    AlbumContent,
    Comment
  },
  data () {
    return {
      songLists: [],
      singers: {},
      count: 0, // 点赞数
      songListId: '', // 歌单ID
      value3: 0,
      value5: 0,
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
      },
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'tempList', // 单个歌单信息
      'listOfSongs', // 存放的音乐
      'userId', // 用户ID
      'avator' // 用户头像
    ])
  },
  created () {
    this.songListId = this.tempList.id // 将歌单的id传入到这里
    this.singers = this.tempList
    this.getSongBySongList() // 获取歌单里面的歌曲ID
    this.getRank(this.songListId) // 获取评分
  },
  mixins: [mixin],
  methods: {
    // 歌单中的歌曲进行展示
    // getSongId () {
    //   getListSongOfSongId(this.songListId)
    //     .then(res => {
    //       // 获取歌单里的歌曲信息
    //       for (let item of res) {
    //         this.getSongList(item.songId)
    //       }
    //       this.$store.commit('setListOfSongs', this.songLists)
    //     })
    //     .catch(err => {
    //       console.log(err)
    //     })
    // },
    // 获取单里的歌曲
    getSongBySongList () {
       // 查询歌单中具有的歌曲信息
         getSongOfSongListId(this.pagination.pageNum,this.pagination.pageSize,this.songListId).then((res) => {
            console.log('所属歌单信息', res.records)
             this.setData(res)
             this.$store.commit('setListOfSongs', this.songLists);
            }).catch(err => {
            console.log(err)
          })
    },

    // 设置数据信息
    setData(res) {
          this.songLists = res.records
          this.pagination.total = res.total;
    },

    // 获取评分
    getRank (id) {
      getRankOfSongListId(id)
        .then(res => {
          this.value5 = res / 2
        })
        .catch(err => {
          console.log(err)
        })
    },

     // 分页的设置
    handlePageSizeChange(val){
      this.pagination.pageSize = val;
      this.getData();
    },
    handlePageNumChange(val){
      this.pagination.pageNum = val;
    this.getData();
    }, 

    // 提交评分
    pushValue () {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('songListId', this.songListId)
        params.append('consumerId', this.userId)
        params.append('score', this.value3 * 2)
        setRank(params)
          .then(res => {
            if (res.code === 1) {
              this.getRank(this.songListId)
              this.notify('评分成功', 'success')
            } else {
              this.notify('评分失败', 'error')
            }
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.value3 = null
        this.notify('请先登录', 'warning')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list-album.scss';
</style>
