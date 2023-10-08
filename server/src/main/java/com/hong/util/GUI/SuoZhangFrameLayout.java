package com.hong.util.GUI;

import javax.swing.*;

/**
 * 布局管理器-布局管理器之null布局（空布局）
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月26日
 */
public class SuoZhangFrameLayout extends JFrame {

    private static int clickCounter = 0;

    private void createA(String title) {
        //设置窗口标题
        this.setTitle(title);
        //这句代码是当用户关闭窗口的时候关掉应用程序
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //设置长和宽,最常见的屏幕分辨率 1366*768，2017年3月9日09:31:24
        this.setSize(800, 600);
        //设置起点位置，屏幕右上角 为(0,0)
        this.setLocation(100, 100);

        //设置窗口的可见性
        this.setVisible(true);
        //设置窗体为空布局,2017年3月9日14:04:26
        this.setLayout(null);

        //创建一个单行文本对象
        JTextField textfield = new JTextField("请输入要发送的消息");
        //添加这个 单行文本 对象 到面板上，2017年3月9日14:16:09
        //这里 实际是两步 ，先初始化一个容器，然后在这个容器上添加一个单行文本控件
        this.getContentPane().add(textfield);
        //设置 "单行文本"的精确位置(x,y,width,height)
        textfield.setBounds(60, 30, 180, 35);

        //创建一个按钮1对象
        JButton button1 = new JButton("发送");
        //添加这个 按钮1 对象 到面板上，2017年3月9日14:16:09
        //这里 实际是两步 ，先初始化一个容器，然后在这个容器上添加一个单行文本控件
        this.getContentPane().add(button1);
        //设置 "按钮1"的精确位置(x,y,width,height)
        //注意 Y ==85 是大于 textfield的 Y 值+height 值，这样两个容器才不会重叠
        button1.setBounds(60, 85, 105, 35);

        //创建一个标签对象
        JLabel label = new JLabel("接收消息显示区域");
        //添加这个 标签 对象 到面板上，2017年3月9日14:16:09
        //这里 实际是两步 ，先初始化一个容器，然后在这个容器上添加一个标签
        this.getContentPane().add(label);
        //设置 "标签对象"的精确位置(x,y,width,height)
        //注意 Y ==85 是大于 button1 的 Y 值+height 值，这样两个控件才不会重叠
        label.setBounds(60, 130, 105, 35);

        //创建一个 显示纯文本的多行区域 对象
        JTextArea textarea = new JTextArea("接收数据显示区域，2017年3月9日15:10:07");
        //添加这个 显示纯文本的多行区域 对象 到面板上，2017年3月9日14:16:09
        //这里 实际是两步 ，先初始化一个容器，然后在这个容器上添加一个显示纯文本的多行区域
        this.getContentPane().add(textarea);
        //设置 "显示纯文本的多行区域"的精确位置(x,y,width,height)
        //注意 Y ==85 是大于 label 的 Y 值+height 值，这样两个控件才不会重叠
        textarea.setBounds(60, 180, 505, 205);

        //使用匿名的内部类,第一种事件监听方法
        button1.addActionListener(e -> {
            clickCounter++;
            // textarea.setText("我被点击了 " + clickCounter + " 次");
            textarea.setText("嘿嘿嘿，我收到了：\r\n" + textfield.getText());
        });
    }

    public static void main(String[] args) {
        SuoZhangFrameLayout layout = new SuoZhangFrameLayout();
        layout.createA("网点控制器网络模拟器 V1.0");
    }
}
