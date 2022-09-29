package com.sznikola.punchcard.util.closesystem;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-07  15:00
 */

/**
 * 类名：Countdown
 * 包名：com.mg
 * 创建时间：2020/6/24 9:49
 * 创建人： 明哥
 * 描述：
 **/
public class Countdown extends Thread {
    CloseSystem cs = new CloseSystem();
    String afx;

    //开启倒计时线程
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            cs.time--;
//            if (cs.time == 5) {//当只剩下5秒时
//                JLabel sb = new JLabel("倒计时五秒你确定不输入吗？");
//                sb.setFont(new java.awt.Font("隶书", 1, 24));
//                sb.setBackground(Color.GREEN);
//                JPanel pa = new JPanel();
//                pa.add(sb);
//                MyFrame.myJF.add(pa);
//                MyFrame.myJF.setVisible(true);
//                MyFrame.edit.setVisible(false);
//                MyFrame.ok.setEnabled(false);
//            }
            afx = "倒计时：" + cs.time;
            ShutdownMain.label.setText(afx);
            ShutdownMain.jp03.add(ShutdownMain.label);
            if (cs.time == 0) {
                System.exit(0);
            }
        }
    }
}
