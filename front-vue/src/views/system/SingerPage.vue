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
        <el-table-column  label="歌手头象" width="100" align="center">
        <template slot-scope="scope">
            <div class="singer-img">
              <img :src="getUrl(scope.row.pic)" alt="" style="width: 100%;"/>
            </div>
            <el-upload
              class="upload-demo"
              :action="uploadUrl(scope.row.id)"
              :headers="myHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <el-button size="mini">更新图片</el-button>
            </el-upload>
        </template>
      </el-table-column>
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
            <el-form-item label="歌手名" prop="name">
              <el-input  v-model="dialogForm.name" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
         <el-row :span="12">
                <el-form-item label="用户性别" prop="sex">
                 <el-radio-group v-model="dialogForm.sex">
                       <el-radio  :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                 </el-radio-group>
                </el-form-item>
        </el-row>
        <el-row :gutter="20">
            <el-form-item label="用户头像" prop="pic">
              <el-input v-model="dialogForm.pic" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :span="12">
                <el-form-item label="用户生日" prop="birth">
                  <el-date-picker type="date" placeholder="选择日期" v-model="dialogForm.birth" style="width: 100%;"></el-date-picker>
                </el-form-item>
          </el-row>
         <el-row :gutter="20">
            <el-form-item label="地域信息" prop="location">
              <el-input v-model="dialogForm.location" autocomplete="off" ></el-input>
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
  import {getSingerByPage,getSingerBySingerName,getSingerByAge,getSingerBySex,addSinger,updateSingerMsg,deleteSinger} from '../../api/system/singer'
  import {getSongOfSingerId} from '../../api/system/song'
  import * as dateUtils from "@/api/data";
  import MixinCUD from '@/components/MixinCUD'

  var token = localStorage.getItem("JWTHeaderName");
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
       myHeaders:{JWTHeaderName: token},

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
       // 设置数据信息
      setData(res) {
          this.tableData = res.records
          this.pagination.total = res.total;
      },
     
     // 根据歌名查询歌手的信息
      querySinger(){
        getSingerBySingerName(this.pagination.pageNum,this.pagination.pageSize,this.singerQueryForm.singerName).then(res =>{   
            console.log(res);
            this.setData(res)
           }).catch(err => {
                this.$message({message: err.message, type: 'error'});
          }) 
     },

    // 根据歌手id来查询他的歌曲信息
     getSongBySingerId(id){
          this.$router.push({path: '/home/song', query: {singerId: id}})
     },
     
     //编辑实现
     updateData(){
             updateSingerMsg(this.dialogForm)
              .then(res => {
               this.$message({message: res, type: 'success'});
               this.submitQueryForm();
               this.handleCloseDialog();
            }).catch(err => {
          console.log(err)
            })
     },

     addData(){
         addSinger(this.dialogForm).then(res => {
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
    changeSex (value) {
       return dateUtils.changeSex(value);
    },

    // 得到图片地址信息
    getUrl (url) {
      return `${this.$store.state.ONHOST}/${url}`
    },

    // 歌曲海报的上传
    uploadUrl (id) {
      return `${this.$store.state.HOST}/singer/updatePicture?id=${id}`
    },
    
    // 更新图片
    handleAvatarSuccess (res, file) {
      let _this = this
      console.log(res)
      if (res.isok) {
        _this.imageUrl = URL.createObjectURL(file.raw)
        this.$message({message: res.data, type: 'success'});
        this.submitQueryForm();
      } else {
          this.$message({message: res.data, type: 'error'});
      }
    },

    // 上传前的图片的校验
    beforeAvatarUpload (file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 20MB!')
      }
      return isJPG && isLt2M
    }

  },
  }
</script>

<style scoped>
  .el-form {
    margin-top: 20px;
    margin-right: 40px;
  }
</style>