<template>
  <div class="search-song-Lists">
    <content-list :contentList="albumDatas"></content-list>
  </div>
</template>

<script>
import ContentList from '../ContentList'
import { getTitleByPage } from '../../api/system/songlist'

export default {
  name: 'search-song-Lists',
  components: {
    ContentList
  },
  data () {
    return {
      albumDatas: []
    }
  },
  mounted () {
    this.getSearchList()
  },
  methods: {
    getSearchList () {
      if (!this.$route.query.keywords) {
        this.notify('您输入内容为空', 'warning')
      } else if (this.$route.query.keywords) {
        getTitleByPage(1,20,this.$route.query.keywords)
          .then(res => {
           if(res.records!= null){
             this.albumDatas = res.records
            }else{ 
               this.albumDatas= null;
               this.$message({message: res, type: 'success'});
             }
           //console.log(res)
          })
      } else {
        this.notify('暂无该歌曲内容', 'error')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-song-Lists.scss';
</style>
