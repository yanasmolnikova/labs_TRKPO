package com.yana.fitnessProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import static com.yana.fitnessProject.Constants.*;

class Main extends JFrame {

    private int counterPushUps, counterJumpRope, counterSquats;     //время для каждой тренировки

    private int timeDelPushUps = 0;
    private int timeDelJumpRope = 0;
    private int timeDelSquats = 0;
    private float caloriesPushUps, caloriesJumpRope, caloriesSquats;

    private JTextArea trainingStatus = new JTextArea("START YOUR TRAINING");
    private JScrollPane scrollPaneInput = new JScrollPane(trainingStatus);
    private JPanel trainingPanel = new JPanel();
    private JPanel kindOfTrainingPanel = new JPanel();
    private JPanel resultsPanel = new JPanel();
    private JButton startPushUpsButton = new JButton("Start PUSH UPS");
    private JButton startJumpRopeButton = new JButton("Start JUMP ROPE");
    private JButton startSquatsButton = new JButton("Start SQUATS");
    private JButton stopTrainingButton = new JButton("STOP, SHOW MY RESULTS");
    private JButton clearAllResultsButton = new JButton("CLEAR ALL RESULTS");
    private JLabel pushUpsLabel = new JLabel("");
    private JLabel jumpRopeLabel = new JLabel("");
    private JLabel squatsLabel = new JLabel("");
    private JLabel pushUpsResultLabel = new JLabel("");
    private JLabel jumpRopeResultsLabel = new JLabel("");
    private JLabel squatsResultsLabel = new JLabel("");
    private JLabel allResultsLabel = new JLabel("");
    private DecimalFormat df = new DecimalFormat("#####.##");
    private String fileName = "data.txt";

    private Timer timePushUps;
    private Timer timeJumpRope;
    private Timer timeSquats;

    public Main(){

        initComponents();

        TimeClassPushUps timeClassPushUps = new TimeClassPushUps(timeDelPushUps);

        timePushUps = new Timer(TIMER_STEP, timeClassPushUps);

        TimeClassJumpRope timeClassJumpRope = new TimeClassJumpRope(timeDelJumpRope);

        timeJumpRope = new Timer(TIMER_STEP, timeClassJumpRope);

        TimeClassSquats timeClassSquats = new TimeClassSquats(timeDelSquats);

        timeSquats = new Timer(TIMER_STEP, timeClassSquats);
    }

