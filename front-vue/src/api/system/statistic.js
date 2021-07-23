import { get, post, deletes } from '../http'



//==========================用户的信息统计

export const getTotalUsers = () => get(`sysuser/statistic/total`)


export const getUsersex= () => get(`sysuser/statistic/sex`)

export const getUserLocation = () => get(`sysuser/statistic/location`)

export const getAddNewUsers = () => get(`sysuser/statistic/newUsers`)


//====================================歌曲信息统计

export const getMaxPlaySongs = () => get(`song/getMaxPlaySongs`)

export const getMaxComment = () => get(`song/getMaxComment`)

export const getTotalSongs = () => get(`song/getTotalSongs`)


//======================================歌手信息统计

export const getSexSingers = () => get(`singer/getSexSingers`)

export const getMaxSongOfSingers = () => get(`singer/getMaxSongOfSingers`)

export const getTotalSingers = () => get(`singer/getTotalSingers`)

//========================================歌单信息统计

export const getTotalSongList = () => get(`songList/getTotalSongList`)

export const getCategoryNumber = () => get(`songList/getCategoryNumber`)