package com.sznikola.punchcard.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.sznikola.punchcard.common.ResultCode;
import com.sznikola.punchcard.common.ResultJson;
import com.sznikola.punchcard.service.PCFaceService;
import com.sznikola.punchcard.util.ImageUtils;
import com.sznikola.punchcard.util.VoiceBroadcastUtils;
import com.sznikola.punchcard.util.closesystem.ShutdownMain;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.sznikola.punchcard.common.ResultCode.*;


/**
 * @author yzh
 * @Description 打卡主界面
 * @email yzhcherish@163.com
 * @data 2022-09-06  14:19
 */

@Slf4j
@SpringBootApplication
public class PunchCardMainFrame {
    //静态代码快，加载OpenCV依赖
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    //获取屏幕大小
    static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    static int windowsWidth = (int) screensize.getWidth();  //得到宽
    static int windowsHeight = (int) screensize.getHeight();    //得到高

    //new一个人脸服务的一个对象
    static PCFaceService fs = new PCFaceService();
    static VoiceBroadcastUtils vs = new VoiceBroadcastUtils();

    /*
     * 全部都是创建工具
     */
    static public JFrame pcFrame;
    //Container container = pcFrame.getContentPane();//创建一个容器，方便向框架内添加组件
    //定义一个panel
    static JPanel pCardPanel;
    //下面都是定义的是头部
    static JPanel headPanel;
    static JPanel headPLeft;
    static JPanel headPCenter;
    static JPanel headPRight;
    //下面都是定义的是中部
    static JPanel centerPanel;
    static JPanel centerPCenter;
    //下面都是定义的是尾部
    static JPanel endPanel;
    static JPanel endPCenter;
    static JLabel message;

    //创建了一个摄像头窗口
    static JLabel cameralable;

    //适用于VideoCapture打开的摄像头
    static VideoCapture camera;

    static JLabel titleName;

    static JLabel time;
    static JLabel date;
    static JLabel week;

    static JButton shutdown;
    static JLabel info;
    static JLabel nameInfo;
    static ImageIcon shutdownIcon;
    static JButton register;
    static BufferedImage showImg = null;
    static ImageIcon registerIcon;
    static ImageIcon faceareaIcon;
    static  ImageIcon infoIcon;
    static JLabel recgarea;

    static long pretime;

    static Image frameImage = null;
    static Image registerImage1 = null;

    static Image registerImage2 = null;

    static Image shutdownImage1 = null;

    static Image shutdownImage2 = null;
    static Image faceareaImage = null;
    static Image infoImage = null;
    static int cPWidth;
    static int cPHeight;

    static {
        try {
            // 图片资源
            frameImage = ImageIO.read(new File("./res/img/nicolalog.png")).getScaledInstance(40, 40, Image.SCALE_DEFAULT);
            registerImage1 = ImageIO.read(new File("./res/img/register1.png")).getScaledInstance(48, 48, Image.SCALE_DEFAULT);
            registerImage2 = ImageIO.read(new File("./res/img/register2.png")).getScaledInstance(48, 48, Image.SCALE_DEFAULT);
            shutdownImage1 = ImageIO.read(new File("./res/img/shutdownicon.png")).getScaledInstance(48, 48, Image.SCALE_DEFAULT);
            shutdownImage2 = ImageIO.read(new File("./res/img/shutdownicon2.png")).getScaledInstance(48, 48, Image.SCALE_DEFAULT);
            faceareaImage = ImageIO.read(new File("./res/img/facearea3.png")).getScaledInstance(500, 500, Image.SCALE_DEFAULT);
            infoImage = ImageIO.read(new File("./res/img/button.png")).getScaledInstance(100, 50, Image.SCALE_DEFAULT);
            // 注册按钮图标
            registerIcon = new ImageIcon();
            registerIcon.setImage(registerImage1);//这里设置图片大小，目前是20*20

            // 退出系统按钮图标
            shutdownIcon = new ImageIcon();
            shutdownIcon.setImage(shutdownImage1);

            //识别固定位置
            faceareaIcon = new ImageIcon();
            faceareaIcon.setImage(faceareaImage);

            //提交信息图标
            infoIcon = new ImageIcon();
            infoIcon.setImage(infoImage);
        } catch (IOException e) {
            throw new RuntimeException("图片和图标加载失败", e);
        }
    }


