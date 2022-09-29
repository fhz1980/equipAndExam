package com.sznikola.punchcard;

import javax.swing.*;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.io.File;

/**

 * 获取当前计算机分辨率的工具类

 * @author admin

 *

 */

public class ScreenSize {

//获取当前计算机分辨率的宽度

    public static Double getScreenWidth(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return screenSize.getWidth();

    }

//获取当前计算机分辨率的高度

    public static Double getScreenHeight(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return screenSize.getHeight();

    }

    public static void main(String[] args) {
        File file = new File("./resources/img/nicolalog.png");

        ImageIcon imageIcon1 = new ImageIcon(file.getAbsolutePath(), "注册名字");
    }
}