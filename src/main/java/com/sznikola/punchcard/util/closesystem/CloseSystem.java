package com.sznikola.punchcard.util.closesystem;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-07  14:56
 */
public class CloseSystem {
    Runtime rTime = Runtime.getRuntime();
    int time = 100;//设置关机时间
    String input = "我是你爸爸";

    public void close() {
        try {
            rTime.exec("shutdown -s -t " + time);
        } catch (Exception e) {
        }
    }

    public void cancel() {
        try {
            rTime.exec("shutdown -a");//取消关机
        } catch (Exception e) {
        }
    }
}
