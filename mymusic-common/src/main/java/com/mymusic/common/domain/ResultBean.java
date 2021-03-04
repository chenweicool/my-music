package com.mymusic.common.domain;
import com.mymusic.common.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 全统一返回处理结果
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResultBean {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回的数据")
    private Map<String, Object> data = new HashMap<>();

    public ResultBean(){}

    /**
     * 默认的成功的消息
     * @return
     */
    public static  ResultBean ok(){
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        resultBean.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultBean.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return  resultBean;
    }

    /**
     * 失败返回
     * @return 失败返回结果
     */
    public static ResultBean error(){
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        resultBean.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        resultBean.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return resultBean;
    }

    /**
     * 设置返回的状态码
     * @param result 枚举类型
     * @return  返回设置的结果
     */
    public static ResultBean setResult(ResultCodeEnum result){
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(result.getSuccess());
        resultBean.setCode(result.getCode());
        resultBean.setMessage(result.getMessage());
        return  resultBean;
    }

    public ResultBean success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ResultBean code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ResultBean message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultBean data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    public ResultBean data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
