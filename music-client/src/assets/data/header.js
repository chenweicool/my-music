// 左侧导航栏
const navMsg = [{
  name: '首页',
  path: '/'
}, {
  name: '歌单',
  path: '/song-list'
}, {
  name: '歌手',
  path: '/singer'
},
{
  name: '热门歌曲',
  path: '/hotSong'
},
{
    name: '每日推荐',
    path: '/todayRecommander'
},

{
  name: '我的音乐',
  path: '/my-music'
}]

// 右侧导航栏
const loginMsg = [{
  name: '登录',
  path: '/login-in'
}, {
  name: '注册',
  path: '/sign-up'
}]

// 用户下拉菜单项
const menuList = [{
  name: '个人中心',
  path: '/setting'
}, 
{
  name: '我的收藏',
  path: '/my-collection'
},
{
  name: '我的创建',
  path: '/my-songList'
},
{
  name: '我的浏览',
  path: '/my-history'
},
{
  name: '退出登录',
  path: 0
}, 
]

export {
  navMsg,
  loginMsg,
  menuList
}
