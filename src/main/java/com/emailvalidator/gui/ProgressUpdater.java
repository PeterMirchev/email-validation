package com.emailvalidator.gui;

public class ProgressUpdater extends Thread {

    private final ProgressPanel progressPanel;

    public ProgressUpdater(ProgressPanel progressPanel) {
        this.progressPanel = progressPanel;
    }

    @Override
    public void run() {
        // Simulate progress updates
        for (int i = 0; i <= 100; i++) {
            progressPanel.setProgress(i);
            try {
                Thread.sleep(100); // Simulate processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
