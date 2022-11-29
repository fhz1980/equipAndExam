package com.sznikola.devicestate.frame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.devicestate.service.FaceService;
import com.sznikola.equipAndExam.frame.EquipAndExamFrame;
import com.sznikola.equipAndExam.manager.SerialPortManager;
import com.sznikola.equipAndExam.util.SpringContextUtil;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.videoio.Videoio.*;
/*
 * Created by JFormDesigner on Wed Nov 16 19:13:57 CST 2022
 */


/**
 * @author Young
 */
@Slf4j
@Getter
@Setter
public class DeviceState extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static int camerabgSize = 550;
    public static BufferedImage bi1 = null;
    public static BufferedImage bi2 = null;
    public static FaceService fs = new FaceService();
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static Mat image_cut =  null;
    public static Mat frame =  null;
    public static int timeCamera = Integer.parseInt(ParameterOperate.extract("timeCamera"));
    private static SerialPort mSerialport;
    private int timeSleep = 3;
    BufferedImage bi = null;
    public static VideoCapture capture = null;
    static {
        try{
            //new File(String.valueOf(ResourceUtils.getFile("classpath:img/devicecontrol/face.png"))))
            camerabg = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", DeviceState.class.getClassLoader()).getInputStream()).getScaledInstance(camerabgSize, camerabgSize, Image.SCALE_AREA_AVERAGING);
            camerabg2 = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", DeviceState.class.getClassLoader()).getInputStream()).getScaledInstance(camerabgSize, camerabgSize, Image.SCALE_AREA_AVERAGING);
            refreshInternet = ImageIO.read(new ClassPathResource("img/sign/refresh.png", DeviceState.class.getClassLoader()).getInputStream()).getScaledInstance(22, 22, Image.SCALE_DEFAULT);
            frameImage = ImageIO.read(new ClassPathResource("img/equipAndExam/nikola.png", DeviceState.class.getClassLoader()).getInputStream());
            faceoBlue = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoBlue.png", DeviceState.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
            faceoRed = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoRed.png", DeviceState.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);

        }catch (IOException e) {
            log.info("图片和图标加载失败", e);
        }
    }

    private static DeviceState equipAndExamFrame = new DeviceState();

    public static RestTemplate restTemplate;

    {
        RestTemplate restTemplate1 = SpringContextUtil.getBean("restTemplate", RestTemplate.class);
        if (Objects.isNull(restTemplate1)){
            restTemplate1 = new RestTemplate();
        }
        restTemplate = restTemplate1;
    }
    public static  ObjectMapper om = new ObjectMapper();

    private DeviceState(){
        initComponents();
        //摄像头实时显示
        executorService.submit(new FaceAdmin());
        executorService.submit(new OpenEquipFrameRun());
    }

    public static DeviceState getInstance(){
        return equipAndExamFrame;
    }

    public static DeviceState newInstance(){
        return new DeviceState();
    }

    private  JPanel contentPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel cententInfoPanel;
    private JPanel panel4;
    private JPanel equipAllPanel;
    private JPanel equipPanel;
    private JPanel equipFlowPanel;
    private JLabel equipNameLabel;
    private JPanel clickEquipPanel;
    private JPanel clickEquipFPanel;
    private JLabel clickEquipLabel;
    private JLabel clickEquipImageLabel;
    private JPanel faceLookPanel;
    private JPanel faceLookFPanel;
    private JLabel faceLookLabel;
    private JLabel faceLookImageLabel;
    private JPanel equipCameraPanel;
    private JPanel equipCameraFPanel;
    private JLabel equipCameraLabel;
    private JPanel equipInfopanel;
    private JPanel equipInfoFpanel;
    private JLabel equipInfoLabel;
    private JButton messageLeftButton;
    private JButton messageInternetButton;
    private JPanel namePanel;
    private JPanel nameFPanel;
    private JButton startOpenBtn;
    private JPanel usernamePanel;
    private JPanel usernameFpanel;
    private JPanel panel7;
    private JPanel panel8;
    private Color examColor;
    private ImageIcon cameraIcon;
    private ImageIcon cameraIcon2;
    private ImageIcon faceoBlueIcon;
    private ImageIcon faceoRedIcon;
    private static ImageIcon refreshIcon;
    private static Image camerabg = null;
    private static Image camerabg2 = null;
    private static Image signcamerabg = null;
    private static Image refreshInternet = null;
    private static Image frameImage = null;
    private static Image faceoBlue = null;
    private static Image faceoRed = null;

    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        contentPanel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        cententInfoPanel = new JPanel();
        panel4 = new JPanel();
        equipAllPanel = new JPanel();
        equipPanel = new JPanel();
        equipFlowPanel = new JPanel();
        equipNameLabel = new JLabel();
        clickEquipPanel = new JPanel();
        clickEquipFPanel = new JPanel();
        clickEquipLabel = new JLabel();
        clickEquipImageLabel = new JLabel();
        faceLookPanel = new JPanel();
        faceLookFPanel = new JPanel();
        faceLookLabel = new JLabel();
        faceLookImageLabel = new JLabel();
        equipCameraPanel = new JPanel();
        equipCameraFPanel = new JPanel();
        equipCameraLabel = new JLabel();
        equipInfopanel = new JPanel();
        equipInfoFpanel = new JPanel();
        equipInfoLabel = new JLabel();
        messageLeftButton = new JButton();
        messageInternetButton = new JButton();
        namePanel = new JPanel();
        nameFPanel = new JPanel();
        startOpenBtn = new JButton();
        usernamePanel = new JPanel();
        usernameFpanel = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        cameraIcon = new ImageIcon();
        cameraIcon2 = new ImageIcon();
        faceoRedIcon = new ImageIcon(faceoRed);
        faceoBlueIcon= new ImageIcon(faceoBlue);
        refreshIcon = new ImageIcon();
        examColor = new Color(0, 85, 255);

        //======== this ========
        setBackground(new Color(0xfbfbfb));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== contentPanel ========
        {
            contentPanel.setBackground(new Color(0xfbfbfb));
            contentPanel.setPreferredSize(new Dimension(1024, 768));
            contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
            contentPanel.setLayout(new BorderLayout());

            //======== panel1 ========
            {
                panel1.setBackground(new Color(0xfbfbfb));
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

                //======== panel2 ========
                {
                    panel2.setBackground(new Color(0xfbfbfb));
                    panel2.setLayout(new BorderLayout());
                }
                panel1.add(panel2);

                //======== cententInfoPanel ========
                {
                    cententInfoPanel.setBackground(new Color(0xfbfbfb));
                    cententInfoPanel.setLayout(new BoxLayout(cententInfoPanel, BoxLayout.X_AXIS));

                    //======== panel4 ========
                    {
                        panel4.setBackground(new Color(0xfbfbfb));
                        panel4.setLayout(new BorderLayout());
                    }
                    cententInfoPanel.add(panel4);

                    //======== equipAllPanel ========
                    {
                        equipAllPanel.setBackground(new Color(0xfbfbfb));
                        equipAllPanel.setBorder(new LineBorder(new Color(0x0055ff)));
                        equipAllPanel.setLayout(new BorderLayout());

                        //======== equipPanel ========
                        {
                            equipPanel.setBackground(new Color(0xfbfbfb));
                            equipPanel.setLayout(new BorderLayout());

                            //======== equipFlowPanel ========
                            {
                                equipFlowPanel.setBackground(new Color(0xfbfbfb));
                                equipFlowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 85, 255)));
                                equipFlowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                                //---- equipNameLabel ----
                                equipNameLabel.setText("设备开启");
                                equipNameLabel.setForeground(examColor);
                                equipNameLabel.setBorder(new EmptyBorder(3, 8, 3, 3));
                                equipNameLabel.setBackground(new Color(0xfbfbfb));
                                equipNameLabel.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 20));
                                equipFlowPanel.add(equipNameLabel);


                            }
                            equipPanel.add(equipFlowPanel, BorderLayout.NORTH);

                            //======== clickEquipPanel ========
                            {
                                clickEquipPanel.setBackground(new Color(0xfbfbfb));
                                clickEquipPanel.setLayout(new BorderLayout());

                                //======== clickEquipFPanel ========
                                {
                                    clickEquipFPanel.setBackground(new Color(0xfbfbfb));
                                    clickEquipFPanel.setLayout(new FlowLayout());
                                }
                                clickEquipPanel.add(clickEquipFPanel, BorderLayout.PAGE_START);

                                //======== faceLookPanel ========
                                {
                                    faceLookPanel.setBackground(new Color(0xfbfbfb));
                                    faceLookPanel.setLayout(new BorderLayout());

                                    //======== faceLookFPanel ========
                                    {
                                        faceLookFPanel.setBackground(new Color(0xfbfbfb));
                                        faceLookFPanel.setFont(faceLookFPanel.getFont().deriveFont(faceLookFPanel.getFont().getSize() + 7f));
                                        faceLookFPanel.setLayout(new FlowLayout());

                                    }
                                    faceLookPanel.add(faceLookFPanel, BorderLayout.NORTH);

                                    //======== equipCameraPanel ========
                                    {
                                        equipCameraPanel.setBackground(new Color(0xfbfbfb));
                                        equipCameraPanel.setLayout(new BorderLayout());

                                        //======== equipCameraFPanel ========
                                        {
                                            equipCameraFPanel.setBackground(new Color(0xfbfbfb));
                                            equipCameraFPanel.setLayout(new FlowLayout());
                                            equipCameraFPanel.setBorder(new EmptyBorder(20, 20, 0, 20));

                                            //---- equipCameraLabel ----
                                            equipCameraLabel.setForeground(examColor);
                                            equipCameraLabel.setBorder(BorderFactory.createEtchedBorder());
                                            equipCameraLabel.setPreferredSize(new Dimension(camerabgSize,camerabgSize));
//                                            equipCameraLabel.setBorder(new LineBorder(new Color(211, 246, 255)));
                                            cameraIcon.setImage(camerabg2);
                                            equipCameraLabel.setIcon(cameraIcon);
                                            equipCameraFPanel.add(equipCameraLabel);
                                        }
                                        equipCameraPanel.add(equipCameraFPanel, BorderLayout.NORTH);

                                        //======== equipInfopanel ========
                                        {
                                            equipInfopanel.setBackground(new Color(0xfbfbfb));
                                            equipInfopanel.setLayout(new BorderLayout());

                                            //======== equipInfoFpanel ========
                                            {
                                                equipInfoFpanel.setBackground(new Color(0xfbfbfb));
                                                equipInfoFpanel.setLayout(new FlowLayout());

                                                //---- equipInfoLabel ----
                                                equipInfoLabel.setText(" ");
                                                equipInfoLabel.setForeground(examColor);
                                                equipInfoLabel.setForeground(Color.black);
                                                equipInfoLabel.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 18));
                                                equipInfoFpanel.add(equipInfoLabel);

                                                //======= messageLeftButton =======
                                                {
                                                    refreshIcon.setImage(refreshInternet);
                                                    messageLeftButton.setContentAreaFilled(false);
                                                    messageLeftButton.setFocusPainted(false);    //去除内边框
                                                    messageLeftButton.setToolTipText("刷新");
                                                    messageLeftButton.setIcon(refreshIcon);
                                                    //除去边框
                                                    messageLeftButton.setBorder(null);
                                                    messageLeftButton.setVisible(false);
                                                    messageLeftButton.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            executorService.submit(new FaceAndCamera());
                                                        }
                                                    });
                                                }
                                                equipInfoFpanel.add(messageLeftButton);

                                                //======= messageLeftButton =======
                                                {
                                                    messageInternetButton.setContentAreaFilled(false);
                                                    messageInternetButton.setFocusPainted(false);    //去除内边框
                                                    messageInternetButton.setToolTipText("刷新");
                                                    messageInternetButton.setIcon(refreshIcon);
                                                    //除去边框
                                                    messageInternetButton.setBorder(null);

                                                    messageInternetButton.setVisible(false);
                                                    messageInternetButton.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            messageInternetButton.setVisible(false);
                                                        }
                                                    });
                                                }
                                                equipInfoFpanel.add(messageInternetButton);

                                            }
                                            equipInfopanel.add(equipInfoFpanel, BorderLayout.NORTH);

                                            //======== namePanel ========
                                            {
                                                namePanel.setBackground(new Color(0xfbfbfb));
                                                namePanel.setLayout(new BorderLayout());

                                                //======== nameFPanel ========
                                                {
                                                    nameFPanel.setBackground(new Color(0xfbfbfb));
                                                    nameFPanel.setLayout(new FlowLayout());
                                                    nameFPanel.setBorder(new EmptyBorder(10, 0, 5, 0));

                                                    //---- nameLabel ----
                                                    //---- clickEquipLabel ----
                                                    startOpenBtn.setText("开启全部设备");
                                                    startOpenBtn.setBackground(Color.WHITE);
                                                    startOpenBtn.setContentAreaFilled(true);
                                                    startOpenBtn.setMargin(new Insets(10, 10, 10, 10));
                                                    startOpenBtn.setFocusPainted(false);
                                                    startOpenBtn.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            startOpenBtn.setVisible(false);
                                                            //开启线程
                                                            executorService.submit(new FaceAndCamera());

                                                        }
                                                    });
                                                    nameFPanel.add(startOpenBtn);

                                                }
                                                namePanel.add(nameFPanel, BorderLayout.NORTH);

                                                //======== usernamePanel ========
                                                {
                                                    usernamePanel.setBackground(new Color(0xfbfbfb));
                                                    usernamePanel.setLayout(new BorderLayout());
                                                }
                                                namePanel.add(usernamePanel, BorderLayout.CENTER);
                                            }
                                            equipInfopanel.add(namePanel, BorderLayout.CENTER);
                                        }
                                        equipCameraPanel.add(equipInfopanel, BorderLayout.CENTER);
                                    }
                                    faceLookPanel.add(equipCameraPanel, BorderLayout.CENTER);
                                }
                                clickEquipPanel.add(faceLookPanel, BorderLayout.CENTER);
                            }
                            equipPanel.add(clickEquipPanel, BorderLayout.CENTER);
                        }
                        equipAllPanel.add(equipPanel, BorderLayout.NORTH);
                    }
                    cententInfoPanel.add(equipAllPanel);


                    //======== panel7 ========
                    {
                        panel7.setBackground(new Color(0xfbfbfb));
                        panel7.setLayout(new BorderLayout());
                    }
                    cententInfoPanel.add(panel7);
                }
                panel1.add(cententInfoPanel);

                //======== panel8 ========
                {
                    panel8.setBackground(new Color(0xfbfbfb));
                    panel8.setLayout(new BorderLayout());
                }
                panel1.add(panel8);
            }
            contentPanel.add(panel1, BorderLayout.CENTER);
        }
        contentPane.add(contentPanel, BorderLayout.CENTER);
        setTitle("NIKOLA");
        setIconImage(frameImage);
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private static int openSerialPort(String comname) {
        int baudrate = 115200;
        // 检查串口名称是否获取正确
        if (comname == null || comname.equals("")) {
            System.out.println("没有搜索到有效串口！");
            return 1;
        } else {
            try {
                mSerialport = SerialPortManager.openPort(comname, baudrate);
                if (mSerialport != null) {
                    System.out.println(comname + "串口已打开");
                    return 2;

                    // mSerialPortOperate.setText("关闭串口");
                }else {
                    return 3;
                }
            } catch (PortInUseException e) {
                System.out.println("串口已被占用！");
                return 3;
            }
        }

    }
    private class FaceAdmin implements Runnable {
        @Override
        public void run() {
            String xmlFileName = null;
            try {
                xmlFileName = ResourceUtils.getFile("res/myopencv/lbpcascade_frontalface_improved.xml").getAbsolutePath();
            } catch (FileNotFoundException e) {
                log.info(String.valueOf(e));
                throw new RuntimeException(e);
            }
            CascadeClassifier cascadeClassifier = new CascadeClassifier(xmlFileName);
            if (cascadeClassifier.empty()) {
                log.info("加载xml模型文件失败！");
                return;
            }
            int firstCamera = Integer.parseInt(ParameterOperate.extract("firstCamera"));
            capture = new VideoCapture(firstCamera);
            if (!capture.isOpened()) {
                log.info("未检测到摄像头设备");
                equipInfoLabel.setText("未检测到摄像头设备");
                messageLeftButton.setVisible(true);
            }else {
                //设置相机参数
                capture.set(CAP_PROP_FRAME_WIDTH, 960);
                capture.set(CAP_PROP_FRAME_HEIGHT, 1280);
                capture.set(CAP_PROP_FPS, 30);

                double frame_width = capture.get(CAP_PROP_FRAME_WIDTH);
                double frame_height = capture.get(CAP_PROP_FRAME_HEIGHT);

                frame = new Mat(new Size(960, 1280), CV_8UC3);
                Mat frameGray = new Mat(new Size(960, 1280), CV_8UC1);
                MatOfRect objectsRect = new MatOfRect();

                log.info(String.valueOf(frame_width));
                log.info(String.valueOf(frame_height));
                //读取图像
                //将摄像头导出的图片居中裁剪（x的坐标）
                int biX = (int) ((frame_width - frame_height) / 2);
                while (true) {
                    //读取图像
                    if (!capture.read(frame)) {
                        log.info("读取相机数据失败");
                        equipInfoLabel.setText("读取相机数据失败");
                        messageLeftButton.setVisible(true);
                        break;
                    }
                    //切割图片
                    //创建一个Rect框，属于cv中的类，四个参数代表x,y,width,height
                    Rect rect = new Rect(biX, 0, (int) frame_height, (int) frame_height);
                    image_cut = new Mat(frame, rect);

                    bi = fs.mat2BIW(image_cut, 640);


                    //转为灰度
                    cvtColor(image_cut, frameGray, COLOR_BGR2GRAY);

                    //人脸检测
                    cascadeClassifier.detectMultiScale(frameGray, objectsRect);
                    if (!objectsRect.empty()) {
                        //绘制矩形
                        Rect[] rects = objectsRect.toArray();
                        for (Rect r : rects) {
                            rectangle(image_cut, r.tl(), r.br(), new Scalar(255, 255, 255), 2);
                        }
                    }

                    //显示
                    BufferedImage bix = fs.mat2BIW(image_cut, camerabgSize);
                    equipCameraLabel.setIcon(new ImageIcon(bix));
                }
            }

        }

    }
    private class FaceAndCamera implements Runnable {

        @Override
        public void run() {
            try {
                String s = "";
                equipInfoLabel.setText("正在人脸检测中");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                s = fs.judgeMemberData(bi,"saveAdminOpenInfo");
                if (Objects.isNull(s) ||"".equals(s)) {
                    // log一下服务器异常，未获取到返回值
                    log.info("系统与服务器断开连接，请重试");
                    equipInfoLabel.setText("系统与服务器断开连接，请重试");
                    messageLeftButton.setVisible(true);
                    return;
                }

                int f1 = s.indexOf('_');
                int f2 = s.indexOf('_', f1 + 1);
                int f3 = s.indexOf('_', f2 + 1);
                int f4 = s.indexOf('_', f3 + 1);
                int f5 = s.indexOf('_', f4 + 1);

                String userId = s.substring(0, f1);
                String userCode = s.substring(f1 + 1, f2);
                String userName = s.substring(f2 + 1, f3);
                String roleId = s.substring(f3 + 1, f4);
                String photoUrl = s.substring(f4 + 1, f5);
                String projects = s.substring(f5 + 1);

                if ("2".equals(roleId)) {
                    try {
//                        int comm = openSerialPort(ParameterOperate.extractX("commName"));
                        int comm = 2;
                        if(comm == 1){
                            equipInfoLabel.setText("没有搜索到有效串口！");
                            return;
                        }else if(comm == 2){
                            SerialPortManager.closePort(mSerialport);
                        }else{
                            equipInfoLabel.setText("串口已被占用！");
                            return;
                        }
//                            if (sendData()) {
//                                String appname = ParameterOperate.extractX("appname");
//                                Runtime.getRuntime().exec(appname);
//                            }
                    }catch (Exception e1) {
                        log.error("硬件设备未准备！");
                        throw new RuntimeException(e1);
                    }
                    if ("2".equals(roleId)){
                        capture.release();
                        Thread.sleep(1000);
                        dispose();
                        executorService.shutdown();
                        EquipAndExamFrame.newInstance();
                        return;
                    }
                } else {
                    equipInfoLabel.setText("身份验证成功,你没有权限开启设备");
                }
                executorService.submit(new FaceAndCamera());
            }catch (Exception exception){
                log.info("人脸识别线程");
                throw new RuntimeException(exception);
            }

        }
    }
    public class OpenEquipFrameRun implements Runnable{
        @Override
        public void run() {
            String mainService = com.sznikola.equipAndExam.util.ParameterOperate.extract("mainService");
            String url = MessageFormat.format("{0}{1}", mainService, "/train/devicestate/OpenCloseDevice");
            Long currenttime = System.currentTimeMillis();
            Long pretime = System.currentTimeMillis();
            while (true){
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 1、封装请求头
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("multipart/form-data");
                headers.setContentType(type);


                // 2、封装请求体
                MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

                // 3、封装整个请求报文
                HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(param, headers);

                // 4、发送请求
                ResponseEntity<String> data = DeviceState.restTemplate.postForEntity(url, formEntity, String.class);

                Map map = null;
                try {
                    map = DeviceState.om.readValue(data.getBody(), Map.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                if (Objects.isNull(map)){
                    return;
                }
                Integer code = (Integer)map.get("code");
                if(Objects.nonNull(code) && code == 2000){
                    return;
                }
                if(code == 1001){
                    capture.release();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    dispose();
                    EquipAndExamFrame.newInstance();
                    executorService.shutdown();
                }else {
                    return;
                }
            }
        }
    }
}
