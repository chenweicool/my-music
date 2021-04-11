import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations'
import getters from './getters'
import actions from './actions'
import system from './modules/system'
import maintabs from './modules/maintabs'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    HOST: 'http://localhost:8888',
    isPlay: false,
    url: '',
    id: ''
  },
  getters: {
    isPlay: state => state.isPlay,
    url: state => state.url,
    id: state => state.id
  },
  mutations: {
    setIsPlay: (state, isPlay) => { state.isPlay = isPlay },
    setUrl: (state, url) => { state.url = url },
    setId: (state, id) => { state.id = id }
  },
  actions,
  modules: {
          system,
          maintabs
      },
})

export default store

// export default new Vuex.Store({
//   state: {
//   },
//   mutations,
//   actions,
//   getters,
//   modules: {
//       system,
//       maintabs
//   }
// })
