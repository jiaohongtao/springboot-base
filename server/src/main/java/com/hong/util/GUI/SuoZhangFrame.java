package com.hong.util.GUI;

import javax.swing.*;

/**
 * 标签控件: https://www.cnblogs.com/suozhang/p/6506489.html
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月26日
 */
public class SuoZhangFrame extends JFrame {
    private SuoZhangFrame(String title) {
        //设置窗口标题
        this.setTitle(title);
        //当用户关闭窗口的时候关掉应用程序
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //设置长和宽,最常见的屏幕分辨率 1366*768，2017年3月9日09:31:24
        this.setSize(1100, 700);
        //设置起点位置，屏幕右上角 为 0,0
        this.setLocation(100, 100);

        //创建一个列表对象,list 需要 设置内容，暂时还不会，2017年3月9日10:15:44
        //JList jlist =new JList("测试列表");

        //添加这个 jlist 对象 到面板上
        //pan.add(jlist);

        //创建一个下拉列表对象,JComboBox 需要 设置内容，暂时还不会，2017年3月9日10:15:44
        //JComboBox jcombobox =new JComboBox("测试下拉列表");

        //添加这个 jcombobox 对象 到面板上
        //pan.add(jcombobox);
    }


    public static void main(String[] args) {
        SuoZhangFrame frame = new SuoZhangFrame("网点控制器网络模拟器 V1.0");

        //创建一个面板对象，这个面板就是一个组件容器
        JPanel pan = new JPanel();
        //添加 容器
        frame.add(pan);

        //创建一个标签对象
        JLabel label = new JLabel("测试标签", null, SwingConstants.RIGHT);
        //添加这个 标签 lable 对象 到面板上
        pan.add(label);

        //创建一个单行文本对象
        JTextField textfield = new JTextField("测试单行文本输入框");
        //添加这个 单行文本 对象 到面板上
        pan.add(textfield);

        //创建一个 单行文本密码输入框 对象 默认密码 123456
        JPasswordField passwordfield = new JPasswordField("123456");
        //添加这个 单行文本密码输入框 对象 到面板上
        pan.add(passwordfield);

        //创建一个 显示纯文本的多行区域 对象
        JTextArea textarea = new JTextArea("测试显示纯文本的多行区域");
        //添加这个 显示纯文本的多行区域 对象 到面板上
        pan.add(textarea);

        //创建一个按钮对象
        JButton button = new JButton("测试按钮");
        //添加这个 BUTTON 对象 到面板上
        pan.add(button);

        //创建一个单选按钮对象
        JRadioButton jradiobutton = new JRadioButton("测试单选按钮");
        //添加这个 jradiobutton 对象 到面板上
        pan.add(jradiobutton);

        //创建一个多选按钮对象
        JCheckBox jchackbox = new JCheckBox("测试多选按钮");
        //添加这个 jchackbox 对象 到面板上
        pan.add(jchackbox);

        //设置窗口的可见性,防止拖动窗体才显示内容的问题
        frame.setVisible(true);
    }
}