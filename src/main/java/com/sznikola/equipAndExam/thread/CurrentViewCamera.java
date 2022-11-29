package com.sznikola.equipAndExam.thread;

import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import com.sznikola.equipAndExam.util.ParameterOperate;
import com.sznikola.equipAndExam.util.UseWhileCamera;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.util.ResourceUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.videoio.Videoio.*;

/**
 * @author yzh
 * @Description 设备体验区
 * @email yzhcherish@163.com
 * @data 2022-11-17  22:39
 */
@Slf4j
public class CurrentViewCamera implements Runnable {

    @Override
    public void run() {
        log.info("设备体验端启动");
        int secondCamera = Integer.parseInt(ParameterOperate.extract("USBORWebcam"));
        if(secondCamera == 0){
            deviceOperation();
        }else if(secondCamera == 1){
            while(true){

            }
        }else{
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("未检测到你所需要的摄像头");
        }
    }
    //USB处
    private void deviceOperation(){

        //加载xml模型
        String xmlFileName = null;
        try {
            xmlFileName = ResourceUtils.getFile("res/myopencv/lbpcascade_frontalface_improved.xml").getAbsolutePath();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            throw new RuntimeException(e);
        }
        CascadeClassifier cascadeClassifier = new CascadeClassifier(xmlFileName);
        if (cascadeClassifier.empty()) {
            log.info("加载xml模型文件失败！");
            return;
        }
        //考虑到有两个摄像头，可能会冲突，修改摄像头前后。
        int usTwoCamera = UseWhileCamera.useTwoCamera();
        if(usTwoCamera == 2){
            log.info("未检测到第二摄像头");
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("未检测到第二摄像头");
            //刷新重新加载摄像头设备
            EquipAndExamFrame.getInstance().getMessageLeftButton().setVisible(true);
            return;
        }
        EquipAndExamFrame.capture = new VideoCapture(usTwoCamera);
        if (!EquipAndExamFrame.capture.isOpened()) {
            log.info("未检测到摄像头设备");
            EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("未检测到摄像头设备");
            //刷新重新加载摄像头设备
            EquipAndExamFrame.getInstance().getMessageLeftButton().setVisible(true);
        } else {
            //设置相机参数
            EquipAndExamFrame.capture.set(CAP_PROP_FRAME_WIDTH, 960); //640*480 1280*960 1920*1440
            EquipAndExamFrame.capture.set(CAP_PROP_FRAME_HEIGHT, 1280); //CAP_PROP_FRAME_WIDTH - CAP_PROP_FRAME_HEIGHT/2
            EquipAndExamFrame.capture.set(CAP_PROP_FPS, 30);

            double frame_width = EquipAndExamFrame.capture.get(CAP_PROP_FRAME_WIDTH);
            double frame_height = EquipAndExamFrame.capture.get(CAP_PROP_FRAME_HEIGHT);

            EquipAndExamFrame.frame = new Mat(new Size(960, 1280), CV_8UC3);
            Mat frameGray = new Mat(new Size(960, 1280), CV_8UC1);
            MatOfRect objectsRect = new MatOfRect();

            log.info(String.valueOf(frame_width));
            log.info(String.valueOf(frame_height));

            long pretime = System.currentTimeMillis();
            //读取图像
            //将摄像头导出的图片居中裁剪（x的坐标）
            int biX = (int) ((frame_width - frame_height) / 2);
            boolean state = true;
            while (true){
                //读取图像
                if (!EquipAndExamFrame.capture.read(EquipAndExamFrame.frame)) {
                    log.info("读取相机数据失败");
                    EquipAndExamFrame.getInstance().getEquipInfoLabel().setText("读取相机数据失败");
                    EquipAndExamFrame.getInstance().getMessageLeftButton().setVisible(true);
                    break;
                }
                //切割图片
                //创建一个Rect框，属于cv中的类，四个参数代表x,y,width,height
                Rect rect = new Rect(biX, 0, (int) frame_height, (int) frame_height);
                EquipAndExamFrame.image_cut = new Mat(EquipAndExamFrame.frame, rect);

                //开启线程，将人脸图片传给服务器
                EquipAndExamFrame.bi2 = EquipAndExamFrame.fs.mat2BIW(EquipAndExamFrame.image_cut, EquipAndExamFrame.camerabgSize);
//                if(EquipAndExamFrame.bi2 != null && state){
//                    EquipAndExamFrame.executorService.submit(new USBOperateDevice());
//                    state = false;
//                }

                //转为灰度
                cvtColor(EquipAndExamFrame.image_cut, frameGray, COLOR_BGR2GRAY);
                //人脸检测
                cascadeClassifier.detectMultiScale(frameGray, objectsRect);
                if (!objectsRect.empty()) {
                    //绘制矩形
                    Rect[] rects = objectsRect.toArray();
                    for (Rect r : rects) {
                        rectangle(EquipAndExamFrame.image_cut, r.tl(), r.br(), new Scalar(255, 255, 255), 1);
                    }
                }
                //显示
                BufferedImage bix = EquipAndExamFrame.fs.mat2BIW(EquipAndExamFrame.image_cut, EquipAndExamFrame.camerabgSize);
                EquipAndExamFrame.getInstance().getEquipCameraLabel().setIcon(new ImageIcon(bix));

            }
        }

    }
}
