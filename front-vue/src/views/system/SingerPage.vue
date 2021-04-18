<template>
  <div>
    <el-card body-style="padding: 0">
      <el-form ref="singerQueryForm" :model="singerQueryForm" label-width="80px">
        <el-row :gutter="20">
             <el-col :span="12">
            <el-form-item label="歌曲名" prop="name">
              <el-input v-model="singerQueryForm.singerName"
                        placeholder="请输入歌曲名查询"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="18">
            <el-form-item>
              <el-button type="primary" size="small"
                         @click="querySinger()" icon="el-icon-search">
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
        <el-table-column prop="name" label="歌手名" width="150" align="center"/>
        <el-table-column prop="pic" label="歌手头象" width="100" align="center"/>
        <el-table-column  label="性别" width="100" align="center" >
            <template slot-scope="scope">
                <div>{{changeSex(scope.row.sex)}}</div>
            </template>
        </el-table-column>
        <el-table-column prop="location" label="地域" width="150" align="center"/>
        <el-table-column  label="出生日期" width="150" align="center" >
              <template slot-scope="scope">
                <div>{{formData(scope.row.birth)}}</div>
              </template>
        </el-table-column>
        <el-table-column label="歌手简介"  width="350" align="center">
          <template slot-scope="scope">
            <ul style="height: 50px; overflow: scroll">
              <div>{{scope.row.introduction}}</div>
            </ul>
          </template>
        </el-table-column>
         <el-table-column label="所属歌曲" width="130" align="center">
            <template  slot-scope="scope">
                <el-button  @click="getSongBySingerId(scope.row.id)">所属歌曲</el-button>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" circle
                       @click="handleEdit(scope.row)"/>
            <el-button size="mini" type="danger" icon="el-icon-delete" circle
                       @click="handleDelete(scope.row)"/>
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
            <el-form-item label="歌手名" prop="name">
              <el-input  :disabled="true" v-model="dialogForm.name" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="用户性别" prop="sex">
              <el-input v-model="dialogForm.sex" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="用户头像" prop="pic">
              <el-input v-model="dialogForm.pic" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="出生日期" prop="birth">
              <el-input v-model="dialogForm.birth" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item label="地域信息" prop="location">
              <el-input v-model="dialogForm.location" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item  label="歌曲的简介" prop="introduction">
              <el-input :disabled="true" v-model="dialogForm.introduction" autocomplete="off" ></el-input>
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
  import {getSingerByPage,getSingerBySingerName,getSingerByAge,getSingerBySex,addSinger,updateSingerMsg,deleteSinger} from '../../api/system/singer'
  import {getSongOfSingerId} from '../../api/system/song'
  import * as dateUtils from "@/api/data";
  import MixinCUD from '@/components/MixinCUD'
  export default {
    name: "SingerPage",
   mixins: [MixinCUD],
    data() {
      return {
        tableData: [],
        queryFormRefName:"singerQueryForm",
        singerQueryForm:{
          singerName: ""   // 歌曲名
        },
        
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
          name: '',
          sex:'',
          pic: '',
          birth:'',
          location:'',
          introduction:''
        },
        dialogFormRules: {
          name: [
            {required: true, message: '请输入歌手名', trigger: 'blur'},
          ],
          location: [
            {required: true, message: '地域信息不能为空', trigger: 'blur'},
          ],
          introduction: [
            {required: true, message: '歌手介绍不能为空', trigger: 'blur'},
          ]
        }
      }
    },

  created () {
    this.getData()
  },

    methods: {
      getData(){
        getSingerByPage(this.pagination.pageNum,this.pagination.pageSize)
          .then(res => {
             console.log(res)
             this.setData(res)
          })
      },
       // 重新的设置数据,分页才能用到这个函数
      setData(res) {
          this.tableData = res.records
          this.pagination.total = res.total;
      },
     
     // 根据歌名查询歌手的信息
      querySinger(){
        getSingerBySingerName(this.pagination.pageNum,this.pagination.pageSize,this.singerQueryForm.singerName).then(res =>{   
            this.setData(res)
           }).catch(err => {
                this.$message({message: err.message, type: 'error'});
          }) 
     },

    // 根据歌手id来查询他的歌曲信息
     getSongBySingerId(){
         getSongOfSingerId(this.pagination.pageNum,this.pagination.pageSize,this.id).then(res =>{
            console.log(res)
            //this .setData(res)
         })
     },

      handleEdit (row) {
      this.idx = row.id
      this.dialogForm = {
        id: row.id,
        name: row.name,
        pic: row.pic,
        sex: row.sex,
        birth: row.birth,
        introduction: row.introduction,
        location: row.location,
      }
      this.dialogFormVisible = true
     },
     
     //保存编辑的信息 这里需要加个判断，是不是增加，使用id来判断
     updateData(){
            // let params = new URLSearchParams()
            // params.append('id', this.dialogForm.id)
            // params.append('title', this.dialogForm.title)
            // params.append('pic', this.dialogForm.pic)
            // params.append('introduction', this.dialogForm.introduction)
            // params.append('style', this.dialogForm.style)
             updateSingerMsg(this.dialogForm)
              .then(res => {
               this.$message({message: res.data, type: 'success'});
               this.getData();
               this.handleCloseDialog();
            }).catch(err => {
          console.log(err)
            })
     },

     addData(){
         addSinger(this.dialogForm).then(res => {
          this.$message({message: res.data, type: 'success'});
          this.getData();
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
     // 删除一条信息
      handleDelete(row){
        this.$confirm("确定删除用户"+row.name+"这条信息?")
          .then(_ => {
            deleteSinger(row.id)
              .then(res => {
                this.getData();               
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

    //   resetQueryForm() {
    //     this.$refs[this.queryFormRefName].resetFields();
    //     this.getData()
    // }, 

       // 日期的转换类
     formData (val) {
      let date = new Date(val);
      return dateUtils.formatDate(date,'yyyy-MM-dd');
    },
    // 用户的性别类的转换 
    changeSex (value) {
       return dateUtils.changeSex(value);
    },

    // 删除数据的校验使用
    handleCloseDialog(){
        //resetFields就是一个坑，有两个作用
        //1.重置的值不是空的，而是第一次被赋予的值。
        //第一次dialogForm赋空值，后续才能重置为空值。
        //这就是我们在新增修改打开弹出框操作的时候，调用resetDialogFrom清空数据的原因。
        //2.清空校验结果
        this.$refs[this.dialogRefName].resetFields();
        this.dialogFormVisible = false;
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