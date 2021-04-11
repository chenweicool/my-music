import { get, post, deletes } from '../http'


// =======================> 歌手 API
// 返回所有歌手
export const getAllSinger = () => get(`singer/allsinger`)
// 添加歌手
export const setSinger = (params) => post(`singer/addsinger`, params)
// 更新歌手信息
export const updateSingerMsg = (params) => post(`singer/update`, params)
// 删除歌手
export const deleteSinger = (id) => get(`singer/delete?id=${id}`)