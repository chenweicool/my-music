<template>
  <div class="content-list" >
    <ul class="section-content">
      <li class="content-item" v-for="(item, index) in contentList" :key="index">
        
        <div class="kuo" @click="goAblum(item, item.name)">
          <img class="item-img" :src="attachImageUrl(item.pic)" alt="">
          <div class="mask"  @click="goAblum(item, item.name)">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-bofang"></use>
            </svg>
          </div>
        </div>
        <p class="item-name">{{item.name || item.title}}</p>
        <div class="collection-icon" @click="collection(item.id)">
           <svg  class="icon" aria-hidden="true">
             <use xlink:href="#icon-xihuan-shi"></use>
           </svg>
         </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import {addCollection} from '../api/system/collection'
export default {
  name: 'content-list',
  mixins: [mixin],
  props: [
    'contentList'
  ],
   data () {
    return {
        centList: this.contentList,
        collectionForm:{
           userId:'',
          songListId:'',
        },
    }
  },
  methods: {
    goAblum (item, type) {
      this.$store.commit('setTempList', item)
      if (type) {
        this.$router.push({path: `/singer-album/${item.id}`})
      } else {
        this.$router.push({path: `/song-list-album/${item.id}`})
      }
    },
    // 添加收藏的方法
      collection (id) {
        if (this.loginIn) {
          this.collectionForm.userId = localStorage.getItem("userId");
          this.collectionForm.songListId = id;
           addCollection(this.collectionForm)
          .then(res => {
            this.$message({message: res, type: 'success'});
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.notify('请先登录', 'warning')
      }
    },
  },

}
</script>

<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
 .collection-icon {
      position: relative;
      width: 10px;
      height: 50px;
      line-height: 10px;
      text-align: center;
      cursor: pointer;
      }
</style>
