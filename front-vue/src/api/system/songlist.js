import { get, post, deletes } from '../http'


// 添加歌单
export const setSongList = (params) => post(`songList/addsonglist`, params)
// 获取全部歌单
export const getSongList = () => get(`songList/allsinglist`)
// 更新歌单信息
export const updateSongListMsg = (params) => post(`songList/update`, params)
// 删除歌单
export const deleteSongList = (id) => get(`songList/delete?id=${id}`)