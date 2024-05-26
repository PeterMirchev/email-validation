package com.emailvalidator.gui;

import javax.swing.*;
import java.awt.*;

public class ProgressPanel extends JPanel {

    private final JProgressBar progressBar;

    public ProgressPanel() {
        setLayout(new BorderLayout());

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        JLabel progressLabel = new JLabel("Progress: ");
        add(progressLabel, BorderLayout.WEST);
        add(progressBar, BorderLayout.CENTER);
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

}
