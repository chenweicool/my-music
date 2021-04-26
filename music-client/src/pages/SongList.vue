<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li
        v-for="(item, index) in songStyle"
        :key="index"
        :class="{active: item.name === activeName}"
        @click="handleChangeView(item.name)">
        {{item.name}}
      </li>
    </ul>
    <div class="song-content">
      <content-list :contentList="albumDatas"></content-list>
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
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { mapGetters } from 'vuex'
import { songStyle } from '../assets/data/songList'
import { getSongListByPage } from '../api/system/songlist'

export default {
  name: 'song-list',
  components: {
    ContentList
  },
  data () {
    return {
      songStyle: [], // 歌单导航栏类别
      activeName: '全部歌单',
      albumDatas: [], // 歌单
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
        },
    }
  },
  computed: {
    ...mapGetters([
      'songsList'
    ]),
    // // 计算当前表格中的数据
    // data () {
    //   return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    // }
  },
  mounted () {
    this.songStyle = songStyle
    this.handleChangeView('全部歌单')
  },
  methods: {
    // // 获取当前页
    // handleCurrentChange (val) {
    //   this.currentPage = val
    // },
    // 获取歌单
    // handleChangeView: function (name) {
    //   this.activeName = name
    //   this.albumDatas = []
    //   if (name === '全部歌单') {
    //     this.getSongList(this.cur_page)
    //   } else {
    //     this.getSongListOfStyle(name)
    //   }
    // },
     getData(){
        getSongListByPage(this.pagination.pageNum,this.pagination.pageSize)
          .then(res => {
           //  console.log(res)
             this.setData(res)
          })
      },
       // 设置数据信息
      setData(res) {
          this.albumDatas = res.records
          this.pagination.total = res.total;
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
    // 通过类别获取歌单
    getSongListOfStyle (style) {
      getSongListOfStyle(style)
        .then(res => {
          this.currentPage = 1
          this.albumDatas = res
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>
