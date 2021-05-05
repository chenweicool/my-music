import Login from '@/views/Login.vue'
import FirstPage from '@/views/system/FirstPage.vue'
import toolsRouter from '@/router/modules/tools'

//菜单项级别的前端路由
const menuRouter = [
    {path:"",redirect: 'firstpage' },
    {
        name: 'firstpage',
        path: 'firstpage',
        component: FirstPage
    },
    {
        name: 'sysuser',
        path: 'sysuser',
        component: () => import('@/views/system/SystemUser.vue')
    },
    {
        name: 'sysrole',
        path: 'sysrole',
        component: () => import('@/views/system/SystemRole.vue')
    },
    {
        name: 'sysorg',
        path: 'sysorg',
        component: () => import('@/views/system/SystemOrg.vue')
    },
    {
      name: 'sysmenu',
      path: 'sysmenu',
      component: () => import('@/views/system/SystemMenu.vue')
    },
    {
      name: 'sysapi',
      path: 'sysapi',
      component: () => import('@/views/system/SystemApi.vue')
    },
    {
      name: 'sysconfig',
      path: 'sysconfig',
      component: () => import('@/views/system/SystemConfig.vue')
    },
    {
      name: 'sysdict',
      path: 'sysdict',
      component: () => import('@/views/system/SystemDict.vue')
    },
    {
        name: 'personal',
        path: 'personal',
        component: () => import('@/views/system/PersonalCenter.vue')
    },

    {
      name: 'singer',
      path: 'singer',
      component: () => import('@/views/system/SingerPage.vue')
    },
    {
      name: 'song',
      path: 'song',
      component: () => import('@/views/system/SongPage.vue')
    }, 
    
    {
      name: 'comment',
      path: 'comment',
      component: () => import('@/views/system/CommentPage.vue')
    },  
    
    {
      name: 'songcatagory',
      path: 'songcatagory',
      component: () => import('@/views/system/SongCategoryPage.vue')
    },
    {
      name: 'songlist',
      path: 'songlist',
      component: () => import('@/views/system/SongListPage.vue')
    },
    {
      name: 'center',
      path: 'center',
      component: () => import('@/views/system/UserCenterPage.vue')
    },

    // ==============================个人中心的页面路由信息
    // 个人收藏信息
    {
      name: 'myCollection',
      path: 'myCollection',
      component: () => import('@/views/system/MyCollection.vue')
    },

    // 我创建的歌单信息
    {
      name: 'myCreateSongList',
      path: 'myCreateSongList',
      component: () => import('@/views/system/MyCreateSongList.vue')
    },

    // 我的评论
    {
      name: 'myComment',
      path: 'myComment',
      component: () => import('@/views/system/MyComment.vue')
    },

    // 我的浏览历史
    {
      name: 'myHistory',
      path: 'myHistory',
      component: () => import('@/views/system/MyHistory.vue')
    },

    // 我的听歌统计信息
    {
      name: 'myListenStatistic',
      path: 'myListenStatistic',
      component: () => import('@/views/system/PlayMusic.vue')
    },
   
    //============================统计信息的实现
    {
      name: 'userstatic',
      path: 'userstatic',
      component: () => import('@/views/system/UserstaticPage.vue')
    }, 
    {
      name: 'singerstatic',
      path: 'singerstatic',
      component: () => import('@/views/system/SingerstaticPage.vue')
    }, 
    {
      name: 'songliststatic',
      path: 'songliststatic',
      component: () => import('@/views/system/SongliststaticPage.vue')
    },

];

export default [
  {
      path: '/',
      name: 'login',
      component: Login
  },
  {
    path: '/home',
    //name: 'home',
    component: () => import( '@/views/Home.vue'),
    children:[
        ...menuRouter,
        ...toolsRouter
    ]
  }
]