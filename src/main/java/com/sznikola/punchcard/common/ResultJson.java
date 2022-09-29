package com.sznikola.punchcard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yzh
 * @Description 接口统一响应结果
 * @email yzhcherish@163.com
 * @data 2022-09-13  17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultJson {

    // 状态码
    private Integer code;

    // 消息
    private String message;

    // 响应数据
    private Object data;


    private static ResultJson build(Integer code, String message, Object data){
        return new ResultJson(code, message, data);
    }

    private static ResultJson build0(ICode code, Object data){
        return new ResultJson(code.getCode(), code.getMessage(), data);
    }

    public static ResultJson ok(){
        return build0(ResultCode.SUCCESS, null);
    }

    public static ResultJson ok(String message){
        return build(ResultCode.SUCCESS.getCode(), message, null);
    }

    public static ResultJson ok(int code, String message){
        return build(code, message, null);
    }

    public static ResultJson ok(ICode code){
        return build0(code, null);
    }

    public static ResultJson ok(Object data){
        return build0(ResultCode.SUCCESS, data);
    }

    public static ResultJson ok(int code, String message, Object data){
        return build(code, message, data);
    }

    public static ResultJson ok(ICode code, Object data){
        return build0(code, data);
    }

    public static ResultJson ok(ICode code, String message, Object data){
        return build(code.getCode(), message, data);
    }

    public static ResultJson failed(){
        return build0(ResultCode.ERROR, null);
    }

    public static ResultJson failed(String message){
        return build(ResultCode.ERROR.getCode(), message, null);
    }

    public static ResultJson failed(int code, String message){
        return build(code, message, null);
    }

    public static ResultJson failed(ICode code){
        return build0(code, null);
    }

    public static ResultJson failed(ICode code, String message){
        return build(code.getCode(), message, null);
    }

}