    public PunchCardMainFrame() {
        initialize();
    }

    /*
     * 默认初始化加载
     */
    private void initialize() {

        //new一个JFrame
        pcFrame = new JFrame("培训打卡系统");
        //设置高度宽度
        pcFrame.setSize(windowsWidth, windowsHeight);
        //设置一个关闭窗口
        pcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小不可变
        pcFrame.setResizable(true);
        //关闭标题栏
        pcFrame.setUndecorated(true);
        //设置是否可见
        //pcFrame.setVisible(true);

        //窗口图标，当文件不存在时抛出异常
        pcFrame.setIconImage(frameImage); //窗口图标


        //新建一个面板，用于存放控件
        pCardPanel = new JPanel();
        pCardPanel.setLayout(new BorderLayout());
        //pCardPanel.setBackground(Color.CYAN);

        //添加timeJlabel进frame窗口
        pcFrame.getLayeredPane().add(pCardPanel, new Integer(Integer.MAX_VALUE));
        //设置大小
        //pCardPanel.setPreferredSize(new Dimension(300,150));
        pCardPanel.setSize(windowsWidth, windowsHeight);
        //将背景设置透明
        pCardPanel.setOpaque(false);

        //调用对象
        this.getCameralable();
        this.headPanel();
        this.setCenterPanel();
        this.endPanel();

    }

    /*
     * 设置头部panel
     **/
    private void headPanel() {
        headPanel = new JPanel();
        //设置边界布局
        headPanel.setLayout(new BorderLayout());
        //设置大小长度
        //headPanel.setSize(windowsWidth, 100);
        //设置大小长度
        headPanel.setPreferredSize(new Dimension(windowsWidth,(int) (windowsHeight * 0.1)));
        //设置背景透明
        headPanel.setOpaque(false);
        pCardPanel.add(headPanel, BorderLayout.NORTH);

        //调用对象
        this.nowTime();
        this.title();
        this.setMessage();

        //时间置于左边
        headPLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headPLeft.add(time);
        headPLeft.add(date);
        headPLeft.add(week);
        //将背景设置透明
        headPLeft.setOpaque(false);
        headPLeft.setLayout(null);


        /*
            置于中间
        */
        headPCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //headPCenter.add(titleName);
        //将背景设置透明
        headPCenter.setOpaque(false);

        /*
            头部右边的注册，关机按钮
        */
        headPRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headPRight.setOpaque(false);    //将背景设置透明

        //注册按钮
        register = new JButton();
        register.setSize(60, 60);
        register.setContentAreaFilled(false);   //将按钮设置透明
        register.setMargin(new Insets(100,0,0,0));    //将边框外的上下左右空间设置为0
        //register.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        register.setBorder(null);   //除去边框
        register.setFocusPainted(false);    //去除内边框
        //registerIcon = new ImageIcon("C:\\parameter\\img\\register1.png","注册名字");
        register.setIcon(registerIcon);
        headPRight.add(register);

        //关闭按钮
        shutdown = new JButton();
        shutdown.setSize(60, 60);

        shutdown.setFocusPainted(false);    //去除内边框
        shutdown.setBorder(null);   //除去边框
        shutdown.setContentAreaFilled(false);   //将按钮设置透明

        shutdown.setIcon(shutdownIcon);
        headPRight.add(shutdown);


        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("你移动到注册按钮");
                registerIcon.setImage(registerImage2);
                register.setIcon(registerIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("你已经离开注册按钮");
                registerIcon.setImage(registerImage1);
                register.setIcon(registerIcon);
            }

        });


        //关闭按钮的鼠标移动事件
        shutdown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("你移动到关机按钮");
                shutdownIcon.setImage(shutdownImage2);//这里设置图片大小，目前是20*20
                shutdown.setIcon(shutdownIcon);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("你已经离开关机按钮");
                shutdownIcon.setImage(shutdownImage1);//这里设置图片大小，目前是20*20
                shutdown.setIcon(shutdownIcon);
            }
        });

        shutdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new ShutdownMain();
                Runtime rTime = Runtime.getRuntime();
                int time = 0;//设置关机时间
                System.out.println("你点击了关机按钮");

                int flag = JOptionPane.showConfirmDialog(pcFrame, "是否关闭电脑?",
                        "关机", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(JOptionPane.YES_OPTION == flag){
                    try {
                        rTime.exec("shutdown -s -t " + time);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    return;
                }
            }
        });
        Box vBox = Box.createHorizontalBox();
        vBox.add(headPLeft);
        vBox.add(headPCenter);
        vBox.add(headPRight);
        headPanel.add(vBox);
        //pcFrame.setContentPane(vBox);
    }
    /*
     * 设置中部panel
     **/
    private void setCenterPanel(){
        centerPanel = new JPanel();
        //设置边界布局
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(windowsWidth,(int) (windowsHeight * 0.8)));

        cPWidth = windowsWidth;
