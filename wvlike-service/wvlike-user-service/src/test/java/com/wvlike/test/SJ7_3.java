package com.wvlike.test;

/**
 * @Date: 2023/6/27
 * @Author: tuxinwen
 * @Description: 字体测试
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SJ7_3 {
    JLabel q1, q2, q3, q4;
    JPanel mb1, mb2, mb3, mb4;
    JCheckBox fx1, fx2;
    JRadioButton dx1, dx2, dx3, dx4;
    JComboBox xlk;
    ButtonGroup yz;

    public class ys implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fx1) {
                q4.setFont(new Font("华文细黑", Font.BOLD, 60));
            } else if (e.getSource() == fx2) {
                q4.setFont(new Font("华文细黑", Font.ITALIC, 60));
            }
        }
    }

    public class ZT implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == dx1) {
                q4.setFont(new Font("宋体", Font.PLAIN, 60));
            } else if (e.getSource() == dx2) {
                q4.setFont(new Font("黑体", Font.PLAIN, 60));
            } else if (e.getSource() == dx3) {
                q4.setFont(new Font("隶书", Font.PLAIN, 60));
            } else if (e.getSource() == dx4) {
                q4.setFont(new Font("楷体", Font.PLAIN, 60));
            }
        }
    }

    public class DX implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (xlk.getSelectedIndex() == 0) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 10));
            } else if (xlk.getSelectedIndex() == 1) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 20));
            } else if (xlk.getSelectedIndex() == 2) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 30));
            } else if (xlk.getSelectedIndex() == 3) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 40));
            } else if (xlk.getSelectedIndex() == 4) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 50));
            } else if (xlk.getSelectedIndex() == 5) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 60));
            } else if (xlk.getSelectedIndex() == 6) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 70));
            } else if (xlk.getSelectedIndex() == 7) {
                q4.setFont(new Font("华文细黑", Font.PLAIN, 80));
            }
        }
    }

    public void tx() {
        JFrame ck = new JFrame("字体特效");
        ck.setSize(600, 500);
        ck.setLocation(500, 300);
        ck.setLayout(null);
        mb1 = new JPanel();
        mb2 = new JPanel();
        mb3 = new JPanel();
        mb4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 90));
        mb4.setSize(500, 240);
        mb4.setLocation(50, 10);
        mb4.setBackground(Color.WHITE);
        mb3.setSize(80, 180);
        mb3.setLocation(420, 260);
        mb2.setLayout(new FlowLayout(FlowLayout.LEFT));
        mb2.setSize(105, 180);
        mb2.setLocation(230, 260);
        mb1.setLocation(40, 260);
        mb1.setSize(110, 180);
        q1 = new JLabel("字体样式");
        q1.setFont(new Font("宋体", Font.BOLD, 22));
        q2 = new JLabel("字体");
        q2.setFont(new Font("宋体", Font.BOLD, 22));
        q3 = new JLabel("字号");
        q3.setFont(new Font("宋体", Font.BOLD, 22));
        q4 = new JLabel("中文字体");
        q4.setFont(new Font("华文细黑", Font.PLAIN, 60));
        fx1 = new JCheckBox("粗体");
        fx2 = new JCheckBox("斜体");
        dx1 = new JRadioButton("宋体");
        dx2 = new JRadioButton("黑体");
        dx3 = new JRadioButton("隶书");
        dx4 = new JRadioButton("楷体_GB2312");
        yz = new ButtonGroup();
        yz.add(dx1);
        yz.add(dx2);
        yz.add(dx3);
        yz.add(dx4);
        String[] ss = {"10", "20", "30", "40", "50", "60", "70", "80"};
        xlk = new JComboBox(ss);
        ck.add(mb4);
        mb4.add(q4);
        ck.add(mb1);
        mb1.add(q1);
        mb1.add(fx1);
        mb1.add(fx2);
        ck.add(mb2);
        mb2.add(q2);
        mb2.add(dx1);
        mb2.add(dx2);
        mb2.add(dx3);
        mb2.add(dx4);
        ck.add(mb3);
        mb3.add(q3);
        mb3.add(xlk);
        fx1.addActionListener(new ys());
        fx2.addActionListener(new ys());
        dx1.addActionListener(new ZT());
        dx2.addActionListener(new ZT());
        dx3.addActionListener(new ZT());
        dx4.addActionListener(new ZT());
        xlk.addActionListener(new DX());
        ck.setResizable(false);
        ck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ck.setVisible(true);
    }

    public static void main(String[] args) {
        SJ7_3 xx = new SJ7_3();
        xx.tx();
    }
}
