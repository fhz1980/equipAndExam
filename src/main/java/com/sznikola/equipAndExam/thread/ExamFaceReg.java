package com.sznikola.equipAndExam.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.equipAndExam.common.ResultJson;
import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

import static com.sznikola.equipAndExam.common.RecStatus.*;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-11-20  14:31
 */
@Slf4j
public class ExamFaceReg implements Runnable {
    @Override
    public void run() {
        EquipAndExamFrame.getInstance().getFaceClick().setText("正在检测人脸");
        //2.人脸识别
        String s = EquipAndExamFrame.fs.judgeMemberData(EquipAndExamFrame.bi1, "userJudge");
        if (Objects.isNull(s) ||"".equals(s)) {
            log.info("系统与服务器断开连接，请重试");
            EquipAndExamFrame.getInstance().getFaceClick().setText("系统与服务器断开连接，请重试");
            EquipAndExamFrame.getInstance().getClickRight().setVisible(true);
            return;
        }

        ResultJson result;
        int code = 0;
        String msg = null;
        Object data = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(s, ResultJson.class);
        } catch (JsonProcessingException e) {
            log.info(String.valueOf(e));
            EquipAndExamFrame.getInstance().getClickRight().setVisible(true);
            throw new RuntimeException(e);
        }

        //得到数据
        code = result.getCode();
        msg = result.getMessage();
        data = result.getData();
        //没有人脸
        if (NO_FACE.getStatus() == code) {
            EquipAndExamFrame.executorService.submit(new ExamFaceReg());
            EquipAndExamFrame.getInstance().getFaceClick().setText("");
            return;
        }else if(NO_USER.getStatus() == code) {
            EquipAndExamFrame.executorService.submit(new ExamFaceReg());
            return;
        }else if (SUCCESS.getStatus() == code) {

            //拿取服务端数据
            String name = null;
            String username = null;

            if ((data instanceof Map) && Objects.nonNull(data)) {
                Map<String, Object> d = (Map) data;
                name = (String) d.get("name");
                username = (String) d.get("username");
                log.info(username);
            }

            EquipAndExamFrame.getInstance().getFaceClick().setText("识别成功");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            EquipAndExamFrame.getInstance().getFaceClick().setText(" ");
            EquipAndExamFrame.getInstance().getStartExamBtn().setVisible(true);
        }
    }
}
