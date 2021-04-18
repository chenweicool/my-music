import { get, post, deletes } from '../http'


// =======================> 歌手 API

// 添加歌手
export const addSinger = (params) => post(`singer/addSinger`, params)
// 更新歌手信息
export const updateSingerMsg = (params) => post(`singer/update`, params)
// 删除歌手
export const deleteSinger = (id) => get(`singer/delete?id=${id}`)

//=========================查询的接口信息

// 分页查询实现
export const getSingerByPage=(pageNum,pageSize) => get(`singer/getSingerByPage?pageNum=${pageNum}&pageSize=${pageSize}`);

// 根据歌曲名或者用户名进行查询
export const getSingerBySingerName = (pageNum,pageSize,singerName) => get(`singer/getSingerBySingerName?pageNum=${pageNum}&pageSize=${pageSize}&singerName=${singerName}`);

// 根据歌手的地域查询
export const getSingerByAge = (pageNum,pageSize,age) => get(`singer/getSingerByAge?pageNum=${pageNum}&pageSize=${pageSize}&age=${age}`);

// 根据歌手的性别查询
export const getSingerBySex = (pageNum,pageSize,sex) => get(`singer/getSingerBySex?pageNum=${pageNum}&pageSize=${pageSize}&sex=${sex}`);