import { get, post, deletes } from '../http'




//=====================================查询的接口
// 分页查询实现
export const getSongListByPage=(pageNum,pageSize) => get(`songList/getSongListByPage?pageNum=${pageNum}&pageSize=${pageSize}`);

// 根据查询我创建歌单信息
export const getMySongList = (pageNum,pageSize,userId) => get(`songList/getMySongList?pageNum=${pageNum}&pageSize=${pageSize}&userId=${userId}`);

// 根据歌单标题查询
export const getTitleByPage = (pageNum,pageSize,title) => get(`songList/getTitleByPage?pageNum=${pageNum}&pageSize=${pageSize}&title=${title}`);

// 根据歌单的风格
export const getStyleByPage = (pageNum,pageSize,style) => get(`songList/getStyleByPage?pageNum=${pageNum}&pageSize=${pageSize}&style=${style}`);

// 获取最新的歌单信息
export const  getSongListByHot = () => get(`/songList/getSongListHot`)

//========================================编辑的接口信息
// 添加歌单
export const addSongList = (params) => post(`songList/addSongList`, params)

// 更新歌单信息
export const updateSongListMsg = (params) => post(`songList/update`, params)

// 删除歌单
export const deleteSongList = (id) => get(`songList/delete?id=${id}`)

// 添加歌曲到指定的歌单
export const addSongToSongList = (params) => post(`songList/addSongToSongList`,params)

// 从歌单中移除歌曲
export const deleteSongToSongList = (params) => post(`songList/deleteSongToSongList`,params)


//===============歌单的评分接口的实现

// 提交评分
export const setRank = (params) => post(`rank/add`, params)
// 获取指定歌单的评分
export const getRankOfSongListId = (songListId) => get(`rank/getSongList?songListId=${songListId}`)

