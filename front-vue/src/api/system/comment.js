import { get, post, deletes } from '../http'

// =======================> 评论列表 API

// 分页获取所有的评论信息
export const getCommentByPage = (pageNum,pageSize) => get(`comment/getCommentByPage?pageNum=${pageNum}&pageSize=${pageSize}`)

// 获得指定歌曲ID的获得评论的内容
export const getCommentOfSongId = (pageNum,pageSize,songId) => get(`comment/getCommentBySongId?songId=${songId}&pageNum=${pageNum}&pageSize=${pageSize}`)

// 根据歌曲名查询歌曲的评论内容
export const getCommentBySongName = (pageNum,pageSize,songName) => get(`comment/getCommentBySongName?songName=${songName}&pageNum=${pageNum}&pageSize=${pageSize}`)

// 根据用户名查询所有的评论内容
export const getCommentByUserName = (userName) => get(`comment/getCommentByUserName?userName=${userName}`)

// 添加一个评论
export const addComment = (params) => post(`comment/addComment`, params)

// 更新评论
export const updateCommentMsg = (params) => post(`comment/updateComment`, params)

// 删除评论
export const deleteComment = (commentId) => get(`comment/deleteComment?commentId=${commentId}`)


// ===================================回复评论的接口信息=============================================

// 添加回复内容
export const addReplyComment = (params) => post(`replyComment/addReplyComment`,params)

// 更新回复内容
export const updateReplyment = (params) => post(`replyComment/updateReplyComment`,params)

// 查询一个评论下的所有回复内容
export const  getCommentByReplyId = (replyUserId) => get(`replyComment/getCommentByReplyId?replyUserId=${replyUserId}`);

// 删除一个回复评论
export const  deleteReplyComment = (commentId) => get(`replyComment/deleteReplyComment?commentId=${commentId}`);