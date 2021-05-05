<template>
  <div>
        <el-card body-style="padding: 10">
              <el-form ref="songQueryForm" :model="songQueryForm" label-width="80px">
                <el-row :gutter="20">
                    <el-col :span="15">
                    <el-form-item label="歌曲名" prop="queryName">
                      <el-input v-model="songQueryForm.queryName"
                                placeholder="请输入歌曲名或者歌手名查询"/>
                    </el-form-item>
                  </el-col>
                  
                  <el-col :span="12" :offset="18">
                    <el-form-item>
                      <el-button type="primary" size="small"
                                @click="querySong()" icon="el-icon-search">
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

       <el-table :data="tableData"  border default-expand-all stripe style="width: 100%;margin-bottom: 20px;">
        <!-- <el-table-column type="selection" width="40"></el-table-column> -->
        <el-table-column label="播放按钮" width="110" align="center">
          <template slot-scope="scope" style="background-color:black">
            <div style="background-color:black" class="play" @click="setSongUrl(scope.row.url, scope.row.songName)">
              <div v-if="toggle !== scope.row.songName">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-bofanganniu"></use>
                </svg>
              </div>
              <div v-if="toggle === scope.row.songName">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-zanting"></use>
                </svg>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="歌名" prop="songName" width="100" align="center"></el-table-column>
        <!-- <el-table-column label="歌手名" prop="singerName" width="100" align="center"></el-table-column> -->
        <el-table-column label="歌词"  width="350" align="center">
          <template slot-scope="scope">
            <ul style="height: 100px; overflow: scroll">
              <li>
              <li v-for="(item, index) in parseLyric(scope.row.lyric)" :key="index">
                {{ item}}
              </li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column  label="歌曲海报" width="100" align="center">
        <template slot-scope="scope">
            <div>
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
        <el-table-column  label="歌曲文件" width="100" align="center">
        <template slot-scope="scope">
             <el-upload
              class="upload-demo" 
               :action="uploadSongUrl(scope.row.id)"
               :headers="myHeaders"
              :limit="1" 
              :on-success="handleSongSuccess"
              :multiple="false" 
              :show-file-list="false"
              >
              <el-button size="mini">更新文件</el-button>
            </el-upload>
        </template>
        
      </el-table-column>
        <el-table-column label="歌曲评论" width="130" align="center">
            <template  slot-scope="scope">
                <el-button  @click="getComment(scope.row.id)">歌曲评论</el-button>
            </template>
        </el-table-column>
        <el-table-column label="创建时间" width="150" align="center">
            <template  slot-scope="scope">
                <div>{{formData(scope.row.createTime)}}</div>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
            <template slot-scope="scope">
                <el-button size="mini" type="primary" icon="el-icon-edit" circle
                           @click="handleEdit(scope.$index, scope.row,'修改歌曲的信息')"/>
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
            <el-form-item label="歌曲名" prop="songName">
              <el-input  v-model="dialogForm.name" autocomplete="off"></el-input>
            </el-form-item>
        </el-row>
         <el-row :span="12">
                <el-form-item label="歌曲文件" prop="file">
                 <div>
                 <label>歌曲上传</label>
                  <br>
                     <input type="file" name="file"  id="upadte-file-input">
                 </div>
                </el-form-item>  
         </el-row>
         <el-row :gutter="20">
            <el-form-item  label="歌词" prop="lyric">
              <el-input  v-model="dialogForm.lyric" type="textarea" autocomplete="off" ></el-input>
            </el-form-item>
        </el-row>
         <el-row :gutter="20">
            <el-form-item  label="歌曲简介" prop="introduction">
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
import SongAudio from '../../components/SongAudio'
//import { mixin } from '../../mixins'
import { mapGetters } from 'vuex'
import '@/assets/js/iconfont.js'
import { getSongsByPage,getSongOfSongListId,getSongOfSingerId,updateSongMsg, deleteSong ,querySongByName,addSong} from '../../api/system/song'
import MixinCUD from '../../components/MixinCUD'
 import * as dateUtils from "@/api/data";

