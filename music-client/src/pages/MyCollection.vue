<template>
  <div class="song-list">
    <ul class="song-list-header">
    </ul>
    <div class="song-content">
      <content-list :contentList="tableData"></content-list>
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
// import { songStyle } from '../assets/data/songList'
import { getSongListByUserId} from '../api/system/collection'

export default {
  name: 'song-list',
  components: {
    ContentList
  },
  data () {
    return {
      songStyle: [], // 歌单导航栏类别
      activeName: '全部歌单',
      tableData: [], // 存储个歌单的数据
      type: 0,
      userId:'',
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
  },
     created () {
   // this.songStyle = songStyle
   // this.handleChangeView('全部歌单')
   this.getData()
  },
  methods: {
    // 获取我收藏的歌单信息
     getData(){
        this.userId = localStorage.getItem("userId")
        getSongListByUserId(this.pagination.pageNum,this.pagination.pageSize,this.userId,this.type)
          .then(res => {
             console.log(res)
             this.setData(res)
          })
      },
       // 设置数据信息
      setData(res) {
          this.tableData = res.records
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

    // 已完成
    // getSongListOfStyle (style) {
    //   getStyleByPage(this.pagination.pageNum,this.pagination.pageSize,style)
    //     .then(res => {
    //       this.setData(res)
    //     })
    //     .catch(err => {
    //       console.log(err)
    //     })
    // }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>
