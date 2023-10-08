package com.hong.util.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月26日
 */
public class FrameDemo {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel();
        emptyLabel.setText("123 456");
        emptyLabel.setPreferredSize(new Dimension(1175, 800));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(FrameDemo::createAndShowGUI);
    }
}