<template>
  <div>
    <el-card body-style="padding: 0">
      <el-form ref="commentQueryForm" :model="commentQueryForm" label-width="80px">
        <el-row :gutter="20">
             <el-col :span="8">
            <el-form-item label="歌曲名" prop="songName">
              <el-input v-model="commentQueryForm.songName"
                        placeholder="请输入歌曲名查询"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="commentQueryForm.userName"
                        placeholder="请输入用户名查询"/>
            </el-form-item>
          </el-col>
          
          <el-col :span="10" :offset="18">
            <el-form-item>
              <el-button type="primary" size="small"
                         @click="queryComment()" icon="el-icon-search">
                查询</el-button>
              <el-button type="primary" size="small" plain
                         @click="resetQueryForm()" icon="el-icon-refresh">
                重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card>
      <!-- <el-button type="primary" size="small" style="margin: 0 0 10px 20px"
                 icon="el-icon-plus" @click="handleAdd('新增全局参数')">新增</el-button> -->

      <el-table :data="tableData" border default-expand-all stripe style="width: 100%;margin-bottom: 20px;">
        <el-table-column prop="userName" label="用户名" width="150" align="center"/>
        <el-table-column prop="avatar" label="用户头象" width="100" align="center"/>
        <el-table-column prop="content" label="评论内容" width="300" align="center"/>
         <el-table-column  label="评论时间" width="150" align="center" >
                <template slot-scope="scope">
                <div>{{formData(scope.row.createTime)}}</div>
            </template>
            </el-table-column>
        <el-table-column prop="likeNum" label="评论点赞数" width="100" align="center"/>
        <el-table-column prop="songName" label="评论歌曲名" width="150" align="center"/>
        <el-table-column prop="commentStatus" label="评论的状态" width="100" align="center"/>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" circle
                       @click="handleEdit(scope.$index, scope.row,'编辑评论信息')"/>
            <el-button size="mini" type="danger" icon="el-icon-delete" circle
                       @click="handleDelete(scope.$index, scope.row)"/>
          </template>
        </el-table-column>
      </el-table>
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
    </el-card>
 
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible"
               :before-close="beforeDialogClose">
      <el-form :model="dialogForm" ref="dialogForm"
               :rules="dialogFormRules" label-width="20%">

        <el-row :gutter="20">
            <el-form-item label="用户名" prop="userName">
              <el-input  :disabled="true" v-model="dialogForm.userName" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="用户头像" prop="avatar">
              <el-input v-model="dialogForm.avatar" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="评论时间" prop="createTime">
              <el-input v-model="dialogForm.createTime" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="评论内容" prop="content">
              <el-input v-model="dialogForm.content" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item label="评论的点赞数" prop="likeNum">
              <el-input v-model="dialogForm.likeNum" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item  label="评论的歌曲名" prop="songName">
              <el-input :disabled="true" v-model="dialogForm.songName" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="15">
            <el-form-item label="评论的状态" prop="commentStatus">
              <el-input v-model="dialogForm.commentStatus" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitDialogForm" size="mini" type="primary">确 定</el-button>
        <el-button @click="handleCloseDialog" size="mini">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {getCommentByPage,getCommentBySongName,addComment,deleteComment,updateCommentMsg,getCommentByUserName,getCommentOfSongId}
  from '../../api/system/comment'
   import * as dateUtils from "@/api/data";
  import MixinCUD from '@/components/MixinCUD'

  export default {
    name: "CommentPage",
    mixins:[MixinCUD],
    data() {
      return {
        tableData: [],
        queryFormRefName:"commentQueryForm",
        commentQueryForm:{
          songName: "",   // 歌曲名
          userName: ""   // 用户名
        },
        
       songId: '',  // 歌曲的Id的信息，根据这个来查询歌曲的评论信息
       pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
        },

        dialogFormVisible: false,
        dialogTitle:"",
        dialogRefName:"dialogForm",

        dialogForm: {
          id: null,
          uesrName: '',
          avatar: '',
          content:'',
          createTime: '',
          likeNum: '',
          songName:'',
          commentStatus:''
        },
        dialogFormRules: {
          uesrName: [
            {required: true, message: '请输入参数中文名称', trigger: 'blur'},
          ],
          content: [
            {required: true, message: '评论内容不能为空', trigger: 'blur'},
          ],
          songName: [
            {required: true, message: '评论的歌曲名也不能为空', trigger: 'blur'},
          ]
        }
      }
    },

  created () {
    this.songId = this.$route.query.songId  // 获取歌曲的评论信息
    this.getData()
  },

    methods: {
      getData(){
        if(this.songId != null){
            this.getCommentBySongId()
        }
        getCommentByPage(this.pagination.pageNum,this.pagination.pageSize)
          .then(res => {
             //console.log(res)
             this.setData(res)
          })
      },
     
     // 这里判断一下，用户输入的是什么，支持按照歌曲来查询
      queryComment(){

        // if(this.queryFormRefName.songName != null && this.queryFormRefName.userName == null){
        // }
        getCommentBySongName(this.pagination.pageNum,this.pagination.pageSize,this.commentQueryForm.songName).then(res =>{   
             //console.log(res) 
            this.setData(res)
           }).catch(err => {
                this.$message({message: err.message, type: 'error'});
          })
          // if(this.queryFormRefName.songName == null && this.queryFormRefName.userName != null){
          //   getCommentByUserName(this.commentQueryForm.userName).then(res =>{
          //      console.log(res);
          //       this.tableData = res.data;
          //  })
          // }           
     },

    // 根据歌曲id来查询他的评论信息
     getCommentBySongId(){
         getCommentOfSongId(this.pagination.pageNum,this.pagination.pageSize,this.songId).then(res =>{
           console.log(res)
            this.setData(res)
         })
     },

      // 重新的设置数据,分页才能用到这个函数
      setData(comments) {
          this.tableData = comments.records
          this.pagination.total = comments.total;
      },

      updateData(){
        updateCommentMsg(this.dialogForm)
          .then(res => {
            this.$message({message: res.data, type: 'success'});
            this.submitQueryForm();//修改之后，重新查询table
            this.handleCloseDialog();
          })
      },

      deleteData(row){
        this.$confirm("确定删除用户"+row.userName+"创建的这条评论?")
          .then(_ => {
            deleteComment(row.id)
              .then(res => {
                this.submitQueryForm();//删除之后，重新查询table
                
                this.$message({message: res.data, type: 'success'});
              }).catch(err => {
                this.$message({message: err.message, type: 'error'});
              })
          });
      },

    

      // 分页的设置
      handlePageSizeChange(val){
        this.pagination.pageSize = val;
        this.submitQueryForm()
      },
      handlePageNumChange(val){
        this.pagination.pageNum = val;
        this.submitQueryForm()
      },

      resetQueryForm() {
        this.$refs[this.queryFormRefName].resetFields();
        this.getData()
    }, 

        // 日期的转换类
     formData (val) {
      let date = new Date(val);
      return dateUtils.formatDate(date,'yyyy-MM-dd');
    },
  },

//  beforeRouteEnter(to, from, next) {
//       getCommentByPage().then(res => {
//          console.log(res);
//         next(vm => vm.setData(res))
//       })
//     }
  }
</script>

<style scoped>
  .el-form {
    margin-top: 20px;
    margin-right: 40px;
  }
</style>