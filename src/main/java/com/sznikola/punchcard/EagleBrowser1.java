//package com.sznikola.punchcard;
//
//
//import java.awt.*;
//
//import java.awt.event.ComponentAdapter;
//
//import java.awt.event.ComponentEvent;
//
//import java.awt.event.MouseAdapter;
//
//import java.awt.event.MouseEvent;
//
//import javax.swing.ImageIcon;
//
//import javax.swing.JFrame;
//
//import javax.swing.JLabel;
//
//import javax.swing.JLayeredPane;
//
//import javax.swing.JPanel;
//
//import javax.swing.JRootPane;
//
//import javax.swing.SwingUtilities;
//
//import com.listener.XZWebBrowserListener;
//
//import com.util.ScreenSize;
//
//import chrriis.common.UIUtils;
//
//import chrriis.dj.nativeswing.swtimpl.NativeInterface;
//
//import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
//
//
///**
// * @author yzh
// * @Description
// * @email yzhcherish@163.com
// * @data 2022-09-08  09:56
// */
//
///**
//
// * CS包浏览器，首先访问的是项目首页
//
// *
//
// * @author admin
//
// *
//
// */
//
//public class EagleBrowser1 extends JPanel {
//
//    private static final long serialVersionUID = 1L;
//
//    private JPanel webBrowserPanel;
//
//    private JWebBrowser webBrowser;
//
//    private JFrame frame;
//
//    private Double browserWidth;// 窗体宽度
//
//    private Double browserHeight;//窗体高度
//
//    public EagleBrowser1() {
//
//    }
//
//// 构造方法，传递url和title可创建CS包容浏览器窗体
//
//    public EagleBrowser1(String url, String title) {
//
///**
//
// * 创建浏览器
//
// */
//
//        webBrowserPanel = new JPanel(new BorderLayout());
//
//        webBrowser = new JWebBrowser();
//
//        webBrowser.navigate(url);
//
//        webBrowser.setButtonBarVisible(false);
//
//        webBrowser.setMenuBarVisible(false);
//
//        webBrowser.setBarsVisible(false);
//
//        webBrowser.setStatusBarVisible(false);
//
//        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
//
//        XZWebBrowserListener listener = new XZWebBrowserListener();
//
//        webBrowser.addWebBrowserListener(listener );
//
////add(webBrowserPanel, BorderLayout.CENTER);
//
//// webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");
//
//// webBrowser.executeJavascript("alert('haha')"); //执行Js代码
//
//        UIUtils.setPreferredLookAndFeel();
//
//        NativeInterface.open();
//
//        SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
//
////JFrame.setDefaultLookAndFeelDecorated(true);
//
//                frame = new JFrame();
//
//// 去掉窗口的装饰
//
//                frame.setUndecorated(true);
//
//// 采用指定的窗口装饰风格
//
//                frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//                frame.setLocationByPlatform(true);
//
//                frame.setLayout(null);
//
//// 注意可见性、窗体大小、窗体居中定位，这三个最好按照此顺序书写
//
//// 设置窗体可见
//
//                frame.setVisible(true);
//
//                frame.setResizable(true);
//
//// 设置窗体的宽度、高度
//
//                frame.setSize(1600, 900);
//
//// 设置窗体居中显示
//
//                frame.setLocationRelativeTo(frame.getOwner());
//
///**
//
// * 获取窗体的总宽
//
// */
//
//                browserWidth = frame.getSize().getWidth();
//
//                browserHeight = frame.getSize().getHeight();
//
///**
//
// * 插入浏览器头部窄条的背景图片
//
// */
//
//// 创建具有分层的JLayeredPane
//
//                JLayeredPane layeredPane = new JLayeredPane();
//
//                layeredPane.setBounds(0, 0, browserWidth.intValue(), 40);
//
//// 添加分层的JLayeredPane
//
//                frame.getContentPane().add(layeredPane,BorderLayout.CENTER);
//
//// 创建图片对象
//
//                ImageIcon img = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_head.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                img.setImage(img.getImage().getScaledInstance(browserWidth.intValue(), 40, Image.SCALE_DEFAULT));
//
//                JPanel panel = new JPanel();
//
//                panel.setBounds(0, 0, browserWidth.intValue(), 40);
//
//                layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);//添加到最底层
//
//                JLabel lblNewLabel = new JLabel("");
//
//                panel.add(lblNewLabel);
//
//                lblNewLabel.setIcon(img);
//
///**
//
// * 将网页添加到窗体frame中来
//
// */
//
////设置浏览器距离头部的距离高度
//
//                webBrowserPanel.setBounds(0, 29, browserWidth.intValue(), browserHeight.intValue()-29);
//
//// 添加网页的JPanel，注意：定要将此行代码放置在添加JLayeredPane以下
//
//                frame.getContentPane().add(webBrowserPanel, BorderLayout.CENTER);
//
///**
//
// * 插入浏览器头部左边的图片
//
// */
//
//// 创建图片对象
//
//                ImageIcon headLeftImg = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_head_left.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                headLeftImg.setImage(headLeftImg.getImage().getScaledInstance(24, 40, Image.SCALE_DEFAULT));
//
//                JPanel headLeftPanel = new JPanel();
//
//                headLeftPanel.setBounds(0, -5, 24, 40);
//
//                layeredPane.add(headLeftPanel, JLayeredPane.MODAL_LAYER);//添加到比背景图片高一层的层次中
//
//                JLabel headLeftLabel = new JLabel("");
//
//                headLeftPanel.add(headLeftLabel);
//
//                headLeftLabel.setIcon(headLeftImg);
//
///**
//
// * 插入浏览器窗体左边的标题
//
// */
//
//                JPanel titlePanel = new JPanel();
//
//                titlePanel.setBounds(32, 6, browserWidth.intValue()-150, 20);
//
//                layeredPane.add(titlePanel, JLayeredPane.MODAL_LAYER);
//
////设置JPanel中文字对齐方式为左对齐
//
//                titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//
////设置JPanel为透明的，这样可以让JPanel后面的背景颜色显示出来
//
//                titlePanel.setBackground(null);
//
//                titlePanel.setOpaque(false);
//
//                JLabel titleLbel = new JLabel();
//
//                titleLbel.setText(title);
//
//                titleLbel.setSize(100, 20);
//
//                titleLbel.setHorizontalAlignment(JLabel.LEFT);
//
//                titleLbel.setFont(new Font("微软雅黑", 0, 12));
//
//                titleLbel.setForeground(Color.white);
//
//                titlePanel.add(titleLbel);
//
///**
//
// * 插入右边关闭背景图片及功能
//
// */
//
//// 创建图片对象
//
//                ImageIcon headCloseImg = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_close_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                headCloseImg.setImage(headCloseImg.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                JPanel headClosePanel = new JPanel();
//
//                headClosePanel.setBounds(browserWidth.intValue() - 40, -5, 40, 40);
//
//                layeredPane.add(headClosePanel, JLayeredPane.MODAL_LAYER);
//
//                JLabel headCloseLabel = new JLabel("");
//
//                headClosePanel.add(headCloseLabel);
//
//                headCloseLabel.setIcon(headCloseImg);
//
//                headCloseLabel.addMouseListener(new MouseAdapter() {
//
//// 鼠标点击关闭图片，实现关闭窗体的功能
//
//                    @Override
//
//                    public void mouseClicked(MouseEvent e) {
//
//                        frame.dispose();//此种方式的关闭只是关闭了窗体，后台程序还是没有真正关闭
//
//                        System.exit(0);//此种方式是真正的关闭
//
//                    }
//
//// 鼠标进入，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseEntered(MouseEvent e) {
//
//// 创建图片对象
//
//                        ImageIcon closeImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_close_02.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                        closeImg1.setImage(closeImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                        headCloseLabel.setIcon(closeImg1);
//
//                    }
//
//// 鼠标离开，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseExited(MouseEvent e) {
//
//// 创建图片对象
//
//                        ImageIcon headCloseImg = new ImageIcon(
//
//                                EagleBrowser1.class.getResource("/images/browser_close_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                        headCloseImg.setImage(headCloseImg.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                        headCloseLabel.setIcon(headCloseImg);
//
//                    }
//
//                });
//
///**
//
// * 插入右边最大化背景图片及功能
//
// */
//
//// 创建图片对象
//
//                ImageIcon headMaxImg = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_middle_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                headMaxImg.setImage(headMaxImg.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                JPanel headMaxPanel = new JPanel();
//
//                headMaxPanel.setBounds(browserWidth.intValue() - 40 - 40, -5, 40, 40);
//
//                layeredPane.add(headMaxPanel, JLayeredPane.MODAL_LAYER);
//
//                JLabel headMaxLabel = new JLabel("");
//
//                headMaxPanel.add(headMaxLabel);
//
//                headMaxLabel.setIcon(headMaxImg);
//
//                headMaxLabel.addMouseListener(new MouseAdapter() {
//
//// 鼠标点击关闭图片，实现关闭窗体的功能
//
//                    @Override
//
//                    public void mouseClicked(MouseEvent e) {
//
//// 判断窗体当前宽度，如果与计算机分辨率同样宽，那么窗体宽度变为默认值，如果小于计算机分辨率宽度，那么窗体变为最大化显示
//
//                        if (frame.getSize().getWidth() < ScreenSize.getScreenWidth()) {
//
////加入此行代码，可以让窗体最大化不会遮挡住桌面上底部的工具栏；
//
//                            frame.setMaximizedBounds(new Rectangle(0, 1, ScreenSize.getScreenWidth().intValue(), ScreenSize.getScreenHeight().intValue()));
//
//                            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// 窗体最大化
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_max_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        } else {
//
//                            frame.setSize(1600, 900);
//
//                            frame.setLocationRelativeTo(frame.getOwner());
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_middle_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        }
//
//                    }
//
//// 鼠标进入，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseEntered(MouseEvent e) {
//
//// 判断窗体当前宽度与计算机分辨率宽度大小
//
//                        if (frame.getSize().getWidth() < ScreenSize.getScreenWidth()) {
//
////窗体宽度小于计算机分辨率宽度，图片应显示中间那种
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_middle_02.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        }else{
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_max_02.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        }
//
//                    }
//
//// 鼠标离开，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseExited(MouseEvent e) {
//
//// 判断窗体当前宽度与计算机分辨率宽度大小
//
//                        if (frame.getSize().getWidth() < ScreenSize.getScreenWidth()) {
//
////窗体宽度小于计算机分辨率宽度，图片应显示中间那种
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_middle_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        }else{
//
//// 创建图片对象
//
//                            ImageIcon maxImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_max_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                            maxImg1.setImage(maxImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                            headMaxLabel.setIcon(maxImg1);
//
//                        }
//
//                    }
//
//                });
//
///**
//
// * 插入右边最小化背景图片及功能
//
// */
//
//// 创建图片对象
//
//                ImageIcon headMinImg = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_min_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                headMinImg.setImage(headMinImg.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                JPanel headMinPanel = new JPanel();
//
//                headMinPanel.setBounds(browserWidth.intValue() - 40 - 40 - 40, -5, 40, 40);
//
//                layeredPane.add(headMinPanel, JLayeredPane.MODAL_LAYER);
//
//                JLabel headMinLabel = new JLabel("");
//
//                headMinPanel.add(headMinLabel);
//
//                headMinLabel.setIcon(headMinImg);
//
//                headMinLabel.addMouseListener(new MouseAdapter() {
//
//// 鼠标点击关闭图片，实现关闭窗体的功能
//
//                    @Override
//
//                    public void mouseClicked(MouseEvent e) {
//
//                        frame.setExtendedState(JFrame.ICONIFIED);// 最小化窗体
//
//                    }
//
//// 鼠标进入，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseEntered(MouseEvent e) {
//
//// 创建图片对象
//
//                        ImageIcon mainImg1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_min_02.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                        mainImg1.setImage(mainImg1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                        headMinLabel.setIcon(mainImg1);
//
//                    }
//
//// 鼠标离开，换关闭的背景图片
//
//                    @Override
//
//                    public void mouseExited(MouseEvent e) {
//
//// 创建图片对象
//
//                        ImageIcon headMinImg = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_min_01.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                        headMinImg.setImage(headMinImg.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//
//                        headMinLabel.setIcon(headMinImg);
//
//                    }
//
//                });
//
///**
//
// * 设置检测窗体大小发生变化的事件
//
// */
//
//                frame.addComponentListener(new ComponentAdapter() {
//
//                    @Override
//
//                    public void componentResized(ComponentEvent e) {
//
///**
//
// * 获取尺寸变化后的窗体的宽度、高度
//
// */
//
//                        browserWidth = frame.getSize().getWidth();
//
//                        browserHeight = frame.getSize().getHeight();
//
///**
//
// * 浏览器顶部窄背景图片宽度改变
//
// */
//
//                        layeredPane.setBounds(0, -5, browserWidth.intValue(), 34);
//
//                        titlePanel.setBounds(32, 6, browserWidth.intValue()-150, 20);
//
//// 创建图片对象
//
//                        ImageIcon img1 = new ImageIcon(EagleBrowser1.class.getResource("/images/browser_head.jpg"));
//
//// 设置图片在窗体中显示的宽度、高度
//
//                        img1.setImage(
//
//                                img1.getImage().getScaledInstance(browserWidth.intValue(), 40, Image.SCALE_DEFAULT));
//
//                        panel.setBounds(0, -5, browserWidth.intValue(), 40);
//
//                        panel.add(lblNewLabel);
//
//                        lblNewLabel.setIcon(img1);
//
///**
//
// * 浏览器顶部窄条右侧最小化、最大化、关闭按钮图片根据宽度进行定位
//
// */
//
//                        headClosePanel.setBounds(browserWidth.intValue() - 40, -5, 40, 40);// 浏览器顶部关闭图片定位
//
//                        headMaxPanel.setBounds(browserWidth.intValue() - 40 - 40, -5, 40, 40);// 浏览器顶部最大化图片定位
//
//                        headMinPanel.setBounds(browserWidth.intValue() - 40 - 40 - 40, -5, 40, 40);// 浏览器顶部最小化图片定位
//
///**
//
// * 浏览器宽度、高度进行改变
//
// */
//
////设置浏览器距离头部的距离高度
//
//                        webBrowserPanel.setBounds(0, 29, browserWidth.intValue(), browserHeight.intValue()-29);
//
//                    }
//
//                });
//
//            }
//
//        });
//
//// 必须将下行代码注释掉，否则不能够跳转到此窗体
//
//// NativeInterface.runEventPump();
//
//    }
//
//    public static void main(String[] args) {
//
//        String url = "http://www.hao123.com";
//
//        String title = "测试CS包浏览器";
//
//        EagleBrowser1 eagleBrowser = new EagleBrowser1(url, title);
//
//    }
//
//}