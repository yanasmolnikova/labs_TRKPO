package com.yana.fitnessProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalTime;

public class TimeClass implements ActionListener {
    int counter = 0;
    float caloriesInHour;
    float calories;
    float caloriesInSecond;
    JLabel label;
    DecimalFormat df;

    public TimeClass(int count, JLabel label, DecimalFormat df) {
        this.counter = count;
        this.label = label;
        this.df = df;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        caloriesInHour =  30;
        caloriesInSecond = caloriesInHour / 3600;       // в одну секунду
        counter++;       // секундомер текущий
        calories++;     // текущий подсчет
        calories = counter * caloriesInSecond;        // формулы для подсчета
        if (counter > 0) {
            // если время пошло, появляется надпись
            label.setText("PUSH UPS - time: " + LocalTime.ofSecondOfDay(counter) + " , calories " + df.format(calories));
        }
    }
}

