package com.sznikola.punchcard;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-07  13:55
 */
import java.awt.Color;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

/**

 * 体验button 事件监听

 *

 */

public class ButtonTest extends JFrame {

    public static final int DEFAULT_WIDTH=300;

    public static final int DEFAULT_HEIGHT=200;

    public static void main(String[] args) {

        ButtonTest bt=new ButtonTest();

        bt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bt.setVisible(true);

    }

//构造器

    public ButtonTest()

    {

        setTitle("ButtonTest");

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

//add panel to frame

        ButtonPanel panel=new ButtonPanel();

        add(panel);

    }

//description the add to the frame of the panel

    private class ButtonPanel extends JPanel

    {

        public ButtonPanel()

        {

            JButton yellowButton=new JButton("Yellow");

            JButton blueButton=new JButton("Blue");

            JButton redButton=new JButton("red");

// add button to panel

            add(yellowButton);

            add(blueButton);

            add(redButton);

//create button action

            ColorAction yellowAction=new ColorAction(Color.YELLOW);

            ColorAction blueAction=new ColorAction(Color.BLUE);

            ColorAction redAction=new ColorAction(Color.RED);

//associate actions with buttons

            yellowButton.addActionListener(yellowAction);

            blueButton.addActionListener(blueAction);

            redButton.addActionListener(redAction);

        }

//An action Listener that sets the panel's background color

        private class ColorAction implements ActionListener

        {

            private Color backgroundColor;

            public ColorAction(Color c)

            {

                backgroundColor=c;

            }

            public void actionPerformed(ActionEvent e)

            {

                setBackground(backgroundColor);

            }

        }

    }

}