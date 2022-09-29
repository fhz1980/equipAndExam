package com.sznikola.punchcard.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//加载程序包

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-21  13:57
 */
public class Test extends JFrame{
//继承JFrame类

    Test(){
//构造无参方法
        this.setDefaultLookAndFeelDecorated(true);
        //美观布局
        this.setUndecorated(true);
        //清楚原有窗体格式
        this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
        //更换为消息窗格式（没有最大最小化按钮）
        this.setTitle("这是一个标题");
        //为窗体添加标题
        this.setSize(300, 140);
        //设置窗体大小
        this.setResizable(false);
        //设置窗体大小不可变
        this.setLocation(650,250);
        //设置窗体位置

        JPanel Panel=new JPanel();
        //添加一个容器

        JLabel Label=new JLabel("这是一个标签");
        //添加一个内容为“这是一个标签”的标签
        Label.setFont(new Font("微软雅黑",Font.BOLD,20));
        //设置标签字体、粗体、字号
        Label.setForeground(Color.red);
        //设置文字颜色
        Label.setToolTipText("这是一个注释");
        //为标签添加一串注释
        Panel.add(Label);
        //向容器内添加此标签

        JButton Button=new JButton("退出");
        //添加一个内容为“退出”的按钮
        Button.setFont(new Font("微软雅黑",Font.BOLD,20));
        //设置按钮字体、粗体、字号
        Button.setToolTipText("这是一个注释");
        //为按钮添加一串注释
        Button.addActionListener(new ActionListener() {
            //为按钮添加一个监视器
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                //设置按钮按下的事件为关闭程序
            }
        });
        Panel.add(Button);
        //向容器内添加此按钮

        this.add(Panel);
        //向窗体内添加此容器
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //设置窗体的关闭按钮为无动作
        this.setVisible(true);
        //设置窗体可见
    }

    public static void main(String[] args) {
        new Test();
    }

}
