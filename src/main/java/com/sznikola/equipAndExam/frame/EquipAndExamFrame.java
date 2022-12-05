package com.sznikola.equipAndExam.frame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sznikola.devicestate.frame.DeviceState;
import com.sznikola.devicestate.frame.DeviceStateClosed;
import com.sznikola.devicestate.frame.service.FaceService;
import com.sznikola.devicestate.service.Examination;
import com.sznikola.equipAndExam.common.ExamData;
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
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private JButton startBtn;
    private JLabel label1;
    private JButton submit;
    private JPanel questionAndAnswerPanel;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JPanel ButtonPanel;
    private JPanel selectPanel;
    private JButton selectAButton;
    private JButton selectBButton;
    private JButton selectCButton;
    private JButton selectDButton;
    private JPanel questionNumberAndQuestionButton;
    private JButton numberOneQuestion;
    private JButton numberTwoQuestion;
    private JPanel NextQuestionAndLastQuestion;
    private JButton lastButton;
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

    private String userId;
    private String userName;
    // 参数
    private String examId = "";
    // Key:Value questionSort:answer
    private Map<String, String> answerMap = new HashMap<String, String>();
    private Map<String, String> questionIdMap = new HashMap<String, String>();

    private int flag_start = 0;
    // 题目序号
    private int sort = 0;
    // 倒计时
    private int time;
    private Timer timer = new Timer();
    // 当前选中的选项
    private String selectString = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
        startBtn = new JButton();
        label1 = new JLabel();
        submit = new JButton();
        questionAndAnswerPanel = new JPanel();
        questionLabel = new JLabel();
        answerLabel = new JLabel();
        ButtonPanel = new JPanel();
        selectPanel = new JPanel();
        selectAButton = new JButton();
        selectBButton = new JButton();
        selectCButton = new JButton();
        selectDButton = new JButton();
        questionNumberAndQuestionButton = new JPanel();
        numberOneQuestion = new JButton();
        numberTwoQuestion = new JButton();
        NextQuestionAndLastQuestion = new JPanel();
        lastButton = new JButton();
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
                                        closeEquipBtn.setText("关闭设备");
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
                                            faceClick.setFont(new Font("等线", Font.PLAIN, 25));
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
                                                    ExecutorService singerExecutorService = Executors.newSingleThreadExecutor();
                                                    singerExecutorService.submit(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            boolean flag = true;
                                                            while(flag) {
                                                                if (EquipAndExamFrame.getInstance().getFaceClick().getText().equals("识别成功")) {
                                                                    ((CardLayout) examCardPanel.getLayout()).next(examCardPanel);
                                                                    setUserId(ExamFaceReg.username);
                                                                    setUserName(ExamFaceReg.name);
                                                                    examineesNameLabel.setText(getUserName());
                                                                    EquipAndExamFrame.getInstance().getFaceClick().setText(" ");
                                                                    EquipAndExamFrame.getInstance().getStartExamBtn().setVisible(true);
                                                                    singerExecutorService.shutdown();
                                                                    flag = false;
                                                                }
                                                            }
                                                        }
                                                    });
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
                                                examFaceLabel.setText("请进行人脸识别");
                                                examFaceLabel.setForeground(examColor);
                                                examFaceLabel.setFont(new Font("黑体", Font.PLAIN, 40));
                                                examFaceFPanel.add(examFaceLabel);

                                                //---- examExamLabel ----
                                                examExamLabel.setText("登录考核");
                                                examExamLabel.setForeground(examColor);
                                                examExamLabel.setFont(new Font("黑体 Light", Font.BOLD, 40));
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
                                            examTitleLabel.setText("考核");
                                            titlePanel.add(examTitleLabel);
                                        }
                                        titleMainPanel.add(titlePanel, BorderLayout.WEST);

                                        //======== examineesPanel ========
                                        {
                                            examineesPanel.setLayout(new FlowLayout());
                                            examineesPanel.setBackground(new Color(0xfbfbfb));

                                            //---- examineesLabel ----
                                            examineesLabel.setText("考生:");
                                            examineesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                                            examineesLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                            examineesPanel.add(examineesLabel);

                                            //---- examineesLabel ----
                                            examineesNameLabel.setText("");
                                            examineesNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                                            examineesNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                            examineesPanel.add(examineesNameLabel);
                                        }
                                        titleMainPanel.add(examineesPanel, BorderLayout.CENTER);

                                        //======== countDownAndButton ========
                                        {
                                            countDownAndButton.setLayout(new FlowLayout(FlowLayout.LEFT));
                                            countDownAndButton.setBackground(new Color(0xfbfbfb));

                                            //---- countDown ----
                                            countDown.setText("倒计时:30秒");
                                            countDownAndButton.add(countDown);

                                            //---- startBtn ----
                                            startBtn.setText("开始考试");
                                            countDownAndButton.add(startBtn);
                                            startBtn.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    flag_start = 1;
                                                    EquipAndExamFrame.vs.VoiceBroadcast("开始考试");
                                                }
                                            });


                                            //---- submit ----
                                            submit.setText("提交");
                                            countDownAndButton.add(submit);
                                        }
                                        titleMainPanel.add(countDownAndButton, BorderLayout.EAST);
                                    }
                                    examMainPanel.add(titleMainPanel, BorderLayout.NORTH);

                                    //======== questionAndAnswerPanel ========
                                    {
                                        questionAndAnswerPanel.setLayout(new BoxLayout(questionAndAnswerPanel, BoxLayout.Y_AXIS));
                                        questionAndAnswerPanel.setBackground(new Color(0xfbfbfb));

                                        //======== questionLabel ========
                                        questionLabel.setText("");
                                        questionAndAnswerPanel.add(questionLabel);

                                        //======== answerLabel ========
                                        answerLabel.setText("");
                                        questionAndAnswerPanel.add(answerLabel);

                                    }
                                    examMainPanel.add(questionAndAnswerPanel, BorderLayout.CENTER);

                                    //======== ButtonPanel ========
                                    {
                                        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
                                        ButtonPanel.setBackground(new Color(0xfbfbfb));

                                        //======== selectPanel ========
                                        {
                                            selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
                                            selectPanel.setBackground(new Color(0xfbfbfb));

                                            //---- selectAButton ----
                                            selectAButton.setText("A");
                                            selectPanel.add(selectAButton);

                                            //---- selectBButton ----
                                            selectBButton.setText("B");
                                            selectPanel.add(selectBButton);

                                            //---- selectCButton ----
                                            selectCButton.setText("C");
                                            selectPanel.add(selectCButton);

                                            //---- selectDButton ----
                                            selectDButton.setText("D");
                                            selectPanel.add(selectDButton);
                                        }
                                        ButtonPanel.add(selectPanel);

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

                                            //======== NextQuestionAndLastQuestion ========
                                            {
                                                NextQuestionAndLastQuestion.setLayout(new BoxLayout(NextQuestionAndLastQuestion, BoxLayout.X_AXIS));
                                                NextQuestionAndLastQuestion.setBackground(new Color(0xfbfbfb));

                                                //---- lastButton ----
                                                lastButton.setText("上一题");
                                                NextQuestionAndLastQuestion.add(lastButton);

                                                //---- nextButton ----
                                                nextButton.setText("下一题");
                                                NextQuestionAndLastQuestion.add(nextButton);
                                            }
                                            questionNumberAndQuestionButton.add(NextQuestionAndLastQuestion);
                                        }
                                        ButtonPanel.add(questionNumberAndQuestionButton);
                                    }
                                    examMainPanel.add(ButtonPanel, BorderLayout.SOUTH);
                                    registerListeners(examTitleLabel, countDown, questionLabel, answerLabel, startBtn, submit,
                                            numberOneQuestion, numberTwoQuestion, lastButton, nextButton, selectAButton,
                                            selectBButton, selectCButton, selectDButton);
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
            String mainService = ParameterOperate.extract("mainService");
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
    public void registerListeners(JLabel examTitleLabel, JLabel countDown, JLabel questionLabel, JLabel answerLabel, JButton startBtn,
                                  JButton submit, JButton numberOneQuestion, JButton numberTwoQuestion,  JButton lastButton,
                                  JButton nextButton, JButton selectAButton, JButton selectBButton, JButton selectCButton, JButton selectDButton){
        // 开始答题事件
        StartListener startListener = new StartListener(examTitleLabel, countDown, questionLabel, answerLabel,
                numberOneQuestion, numberTwoQuestion, submit);
            //startExamBtn.addActionListener(startListener);
        startBtn.addActionListener(startListener);

        // 跳转事件
        AnswerQuestionListener answerQuestionListener = new AnswerQuestionListener(questionLabel, answerLabel,
                numberOneQuestion, numberTwoQuestion, selectAButton, selectBButton, selectCButton, selectDButton, submit);
        numberOneQuestion.addActionListener(answerQuestionListener);
        numberTwoQuestion.addActionListener(answerQuestionListener);
        lastButton.addActionListener(answerQuestionListener);
        nextButton.addActionListener(answerQuestionListener);

        // 提交事件
        SubmitListener submitListener = new SubmitListener(examTitleLabel, countDown, questionLabel, answerLabel,
                selectAButton, selectBButton, selectCButton, selectDButton);
        submit.addActionListener(submitListener);

        SelectJLabelListener selectJLabelListener = new SelectJLabelListener(selectAButton, selectBButton,
                selectCButton, selectDButton);
        selectAButton.addActionListener(selectJLabelListener);
        selectBButton.addActionListener(selectJLabelListener);
        selectCButton.addActionListener(selectJLabelListener);
        selectDButton.addActionListener(selectJLabelListener);
    }

    //选择事件
    class SelectJLabelListener implements ActionListener {
        private JButton selectAButton, selectBButton, selectCButton, selectDButton;

        public SelectJLabelListener(JButton selectAButton, JButton selectBButton, JButton selectCButton,
                                    JButton selectDButton) {
            super();
            this.selectAButton = selectAButton;
            this.selectBButton = selectBButton;
            this.selectCButton = selectCButton;
            this.selectDButton = selectDButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (flag_start != 1) {
                JOptionPane.showMessageDialog(null, "先点击开始考试！");
                return;
            }
            String str = ((JButton) e.getSource()).getText();
            if (str.equals("A")) {
                selectAButton.setBackground(new Color(37, 102, 68));
                selectBButton.setBackground(Color.LIGHT_GRAY);
                selectCButton.setBackground(Color.LIGHT_GRAY);
                selectDButton.setBackground(Color.LIGHT_GRAY);
                selectString = "A";
            } else if (str.equals("B")) {
                selectAButton.setBackground(Color.LIGHT_GRAY);
                selectBButton.setBackground(new Color(37, 102, 68));
                selectCButton.setBackground(Color.LIGHT_GRAY);
                selectDButton.setBackground(Color.LIGHT_GRAY);
                selectString = "B";
            } else if (str.equals("C")) {
                selectAButton.setBackground(Color.LIGHT_GRAY);
                selectBButton.setBackground(Color.LIGHT_GRAY);
                selectCButton.setBackground(new Color(37, 102, 68));
                selectDButton.setBackground(Color.LIGHT_GRAY);
                selectString = "C";
            } else if (str.equals("D")) {
                selectAButton.setBackground(Color.LIGHT_GRAY);
                selectBButton.setBackground(Color.LIGHT_GRAY);
                selectCButton.setBackground(Color.LIGHT_GRAY);
                selectDButton.setBackground(new Color(37, 102, 68));
                selectString = "D";
            }
            answerMap.put(Integer.toString(sort), selectString);
        }
    }

    // 跳转事件
    class AnswerQuestionListener implements ActionListener {
        private JLabel questionLabel, answerLabel;
        private JButton numberOneQuestion, numberTwoQuestion;
        private JButton selectAButton, selectBButton, selectCButton, selectDButton;
        private JButton submit;

        public AnswerQuestionListener(JLabel questionLabel, JLabel answerLabel,JButton numberOneQuestion,JButton numberTwoQuestion,
                                      JButton selectAButton, JButton selectBButton, JButton selectCButton, JButton selectDButton,
                                      JButton submit) {
            super();
            this.questionLabel = questionLabel;
            this.answerLabel = answerLabel;
            this.numberOneQuestion = numberOneQuestion;
            this.numberTwoQuestion = numberTwoQuestion;
            this.selectAButton = selectAButton;
            this.selectBButton = selectBButton;
            this.selectCButton = selectCButton;
            this.selectDButton = selectDButton;
            this.submit = submit;
        }

            @Override
            public void actionPerformed(ActionEvent e) {
                // 2道题目
                if (flag_start != 1) {
                    JOptionPane.showMessageDialog(null, "先点击开始考试！");
                    return;
                }
                // 获取当前选项
                String selectABCD = answerMap.get(Integer.toString(sort));
                //System.out.println(selectABCD);

                // 跳转的操作
                String questionSort = ((JButton) e.getSource()).getText();
                String jumpSort = questionSort;
                ExamData examData = new ExamData();
                // 下一题事件或者跳转事件
                if (questionSort.equals("下一题")) {
                    if (Integer.valueOf(sort) == 2) {
                        JOptionPane.showMessageDialog(null, "已到最后一题！");
                        //提交操作
                        submit.doClick();
                        return;
                    }
                    // 获取下一题序号
                    String nextSort = Integer.toString(sort + 1);
                    jumpSort = nextSort;
                    examData = getNext(getParam(nextSort, questionIdMap.get(Integer.toString(sort)), selectABCD));
                } else if (questionSort.equals("上一题")) {
                    if (Integer.valueOf(sort) == 1) {
                        JOptionPane.showMessageDialog(null, "已是第一题！");
                        return;
                    }
                    // 获取上一题序号
                    String previousSort = Integer.toString(sort - 1);
                    jumpSort = previousSort;
                    examData = getNext(getParam(previousSort, questionIdMap.get(Integer.toString(sort)), selectABCD));
                } else {
                    // 获取当前题目位置
                    String nowSort = Integer.toString(sort);
                    examData = getNext(getParam(questionSort, questionIdMap.get(nowSort), selectABCD));
                }
                // 改变按钮颜色
                changeButton(numberOneQuestion, numberTwoQuestion);
                // 通用部分
                try {
                    JlabelSetText(questionLabel, examData.getQuestion().getSubject());
                    AnswerSetText(answerLabel, examData.getQuestion().getOptions());

                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // 回显选项
                if (answerMap.containsKey(jumpSort)) {
                    String selectLocalString = answerMap.get(jumpSort);
                    if (selectLocalString.equals("A")) {
                        selectAButton.setBackground(new Color(37,102,68));
                        selectBButton.setBackground(Color.LIGHT_GRAY);
                        selectCButton.setBackground(Color.LIGHT_GRAY);
                        selectDButton.setBackground(Color.LIGHT_GRAY);
                    } else if (selectLocalString.equals("B")) {
                        selectBButton.setBackground(new Color(37,102,68));
                        selectAButton.setBackground(Color.LIGHT_GRAY);
                        selectCButton.setBackground(Color.LIGHT_GRAY);
                        selectDButton.setBackground(Color.LIGHT_GRAY);
                    } else if (selectLocalString.equals("C")) {
                        selectCButton.setBackground(new Color(37,102,68));
                        selectBButton.setBackground(Color.LIGHT_GRAY);
                        selectAButton.setBackground(Color.LIGHT_GRAY);
                        selectDButton.setBackground(Color.LIGHT_GRAY);
                    } else if (selectLocalString.equals("D")) {
                        selectDButton.setBackground(new Color(37,102,68));
                        selectBButton.setBackground(Color.LIGHT_GRAY);
                        selectCButton.setBackground(Color.LIGHT_GRAY);
                        selectAButton.setBackground(Color.LIGHT_GRAY);
                    }
                } else {
                    selectAButton.setBackground(Color.LIGHT_GRAY);
                    selectBButton.setBackground(Color.LIGHT_GRAY);
                    selectCButton.setBackground(Color.LIGHT_GRAY);
                    selectDButton.setBackground(Color.LIGHT_GRAY);
                }
        }
    }

        // 提交事件
        class SubmitListener implements ActionListener {
            private JLabel examTitleLabel, countDown, questionLabel, answerLabel;
            private JButton selectAButton, selectBButton, selectCButton, selectDButton;

            public SubmitListener(JLabel examTitleLabel, JLabel countDown, JLabel questionLabel, JLabel answerLabel,
                                  JButton selectAButton, JButton selectBButton, JButton selectCButton,
                                  JButton selectDButton) {
                super();
                this.examTitleLabel = examTitleLabel;
                this.countDown = countDown;
                this.questionLabel = questionLabel;
                this.answerLabel = answerLabel;
                this.selectAButton = selectAButton;
                this.selectBButton = selectBButton;
                this.selectCButton = selectCButton;
                this.selectDButton = selectDButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag_start != 1) {
                    JOptionPane.showMessageDialog(null, "请先点击开始考试！");
                    return;
                }
                //提交最后一次答题结果

                // 获取当前选项
                String selectABCD = answerMap.get(Integer.toString(sort));

                String nowSort = Integer.toString(sort);

                getNext(getParam(nowSort, questionIdMap.get(nowSort), selectABCD));


                // 销毁timer
                timer.cancel();
                timer = new Timer();

                flag_start = 0;
                examTitleLabel.setText("未开始");
                countDown.setText("剩时：30秒");
                sort = 0;
                String submitString = getSubmit(getParam("0", "0", "0"));
                // 重置全局变量数据
                examId = "";
                answerMap = new HashMap<String, String>();
                questionIdMap = new HashMap<String, String>();

                selectAButton.setBackground(Color.LIGHT_GRAY);
                selectBButton.setBackground(Color.LIGHT_GRAY);
                selectCButton.setBackground(Color.LIGHT_GRAY);
                selectDButton.setBackground(Color.LIGHT_GRAY);

                questionLabel.setText("你的分数是:" + submitString.substring(11) + "分");
                answerLabel.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EquipAndExamFrame.vs.VoiceBroadcast("考试结束");
                            Thread.sleep(10000);
                            questionLabel.setText("");
                            ((CardLayout) examCardPanel.getLayout()).next(examCardPanel);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }).start();

        }
    }

    // 开始答题事件
    class StartListener implements ActionListener {
        private JLabel examTitleLabel, countDown, questionLabel, answerLabel;
        private JButton numberOneQuestion, numberTwoQuestion;
        private JButton submit;

        public StartListener(JLabel examTitleLabel, JLabel countDown, JLabel questionLabel,
                                     JLabel answerLabel, JButton numberOneQuestion, JButton numberTwoQuestion, JButton submit) {
            super();
            this.examTitleLabel = examTitleLabel;
            this.countDown = countDown;
            this.questionLabel = questionLabel;
            this.answerLabel = answerLabel;
            this.numberOneQuestion = numberOneQuestion;
            this.numberTwoQuestion = numberTwoQuestion;
            this.submit = submit;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (flag_start != 0) {
                return;
            }
            // 刷新时间
            time = 30 + 3;
            timer.cancel();
            timer = new Timer();
            timerStart(1000, 1, countDown,submit);
            // 第二次点击开始按钮无效
            flag_start = 1;
            examTitleLabel.setText("开始答题");
            ExamData examData = getStart(getParam("1", "0", "0"));
            AnswerSetText(answerLabel, examData.getQuestion().getOptions());

            // 改变颜色
            changeButton(numberOneQuestion, numberTwoQuestion);

            try {
                JlabelSetText(questionLabel, examData.getQuestion().getSubject());

            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    // 调用http服务
    public ExamData getStart(Map<String, String> map) {
        Examination e = new Examination();
        String jsonString = e.doGet(map);
        ExamData examData = Examination.JsonToObject(jsonString, ExamData.class);

        // 全局变量赋值
        questionIdMap.put(examData.getQuestion().getQuestionSort(),
                Integer.toString(examData.getQuestion().getQuestionId()));
        examId = examData.getExamId().toString();
        return examData;
    }

    // 获取下一题
    public ExamData getNext(Map<String, String> map) {
        Examination e = new Examination();
        String jsonString = e.nextGet(map);
        ExamData examData = Examination.JsonToObject(jsonString, ExamData.class);

        questionIdMap.put(examData.getQuestion().getQuestionSort(),
                Integer.toString(examData.getQuestion().getQuestionId()));
        return examData;
    }

    // 提交
    public String getSubmit(Map<String, String> map) {
        Examination e = new Examination();
        String submitString = e.submitGet(map);
        return submitString;
    }

    // 获取参数
    public Map<String, String> getParam(String questionSort, String questionId, String answer) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("userID", userId);
        map.put("userName", userName);
        map.put("customId", ParameterOperate.extract("CurrentCustomID"));
        map.put("customName", ParameterOperate.extract("CurrentProName"));

        // 可变参数
        map.put("questionSort", questionSort);
        map.put("questionId", questionId);
        map.put("answer", answer);

        // 每一次调用参数更新当前题目位置
        sort = Integer.valueOf(questionSort);

        if (examId != null) {
            map.put("examId", examId);
        }
        return map;
    }

    // 选项换行
    public void AnswerSetText(JLabel Label, String options) {
        StringBuilder builder = new StringBuilder("<html>");
        builder.append(options);
        builder.append("</html>");
        Label.setText(builder.toString());
    }

    // 题目换行
    public void JlabelSetText(JLabel Label, String longString) throws InterruptedException {
        longString = "(第" + sort + "/2题)" + longString;
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len = len + 1;
                if (start + len > longString.length())
                    break;
            }
            builder.append(chars, start, len - 1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length() - start);
        builder.append("</html>");
        Label.setText(builder.toString());
    }

    // 已完成题目按钮颜色改变
    public void changeButton(JButton numberOneQuestion, JButton numberTwoQuestion) {
        if (!answerMap.isEmpty()) {
            becomeGRAY();
            // 遍历key(questionSort)
            for (String key : answerMap.keySet()) {
                switch (Integer.valueOf(key)) {
                    case 1:
                        numberOneQuestion.setBackground(new Color(37,102,68));
                        break;
                    case 2:
                        numberTwoQuestion.setBackground(new Color(37,102,68));
                        break;
                    default:
                        break;
                }
            }
            becomeCYAN();
        } else {
            becomeGRAY();
            //当前题号颜色变为黄色
            becomeCYAN();
        }

    }
    public void becomeGRAY() {
        numberOneQuestion.setBackground(Color.LIGHT_GRAY);
        numberTwoQuestion.setBackground(Color.LIGHT_GRAY);
    }

    public void becomeCYAN() {
        switch(sort) {
            case 1:
                numberOneQuestion.setBackground(Color.CYAN);
                break;
            case 2:
                numberTwoQuestion.setBackground(Color.CYAN);
                break;
            default:
                break;
        }
    }
    // 倒计时
    public void timerStart(int period, int type, JLabel countDown,JButton submit) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                time--;
                countDown.setText("剩时：" + String.valueOf(time) + "秒");
                if (time == 0) {
                    timer.cancel();
                    //时间结束调用提交事件
                    submit.doClick();
                }
            }
        }, 0, period);
    }
}

