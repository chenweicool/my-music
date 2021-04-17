import { get, post, deletes } from '../http'



// =======================> 歌曲 API
// 返回所有歌曲
export const getAllSongs = () => get(`song/allsongs`)

// 分页查询实现
export const getSongsByPage=(pageNum,pageSize) => get(`song/querySongByPage?pageNum=${pageNum}&pageSize=${pageSize}`);

// 根据歌曲名或者用户名进行查询
export const querySongByName = (pageNum,pageSize,queryName) => get(`song/SongOfQuerySongNameOrSingerName?pageNum=${pageNum}&pageSize=${pageSize}&queryName=${queryName}`);

// 根据歌手id查询其具有的歌曲信息
export const getSongOfSingerId = (pageNum,pageSize,singerId) => get(`song/getSongOfSingerId?pageNum=${pageNum}&pageSize=${pageSize}&singerId=${singerId}`);

// 根据歌单id返回该歌单具有的歌曲信息
export const getSongOfSongListId = (pageNum,pageSize,songListId) => get(`song/getSongOfSongListId?pageNum=${pageNum}&pageSize=${pageSize}&songListId=${songListId}`);

// 返回的指定用户ID收藏列表
export const getSongOfId = (id) => get(`song/detail?id=${id}`)

// 返回指定歌手名的歌曲
export const getSongOfSingerName = (id) => get(`song/singerName/detail?name=${id}`)

// 更新歌曲信息
export const updateSongMsg = (params) => post(`song/update`, params)

// 删除歌曲
export const deleteSong = (id) => get(`song/delete?songId=${id}`)
