<template>
  <div class="singer">
    <ul class="singer-header">
      <li
        v-for="(item, index) in singerStyle"
        :key="index"
        :class="{active: item.name === activeName}"
        @click="handleChangeView(item)">
        {{item.name}}
      </li>
    </ul>
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
</template>

<script>
import ContentList from '../components/ContentList'
import { singerStyle } from '../assets/data/singer'
import { getSingerByPage,getSingerBySex} from '../api/system/singer'

export default {
  name: 'singer',
  components: {
    ContentList
  },
  data () {
    return {
      singerStyle: [], // 歌手导航栏类别
      activeName: '全部歌手',
      pageSize: 15, // 页数
      currentPage: 1, // 当前页
      albumDatas: [],
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
      },
    }
  },
  // computed: {
  //   // 计算当前表格中的数据
  //   data () {
  //     return this.albumDatas.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
  //   }
  // },
  created () {
    this.singerStyle = singerStyle
    this.getData()
  },
  methods: {
    handleChangeView (item) {
      this.activeName = item.name
      this.albumDatas = []
      if (item.name === '全部歌手') {
        this.getData()
      } else {
        this.getSingerSex(item.type)
      }
    },
    // 获取所有歌手
   getData(){
        getSingerByPage(this.pagination.pageNum,this.pagination.pageSize)
          .then(res => {
            console.log(res)
             this.setData(res)
          })
      },

    // 通过性别对歌手分类
    getSingerSex (sex) {
        getSingerBySex(this.pagination.pageNum,this.pagination.pageSize,sex).then(res =>{   
           // console.log(res);
            this.setData(res)
           }).catch(err => {
                this.$message({message: err.message, type: 'error'});
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
      this.getData()
    },
    handlePageNumChange(val){
      this.pagination.pageNum = val;
      this.getData()
    }, 

 }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer.scss';
</style>
