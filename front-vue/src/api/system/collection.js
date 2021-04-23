import { get, post, deletes } from '../http'

// =======================> 收藏列表 API

// 根据用户的id返回用户的收藏的歌单信息
export const getSongListByUserId = (pageNum,pageSize,userId) => get(`collection/getCollectsByUserId?userId=${userId}&pageNum=${pageNum}&pageSize=${pageSize}`)
