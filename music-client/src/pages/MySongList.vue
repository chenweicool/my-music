<template>
  <div class="song-list">
    <ul class="song-list-header">
    </ul>
    <div style="float:right;padding: 10px,20px ">
         <el-button type="primary" icon="el-icon-edit" circle @click="handleAdd('新增歌单')"></el-button>
    </div> 
    <div class="song-content">
      <content-list :contentList="tableData"></content-list>
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

      <!-- 新增歌单 -->
     <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible"
              >
      <el-form :model="dialogForm" ref="dialogForm"
               :rules="dialogFormRules" label-width="20%">

        <el-row :gutter="20">
            <el-form-item label="歌单标题"    prop="title">
              <el-input  v-model="dialogForm.title" autocomplete="off" placeholder="请输歌单标题，限制20个字"></el-input>
            </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addMySongList()" size="mini" type="primary">确 定</el-button>
        <el-button @click="handleCloseDialog" size="mini">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { mapGetters } from 'vuex'
// import { songStyle } from '../assets/data/songList'
import { getSongListByUserId} from '../api/system/collection'
import {addSongList,getMySongList} from '../api/system/songlist'

export default {
  name: 'song-list',
  components: {
    ContentList
  },
  data () {
    return {
      songStyle: [], // 歌单导航栏类别
      activeName: '全部歌单',
      tableData: [], // 存储歌单的数据
      type: 0,
      userId:'',
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
      },
    dialogFormVisible: false,
    dialogTitle:"",
    dialogRefName:"dialogForm",
    dialogForm: {
        title: '',
        pic: '',
        style:'',
        introduction:'',
        type: '',  // 歌单的类型  0 是系统创建  1 是用户自己创建
        userId:'',
     },
      dialogFormRules: {
      title: [
        {required: true, message: '请输入歌单标题', trigger: 'blur'},
       ],
    }
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
        getMySongList(this.pagination.pageNum,this.pagination.pageSize,this.userId)
          .then(res => {
             //console.log(res)
             this.setData(res)
             
          })
      },
       // 设置数据信息
      setData(res) {
          this.tableData = res.records
          // 存储我创建的歌单信息
          localStorage.setItem("mySongList",JSON.stringify(res.records))
          this.pagination.total = res.total;
      },

      addMySongList(){
          this.dialogForm.userId = this.userId;
          addSongList(this.dialogForm).then(res => {
          this.$message({message: res, type: 'success'});
           this.getData()
          this.handleCloseDialog();
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

      // 新增歌单
      handleAdd(dialogTitle){
        this.dialogFormVisible = true;
        this.dialogTitle = dialogTitle
        this.resetDialogForm()
      },
      handleCloseDialog(){
        //resetFields就是一个坑，有两个作用
        //1.重置的值不是空的，而是第一次被赋予的值。
        //第一次dialogForm赋空值，后续才能重置为空值。
        //这就是我们在新增修改打开弹出框操作的时候，调用resetDialogFrom清空数据的原因。
        //2.清空校验结果
        this.$refs[this.dialogRefName].resetFields();
        this.dialogFormVisible = false;
      },
        resetDialogForm(){
        for(let item in this.dialogForm){
          if (typeof this.dialogForm[item]==='string'){
            this.dialogForm[item]='';
          } else if (this.dialogForm[item] instanceof Array) {
            this.dialogForm[item]=[];
          } else{
            this.dialogForm[item]=null
          }
        }
      },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
@import '../assets/css/comment.scss';
</style>
