package com.sznikola.equipAndExam.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sznikola.equipAndExam.common.ResultJson;
import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import com.sznikola.equipAndExam.util.ParameterOperate;
import com.sznikola.equipAndExam.util.UploadUtil;
import com.sznikola.equipAndExam.util.hk.HKDVRVideo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.sznikola.equipAndExam.common.RecStatus.*;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * @author yzh
 * @Description 设备操作情况说明
 * @email yzhcherish@163.com
 * @data 2022-11-18  17:11
 */
@Slf4j
public class USBOperateDevice implements Runnable {

    @Override
    public void run() {
        //1.设备是否开启
        ParameterOperate.extract("commNameInner");
        EquipAndExamFrame.getInstance().getClickEquipImageLabel().setIcon(EquipAndExamFrame.getInstance().getEquipRedIcon());
        //2.人脸识别
        log.info("经过了USB");
        EquipAndExamFrame.vs.VoiceBroadcast("请正对摄像头");
        EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("正在检测人脸");
        Date time = new Date();
        System.out.println(time);
        String say = "正在检测人脸";


//        EquipAndExamFrame.getInstance().getFaceLookImageLabel().setIcon(EquipAndExamFrame.getInstance().getFaceoRedIcon());
        EquipAndExamFrame.vs.VoiceBroadcast(say);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        String s = EquipAndExamFrame.fs.judgeMemberData(EquipAndExamFrame.bi2, "userJudge");
        String s = EquipAndExamFrame.fs.judgeOpenData(EquipAndExamFrame.bi2,"userJudge");
        if (Objects.isNull(s) ||"".equals(s)) {
            log.info("系统与服务器断开连接，请重试");
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("系统与服务器断开连接，请重试");
            EquipAndExamFrame.getInstance().getMessageInternetButton().setVisible(true);
            return;
        }
        ResultJson result;
        int code = 0;
        String msg = null;
        Object data = null;
        try {
            result = EquipAndExamFrame.objectMapper.readValue(s, ResultJson.class);
        } catch (JsonProcessingException e) {
            log.info(String.valueOf(e));
            EquipAndExamFrame.getInstance().getMessageInternetButton().setVisible(true);
            throw new RuntimeException(e);
        }
        //得到数据
        code = result.getCode();
        msg = result.getMessage();
        data = result.getData();

        if (NO_FACE.getStatus() == code) {
//                    message.setText(msg);
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("");
            EquipAndExamFrame.executorService.submit(new USBOperateDevice());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }else if(NO_USER.getStatus() == code) {
            EquipAndExamFrame.executorService.submit(new USBOperateDevice());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }else if (SUCCESS.getStatus() == code) {
//            faceLookImageLabel.setIcon(faceoBlueIcon);
            //人脸识别成功
            //拿取服务端数据
            Integer id = null;
            String name = null;
            String categoryName = null;


            if ((data instanceof Map) && Objects.nonNull(data)) {
                Map<String, Object> d = (Map) data;
                id = (Integer) d.get("id");
                name = (String) d.get("name");
                categoryName = (String) d.get("categoryName");

                log.info(name);
            }
//            String categoryName = ParameterOperate.extract("categoryName");
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText(name + "，正在体验设备" + categoryName);
            Date date = new Date();
            System.out.println(date);
            EquipAndExamFrame.vs.VoiceBroadcast(name +",请体验" + categoryName);
//            EquipAndExamFrame.getInstance().getFaceLookImageLabel().setIcon(EquipAndExamFrame.getInstance().getFaceoBlueIcon());

            boolean flag = true;
            long pre = System.currentTimeMillis();
            long current = pre;
            File photo = null;

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            String saveVideo = MessageFormat.format(".\\res\\temporaryimg\\{0}.mp4", UUID.randomUUID().toString());
            String savePic = MessageFormat.format(".\\res\\temporaryimg\\{0}.jpg", UUID.randomUUID().toString());

//            final VideoWriter videoWriter = new VideoWriter(saveVideo, VideoWriter.fourcc('m', 'p', '4', 'v'), 30, new Size(EquipAndExamFrame.camerabgSize, EquipAndExamFrame.camerabgSize));

            //网络摄像头保存两秒的操作视频
            HKDVRVideo bt = new HKDVRVideo();
            String ip = ParameterOperate.extract("ip");
            int port = Integer.parseInt(ParameterOperate.extract("port"));
            String videoUserename = ParameterOperate.extract("videoUsername");
            String videoPassword = ParameterOperate.extract("videoPassword");
            int channel = Integer.parseInt(ParameterOperate.extract("channel"));
            bt.getVideo(ip, port, videoUserename,videoPassword, channel);

            //获取网络摄像头存储路径
            String saveVideo = HKDVRVideo.getSaveVideo();

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
            //保存信息
            String st = EquipAndExamFrame.fs.judgeCloseData(id, name, saveVideo,"UserCloseDevice");
            //关闭
            EquipAndExamFrame.vs.VoiceBroadcast("体验结束");
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("请体验");
        }
        else {
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("服务器出现故障，请联系管理员");
        }
    }

}
