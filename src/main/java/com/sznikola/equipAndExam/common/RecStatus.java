package com.sznikola.equipAndExam.common;

import lombok.Getter;

/**
 * @description: 识别人脸的状态
 * @author: yzh
 * @data 2022/9/14 21:12
 * @param:
 * @return:
 **/
@Getter
public enum RecStatus {


    NO_FACE(0, "未检测到到人脸"),
    NO_USER(1, "未识别到您的用户信息"),
    //LACK_JUDGE_USER(2, "请将人脸置于屏幕中央"),
    SERVER_FAILURE(505, "服务器出现故障，请联系管理员"),
    SUCCESS(3, "人脸识别成功");

    RecStatus(int status, String message){
        this.status = status;
        this.message = message;
    }

    private int status;

    private String message;

}
