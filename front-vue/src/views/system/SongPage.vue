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
      <el-table :data="tableData"  border default-expand-all stripe style="width: 100%;margin-bottom: 20px;">
        <!-- <el-table-column type="selection" width="40"></el-table-column> -->
        <el-table-column label="播放按钮" width="100" align="center">
          <template slot-scope="scope">
            <div style="width: 80px; height: 10px; overflow: hidden">
              <img :src="getUrl(scope.row.pic)" alt="" style="width: 100%;"/>
            </div>
            <div class="play" @click="setSongUrl(scope.row.url, scope.row.songName)">
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
        <el-table-column label="歌手名" prop="singerName" width="100" align="center"></el-table-column>
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
        <el-table-column label="歌曲海报" width="150" align="center">
          <template slot-scope="scope">
            <el-upload
              class="upload-demo"
              :action="uploadUrl(scope.row.id)"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
                <el-button >更新图片</el-button>
            </el-upload>
            <br>
            <!-- <el-upload
              class="upload-demo change"
              :action="uploadSongUrl(scope.row.id)"
              :show-file-list="false"
              :on-success="handleSongSuccess"
              :before-upload="beforeSongUpload">
              <el-button >更新歌曲</el-button>
            </el-upload> -->
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
                <el-button  size="mini" type="primary" icon="el-icon-edit" circle  @click="handleEdit(scope.row)"/>
                <el-button size="mini" type="danger" icon="el-icon-delete" circle
                       @click="handleDelete(scope.row.id)"/>
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

    <!--添加歌曲-->
    <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form action="" :model="registerForm" id="tf">
        <div>
          <label>歌曲名</label>
          <el-input type="text" name="name"></el-input>
        </div>
        <div>
          <label>专辑</label>
          <el-input type="text" value="" name="introduction"></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input type="textarea" value="" name="lyric"></el-input>
        </div>
        <div>
          <label>歌曲上传</label>
          <br>
          <input type="file" name="file" id="upadte-file-input">
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSong">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="400px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="歌手-歌曲" size="mini">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="简介" size="mini">
          <el-input v-model="form.introduction"></el-input>
        </el-form-item>
        <el-form-item label="歌词" size="mini">
          <el-input  type="textarea" v-model="form.lyric"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="editVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt" align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="delVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import SongAudio from '../../components/SongAudio'
import { mixin } from '../../mixins'
import { mapGetters } from 'vuex'
import '@/assets/js/iconfont.js'
import { getSongsByPage, updateSongMsg, deleteSong ,querySongByName} from '../../api/system/song'
import MixinCUD from '../../components/MixinCUD'
 import * as dateUtils from "@/api/data";

export default {
  name: 'song-page',
  mixins:[MixinCUD],
  components: {
    SongAudio
  },
  mixins: [mixin],
  data () {
    return {
      toggle: false, // 控制播放图标状态
      singerId: '',
      singerName: '',
      registerForm: {
        name: '',
        singerName: '',
        introduction: '',
        lyric: ''
      },
      tableData: [],
      tempDate: [],
      is_search: false,
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      select_word: '',
      form: {
        id: '',
        singerId: '',
        name: '',
        introduction: '',
        createTime: '',
        updateTime: '',
        pic: '',
        lyric: '',
        url: ''
      },
      queryFormRefName:"songQueryForm",
      songQueryForm:{
          queryName: "",   // 查询的参数可以是歌曲或者用户名
        },
       pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
        },
    }
  },
  computed: {
    ...mapGetters([
      'isPlay' // 播放状态
    ]),
   
  },
  created () {
    this.singerId = this.$route.query.id
    this.singerName = this.$route.query.name
    this.getData()
  },

  destroyed () {
    this.$store.commit('setIsPlay', false)
  },
  methods: {
    // 获取歌曲
    getData () {
      this.tableData = []
      this.tempDate = []
      getSongsByPage(this.pagination.pageNum,this.pagination.pageSize).then((res) => {
       // console.log('所有歌曲信息', res.records)
        this.tableData = res.records
        this.pagination.total = res.total;
        this.tempDate = res.records
      }).catch(err => {
        console.log(err)
      })
    },

       //根据歌曲名查询歌曲
    querySong(){
        this.tableData = []
        querySongByName(this.pagination.pageNum,this.pagination.pageSize,this.songQueryForm.queryName).then
        ((res) => {
            if(res.total >=0){
                console.log(res)
                this.tableData = res.records
                this.pagination.total = res.total;
                this.tempDate = res.records
                console.log(res.records)
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

    setSongUrl (url, name) {
      this.$store.commit('setUrl', this.$store.state.HOST + url)
      this.toggle = name
      if (this.isPlay) {
        this.$store.commit('setIsPlay', false)
      } else {
        this.$store.commit('setIsPlay', true)
      }
    },
    // 更新歌曲图片
    uploadUrl (id) {
      return `${this.$store.state.HOST}/song/img/update?id=${id}`
    },
    // 更新歌曲url
    uploadSongUrl (id) {
      return `${this.$store.state.HOST}/song/url/update?id=${id}`
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

     handleSongSuccess (res, file) {
      if (res.code === 1) {
        this.getData()
        this.notify('上传成功', 'success')
      } else {
        this.notify('上传失败', 'error')
      }
    },
    // 添加音乐
    addSong () {
      let _this = this
      var form = new FormData(document.getElementById('tf'))
      form.append('singerId', this.singerId)
      form.set('name', this.singerName + '-' + form.get('name'))
      if (!form.get('lyric')) {
        form.set('lyric', '[00:00:00]暂无歌词')
      }
      var req = new XMLHttpRequest()
      req.onreadystatechange = function () {
        if (req.readyState === 4 && req.status === 200) {
          let res = JSON.parse(req.response)
          if (res.code) {
            _this.getData()
            _this.registerForm = {}
            _this.notify(res.msg, 'success')
          } else if (!res.code) {
            _this.notify('上传失败', 'error')
          }
        }
      }
      req.open('post', `${_this.$store.state.HOST}/song/add`, false)
      req.send(form)
      _this.centerDialogVisible = false
    },

    // 编辑
    handleEdit (row) {
      this.idx = row.id
      this.form = {
        id: row.id,
        singerId: row.singerId,
        name: row.name,
        introduction: row.introduction,
        createTime: row.createTime,
        updateTime: row.updateTime,
        pic: row.pic,
        lyric: row.lyric,
        url: row.url
      }
      this.editVisible = true
    },
    getComment (id) {
      this.$router.push({path: '/home/comment', query: {songId: id, type: 0}})
    },

    // 保存编辑
    saveEdit () {
      let params = new URLSearchParams()
      params.append('id', this.form.id)
      params.append('singerId', this.form.singerId)
      params.append('name', this.form.name)
      params.append('introduction', this.form.introduction)
      params.append('lyric', this.form.lyric)
      updateSongMsg(params)
        .then(res => {
          console.log(res)
          if (res) {
            this.getData()
            this.notify('编辑成功', 'success')
          } else {
            this.notify('编辑失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.editVisible = false
    },
    // 确定删除
    deleteRow () {
      deleteSong(this.idx)
        .then(response => {
          if (response) {
            this.getData()
            this.notify('删除成功', 'success')
          } else {
            this.notify('删除失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.delVisible = false
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
