package com.sznikola.equipAndExam.common;

import lombok.Getter;

@Getter
public enum ResultCode implements ICode {

    SUCCESS(2000, "操作成功"),
    ON_DUTY_PUNCHCARD_SUCCESS(2001, "上班打卡成功"),
    OFF_DUTY_PUNCHCARD_SUCCESS(2002, "下班打卡成功"),
    OVER_PUNCHCARD_SUCCESS(2003, "重复打卡"),
    UPDATE_DUTY_PUNCHCARD_SUCCESS(2004, "下班打卡更新成功"),
    NO_VOICE(2005, "人脸识别接口调用成功（不播报信息）"),

    ERROR(5000, "操作失败"),
    NO_REC(50000, "人脸识别不成功");

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    private String message;

    private int code;
}
