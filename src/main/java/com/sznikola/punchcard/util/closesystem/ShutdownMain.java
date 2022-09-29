package com.sznikola.punchcard.util.closesystem;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-07  15:01
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 类名：MyFrame
 * 包名：com.mg
 * 创建时间：2020/6/24 9:53
 * 创建人： 明哥
 * 描述：
 **/
public class ShutdownMain {
    Countdown cd = new Countdown();
    static JLabel label = new JLabel();
    static JFrame myJF = new JFrame("关机程序");
    static JButton ok = new JButton("确定");
    static JTextField edit = new JTextField(8);
    JPanel jp01 = new JPanel();
    JPanel jp02 = new JPanel();
    static JPanel jp03 = new JPanel();
    JPanel jp04 = new JPanel();
    CloseSystem cs = new CloseSystem();

    public ShutdownMain() {
        myJF.setSize(500, 500);
        myJF.setLocation(400, 100);
        myJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJF.getRootPane().setDefaultButton(ok);//响应回车键
        label.setFont(new Font("行楷", 1, 24));
        label.setForeground(Color.RED);
        edit.setFont(new Font("行楷", 1, 18));
        jp02.add(edit);
        jp03.add(label);
        jp04.add(ok);
        myJF.add(jp01);
        myJF.add(jp02);
        myJF.add(jp03);
        myJF.add(jp04);
        myJF.setLayout(new GridLayout(5, 1));

        myJF.setVisible(true);
        cd.start();


        ok.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                cs.close();

                if (edit.getText().equals(cs.input)) {
                    cs.cancel();
                    JOptionPane.showMessageDialog(null, "关机已取消！",
                            "", JOptionPane.INFORMATION_MESSAGE);
                    cd.stop(); //线程停止
                    edit.setEnabled(false);
                    ok.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "输入错误！",
                            "提示", JOptionPane.ERROR_MESSAGE);
                    edit.setText("");
                }
            }
        });
    }

    public static void main() {
        new ShutdownMain();
    }
}
