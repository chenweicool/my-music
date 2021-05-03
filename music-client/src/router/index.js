import Vue from 'vue'
import Router from 'vue-router'
import LoginIn from '@/pages/LoginIn'
import SignUp from '@/pages/SignUp'
import Home from '@/pages/Home'
import SongList from '@/pages/SongList'
import Singer from '@/pages/Singer'
import MyMusic from '@/pages/MyMusic'
import SongListAlbum from '@/pages/SongListAlbum'
import SingerAlbum from '@/pages/SingerAlbum'
import Search from '@/pages/Search'
import Setting from '@/pages/Setting'
import Lyric from '@/pages/Lyric'
import Recommander from '@/pages/Recommander'
import HotSong from '@/pages/HotSong'

import MySongList from '@/pages/MySongList'
import MyCollection from '@/pages/MyCollection'
import MyHistory from '@/pages/MyHistory'
import MyCreate from '@/pages/MyCreate'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '*',
      redirect: '/404'
    },
    {
      path: '/404',
      component: resolve => require(['../pages/404.vue'], resolve)
    },
    {
      path: '/login-in',
      name: 'login-in',
      component: LoginIn
    },
    {
      path: '/sign-up',
      name: 'sign-up',
      component: SignUp
    },
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/song-list',
      name: 'song-list',
      component: SongList
    },
    {
      path: '/my-music',
      name: 'my-music',
      component: MyMusic
    },
    {
      path: '/song-list-album/:id',
      name: 'song-list-album',
      component: SongListAlbum
    },
    {
      path: '/singer',
      name: 'singer',
      component: Singer
    },
    {
      path: '/singer-album/:id',
      name: 'singer-album',
      component: SingerAlbum
    },
    {
      path: '/lyric/:id',
      name: 'lyric',
      component: Lyric
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    },
    {
      path: '/setting',
      name: 'setting',
      component: Setting
    },
    {
      path: '/hotSong',
      name: 'hotSong',
      component: HotSong
    },
    {
      path: '/todayRecommander',
      name: 'recommander',
      component: Recommander
    },
    
    //个人中心的页面展示
    {
      path: '/my-collection',
      name: 'mycollection',
      component: MyCollection
    },
    {
      path: '/my-songList',
      name: 'mysongList',
      component: MySongList
    },
    {
      path: '/my-create',
      name: 'mycreate',
      component: MyCreate
    },
    {
      path: '/my-history',
      name: 'myhistory',
      component: MyHistory
    },

  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
