<template>
  <div>
    <div class="comment">
      <h2>评论</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img :src=attachImageUrl(avator) alt="">
        </div>
        <el-input
          class="comment-input"
          type="textarea"
          :rows="2"
          placeholder="请输入评论内容"
          v-model="textarea">
        </el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment()">提交评论</el-button>
    </div>
    <el-badge :value=total :max="99"  class="item">
      <el-button >精彩评论</el-button>
      </el-badge>
    <!-- <p>精彩评论: 共 {{commentList.length}} 条评论</p> -->
    <ul class="popular" v-for="(item, index) in commentList" :key="index">
      <li>
        <div class="popular-img">
          <img :src=getUrl(item.avatar) alt="">
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{item.userName}}</li>
            <li class="time">{{formData(item.createTime)}}</li>
            <li class="content">{{item.content}}</li>
          </ul>
        </div>
        <div class="up" ref="up" @click="postUp(item.id, item.likeNum,item.flag,index)">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-zan"></use>
          </svg>
          {{item.likeNum}}
        </div>
         <!-- <el-badge :value="3" class="item" :max="10" style="float:right;margin-top:20px;margin-bottom:0px;">
              <el-button size="small">回复</el-button>
           </el-badge> -->
      </li>
    </ul>
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
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import { setLike} from '../api/index'
import {getCommentOfSongId,addComment,updateCommentLikeNum} from '../api/system/comment'
import * as dateUtils from  '../api/data'
export default {
  name: 'comment',
  mixins: [mixin],
  props: [
    'playId', // 歌曲ID或歌单ID
    'type' // 歌单（0）/歌曲（1）
  ],
  data () {
    return {
      commentList: [], // 存放评论内容
      textarea: '' ,// 存放输入内容
      commentForm:{    // 提交评论表单
         songId:'',
         songListId:'',
         userId:'',
         avatar:'',
         userName: '',
         type:'',
         commentContent:'',

      },
      flag: true,
      total: '',
      pagination:{
        pageNum: 1,
        pageSize: 20,
        total: null
      },

    }
  },
  computed: {
    ...mapGetters([
      'id',
      'userId', // 用户ID
      'index', // 列表中的序号
      'loginIn', // 用户是否登录
      'avator' // 用户头像
    ])
  },
  watch: {
    id () {
      this.getData()
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    // 获取所有评论
    getData () {
      getCommentOfSongId(this.pagination.pageNum,this.pagination.pageSize,this.playId,this.type)
        .then(res => {
         // console.log(res.records)
          this.commentList = res.records
          this.total = res.total;

        })
        .catch(err => {
          console.log(err)
        })
    },
    
    // 提交评论
    postComment () {
      if (this.loginIn) {
        // 0 代表歌单， 1 代表歌曲
        let params = new URLSearchParams()
        if (this.type === 0) {
         this.commentForm.songListId = this.playId
        } else if (this.type === 1) {
         this.commentForm.songId = this.playId
        }
        this.commentForm.userId=localStorage.getItem("userId");
        this.commentForm.userName = localStorage.getItem("username");
        this.commentForm.avatar = localStorage.getItem("avator")
        this.commentForm.type = this.type
        this.commentForm.commentContent = this.textarea
        addComment(this.commentForm)
          .then(res => {
             this.$message({message: res, type: 'success'});
             this.getData();
             this.textarea = ''
          })
          .catch(err => {
            this.$message({message: res, type: 'error'});
          })
      } else {
        this.notify('请先登录', 'warning')
      }
    },
    // 点赞
    postUp (id, likeNum,index,flag) {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('id', id)
        if(this.flag){
            params.append('likeNum',  likeNum + 1)
             this.flag= false;
            this.notify('点赞成功', 'success')
        }else{
            params.append('likeNum',  likeNum - 1)
             this.flag = true;
             this.notify('取消点赞', 'success')
        }
        updateCommentLikeNum(params)
          .then(res => {
              //this.$message({message: res, type: 'success'});
             // this.$refs.likeNum[index].children[0].style.color = '#2796dd'
              this.getData();
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.notify('请先登录', 'warning')
      }
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
      
      // 评论日期和图片的设置
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
      //console.log(url)
      return `${this.$store.state.configure.ONHOST}/${url}`
    },

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>
