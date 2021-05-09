<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src=attachImageUrl(avator) alt="出现网络问题">
      </div>
      <ul class="album-info">
        <li>昵称： {{username}}</li>
        <li>性别： {{userSex}}</li>
        <li>生日： {{birth}}</li>
        <li>故乡： {{location}}</li>
        <li><div class="album-title">个性签名: {{introduction}}</div></li>
      </ul>
    </div>
    <div class="album-content">
      <div class="songs-body">
        <album-content :songList="collectList">
          <template slot="title">我喜欢的音乐</template>
        </album-content>
      </div>
    </div>
     <div class="pagination">
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
      </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
import { getCollectionOfUser, getUserOfId, getSongOfId } from '../api/index'
import {getSongListByUserId} from '../api/system/collection'
import * as dateUtils from  '../api/data'

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
      username: '',
      userSex: '',
      birth: '',
      location: '',
      introduction: '',
      type: 1,
      collectList: [] ,// 收藏的歌曲
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
      },
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
    this.getCollection(this.userId)
  },
  methods: {
    getMsg () {
         this.userId = localStorage.getItem("userId")
         this.username = localStorage.getItem("username");
         this.avator = localStorage.getItem("avator");
         this.getuserSex(localStorage.getItem("sex"))
         this.location = localStorage.getItem("location");
         this.introduction = localStorage.getItem("introduction")
         this.changeBirth(localStorage.getItem("birth"))
    },

    // 收藏的歌曲ID
    getCollection (userId) {
      getSongListByUserId(this.pagination.pageNum,this.pagination.pageSize,this.userId,this.type)
        .then(res => {
        //  console.log(res.records)
          this.collectList = res.records
          this.$store.commit('setListOfSongs', this.collectList)
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
    
    // 辅助的工具类
    getuserSex (value) {
      if(value == 1){
        this.userSex = '男'
        console.log(this.userSex)
      }else if(value == 0){
        this.userSex = '女'
      }else if(value == 2){
        this.userSex = '组合'
      }else{
        this.userSex = '未知'
      }
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
