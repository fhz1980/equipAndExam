package com.sznikola.equipAndExam.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.equipAndExam.common.ResultJson;
import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import com.sznikola.equipAndExam.util.UploadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.sznikola.equipAndExam.common.RecStatus.*;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-11-20  14:31
 */
@Slf4j
public class ExamFaceReg implements Runnable {

    public static String name;
    public static String username;

    @Override
    public void run() {
        EquipAndExamFrame.getInstance().getFaceClick().setText("正在检测人脸");
        EquipAndExamFrame.vs.VoiceBroadcast("正在检测人脸");
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
            name = null;
            username = null;

            if ((data instanceof Map) && Objects.nonNull(data)) {
                Map<String, Object> d = (Map) data;
                name = (String) d.get("name");
                username = (String) d.get("username");
                log.info(username);
            }

            log.info("识别成功");
            EquipAndExamFrame.getInstance().getFaceClick().setText("识别成功");
            EquipAndExamFrame.vs.VoiceBroadcast("识别成功");
            EquipAndExamFrame.vs.VoiceBroadcast(name +",请开始考试");
            EquipAndExamFrame.getInstance().getFaceLookImageLabel().setIcon(EquipAndExamFrame.getInstance().getFaceoBlueIcon());

            boolean flag = true;
            long pre = System.currentTimeMillis();
            long current = pre;
            File photo = null;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String savePic = MessageFormat.format(".\\res\\temporaryimg\\{0}.jpg", UUID.randomUUID().toString());

            //拍照
            boolean b;
            while (true && EquipAndExamFrame.useState) {
                if ((current - pre) / 1000 == EquipAndExamFrame.timeCamera && flag) {
                    imwrite(savePic, EquipAndExamFrame.frame);  //保存图片
                    photo = new File(savePic);
                    b = UploadUtil.build().uploadFile("img", new File(savePic));
                    log.info(String.valueOf(b));
                }
                current = System.currentTimeMillis();
            }
            //删除图片视频信息
            if(Objects.nonNull(photo)){
                photo.delete();
            }

            EquipAndExamFrame.getInstance().getFaceClick().setText(" ");
            EquipAndExamFrame.getInstance().getStartExamBtn().setVisible(true);
        }
    }
}