//        cPHeight = (int) (windowsHeight * 0.8);
//        //设置大小长度
//        centerPanel.setPreferredSize(new Dimension(cPWidth,cPHeight));
        //设置背景透明
        centerPanel.setOpaque(false);
        pCardPanel.add(centerPanel, BorderLayout.CENTER);




        centerPCenter = new JPanel();
        centerPCenter.setLayout(new FlowLayout());
        centerPCenter.setPreferredSize(new Dimension(windowsWidth,(int) (windowsHeight * 0.8)));
        //设置背景透明
        centerPCenter.setOpaque(false);
        centerPCenter.setBackground(Color.CYAN);
        //centerPanel.add(centerPCenter);

        Box vBox = Box.createHorizontalBox();
        centerPanel.add(vBox);
        vBox.setSize(windowsWidth,(int) (windowsHeight * 0.8));
        vBox.add(centerPCenter);

        centerPCenter.setLayout(null);
        //将图片放入识别区
        recgarea = new JLabel();
        recgarea.setIcon(faceareaIcon);
        recgarea.setSize(500,500);

        recgarea.setLocation( (windowsWidth /2 - 250), (int) ((windowsHeight *0.8) /2 - 250));
        //recgarea.setHorizontalAlignment(SwingConstants.CENTER); //将label居中
        centerPCenter.add(recgarea);

        nameInfo = new JLabel();
        //设置背景,大小，字体
        nameInfo.setForeground(Color.WHITE);
        //nameInfo.setOpaque(true);
        nameInfo.setLocation((windowsWidth /2 - 250), 100);
        nameInfo.setSize(500,70);
        //info.setIcon(infoIcon);
        nameInfo.setFont(new Font("微软雅黑", Font.BOLD, 60));
        nameInfo.setHorizontalAlignment(SwingConstants.CENTER);  //设置控件左右居中
        centerPCenter.add(nameInfo,BorderLayout.CENTER);

        //弹窗信息
        info = new JLabel();
        //设置背景,大小，字体
        info.setForeground(Color.WHITE);
        info.setLocation((windowsWidth /2)+250, 100);
        info.setSize(500,30);
        //info.setIcon(infoIcon);
        info.setFont(new Font("微软雅黑", Font.BOLD, 30));
        info.setHorizontalAlignment(SwingConstants.CENTER);  //设置控件左右居中
        centerPCenter.add(info,BorderLayout.CENTER);
    }

    private void endPanel() {

        /*
            设置头部的panel
        */
        endPanel = new JPanel();
        //设置边界布局
        endPanel.setLayout(new BorderLayout());
//        //设置大小长度
//        endPanel.setSize(windowsWidth, 80);
        //设置大小长度
        endPanel.setPreferredSize(new Dimension(windowsWidth,(int) (windowsHeight * 0.1)));
        //设置背景透明
        endPanel.setOpaque(false);
        pCardPanel.add(endPanel, BorderLayout.SOUTH);


//        message = new JLabel("正在识别中...");
//        endPanel.add(message);
//        message.setHorizontalAlignment(SwingConstants.CENTER);  //设置控件左右居中
        //尾部中部
        endPCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //headPLeft.add(time);
        endPCenter.add(message);
        //将背景设置透明
        endPCenter.setOpaque(false);

        Box vBox = Box.createHorizontalBox();
        vBox.add(endPCenter);
        endPanel.add(vBox);
        //pcFrame.setContentPane(vBox);
    }

    /*
     * 设置当前时间相关信息
     */
    private void title() {
        //new一个时间控件
        titleName = new JLabel("培训签到处");
        //设置背景,大小，字体
        titleName.setForeground(Color.WHITE);
        titleName.setFont(new Font("微软雅黑", Font.BOLD, 40));
        //headPanel.add(titleName);
        titleName.setHorizontalAlignment(SwingConstants.CENTER);  //设置控件左右居中
    }

    /*
     * 设置当前时间相关信息
     */
    private void nowTime() {

        //new一个时间控件
        time = new JLabel();
        //设置背景,大小，字体
        time.setForeground(Color.WHITE);
        time.setSize(210, 48);
        //time.setBounds(5, 5, 300,60);
        //time.setLocation(0, 0);
        time.setFont(new Font("微软雅黑", Font.BOLD, 48));
        //添加timelable进pCardPanel
        //headPanel.add(time);
        time.setHorizontalAlignment(SwingConstants.LEFT);  //设置控件左右居中
        this.setTimer(time);

        date = new JLabel();
        //设置背景,大小，字体
        date.setForeground(Color.WHITE);
        date.setSize(180, 24);
        date.setLocation(211, 2);
        //time.setBounds(5, 5, 300,60);
        date.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.setDate(date);

        week = new JLabel();
        //设置背景,大小，字体
        week.setForeground(Color.WHITE);
        week.setSize(180, 19);
        week.setLocation(211, 25);
        //time.setBounds(5, 5, 300,60);
        week.setFont(new Font("微软雅黑", Font.BOLD, 18));
        this.setWeek(week);
        System.out.println(week);

    }

    private void setMessage() {
        //new一个时间控件
        message = new JLabel("");
        //设置背景,大小，字体
        message.setForeground(Color.WHITE);
        message.setSize(300, 60);
        //time.setBounds(5, 5, 300,60);
        message.setFont(new Font("微软雅黑", Font.BOLD, 30));
        message.setHorizontalAlignment(SwingConstants.CENTER);  //设置控件左右居中

    }

    /*
     * 设置摄像头相关信息
     */
    private void getCameralable() {
        //new一个JLabel,设置高度宽度
        cameralable = new JLabel("");
        cameralable.setSize(windowsWidth, windowsHeight);
        //添加lable进frame窗口
        //pCardPanel.add(cameralable);
        pcFrame.getContentPane().add(cameralable);
    }

    /*
     * 添加 显示时间的JLabel
     */

    /*
     * 设置时间的动态更新
     */
    private void setTimer(JLabel time) {
        final JLabel varTime = time;
        Timer timeAction = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式yyyy-MM-dd HH:mm:ss
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        //时间启动开始
        timeAction.start();
    }

    /*
     * 设置日期的动态更新
     */
    private void setDate(JLabel data) {
        final JLabel varTime = data;
        Timer timeAction = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式yyyy-MM-dd HH:mm:ss
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        //时间启动开始
        timeAction.start();
    }
    private void setWeek(JLabel week) {
        final JLabel varTime = week;
        Timer timeAction = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式yyyy-MM-dd HH:mm:ss
                SimpleDateFormat df = new SimpleDateFormat("E");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        //时间启动开始
        timeAction.start();
    }

    public static void main(String[] args) {

        SpringApplication.run(PunchCardMainFrame.class, args);

        log.info("打卡系统正在启动...");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PunchCardMainFrame window = new PunchCardMainFrame();
                    window.pcFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //创建OpenCV的视频截取类
        camera = new VideoCapture(0);


        if (camera.isOpened()) {
            pretime = System.currentTimeMillis();
            long currenttime;
            Mat frame = new Mat();
            //加入循环， 因为图片需要一直刷新
            while (true) {
                //这里底层我也不懂，反正大概就是截取摄像头的一帧放入Mat中，Mat就是存图片的一个类
                camera.read(frame); //定义了一个视频流
                BufferedImage bi = null;
                try {
                    bi = fs.mat2BI(frame); //输入Mat 返回Mat中存放的图片（进行了一些处理）
//                    bi = fs.mat2BIEn(frame,windowsWidth,windowsHeight);
                    //bi = fs.mat2BI(frame,windowsWidth,windowsHeight);
                } catch (Exception e) {
                    int flag = JOptionPane.showConfirmDialog(pcFrame, "摄像头复用，请勿重复打开相同的软件",
                            "提示", JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                    if(JOptionPane.YES_OPTION == flag){
                        System.exit(0);
                    }else{
                        System.exit(0);
                    }
                    System.out.println("摄像头复用");
                }

                //对于人脸的操作
                currenttime = System.currentTimeMillis();
                boolean state = true;
                if (currenttime - pretime > 10000 && state) {
                    System.out.println("初始时间" + pretime);
                    System.out.println("结束时间" + currenttime);
                    System.out.println(currenttime - pretime);
                    BufferedImage finalBi = bi; //内部bi
                    ObjectMapper objectMapper = new ObjectMapper();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //设置不截取图片上传
                            String s = fs.judgeMember(finalBi); //对于bi截取的图片做判断

                            if(Objects.isNull(s)){
                                // log一下服务器异常，未获取到返回值
                                log.info("系统与服务器断开连接，请重试");
                                message.setText("系统与服务器断开连接，请重试");
                                return;
                            }

                            ResultJson result ;
                            try {
                                result = objectMapper.readValue(s, ResultJson.class);
                                System.out.println(result);
                            } catch (JsonProcessingException e) {
                                info.setText("");
                                log.info("服务器异常，请联系管理员");
                                message.setText("服务器异常，请联系管理员");
                                throw new RuntimeException(e);
                            }

                            String msg = result.getMessage();
                            int code = result.getCode();
                            System.out.println(result.getData());
                            //50000 = code
                            final Object data = result.getData();
                            //未识别到人脸不播报
                            if(NO_REC.getCode() == code){
                                //message.setText("");
                                nameInfo.setText((String) data);
                                message.setText(msg);
                                vs.VoiceBroadcast((String) data);
                                vs.VoiceBroadcast(msg);
                            }else if ((code >=2001 &&code <=2004)){
                                //message.setText("");
                                nameInfo.setText((String) data);
                                message.setText(msg);
                                vs.VoiceBroadcast((String) data);
                                vs.VoiceBroadcast(msg);
                            }
                            else if(NO_VOICE.getCode() == code){
                                nameInfo.setText("");
                                //info.setText("");
                                message.setText("未识别到人脸");
                            }
                        }
                    }).start();
                    pretime = currenttime;
                }
                try {
                    ImageIcon imageIcon = ImageUtils.imageScaled(bi,windowsWidth , windowsHeight);
                    cameralable.setIcon(imageIcon);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            log.info("未启用摄像头");
        }
        //判断摄像头有没有打开
//        if (camera.isOpened()) {
//            pretime = System.currentTimeMillis();
//            Mat frame = new Mat();
//            //加入循环， 因为图片需要一直刷新
//            while (true) {
//                //这里底层我也不懂，反正大概就是截取摄像头的一帧放入Mat中，Mat就是存图片的一个类
//                camera.read(frame);
//                // fs.mat2BI() 输入Mat 返回Mat中存放的图片（进行了一些处理）
//                BufferedImage bi=fs.mat2BI(frame);
//                //System.out.println(bi);
//
//                long currenttime = System.currentTimeMillis();
//                state = true;
//                if (currenttime - pretime > 5000 && state) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //如果人脸在数据库中，关闭线程。输出和播报语音：***，打卡成功。   播报结束后6秒，继续开启线程
//                            //如果人脸不在数据库中，关闭线程。输出和播报语音：输出和播报语音：陌生人，你不需要打卡哦   播报结束后6秒，继续开启线程
//                            //如果没有人脸，开启人脸识别线程，关闭打卡线程。
//                            //如果有人脸，关闭人脸识别线程，开启打卡线程。
//
//
//                        }
//                    }).start();
//                }
//                //获取对应大小的ImageIcon
//                ImageIcon imageIcon = ImageUtils.imageScaled(bi, 1920, 1080);
//                //将图片放入label中
//                cameralable.setIcon(imageIcon);
//            }
//
//        }
//        else if(!camera.isOpened()){
//            System.out.println("未启用摄像头");
//        }else {
//            System.out.println("摄像头故障");
//        }

    }

}
