<template>
  <div class="content">
    <h1 class="title">
      <slot name="title"></slot>
    </h1>
    <hr>
    <ul>
      <li class="list-title">
        <div class="song-item">
          <span class="item-index"></span>
          <span class="item-title">歌曲名</span>
          <span class="item-name">艺人</span>
          <span class="item-intro">专辑</span>
        </div>
      </li>
      <li class="list-content" v-for="(item, index) in songList" :key="index">
         <div style="float:right;">
           <el-button type="success" size="mini" @click="handleAdd('新增收藏',item.id)">添加收藏</el-button>
            <!-- <el-button size="mini" type="primary" icon="el-icon-edit" circle
                           @click="handleAdd('新增收藏',item.id)"/> -->
                <!-- <el-button size="mini" type="danger" icon="el-icon-delete" circle
                           @click="handleDelete(item)"/> -->
        </div>
        <div class="song-item" :class="{'is-play': id === item.id}"  @click="toplay(item.id, item.url, item.pic, index, item.songName, item.lyric)">
          <span class="item-index">
            <span v-if="id !== item.id">{{index + 1}}</span>
            <svg v-if="id === item.id" class="icon" aria-hidden="true">
              <use xlink:href="#icon-yinliang"></use>
            </svg>
          </span>
          <span class="item-title">{{replaceFName(item.songName)}}</span>
          <span class="item-name">{{replaceLName(item.songName)}}</span>
          <span class="item-intro">{{item.introduction}}</span>
        </div>
     <!-- 新增收藏 -->
      <el-dialog :title="dialogTitle" :visible.sync="songDialogVisible">
            <el-button type="primary"
                      size="small"
                      style="float: right"
                      @click="saveSongList">
              确认收藏
            </el-button>
            <el-transfer
              v-model="songListIds"
              :props="songListDataProp"
              :data="songListData"
              :titles="['备选歌单', '已选歌单']">
            </el-transfer>
          </el-dialog>
      </li>
    </ul>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import {getMySongList,addSongToSongList,deleteSongToSongList} from '../api/system/songlist'
import { Loading } from 'element-ui'

export default {
  name: 'album-content',
  mixins: [mixin],
  props: [
    'songList'
  ],
  computed: {
    ...mapGetters([
      'id' // 音乐ID
    ])
  },

   data(){
     return{
          dialogTitle:"",
       //   roleDialogTitle:"",
          songId:'',
          userId:'',
          songDialogVisible: false,
          songListIds:[],
          songListDataProp:{
              key: 'id',
              label: 'title'
          },
          songListData:[],
          type: 1,  // 歌曲收藏的类型
     }
  },
  created(){
  }, 
   
   //添加歌曲的方法
  methods: {

   // 新增收藏
   handleAdd(dialogTitle,id){
     this.songDialogVisible = true
       this.userId =localStorage.getItem("userId")
        getMySongList(1,20,this.userId).then(res => {
          this.songListData = res.records
           this.songId = id;
           console.log(this.songId);
           for(var i = 0;i < songListData.length;i++){
               this.songListIds[i]=this.songListData[i].id
           }
        })
    },
    // 保存收藏歌曲信息
    saveSongList(){
      if(this.songListIds.length > 0){
        let params = new URLSearchParams();
        params.append("songId",this.songId)
        params.append("songListIds",this.songListIds)
        params.append("type",this.type)
        addSongToSongList(params)
          .then(res => {
           // console.log(res)
            this.$message({message: res, type: 'success'});
            this.songDialogVisible = false
          })
      }else{
        this.$message.error("已选收藏歌单不能为空");
      }
    },

    // 移除收藏
    handleDelete(itemd){
      this.deleteData(item);
    },
 
    // 待实现,因为songListId不好拿
    deleteData(item){
        this.$confirm("确定删除["+item.songName+"]?")
          .then(_ => {
            deleteUser(row.id)
              .then(res => {
                this.submitQueryForm();//删除之后，重新查询table
                this.$message({message: res.data, type: 'success'});
              }).catch(err => {
              this.$message({message: err.message, type: 'error'});
            })
          });
      },
    handleCloseDialog(){
      this.$refs[this.dialogRefName].resetFields();
      this.dialogFormVisible = false;
    },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
</style>
