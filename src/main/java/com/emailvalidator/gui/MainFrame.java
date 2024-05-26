package com.emailvalidator.gui;

import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.*;

public class MainFrame extends JPanel {

    private JButton selectButton;
    private JButton validateButton;
    private SpringLayout layout;


    public MainFrame() {
        MainFrame mainFrame = new MainFrame();
        setSize(400, 200);

        ProgressPanel progressPanel = new ProgressPanel();
        add(progressPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
