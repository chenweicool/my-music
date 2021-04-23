<template>
  <div>
    <el-card body-style="padding: 0">
      <el-form ref="songListName" :model="songListName" label-width="80px">
        <el-row :gutter="20">
             <el-col :span="12">
            <el-form-item label="歌曲名" prop="title">
              <el-input v-model="songListName.title"
                        placeholder="请输入歌曲名查询"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="18">
            <el-form-item>
              <el-button type="primary" size="small"
                         @click="songListQuery()" icon="el-icon-search">
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
       <el-button type="primary" size="small" style="margin: 0 0 10px 20px"
                     icon="el-icon-plus" @click="handleAdd('新增歌曲')">新增</el-button>
      <el-table :data="tableData" border default-expand-all stripe style="width: 100%;margin-bottom: 20px;">
        <el-table-column prop="title" label="歌单标题" width="150" align="center"/>
        <el-table-column prop="pic" label="歌单头像" width="100" align="center"/>
        <el-table-column prop="style" label="歌单风格" width="150" align="center"/>
        <el-table-column  label="创建日期" width="150" align="center" >
              <template slot-scope="scope">
                <div>{{formData(scope.row.createTime)}}</div>
              </template>
        </el-table-column>
        <el-table-column  label="歌单类型" width="100" align="center" >
            <template slot-scope="scope">
                <div>{{changeSongListType(scope.row.type)}}</div>
            </template>
        </el-table-column>
        <el-table-column label="歌单简介"  width="350" align="center">
          <template slot-scope="scope">
            <ul style="height: 50px; overflow: scroll">
              <div>{{scope.row.introduction}}</div>
            </ul>
          </template>
        </el-table-column>
         <el-table-column label="所属歌曲" width="130" align="center">
            <template  slot-scope="scope">
                <el-button  @click="getSongBySongListId(scope.row.id)">所属歌曲</el-button>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="scope">
                       <el-button size="mini" type="primary" icon="el-icon-edit" circle
                           @click="handleEdit(scope.$index, scope.row,'修改歌手的信息')"/>
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
              >
      <el-form :model="dialogForm" ref="dialogForm"
               :rules="dialogFormRules" label-width="20%">

        <el-row :gutter="20">
            <el-form-item label="歌单标题" prop="title">
              <el-input  v-model="dialogForm.title" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="歌单的海报" prop="pic">
              <el-input v-model="dialogForm.pic" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item label="歌单的风格" prop="style">
              <el-input v-model="dialogForm.style" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item  label="歌手简介" prop="introduction">
              <el-input  v-model="dialogForm.introduction" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitDialogForm()" size="mini" type="primary">确 定</el-button>
        <el-button @click="handleCloseDialog" size="mini">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {getSongListByPage,getMySongList,getTitleByPage,getStyleByPage,addSongList,updateSongListMsg,deleteSongList} from '../../api/system/songlist'
  import * as dateUtils from "@/api/data";
  import MixinCUD from '@/components/MixinCUD'
  export default {
   name: "SongListPage",
   mixins: [MixinCUD],
    data() {
      return {
        tableData: [],
        queryFormRefName:"songListName",
        songListName:{
          title: ""   // 歌单的标题
        },
       userId:'',  // 用户的id 
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
          title: '',
          pic: '',
          style:'',
          introduction:'',
          type: '',  // 歌单的类型  0 是系统创建  1 是用户自己创建
          createTime:'',
          updateTime:'',
          userName: '',
          userId:'',
        },
        dialogFormRules: {
          title: [
            {required: true, message: '请输入歌单标题', trigger: 'blur'},
          ],
          style: [
            {required: true, message: '输入歌单风格', trigger: 'blur'},
          ],
          introduction: [
            {required: true, message: '输入歌单的介绍', trigger: 'blur'},
          ]
        }
      }
    },

  created () {
    this.userId = this.$route.query.userId  //得到用户id信息
    this.getData()
  },

    methods: {
      getData(){
        getMySongList(this.pagination.pageNum,this.pagination.pageSize,this.userId)
          .then(res => {
           //  console.log(res)
             this.setData(res)
          })
      },
       // 设置数据信息
      setData(res) {
          this.tableData = res.records
          this.pagination.total = res.total;
      },
     
     // 根据歌单风格查询歌单
      songListQuery(){
        getTitleByPage(this.pagination.pageNum,this.pagination.pageSize,this.songListName.title).then(res =>{   
            console.log(res);
            this.setData(res)
           }).catch(err => {
                this.$message({message: err.message, type: 'error'});
          }) 
     },

    // 根据歌单id来查询所属歌曲信息
     getSongBySongListId(id){
          this.$router.push({path: '/home/song', query: {songListId: id}})
     },
     
     //编辑实现
     updateData(){
             updateSongListMsg(this.dialogForm)
              .then(res => {
               this.$message({message: res, type: 'success'});
               this.submitQueryForm();
               this.handleCloseDialog();
            }).catch(err => {
          console.log(err)
            })
     },

     addData(){
         addSongList(this.dialogForm).then(res => {
          this.$message({message: res, type: 'success'});
          this.submitQueryForm();
          this.handleCloseDialog();
        })
      },

     saveEdit(){
        this.$refs[this.dialogRefName].validate((valid) => {
          if (valid) {
            this.$confirm("确定提交数据么?")
              .then(_ => {
                if(this.dialogForm.id){ //有id是更新数据，没有id是新增数据
                  this.updateData();
                }else {
                  this.addData();
                }
                //取消新增或修改也要重置表单
              }).catch(err => {this.handleCloseDialog();});
          } else {
            return false;
          }
        });
     },
    deleteData(row){
        this.$confirm("确定删除["+row.name+"]?")
          .then(_ => {
            deleteUser(row.id)
              .then(res => {
                this.submitQueryForm();//删除之后，重新查询table
                this.$message({message: res.data, type: 'success'});
              }).catch(err => {S
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

      // 日期的转换类
     formData (val) {
      let date = new Date(val);
      return dateUtils.formatDate(date,'yyyy-MM-dd');
    },
    // 用户的性别类的转换 
    changeSongListType (value) {
       return dateUtils.changeSongListType(value);
    },
    
  },
  }
</script>

<style scoped>
  .el-form {
    margin-top: 20px;
    margin-right: 40px;
  }
</style>