var token = localStorage.getItem("JWTHeaderName");
export default {
  name: 'song-page',
  mixins:[MixinCUD],
  components: {
    SongAudio
  },
 // mixins: [mixin],
  data () {
    return {
      toggle: false, // 控制播放图标状态
      singerId: '',  //歌手的id
      songListId:'',  // 歌单id
      singerName: '',
      tableData: [],
      file: '',
      delVisible: false,
      myHeaders:{JWTHeaderName: token},
      dialogFormVisible: false,
      dialogTitle:"",
      dialogRefName:"dialogForm",
      dialogForm: {
        id: '',
        singerId: '',   
        name: '',
        introduction: '',
        pic: '',
        lyric: '',
      },
      fileList: [],
      queryFormRefName:"songQueryForm",
      songQueryForm:{
          queryName: "",   // 查询的参数可以是歌曲或者用户名
      },
      pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
        },
      dialogFormRules: {
        name: [
          {required: true, message: '请输入歌曲名', trigger: 'blur'},
        ],
        introduction: [
          {required: true, message: '歌曲简介不能为空', trigger: 'blur'},
        ]
      }
    }   
  },
  computed: {
    ...mapGetters([
      'isPlay' // 播放状态
    ]),
   
  },
  created () {
    this.singerId = this.$route.query.singerId     // 获取歌手的id的信息
    this.songListId = this.$route.query.songListId  //  获取歌单的id
    this.getData()
  },

  destroyed () {
    this.$store.commit('setIsPlay', false)
  },
  methods: {
    // 获取歌曲
    getData () {
      this.tableData = []
      if(this.songListId){       // 如果歌单的id不为空，那就返回更新后的歌单所属的歌曲的数据
         this.getSongListSong()
      }
      else if(this.singerId){  // 如果歌手的id不能为空，那就返回该歌手的下面的信息
         this.getSingerSong()
      }else {
        getSongsByPage(this.pagination.pageNum,this.pagination.pageSize).then((res) => {
          // console.log('所有歌曲信息', res.records)
          this.setData(res)
        }).catch(err => {
          console.log(err)
         })
      }
      
    },
    
      // 设置数据信息
      setData(res) {
          this.tableData = res.records
          this.pagination.total = res.total;
      },
    

   //根据名称查询
    querySong(){
        this.tableData = []
        querySongByName(this.pagination.pageNum,this.pagination.pageSize,this.songQueryForm.queryName).then
        ((res) => {
            if(res.total >=0){
              //  console.log(res)
                this.tableData = res.records
                this.pagination.total = res.total;
                //console.log(res.records)
            }else{
                this.$message({message: res, type:'success'});
                this.getData();
            }
           
           // console.log("查询的歌曲信息",res);
          }).catch(err => {
             this.$message({message: err.message, type: 'error'});
             this.getData();
          })
    },

    // 查询歌单中具有的歌曲信息
    getSongListSong(){
         getSongOfSongListId(this.pagination.pageNum,this.pagination.pageSize,this.songListId).then((res) => {
            // console.log('所有歌曲信息', res.records)
             this.setData(res)
            }).catch(err => {
            console.log(err)
          })
    },

    // 查询歌手具有的歌曲信息
    getSingerSong(){
        getSongOfSingerId(this.pagination.pageNum,this.pagination.pageSize,this.singerId).then((res) => {
             console.log('歌手的所属歌曲', res.records)
             this.setData(res)
           }).catch(err => {
            console.log(err)
          })  
    },
       //编辑实现
     updateData(){
             updateSongMsg(this.dialogForm)
              .then(res => {
               this.$message({message: res, type: 'success'});
               this.submitQueryForm();
               this.handleCloseDialog();
            }).catch(err => {
          console.log(err)
            })
     },
    
    // 添加数据
     addData(){
        // let params = new URLSearchParams()
        // console.log(this.dialogForm.name);
        // params.append('name', this.dialogForm.name)
        // params.append('introduction', this.dialogForm.introduction)
        // params.append('lyric',this.dialogForm.lyric)
        // params.append('file', this.fileList[0])
         addSong(this.dialogForm).then(res => {
          this.$message({message: res, type: 'success'});
          this.submitQueryForm();
          this.handleCloseDialog();
        })
      },
   
   // 和表单一起提交的方法还是没有实现
  //   addData(){
  //     console.log("zhehsi ")
  //     if (this.$refs.dialogForm.validate()){
  //       console.log("zhehsijfiegg  ")
  //       this.requestFile(this.fileList)
  //     }
       
  //   },

  //  // 上传歌曲的实现
  //   requestFile(fileList){
  //      // let uploadUrl="localhost:9999/song/addSong";
  //       let form = new FormData()    // FormData 对象
  //       form.append('file',fileList[0])    // 文件对象
  //       form.append('name', this.dialogForm.name)  //表单其他参数。。
  //       form.append('introduction', this.dialogForm.introduction)
  //       form.append('lyric',this.dialogForm.lyric)
  //        addSong(this.form).then(res => {
  //         this.$message({message: res, type: 'success'});
  //         this.submitQueryForm();
  //         this.handleCloseDialog();
  //       })
  //   },


    
    // 提交表单数据信息
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
    // 确定删除
    deleteData(row){
        this.$confirm("确定删除["+row.name+"]?")
          .then(_ => {
            deleteSong(row.id)
              .then(res => {
                this.submitQueryForm();//删除之后，重新查询table
                this.$message({message: res, type: 'success'});
              }).catch(err => {S
              this.$message({message: err.message, type: 'error'});
            })
          });
    },
    
    // 播放歌曲实现
    setSongUrl (url, name) {
      //console.log("歌曲的url"+url)
      this.$store.commit('setUrl', this.$store.state.ONHOST+url)
      console.log("歌曲的Url:"+this.$store.state.ONHOST+url)
        //return this.$store.state.ONHOST+url;
      this.toggle = name
      if (this.isPlay) {
        this.$store.commit('setIsPlay', false)
      } else {
        this.$store.commit('setIsPlay', true)
      }
    },

     //完成歌曲文件的上传
    uploadSongUrl(id){
       return `${this.$store.state.HOST}/song/updateSongFile?id=${id}`
    },

     handleSongSuccess(res,file){
       console.log(res)
       if (res.isok) {    
        this.$message({message: res.data, type: 'success'});
        this.submitQueryForm();
        }else{
         this.$message({message: res.data, type: 'success'}); 
       }
     },
    // 得到图片地址信息
    getUrl (url) {
      return `${this.$store.state.ONHOST}/${url}`
    },

    // 歌曲图片的上传
    uploadUrl (id) {
      return `${this.$store.state.HOST}/song/updatePicture?id=${id}`
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
    },

    beforeSongUpload (file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = testmsg === 'mp3'
      if (!extension) {
        this.$message({
          message: '上传文件只能是mp3格式！',
          type: 'error'
        })
      }
      return extension
    },
    // 获取当前页
     // 分页的设置
      handlePageSizeChange(val){
        this.pagination.pageSize = val;
        this.getData();
      },
      handlePageNumChange(val){
        this.pagination.pageNum = val;
        this.getData();
      },

    //  handleSongSuccess (res, file) {
    //   if (res.code === 1) {
    //     this.getData()
    //     this.notify('上传成功', 'success')
    //   } else {
    //     this.notify('上传失败', 'error')
    //   }
    //},
    getComment (id) {
      this.$router.push({path: '/home/comment', query: {songId: id, type: 0}})
    }, 

    // 日期的转换类
    formData (val) {
      let date = new Date(val);
      return dateUtils.formatDate(date,'yyyy-MM-dd');
    },
    // 解析歌词
    parseLyric (text) {
          let lines = text.split('\n')
          let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g
          let result = []

          // 对于歌词格式不对的特殊处理
          if (!(/\[.+\]/.test(text))) {
            return [text]
          }
          for (let item of lines) {
            if (pattern.test(item)) {
              let value = item.replace(pattern, '') // 存歌词
              result.push(value)
            }
          }
          return result
     }
  }
}

</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}
.handle-input {
    width: 300px;
    display: inline-block;
}
.el-input__inner{
  background-color: aqua
}
.play {
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    top: 18px;
    left: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.icon {
  width: 2em;
  height: 2em;
  color: white;
  fill: currentColor;
  overflow: hidden;
}
.pagination {
    display: flex;
    justify-content: center;
}
</style>
