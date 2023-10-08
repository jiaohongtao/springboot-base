package com.hong.util.GUI;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JFrame应用
 * study href: https://www.cnblogs.com/suozhang/p/6491487.html
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月26日
 */
public class JFrameDemo {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("我的第一个java窗口代码");
        //设置长和宽
        frame.setSize(800, 400);
        //设置起点位置
        frame.setLocation(100, 100);
        //设置窗口的可见性
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame {
    MyFrame(String title) {
        // 设置窗口标题
        this.setTitle(title);
        // 当用户关闭窗口的时候关掉应用程序
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //创建一个面板对象，这个面板就是一个组件容器
        JPanel pan = new JPanel();
        //创建一个按钮对象
        JButton button = new JButton("测试按钮");

        //添加 容器
        this.add(pan);
        //添加这个 BUTTON 对象 到面板上
        pan.add(button);

        AtomicInteger count = new AtomicInteger();
        //使用匿名的内部类,第一种事件监听方法
        button.addActionListener(e -> {
            JOptionPane.showConfirmDialog(null, "测试按钮被点击了", "提示信息", JOptionPane.DEFAULT_OPTION);

            count.getAndIncrement();
            System.out.println(count.get());
        });
    }
}
