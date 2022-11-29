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
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-11-20  12:23
 */
@Slf4j
public class ExamFace implements Runnable {
    @Override
    public void run() {
        log.info("考试端启动");
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
        //考虑到有两个摄像头，可能会冲突，修改摄像头前后。第一个摄像头
        int firstCamera = Integer.parseInt(ParameterOperate.extract("firstCamera"));
        EquipAndExamFrame.capture1 = new VideoCapture(firstCamera);
        if (!EquipAndExamFrame.capture1.isOpened()) {
            log.info("未检测到摄像头设备");
            EquipAndExamFrame.getInstance().getFaceClick().setText("未检测到摄像头设备");
            //刷新重新加载摄像头设备
            EquipAndExamFrame.getInstance().getClickRight().setVisible(true);
        }else {
            //设置相机参数
            EquipAndExamFrame.capture1.set(CAP_PROP_FRAME_WIDTH, 960); //640*480 1280*960 1920*1440
            EquipAndExamFrame.capture1.set(CAP_PROP_FRAME_HEIGHT, 1280); //CAP_PROP_FRAME_WIDTH - CAP_PROP_FRAME_HEIGHT/2
            EquipAndExamFrame.capture1.set(CAP_PROP_FPS, 30);

            double frame_width = EquipAndExamFrame.capture1.get(CAP_PROP_FRAME_WIDTH);
            double frame_height = EquipAndExamFrame.capture1.get(CAP_PROP_FRAME_HEIGHT);

            Mat frame1 = new Mat(new Size(960, 1280), CV_8UC3);
            Mat frameGray = new Mat(new Size(960, 1280), CV_8UC1);
            MatOfRect objectsRect = new MatOfRect();

            log.info(String.valueOf(frame_width));
            log.info(String.valueOf(frame_height));

            long pretime = System.currentTimeMillis();
            //读取图像
            //将摄像头导出的图片居中裁剪（x的坐标）
            int biX = (int) ((frame_width - frame_height) / 2);

            boolean state = true;
            while (true) {
                //读取图像
                if (!EquipAndExamFrame.capture1.read(frame1)) {
                    log.info("读取相机数据失败");
                    EquipAndExamFrame.getInstance().getFaceClick().setText("未检测到摄像头设备");
                    EquipAndExamFrame.getInstance().getClickRight().setVisible(true);
                    break;
                }
                //切割图片
                //创建一个Rect框，属于cv中的类，四个参数代表x,y,width,height
                Rect rect = new Rect(biX, 0, (int) frame_height, (int) frame_height);
                Mat image_cut1 = new Mat(frame1, rect);
                //开启线程，将人脸图片传给服务器
                EquipAndExamFrame.bi1 = EquipAndExamFrame.fs.mat2BIW(image_cut1, 550);

                //转为灰度
                cvtColor(image_cut1, frameGray, COLOR_BGR2GRAY);

                //人脸检测
                cascadeClassifier.detectMultiScale(frameGray, objectsRect);
                if (!objectsRect.empty()) {
                    //绘制矩形
                    Rect[] rects = objectsRect.toArray();
                    for (Rect r : rects) {
                        rectangle(image_cut1, r.tl(), r.br(), new Scalar(255, 255, 255), 2);
                    }
                }
                //显示
                BufferedImage bi1x = EquipAndExamFrame.fs.mat2BIW(image_cut1, 550);
                EquipAndExamFrame.getInstance().getExamCameraLabel().setIcon(new ImageIcon(bi1x));

            }
        }
    }
}
