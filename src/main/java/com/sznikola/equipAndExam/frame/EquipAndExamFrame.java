package com.sznikola.equipAndExam.frame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.devicestate.frame.DeviceState;
import com.sznikola.devicestate.frame.DeviceStateClosed;
import com.sznikola.devicestate.frame.service.FaceService;
import com.sznikola.equipAndExam.thread.CurrentViewCamera;
import com.sznikola.equipAndExam.thread.ExamFace;
import com.sznikola.equipAndExam.thread.ExamFaceReg;
import com.sznikola.equipAndExam.thread.USBOperateDevice;
import com.sznikola.equipAndExam.util.ParameterOperate;
import com.sznikola.equipAndExam.util.VoiceBroadcastUtils;
import gnu.io.SerialPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/*
 * Created by JFormDesigner on Wed Nov 16 19:13:57 CST 2022
 */


/**
 * @author Young
 */
@Slf4j
@Getter
@Setter
public class EquipAndExamFrame extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public static ExecutorService executorService = Executors.newFixedThreadPool(8);
    public static VoiceBroadcastUtils vs = new VoiceBroadcastUtils();
    public static int camerabgSize = 350;
    public static BufferedImage bi1 = null;
    public static BufferedImage bi2 = null;
    public static FaceService fs = new FaceService();
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static Mat image_cut =  null;
    public static Mat frame =  null;
    public static boolean useState = true;
    public static int timeCamera = Integer.parseInt(ParameterOperate.extract("timeCamera"));
    public static VideoCapture capture = null;
    public static VideoCapture capture1 = null;
    static {
        try{
            //new File(String.valueOf(ResourceUtils.getFile("classpath:img/devicecontrol/face.png"))))
            camerabg = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(camerabgSize, camerabgSize, Image.SCALE_AREA_AVERAGING);
            camerabg2 = ImageIO.read(new ClassPathResource("img/equipAndExam/camera.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(550, 550, Image.SCALE_AREA_AVERAGING);
            refreshInternet = ImageIO.read(new ClassPathResource("img/sign/refresh.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(22, 22, Image.SCALE_DEFAULT);
            frameImage = ImageIO.read(new ClassPathResource("img/equipAndExam/nikola.png", EquipAndExamFrame.class.getClassLoader()).getInputStream());
            equipBlue = ImageIO.read(new ClassPathResource("img/equipAndExam/equipBlue.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
            equipRed = ImageIO.read(new ClassPathResource("img/equipAndExam/equipRed.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
            faceoBlue = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoBlue.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
            faceoRed = ImageIO.read(new ClassPathResource("img/equipAndExam/faceoRed.png", EquipAndExamFrame.class.getClassLoader()).getInputStream()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);

        }catch (IOException e) {
            log.info("图片和图标加载失败", e);
        }
    }

    private static EquipAndExamFrame equipAndExamFrame = new EquipAndExamFrame();


    private EquipAndExamFrame(){
        initComponents();
        //摄像头实时显示
        executorService.submit(new ExamFace());
        executorService.submit(new CurrentViewCamera());
        executorService.submit(new CloseEquipFrameRun());
    }

    public static EquipAndExamFrame getInstance(){
        return equipAndExamFrame;
    }
    public static EquipAndExamFrame newInstance(){
        return new EquipAndExamFrame();
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
    private JButton clickStartButton;
    private JButton clickStartButton2;
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
    private JLabel nameLabel;
    private JPanel usernamePanel;
    private JPanel usernameFpanel;
    private JLabel usernameLabel;
    private JPanel examAllPanel;
    private JPanel examPanel;
    private JPanel examNamePanel;
    private JPanel leftAndRightBox;
    private JPanel RightPanal;
    private JPanel examNameFPanel;
    private JLabel examNameLabel;
    private JButton closeEquipBtn;
    private JPanel examCardPanel;
    private JPanel examMainPanel;
    private JPanel titleMainPanel;
    private JPanel titlePanel;
    private JLabel examTitleLabel;
    private JPanel examineesPanel;
    private JLabel examineesLabel;
    private JLabel examineesNameLabel;
    private JPanel countDownAndButton;
    private JLabel countDown;
    private JLabel label1;
    private JButton commit;
    private JPanel questionPanel;
    private JPanel ButtonPanel;
    private JPanel selsectPanel;
    private JButton selectAButton;
    private JButton selectBButton;
    private JButton selectCButton;
    private JButton selectDButton;
    private JPanel questionNumberAndQuestionButton;
    private JButton numberOneQuestion;
    private JButton numberTwoQuestion;
    private JButton numberThreeQuestion;
    private JButton numberFourQuestion4;
    private JButton numberFiveQuestion;
    private JButton numberSixQuestion;
    private JButton numberSevenQuestion;
    private JButton numberEightQuestion;
    private JButton numberNineQuestion;
    private JButton numberTenQuestion;
    private JPanel NextQuestionAndLastQuestion;
    private JButton button1;
    private JButton nextButton;
    private JPanel examInfoLabel;
    private JPanel examCameraFPanel;
    private JLabel examCameraLabel;
    private JPanel examStartAllPanel;
    private JPanel examStartFPanel;
    private JLabel clickLeft;
    private JLabel faceClick;
    private JButton startExamBtn;
    private JButton clickRight;
    private JPanel examFacePanel;
    private JPanel examFaceFPanel;
    private JLabel examFaceLabel;
    private JLabel examExamLabel;
    private JPanel panel7;
    private JPanel panel8;
    private Color examColor;
    private ImageIcon cameraIcon;
    private ImageIcon cameraIcon2;
    private ImageIcon equipBlueIcon;
    private ImageIcon equipRedIcon;
    private ImageIcon faceoBlueIcon;
    private ImageIcon faceoRedIcon;
    private static ImageIcon refreshIcon;
    private static Image camerabg = null;
    private static Image camerabg2 = null;
    private static Image signcamerabg = null;
    private static Image refreshInternet = null;
    private static Image frameImage = null;
    private static Image equipBlue = null;
    private static Image equipRed = null;
    private static Image faceoBlue = null;
    private static Image faceoRed = null;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    BufferedImage bi = null;

    private static SerialPort mSerialport;
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
        closeEquipBtn = new JButton();
        clickEquipPanel = new JPanel();
        clickEquipFPanel = new JPanel();
        clickEquipLabel = new JLabel();
        clickEquipImageLabel = new JLabel();
        clickStartButton = new JButton();
        clickStartButton2 = new JButton();
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
        nameLabel = new JLabel();
        usernamePanel = new JPanel();
        usernameFpanel = new JPanel();
        usernameLabel = new JLabel();
        examAllPanel = new JPanel();
        examPanel = new JPanel();
        examNamePanel = new JPanel();
        leftAndRightBox = new JPanel();
        RightPanal = new JPanel();
        examNameFPanel = new JPanel();
        examNameLabel = new JLabel();
        examCardPanel = new JPanel();
        examInfoLabel = new JPanel();
        examCameraFPanel = new JPanel();
        examCameraLabel = new JLabel();
        examStartAllPanel = new JPanel();
        examStartFPanel = new JPanel();
        clickLeft = new JLabel();
        faceClick = new JLabel();
        startExamBtn = new JButton();
        clickRight = new JButton();
        examFacePanel = new JPanel();
        examFaceFPanel = new JPanel();
        examFaceLabel = new JLabel();
        examExamLabel = new JLabel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        cameraIcon = new ImageIcon();
        cameraIcon2 = new ImageIcon();
        equipBlueIcon = new ImageIcon(equipBlue);
        equipRedIcon = new ImageIcon(equipRed);
        faceoRedIcon = new ImageIcon(faceoRed);
        faceoBlueIcon= new ImageIcon(faceoBlue);
        refreshIcon = new ImageIcon();
        examColor = new Color(0, 85, 255);
        examMainPanel = new JPanel();
        titleMainPanel = new JPanel();
        titlePanel = new JPanel();
        examTitleLabel = new JLabel();
        examineesPanel = new JPanel();
        examineesLabel = new JLabel();
        examineesNameLabel = new JLabel();
        countDownAndButton = new JPanel();
        countDown = new JLabel();
        label1 = new JLabel();
        commit = new JButton();
        questionPanel = new JPanel();
        ButtonPanel = new JPanel();
        selsectPanel = new JPanel();
        selectAButton = new JButton();
        selectBButton = new JButton();
        selectCButton = new JButton();
        selectDButton = new JButton();
        questionNumberAndQuestionButton = new JPanel();
        numberOneQuestion = new JButton();
        numberTwoQuestion = new JButton();
        numberThreeQuestion = new JButton();
        numberFourQuestion4 = new JButton();
        numberFiveQuestion = new JButton();
        numberSixQuestion = new JButton();
        numberSevenQuestion = new JButton();
        numberEightQuestion = new JButton();
        numberNineQuestion = new JButton();
        numberTenQuestion = new JButton();
        NextQuestionAndLastQuestion = new JPanel();
        button1 = new JButton();
        nextButton = new JButton();

        //======== this ========
        setBackground(new Color(0xfbfbfb));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== contentPanel ========
        {
            contentPanel.setBackground(new Color(0xfbfbfb));
            contentPanel.setPreferredSize(new Dimension(1024, 768));
            contentPanel.setBorder(new EmptyBorder(12, 20, 12, 12));
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
//                                equipNameLabel.setText("\u8bbe\u5907\u4f53\u9a8c\u533a");
                                equipNameLabel.setText("设备体验区");
                                equipNameLabel.setForeground(examColor);
                                equipNameLabel.setBorder(new EmptyBorder(3, 8, 3, 3));
                                equipNameLabel.setBackground(new Color(0xfbfbfb));
                                equipNameLabel.setFont(new Font("黑体", Font.PLAIN, 20));
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
                                            equipCameraFPanel.setBorder(new EmptyBorder(60, 20, 0, 20));

                                            //---- equipCameraLabel ----
                                            equipCameraLabel.setForeground(examColor);
                                            equipCameraLabel.setBorder(BorderFactory.createEtchedBorder());
                                            equipCameraLabel.setPreferredSize(new Dimension(camerabgSize,camerabgSize));
//                                            equipCameraLabel.setBorder(new LineBorder(new Color(211, 246, 255)));
                                            cameraIcon.setImage(camerabg);
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
                                                equipInfoLabel.setText("请体验");
                                                equipInfoLabel.setForeground(examColor);
                                                equipInfoLabel.setForeground(Color.black);
                                                equipInfoLabel.setFont(new Font("等线", Font.PLAIN, 18));
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
                                                            messageLeftButton.setVisible(false);
                                                            //开启线程
                                                            executorService.submit(new CurrentViewCamera());
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
                                                            equipInfoLabel.setText(" ");
                                                            //开启线程
                                                            executorService.submit(new USBOperateDevice());
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
                                                    nameFPanel.setBorder(new EmptyBorder(50, 0, 10, 0));

                                                    //---- nameLabel ----
                                                    //---- clickEquipLabel ----
                                                    clickEquipLabel.setText("1、按下设备旁体验按钮");
                                                    clickEquipLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
                                                    clickEquipLabel.setForeground(examColor);
                                                    clickEquipLabel.setBackground(new Color(0xfbfbfb));
                                                    clickEquipLabel.setFont(new Font("等线", Font.PLAIN, 26));

                                                    nameFPanel.add(clickEquipLabel);

                                                    //---- clickEquipImageLabel ----
                                                    clickEquipImageLabel.setIcon(equipBlueIcon);
                                                    clickEquipImageLabel.setBackground(new Color(0xfbfbfb));
                                                    clickEquipImageLabel.setAutoscrolls(true);
                                                    clickEquipImageLabel.setBorder(new EmptyBorder(2, 3, 0, 0));
//                                                    nameFPanel.add(clickEquipImageLabel);

                                                    //---- clickStartButton ----
                                                    clickStartButton.setIcon(equipBlueIcon);
                                                    clickStartButton.setBackground(Color.WHITE);
                                                    clickStartButton.setContentAreaFilled(true);
                                                    clickStartButton.setMargin(new Insets(3, 3, 3, 3));
                                                    clickStartButton.setFocusPainted(false);
                                                    clickStartButton.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            EquipAndExamFrame.useState = true;
                                                            clickStartButton.setVisible(false);

                                                            clickStartButton2.setVisible(true);

                                                            //开启线程
                                                            executorService.submit(new USBOperateDevice());

                                                        }
                                                    });
                                                    nameFPanel.add(clickStartButton);


                                                    //---- clickStartButton ----
                                                    clickStartButton2.setIcon(equipRedIcon);
                                                    clickStartButton2.setBackground(Color.WHITE);
                                                    clickStartButton2.setContentAreaFilled(true);
                                                    clickStartButton2.setVisible(false);
                                                    clickStartButton2.setMargin(new Insets(3, 3, 3, 3));
                                                    clickStartButton2.setFocusPainted(false);
                                                    clickStartButton2.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            EquipAndExamFrame.useState = false;
                                                            clickStartButton2.setVisible(false);
                                                            clickStartButton.setVisible(true);

                                                        }
                                                    });
                                                    nameFPanel.add(clickStartButton2);


                                                }
                                                namePanel.add(nameFPanel, BorderLayout.NORTH);

                                                //======== usernamePanel ========
                                                {
                                                    usernamePanel.setBackground(new Color(0xfbfbfb));
                                                    usernamePanel.setLayout(new BorderLayout());
                                                    usernamePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

                                                    //======== usernameFpanel ========
                                                    {
                                                        usernameFpanel.setBackground(new Color(0xfbfbfb));
                                                        usernameFpanel.setLayout(new FlowLayout());

                                                        //---- usernameLabel ----
                                                        //---- faceLookLabel ----
                                                        faceLookLabel.setText("2、人脸识别记录体验");
                                                        faceLookLabel.setForeground(examColor);
                                                        faceLookLabel.setFont(new Font("等线", Font.PLAIN, 26));
                                                        usernameFpanel.add(faceLookLabel);

                                                        //---- faceLookImageLabel ----
                                                        faceLookImageLabel.setForeground(examColor);
                                                        faceLookImageLabel.setIcon(faceoBlueIcon);
                                                        faceLookImageLabel.setBorder(new EmptyBorder(0, 3, 0, 0));
                                                        usernameFpanel.add(faceLookImageLabel);
                                                    }
                                                    usernamePanel.add(usernameFpanel, BorderLayout.NORTH);
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

                    //======== examAllPanel ========
                    {
                        examAllPanel.setBackground(new Color(0xfbfbfb));
                        examAllPanel.setBorder(new LineBorder(new Color(0x0055ff)));
                        examAllPanel.setLayout(new BorderLayout());

                        //======== examPanel ========
                        {
                            examPanel.setBackground(new Color(0xfbfbfb));
                            examPanel.setLayout(new BorderLayout());

                            //======== examNamePanel ========
                            {
                                examNamePanel.setBackground(new Color(0xfbfbfb));
                                examNamePanel.setLayout(new BorderLayout());
                                examNamePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 85, 255)));
                                //======== leftAndRightBox ========
                                {
                                    leftAndRightBox.setLayout(new BoxLayout(leftAndRightBox, BoxLayout.X_AXIS));


                                    //======== leftPanel ========
                                    {
                                        examNameFPanel.setBackground(new Color(0xfbfbfb));
                                        examNameFPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


                                        //---- label1 ----
                                        //---- examNameLabel ----
                                        examNameLabel.setText("知识考核区");
                                        examNameLabel.setForeground(examColor);
                                        examNameLabel.setBorder(new EmptyBorder(3, 8, 3, 3));
                                        examNameLabel.setFont(new Font("黑体", Font.PLAIN, 20));
                                        examNameFPanel.add(examNameLabel);
                                    }
                                    leftAndRightBox.add(examNameFPanel);

                                    //======== RightPanal ========
                                    {
                                        RightPanal.setLayout(new FlowLayout(FlowLayout.RIGHT));
                                        RightPanal.setBackground(new Color(0xfbfbfb));

                                        //---- closeEquip ----
                                        closeEquipBtn.setText("关闭");
                                        closeEquipBtn.setBackground(Color.WHITE);
                                        closeEquipBtn.setContentAreaFilled(true);
                                        closeEquipBtn.setMargin(new Insets(4, 8, 4, 8));
                                        closeEquipBtn.setFocusPainted(false);
                                        closeEquipBtn.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                capture.release();
                                                capture1.release();
                                                try {
                                                    Thread.sleep(2000);
                                                } catch (InterruptedException ex) {
                                                    throw new RuntimeException(ex);
                                                }
                                                dispose();
                                                executorService.shutdown();
                                                DeviceStateClosed.newInstance();
                                            }
                                        });
                                        RightPanal.add(closeEquipBtn);
                                    }
                                    leftAndRightBox.add(RightPanal);
                                }
                                examNamePanel.add(leftAndRightBox, BorderLayout.NORTH);
                            }
                            examPanel.add(examNamePanel, BorderLayout.NORTH);
                            //======== examCardPanel ========
                            {
                                examCardPanel.setBackground(new Color(0xfbfbfb));
                                examCardPanel.setLayout(new CardLayout());

                                //======== examInfoLabel ========
                                {
                                    examInfoLabel.setBackground(new Color(0xfbfbfb));
                                    examInfoLabel.setLayout(new BorderLayout());

                                    //======== examCameraFPanel ========
                                    {
                                        examCameraFPanel.setBackground(new Color(0xfbfbfb));
                                        examCameraFPanel.setLayout(new FlowLayout());
                                        examCameraFPanel.setBorder(new EmptyBorder(10, 10, 5, 10));

                                        //---- examCameraLabel ----
                                        cameraIcon2.setImage(camerabg2);
                                        examCameraLabel.setIcon(cameraIcon2);
                                        examCameraLabel.setBorder(new LineBorder(new Color(211, 246, 255)));
                                        examCameraFPanel.add(examCameraLabel);
                                    }
                                    examInfoLabel.add(examCameraFPanel, BorderLayout.NORTH);

                                    //======== examStartAllPanel ========
                                    {
                                        examStartAllPanel.setBackground(new Color(0xfbfbfb));
                                        examStartAllPanel.setLayout(new BorderLayout());

                                        //======== examStartFPanel ========
                                        {
                                            examStartFPanel.setBackground(new Color(0xfbfbfb));
                                            examStartFPanel.setLayout(new FlowLayout());

                                            //---- clickLeft ----
//                                        clickLeft.setText("text");
//                                        examStartFPanel.add(clickLeft);

                                            //---- faceClick ----
                                            faceClick.setText(" ");
                                            faceClick.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 25));
                                            examStartFPanel.add(faceClick);

                                            startExamBtn.setContentAreaFilled(false);
                                            startExamBtn.setToolTipText("开始考试");
                                            startExamBtn.setText("开始考试");
                                            startExamBtn.setBackground(Color.WHITE);
                                            startExamBtn.setContentAreaFilled(true);
                                            startExamBtn.setMargin(new Insets(10, 10, 10, 10));
                                            startExamBtn.setFocusPainted(false);
                                            examStartFPanel.add(startExamBtn);
                                            startExamBtn.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    startExamBtn.setVisible(false);
                                                    executorService.submit(new ExamFaceReg());
                                                    if (EquipAndExamFrame.getInstance().getFaceClick().getText().equals("识别成功")) {
                                                        ((CardLayout) examCardPanel.getLayout()).next(examCardPanel);
                                                    }
                                                }
                                            });

                                            //---- clickRight ----
                                            clickRight.setText("text");
                                            refreshIcon.setImage(refreshInternet);
                                            clickRight.setContentAreaFilled(false);
                                            clickRight.setFocusPainted(false);    //去除内边框
                                            clickRight.setToolTipText("刷新");
                                            clickRight.setIcon(refreshIcon);
                                            //除去边框
                                            clickRight.setBorder(null);
                                            clickRight.setVisible(false);
                                            examStartFPanel.add(clickRight);
                                            clickRight.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    clickRight.setVisible(false);
                                                    //开启线程
                                                    executorService.submit(new ExamFace());
                                                }
                                            });

                                        }
                                        examStartAllPanel.add(examStartFPanel, BorderLayout.NORTH);

                                        //======== examFacePanel ========
                                        {
                                            examFacePanel.setBackground(new Color(0xfbfbfb));
                                            examFacePanel.setLayout(new BorderLayout());

                                            //======== examFaceFPanel ========
                                            {
                                                examFaceFPanel.setBackground(new Color(0xfbfbfb));
                                                examFaceFPanel.setLayout(new FlowLayout());

                                                //---- examFaceLabel ----
                                                examFaceLabel.setText("\u8bf7\u8fdb\u884c\u4eba\u8138\u8bc6\u522b");
                                                examFaceLabel.setForeground(examColor);
                                                examFaceLabel.setFont(new Font("\u5fae\u8edf\u6b63\u9ed1\u9ad4", Font.PLAIN, 40));
                                                examFaceFPanel.add(examFaceLabel);

                                                //---- examExamLabel ----
                                                examExamLabel.setText("\u767b\u5f55\u8003\u6838");
                                                examExamLabel.setForeground(examColor);
                                                examExamLabel.setFont(new Font("\u5fae\u8edf\u6b63\u9ed1\u9ad4 Light", Font.BOLD, 40));
                                                examFaceFPanel.add(examExamLabel);
                                            }
                                            examFacePanel.add(examFaceFPanel, BorderLayout.NORTH);
                                        }
                                        examStartAllPanel.add(examFacePanel, BorderLayout.CENTER);
                                    }
                                    examInfoLabel.add(examStartAllPanel, BorderLayout.CENTER);
                                }
                                examCardPanel.add(examInfoLabel, "card1");

                                //======== examMainPanel ========
                                {
                                    examMainPanel.setLayout(new BorderLayout());
                                    examMainPanel.setBackground(new Color(0xfbfbfb));

                                    //======== titleMainPanel ========
                                    {
                                        titleMainPanel.setLayout(new BorderLayout());
                                        titleMainPanel.setBackground(new Color(0xfbfbfb));

                                        //======== titlePanel ========
                                        {
                                            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
                                            titlePanel.setBackground(new Color(0xfbfbfb));

                                            //---- examTitleLabel ----
                                            examTitleLabel.setText("\u8003\u6838");
                                            titlePanel.add(examTitleLabel);
                                        }
                                        titleMainPanel.add(titlePanel, BorderLayout.WEST);

                                        //======== examineesPanel ========
                                        {
                                            examineesPanel.setLayout(new FlowLayout());
                                            examineesPanel.setBackground(new Color(0xfbfbfb));

                                            //---- examineesLabel ----
                                            examineesLabel.setText("\u8003\u751f:");
                                            examineesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                                            examineesLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                            examineesPanel.add(examineesLabel);
                                            examineesPanel.add(examineesNameLabel);
                                        }
                                        titleMainPanel.add(examineesPanel, BorderLayout.CENTER);

                                        //======== countDownAndButton ========
                                        {
                                            countDownAndButton.setLayout(new FlowLayout(FlowLayout.LEFT));
                                            countDownAndButton.setBackground(new Color(0xfbfbfb));

                                            //---- countDown ----
                                            countDown.setText("\u5012\u8ba1\u65f6:");
                                            countDownAndButton.add(countDown);

                                            //---- label1 ----
                                            label1.setText("30\u5206");
                                            countDownAndButton.add(label1);

                                            //---- commit ----
                                            commit.setText("\u63d0\u4ea4");
                                            countDownAndButton.add(commit);
                                        }
                                        titleMainPanel.add(countDownAndButton, BorderLayout.EAST);
                                    }
                                    examMainPanel.add(titleMainPanel, BorderLayout.NORTH);

                                    //======== questionPanel ========
                                    {
                                        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
                                        questionPanel.setBackground(new Color(0xfbfbfb));
                                    }
                                    examMainPanel.add(questionPanel, BorderLayout.CENTER);

                                    //======== ButtonPanel ========
                                    {
                                        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
                                        ButtonPanel.setBackground(new Color(0xfbfbfb));

                                        //======== selsectPanel ========
                                        {
                                            selsectPanel.setLayout(new BoxLayout(selsectPanel, BoxLayout.X_AXIS));
                                            selsectPanel.setBackground(new Color(0xfbfbfb));

                                            //---- selectAButton ----
                                            selectAButton.setText("A");
                                            selectAButton.setMinimumSize(new Dimension(23, 23));
                                            selectAButton.setPreferredSize(new Dimension(23, 23));
                                            selsectPanel.add(selectAButton);

                                            //---- selectBButton ----
                                            selectBButton.setText("B");
                                            selsectPanel.add(selectBButton);

                                            //---- selectCButton ----
                                            selectCButton.setText("C");
                                            selsectPanel.add(selectCButton);

                                            //---- selectDButton ----
                                            selectDButton.setText("D");
                                            selsectPanel.add(selectDButton);
                                        }
                                        ButtonPanel.add(selsectPanel);

                                        //======== questionNumberAndQuestionButton ========
                                        {
                                            questionNumberAndQuestionButton.setLayout(new BoxLayout(questionNumberAndQuestionButton, BoxLayout.X_AXIS));
                                            questionNumberAndQuestionButton.setBackground(new Color(0xfbfbfb));

                                            //---- numberOneQuestion ----
                                            numberOneQuestion.setText("1");
                                            questionNumberAndQuestionButton.add(numberOneQuestion);

                                            //---- numberTwoQuestion ----
                                            numberTwoQuestion.setText("2");
                                            questionNumberAndQuestionButton.add(numberTwoQuestion);

                                            //---- numberThreeQuestion ----
                                            numberThreeQuestion.setText("3");
                                            questionNumberAndQuestionButton.add(numberThreeQuestion);

                                            //---- numberFourQuestion4 ----
                                            numberFourQuestion4.setText("4");
                                            questionNumberAndQuestionButton.add(numberFourQuestion4);

                                            //---- numberFiveQuestion ----
                                            numberFiveQuestion.setText("5");
                                            questionNumberAndQuestionButton.add(numberFiveQuestion);

                                            //---- numberSixQuestion ----
                                            numberSixQuestion.setText("6");
                                            numberSixQuestion.setMaximumSize(new Dimension(23, 23));
                                            numberSixQuestion.setMinimumSize(new Dimension(23, 23));
                                            questionNumberAndQuestionButton.add(numberSixQuestion);

                                            //---- numberSevenQuestion ----
                                            numberSevenQuestion.setText("7");
                                            numberSevenQuestion.setMaximumSize(new Dimension(23, 23));
                                            numberSevenQuestion.setMinimumSize(new Dimension(23, 23));
                                            questionNumberAndQuestionButton.add(numberSevenQuestion);

                                            //---- numberEightQuestion ----
                                            numberEightQuestion.setText("8");
                                            numberEightQuestion.setMaximumSize(new Dimension(23, 23));
                                            numberEightQuestion.setMinimumSize(new Dimension(23, 23));
                                            numberEightQuestion.setPreferredSize(new Dimension(23, 23));
                                            questionNumberAndQuestionButton.add(numberEightQuestion);

                                            //---- numberNineQuestion ----
                                            numberNineQuestion.setText("9");
                                            numberNineQuestion.setMinimumSize(new Dimension(23, 23));
                                            numberNineQuestion.setMaximumSize(new Dimension(23, 23));
                                            numberNineQuestion.setPreferredSize(new Dimension(23, 23));
                                            questionNumberAndQuestionButton.add(numberNineQuestion);

                                            //---- numberTenQuestion ----
                                            numberTenQuestion.setText("10");
                                            numberTenQuestion.setMinimumSize(new Dimension(23, 23));
                                            numberTenQuestion.setMaximumSize(new Dimension(23, 23));
                                            numberTenQuestion.setPreferredSize(new Dimension(23, 23));
                                            questionNumberAndQuestionButton.add(numberTenQuestion);

                                            //======== NextQuestionAndLastQuestion ========
                                            {
                                                NextQuestionAndLastQuestion.setLayout(new BoxLayout(NextQuestionAndLastQuestion, BoxLayout.X_AXIS));
                                                NextQuestionAndLastQuestion.setBackground(new Color(0xfbfbfb));

                                                //---- button1 ----
                                                button1.setText("\u4e0a\u4e00\u9898");
                                                NextQuestionAndLastQuestion.add(button1);

                                                //---- nextButton ----
                                                nextButton.setText("\u4e0b\u4e00\u9898");
                                                NextQuestionAndLastQuestion.add(nextButton);
                                            }
                                            questionNumberAndQuestionButton.add(NextQuestionAndLastQuestion);
                                        }
                                        ButtonPanel.add(questionNumberAndQuestionButton);
                                    }
                                    examMainPanel.add(ButtonPanel, BorderLayout.SOUTH);
                                }
                                examCardPanel.add(examMainPanel, "card2");
                            }

                            examPanel.add(examCardPanel, BorderLayout.CENTER);
                        }
                        examAllPanel.add(examPanel, BorderLayout.NORTH);
                    }
                    cententInfoPanel.add(examAllPanel);

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
        pack();
        setTitle("NIKOLA");
        setIconImage(frameImage);
        setLocationRelativeTo(getOwner());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    public class CloseEquipFrameRun implements Runnable{
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
                if(code == 1002){
                    capture.release();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    executorService.shutdown();
                    System.exit(0);
                }else {
                    return;
                }
            }
        }
    }
}
