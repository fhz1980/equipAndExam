package com.sznikola.equipAndExam.util;

/**
 * @author yzh
 * @Description Young
 * @data 2022-11-18  15:55
 */
public class UseWhileCamera {
    public static int useTwoCamera(){
        int firstCamera = Integer.parseInt(ParameterOperate.extract("firstCamera"));
        if (firstCamera == 0){
            return 1;
        }else if(firstCamera == 1){
            return 0;
        }
        return 2;
    }
}
