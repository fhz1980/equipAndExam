package com.sznikola.devicestate.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.devicestate.service.FaceService;
import com.sznikola.equipAndExam.util.SpringContextUtil;
import gnu.io.SerialPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.core.io.ClassPathResource;
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
public class DeviceStateClosed extends JFrame {
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
            camerabg = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", DeviceStateClosed.class.getClassLoader()).getInputStream()).getScaledInstance(camerabgSize, camerabgSize, Image.SCALE_AREA_AVERAGING);
            camerabg2 = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", DeviceStateClosed.class.getClassLoader()).getInputStream()).getScaledInstance(camerabgSize, camerabgSize, Image.SCALE_AREA_AVERAGING);
            refreshInternet = ImageIO.read(new ClassPathResource("img/sign/refresh.png", DeviceStateClosed.class.getClassLoader()).getInputStream()).getScaledInstance(22, 22, Image.SCALE_DEFAULT);
            frameImage = ImageIO.read(new ClassPathResource("img/equipAndExam/nikola.png", DeviceStateClosed.class.getClassLoader()).getInputStream());
            faceoBlue2 = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoBlue.png", DeviceStateClosed.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
            faceoRed2 = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoRed.png", DeviceStateClosed.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);

        }catch (IOException e) {
            log.info("图片和图标加载失败", e);
        }
    }

    private static DeviceStateClosed equipAndExamFrame = new DeviceStateClosed();

    public static RestTemplate restTemplate;

    {
        RestTemplate restTemplate1 = SpringContextUtil.getBean("restTemplate", RestTemplate.class);
        if (Objects.isNull(restTemplate1)){
            restTemplate1 = new RestTemplate();
        }
        restTemplate = restTemplate1;
    }
    public static  ObjectMapper om = new ObjectMapper();

    private DeviceStateClosed(){
        initComponents();
        //摄像头实时显示
        executorService.submit(new FaceAdmin());
    }

    public static DeviceStateClosed getInstance(){
        return equipAndExamFrame;
    }

    public static DeviceStateClosed newInstance(){
        return new DeviceStateClosed();
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
    private ImageIcon faceoBlueIcon2;
    private ImageIcon faceoRedIcon2;
    private static ImageIcon refreshIcon;
    private static Image camerabg = null;
    private static Image camerabg2 = null;
    private static Image signcamerabg = null;
    private static Image refreshInternet = null;
    private static Image frameImage = null;
    private static Image faceoBlue2 = null;
    private static Image faceoRed2 = null;

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
        faceoRedIcon2 = new ImageIcon(faceoRed2);
        faceoBlueIcon2 = new ImageIcon(faceoBlue2);
        refreshIcon = new ImageIcon();
        examColor = new Color(0, 85, 255);

        //======== this ========
        setBackground(new Color(0xfbfbfb));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== contentPanel ========
        {
            contentPanel.setBackground(new Color(0xfbfbfb));
            contentPanel.setPreferredSize(new Dimension(1024, 698));
//            contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
            contentPanel.setBorder(new EmptyBorder(0,12,0,12));
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
                                equipNameLabel.setText("设备关闭");
                                equipNameLabel.setForeground(examColor);
                                equipNameLabel.setBorder(new EmptyBorder(3, 8, 3, 3));
                                equipNameLabel.setBackground(new Color(0xfbfbfb));
                                equipNameLabel.setFont(new Font("黑体", Font.PLAIN, 18));
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
//                                            equipCameraFPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
                                            equipCameraFPanel.setBorder(new EmptyBorder(0,20,0,20));

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
                                                equipInfoLabel.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 16));
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
//                                                    nameFPanel.setBorder(new EmptyBorder(10, 0, 5, 0));

                                                    //---- nameLabel ----
                                                    //---- clickEquipLabel ----
                                                    startOpenBtn.setText("关闭全部设备");
                                                    startOpenBtn.setBackground(Color.WHITE);
                                                    startOpenBtn.setContentAreaFilled(true);
                                                    startOpenBtn.setMargin(new Insets(5, 5, 5, 5));
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
                s = fs.judgeMemberData(bi,"saveAdminCloseInfo");
                if (Objects.isNull(s) ||"".equals(s)) {
                    // log一下服务器异常，未获取到返回值
                    log.info("系统与服务器断开连接，请重试");
                    equipInfoLabel.setText("系统与服务器断开连接，请重试");
                    messageLeftButton.setVisible(true);
                    return;
                }
                if(s == "noUser") {
                    equipInfoLabel.setText("未发现用户");
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
                       capture.release();
                        executorService.shutdown();
                        System.exit(0);
                } else {
                    equipInfoLabel.setText("身份验证成功，你没有权限关闭设备");
                }
                executorService.submit(new FaceAndCamera());
            }catch (Exception exception){
                log.info("人脸识别线程");
                throw new RuntimeException(exception);
            }

        }
    }
}
