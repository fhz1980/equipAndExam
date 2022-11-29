package com.sznikola;


import com.sznikola.devicestate.frame.DeviceState;
import com.sznikola.devicestate.frame.DeviceStateClosed;
import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YOUNG
 * @Description 程序主入口
 * @data 2022-10-25  14:16
 */
@Slf4j
@SpringBootApplication
public class EquipAndExam {
    public static void main(String[] args) {
        log.info("程序启动");
        DeviceState deviceState = DeviceState.getInstance();
        deviceState.setVisible(true);
    }
}
