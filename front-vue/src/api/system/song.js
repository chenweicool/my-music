import { get, post, deletes } from '../http'



// =======================> 歌曲 API
// 返回所有歌曲
export const getAllSongs = () => get(`song/allsongs`)

// 返回的指定用户ID收藏列表
export const getSongOfId = (id) => get(`song/detail?id=${id}`)
// 返回指定歌手名的歌曲
export const getSongOfSingerName = (id) => get(`song/singerName/detail?name=${id}`)
// 更新歌曲信息
export const updateSongMsg = (params) => post(`song/update`, params)
// 删除歌曲
export const deleteSong = (id) => get(`song/delete?songId=${id}`)
