/*
 * Created by JFormDesigner on Sun Nov 20 17:26:10 CST 2022
 */

package com.sznikola.devicestate;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class zhangsan extends JFrame {
    public zhangsan() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        button1 = new JButton();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- button1 ----
        button1.setText("text");
        contentPane.add(button1, BorderLayout.SOUTH);

        //======== panel1 ========
        {
            panel1.setLayout(new CardLayout());

            //======== panel2 ========
            {
                panel2.setBackground(new Color(0xff3333));
                panel2.setLayout(new BorderLayout());
            }
            panel1.add(panel2, "card3");

            //======== panel3 ========
            {
                panel3.setBackground(new Color(0xffcc33));
                panel3.setLayout(new BorderLayout());
            }
            panel1.add(panel3, "card2");
        }
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton button1;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        new zhangsan();
    }
}
