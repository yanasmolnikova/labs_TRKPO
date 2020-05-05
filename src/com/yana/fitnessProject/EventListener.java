package com.yana.fitnessProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventListener implements  ActionListener{

    JTextArea trainingStatus;
    Timer timePushUps;
    Timer timeJumpRope;       // секундомеры для трех кнопок
    Timer timeSquats;
    String training;

    public EventListener(String training, JTextArea trainingStatus, Timer timePushUps, Timer timeJumpRope, Timer timeSquats){
        this.training = training;
        this.trainingStatus = trainingStatus;
        this.timeJumpRope = timeJumpRope;
        this.timePushUps = timePushUps;
        this.timeSquats = timeSquats;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        trainingStatus.setText(training);
        timeJumpRope.start();
        timeJumpRope.setRepeats(true);

        timePushUps.stop();
        timeSquats.stop();
    }
}