    private void initComponents() {

        setBounds(30, 30, 1200, 200);

        setSize(1200, 300);

        Container container = getContentPane();

        trainingStatus.setPreferredSize(new Dimension(250, 20));        // окно вывода статуса тренировки
        trainingStatus.setSize(20, 20);

        startPushUpsButton.setPreferredSize(new Dimension(200, 40));
        startJumpRopeButton.setPreferredSize(new Dimension(200, 40));
        startSquatsButton.setPreferredSize(new Dimension(200, 40));

        startPushUpsButton.addActionListener(new startEventListenerPushUps());
        startJumpRopeButton.addActionListener(new startEventListenerJumpRope());
        startSquatsButton.addActionListener(new startEventListenerSquats());

        stopTrainingButton.setPreferredSize(new Dimension(200, 40));
        stopTrainingButton.addActionListener(new stopTrainingButtonEventListener());

        clearAllResultsButton.setPreferredSize(new Dimension(200, 40));

        clearAllResultsButton.addActionListener(new clearAllResultsEventListener());

        kindOfTrainingPanel.setPreferredSize(new Dimension(400, 200));
        kindOfTrainingPanel.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("CHOOSE Training")));
        kindOfTrainingPanel.add(startPushUpsButton);
        kindOfTrainingPanel.add(startJumpRopeButton);
        kindOfTrainingPanel.add(startSquatsButton);
        kindOfTrainingPanel.add(stopTrainingButton);
        kindOfTrainingPanel.setBackground(Color.pink);

        trainingPanel.add(BorderLayout.EAST, scrollPaneInput);
        trainingPanel.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("YOUR training")));
        trainingPanel.setPreferredSize(new Dimension(400, 200));
        trainingPanel.add(pushUpsLabel);
        trainingPanel.add(jumpRopeLabel);
        trainingPanel.add(squatsLabel);
        trainingPanel.setBackground(Color.orange);

        resultsPanel.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("RESULTS")));
        resultsPanel.setPreferredSize(new Dimension(400, 200));
        resultsPanel.add(pushUpsResultLabel);
        resultsPanel.add(jumpRopeResultsLabel);
        resultsPanel.add(squatsResultsLabel);
        resultsPanel.add(allResultsLabel);
        resultsPanel.add(clearAllResultsButton);
        resultsPanel.setBackground(Color.lightGray);

        container.add(BorderLayout.WEST, kindOfTrainingPanel);
        container.add(BorderLayout.CENTER, trainingPanel);
        container.add(BorderLayout.EAST, resultsPanel);
    }

    class startEventListenerPushUps implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEventPushUps) {
            trainingStatus.setText(PUSH_UPS);

            timePushUps.start();
            timePushUps.setRepeats(true);

            timeJumpRope.stop();
            timeSquats.stop();
        }
    }

    class startEventListenerJumpRope implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEventJumpRope) {

            trainingStatus.setText(JUMP_ROPE);

            timeJumpRope.start();
            timeJumpRope.setRepeats(true);

            timePushUps.stop();
            timeSquats.stop();
        }
    }

    class startEventListenerSquats implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEventSquats) {
            trainingStatus.setText(SQUATS);

            timeSquats.start();
            timeSquats.setRepeats(true);

            timeJumpRope.stop();
            timePushUps.stop();
        }
    }

    public class TimeClassPushUps implements ActionListener {

        public TimeClassPushUps(int count) {
            counterPushUps = count;
        }

        @Override
        public void actionPerformed(ActionEvent pushUpStart) {

            counterPushUps++;
            caloriesPushUps++;
            caloriesPushUps = counterPushUps * CALORIES_PER_SECOND_PUSH_UPS;

            if (counterPushUps > 0) {
                pushUpsLabel.setText(PUSH_UPS + " - " + TIME + LocalTime.ofSecondOfDay(counterPushUps) + CALORIES + df.format(caloriesPushUps));
            }
        }
    }

    public class TimeClassJumpRope implements ActionListener {

        public TimeClassJumpRope(int count) {
            counterJumpRope = count;
        }

        @Override
        public void actionPerformed(ActionEvent jumpRopeStart) {

            counterJumpRope++;
            caloriesJumpRope++;
            caloriesJumpRope = counterJumpRope * CALORIES_PER_SECOND_JUMP_ROPE;

            if (counterJumpRope > 0) {
                jumpRopeLabel.setText(JUMP_ROPE + " - " + TIME + LocalTime.ofSecondOfDay(counterJumpRope) + CALORIES + df.format(caloriesJumpRope));
            }
        }
    }

    public class TimeClassSquats implements ActionListener {

        public TimeClassSquats(int count) {
            counterSquats = count;
        }

        @Override
        public void actionPerformed(ActionEvent squatsStart) {

            counterSquats++;
            caloriesSquats++;
            caloriesSquats = counterSquats * CALORIES_PER_SECOND_SQUATS;

            if (counterSquats >= -1) {

                squatsLabel.setText(SQUATS + " - " + TIME + LocalTime.ofSecondOfDay(counterSquats) + CALORIES + df.format(caloriesSquats));
            }
        }
    }

    class stopTrainingButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            float caloriesResult;
            long timeResult;

            timePushUps.stop();
            timeJumpRope.stop();
            timeSquats.stop();

            int counterPushUpsPer = 0;
            int counterJumpRopePer = 0;
            int counterSquatsPer = 0;
            float caloriesPushUpsPer = 0;
            float caloriesJumpRopePer = 0;
            float caloriesSquatsPer = 0;

            if (!(new File(fileName)).exists()) {

                File file = new File("data.txt");

                try {

                    file.createNewFile();

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }

                FileOutputStream fout = null;

                try {

                    fout = new FileOutputStream("data.txt");

                } catch (FileNotFoundException fileNotFoundException) {

                    fileNotFoundException.printStackTrace();
                }

                ObjectOutputStream objectOutputStream = null;

                try {
                    objectOutputStream = new ObjectOutputStream(fout);
                    objectOutputStream.writeInt(counterPushUpsPer);
                    objectOutputStream.writeInt(counterJumpRopePer);
                    objectOutputStream.writeInt(counterSquatsPer);
                    objectOutputStream.writeFloat(caloriesPushUpsPer);
                    objectOutputStream.writeFloat(caloriesJumpRopePer);
                    objectOutputStream.writeFloat(caloriesSquatsPer);

                    caloriesResult = caloriesPushUps + caloriesSquats + caloriesJumpRope;
                    timeResult = counterPushUps + counterSquats + counterJumpRope;

                    pushUpsResultLabel.setText(PUSH_UPS + " - " + LocalTime.ofSecondOfDay(counterPushUps) + CALORIES + df.format(caloriesPushUps) + ";");

                    jumpRopeResultsLabel.setText(JUMP_ROPE + " - " + LocalTime.ofSecondOfDay(counterJumpRope) + CALORIES + df.format(caloriesJumpRope) + ";");

                    squatsResultsLabel.setText(SQUATS + " - " + LocalTime.ofSecondOfDay(counterSquats) + CALORIES + df.format(caloriesSquats) + ";");

                    allResultsLabel.setText(ALL_RESULTS + LocalTime.ofSecondOfDay(timeResult) + CALORIES
                            + df.format(caloriesResult));      // результаты тренировки

                    counterPushUpsPer = counterPushUps;
                    counterJumpRopePer = counterJumpRope;
                    counterSquatsPer = counterSquats;
                    caloriesPushUpsPer = caloriesPushUps;
                    caloriesJumpRopePer = caloriesJumpRope;
                    caloriesSquatsPer = caloriesSquats;

                    objectOutputStream.close();

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }

            } else {

                FileInputStream fileInputStream = null;

                try {

                    fileInputStream = new FileInputStream("data.txt");

                } catch (FileNotFoundException fileNotFoundException) {

                    fileNotFoundException.printStackTrace();
                }

                ObjectInputStream objectInputStream = null;

                try {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    counterPushUpsPer = objectInputStream.readInt();
                    counterJumpRopePer = objectInputStream.readInt();
                    counterSquatsPer = objectInputStream.readInt();
                    caloriesPushUpsPer = objectInputStream.readFloat();
                    caloriesJumpRopePer = objectInputStream.readFloat();
                    caloriesSquatsPer = objectInputStream.readFloat();

                    counterPushUpsPer = counterPushUpsPer + counterPushUps;
                    counterJumpRopePer = counterJumpRopePer + counterJumpRope;
                    counterSquatsPer = counterSquatsPer + counterSquats;
                    caloriesPushUpsPer = caloriesPushUpsPer + caloriesPushUps;
                    caloriesJumpRopePer = caloriesJumpRopePer + caloriesJumpRope;
                    caloriesSquatsPer = caloriesSquatsPer + caloriesSquats;

                    timeResult = counterPushUpsPer + counterJumpRopePer + counterSquatsPer;
                    caloriesResult = caloriesPushUpsPer + caloriesJumpRopePer + caloriesSquatsPer;

                    pushUpsResultLabel.setText(PUSH_UPS + " - " + LocalTime.ofSecondOfDay(counterPushUpsPer) + CALORIES + df.format(caloriesPushUpsPer) + ";");
                    jumpRopeResultsLabel.setText(JUMP_ROPE + " - " + LocalTime.ofSecondOfDay(counterJumpRopePer) + CALORIES + df.format(caloriesJumpRopePer) + ";");
                    squatsResultsLabel.setText(SQUATS + " - " + LocalTime.ofSecondOfDay(counterSquatsPer) + CALORIES + df.format(caloriesSquatsPer) + ";");
                    allResultsLabel.setText(ALL_RESULTS + " - " + LocalTime.ofSecondOfDay(timeResult) + CALORIES
                            + df.format(caloriesResult));

                    objectInputStream.close();

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }
            }

            FileOutputStream fileOutputStream = null;
            try {

                fileOutputStream = new FileOutputStream("data.txt");

            } catch (FileNotFoundException fileNotFoundException) {

                fileNotFoundException.printStackTrace();
            }

            ObjectOutputStream objectOutputStream = null;

            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeInt(counterPushUpsPer);
                objectOutputStream.writeInt(counterJumpRopePer);
                objectOutputStream.writeInt(counterSquatsPer);
                objectOutputStream.writeFloat(caloriesPushUpsPer);
                objectOutputStream.writeFloat(caloriesJumpRopePer);
                objectOutputStream.writeFloat(caloriesSquatsPer);

                objectOutputStream.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            trainingStatus.setText("Training is over, quit or start a new one");

            caloriesPushUps = 0;
            caloriesJumpRope = 0;
            caloriesSquats = 0;
            counterPushUps = 0;
            counterJumpRope = 0;
            counterSquats = 0;
        }
    }

    class clearAllResultsEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent tsn) {

            if ((new File(fileName)).exists()) {
                File file = new File("data.txt");
                file.delete();
            }

            pushUpsLabel.setText("");
            jumpRopeLabel.setText("");
            squatsLabel.setText("");

            pushUpsResultLabel.setText("All results are cleared");
            jumpRopeResultsLabel.setText("");
            squatsResultsLabel.setText("");
            allResultsLabel.setText("");
        }
    }

    public static void main(String[] args){

        Main graphic = new Main();
        graphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphic.setVisible(true);

    }
}