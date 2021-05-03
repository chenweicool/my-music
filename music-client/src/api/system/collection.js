import { get, post, deletes } from '../http'

// =======================> 收藏列表 API

// 根据用户的id返回用户的收藏的歌单或者歌曲信息
export const getSongListByUserId = (pageNum,pageSize,userId,type) => get(`collection/getCollectsByUserId?userId=${userId}&pageNum=${pageNum}&pageSize=${pageSize}&type=${type}`)

// 添加收藏歌曲
export const addCollection = (param) => post(`/collection/add`,param)