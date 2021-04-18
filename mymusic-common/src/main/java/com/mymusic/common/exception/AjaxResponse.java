package com.mymusic.common.exception;

import com.mymusic.common.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class AjaxResponse {

  private Boolean isok;  //请求是否处理成功

  private Integer code; //请求响应状态码（200、400、500）

  private String message;  //请求结果描述信息

  private Object data; //请求结果数据（通常用于查询操作）

  private AjaxResponse(){}

  //请求出现异常时的响应数据封装
  public static AjaxResponse error(CustomException e) {
    AjaxResponse resultBean = new AjaxResponse();
    resultBean.setIsok(false);
    resultBean.setCode(e.getCode());
    resultBean.setMessage(e.getMessage());
    return resultBean;
  }



  //请求出现异常时的响应数据封装
  public static AjaxResponse error(CustomExceptionType customExceptionType,
                                   String errorMessage) {
    AjaxResponse resultBean = new AjaxResponse();
    resultBean.setIsok(false);
    resultBean.setCode(customExceptionType.getCode());
    resultBean.setMessage(errorMessage);
    return resultBean;
  }

  /**
   * 出现异常的信息处理
   * @param message
   * @return
   */
  public static AjaxResponse error(String message) {
    AjaxResponse response = new AjaxResponse();
    response.setIsok(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
    response.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
    response.setMessage(message);
    return response;
  }

  /**
   * 异常信息的处理的类
   * @param resultCodeEnum  定义的异常信息类
   * @return
   */
  public static AjaxResponse setResult(ResultCodeEnum resultCodeEnum){
    AjaxResponse responseBean = new AjaxResponse();
    responseBean.setCode(resultCodeEnum.getCode());
    responseBean.setIsok(resultCodeEnum.getSuccess());
    responseBean.setMessage(resultCodeEnum.getMessage());
    return  responseBean;
  }



  //请求成功的响应，不带查询数据（用于删除、修改、新增接口）
  public static AjaxResponse success(){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage("请求响应成功!");
    return ajaxResponse;
  }

  //请求成功的响应，带有查询数据（用于数据查询接口）
  public static AjaxResponse success(Object obj){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage("请求响应成功!");
    ajaxResponse.setData(obj);
    return ajaxResponse;
  }

  //请求成功的响应，带有查询数据（用于数据查询接口）
  public static AjaxResponse success(Object obj,String message){
    AjaxResponse ajaxResponse = new AjaxResponse();
    ajaxResponse.setIsok(true);
    ajaxResponse.setCode(200);
    ajaxResponse.setMessage(message);
    ajaxResponse.setData(obj);
    return ajaxResponse;
  }

}
