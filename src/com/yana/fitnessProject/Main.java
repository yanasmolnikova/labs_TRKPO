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
    //private float caloriesInHour;
    private float caloriesPushUps, caloriesJumpRope, caloriesSquats;
    //private float caloriesInSecond;

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
        public void actionPerformed(ActionEvent e) {        // обработка события нажатия на button start отжимание
            trainingStatus.setText("PUSH UPS");

            timePushUps.start();
            timePushUps.setRepeats(true);      // старт секундомера с повторением

            timeJumpRope.stop();       // остановка других секундомеров, если они включены
            timeSquats.stop();
        }
    }

    class startEventListenerJumpRope implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            trainingStatus.setText("JUMP ROPE");

            timeJumpRope.start();
            timeJumpRope.setRepeats(true);

            timePushUps.stop();
            timeSquats.stop();
        }
    }

    class startEventListenerSquats implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            trainingStatus.setText("SQUATS");

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
        public void actionPerformed(ActionEvent ts) {

            counterPushUps ++;       // секундомер текущий
            caloriesPushUps ++;     // текущий подсчет
            caloriesPushUps = counterPushUps * CALORIES_PER_SECOND_PUSH_UPS;        // формулы для подсчета
            if (counterPushUps > 0) {
                pushUpsLabel.setText("PUSH UPS - time: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUps));
            }
        }
    }

    public class TimeClassJumpRope implements ActionListener {

        public TimeClassJumpRope(int count) {
            counterJumpRope = count;
        }

        @Override
        public void actionPerformed(ActionEvent ts1) {

            counterJumpRope++;
            caloriesJumpRope++;
            caloriesJumpRope = counterJumpRope * CALORIES_PER_SECOND_JUMP_ROPE;
            if (counterJumpRope > 0) {
                jumpRopeLabel.setText("JUMP ROPE - time: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope));
            }
        }

    }

    public class TimeClassSquats implements ActionListener {

        public TimeClassSquats(int count) {
            counterSquats = count;
        }

        @Override
        public void actionPerformed(ActionEvent ts) {

            counterSquats++;
            caloriesSquats++;
            caloriesSquats = counterSquats * CALORIES_PER_SECOND_SQUATS;
            if (counterSquats >= -1) {

                squatsLabel.setText("SQUATS - time: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats));
            }
        }
    }

    class stopTrainingButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            float caloriesJumpRopeymm;
            long counterJumpRopeymm;

            timePushUps.stop();
            timeJumpRope.stop();
            timeSquats.stop();

            int counterPushUpsper = 0;
            int counterJumpRopeper = 0;
            int counterSquatsper = 0;
            float caloriesPushUosper = 0;
            float caloriesJumpRopeper = 0;
            float caloriesSquatsper = 0;

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

                //вынести логику в отдельный класс

                try {
                    objectOutputStream = new ObjectOutputStream(fout);
                    objectOutputStream.writeInt(counterPushUpsper);
                    objectOutputStream.writeInt(counterJumpRopeper);
                    objectOutputStream.writeInt(counterSquatsper);
                    objectOutputStream.writeFloat(caloriesPushUosper);
                    objectOutputStream.writeFloat(caloriesJumpRopeper);
                    objectOutputStream.writeFloat(caloriesSquatsper);

                    caloriesJumpRopeymm = caloriesPushUps + caloriesSquats + caloriesJumpRope;
                    counterJumpRopeymm = counterPushUps + counterSquats + counterJumpRope;

                    pushUpsResultLabel.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUps) + ";");

                    jumpRopeResultsLabel.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope) + ";");

                    squatsResultsLabel.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats) + ";");

                    allResultsLabel.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " , calories "
                            + df.format(caloriesJumpRopeymm));      // выведем результаты первой тренировки

                    counterPushUpsper = counterPushUps;
                    counterJumpRopeper = counterJumpRope;
                    counterSquatsper = counterSquats;
                    caloriesPushUosper = caloriesPushUps;
                    caloriesJumpRopeper = caloriesJumpRope;
                    caloriesSquatsper = caloriesSquats;

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
                    counterPushUpsper = objectInputStream.readInt();
                    counterJumpRopeper = objectInputStream.readInt();
                    counterSquatsper = objectInputStream.readInt();
                    caloriesPushUosper = objectInputStream.readFloat();
                    caloriesJumpRopeper = objectInputStream.readFloat();
                    caloriesSquatsper = objectInputStream.readFloat();

                    counterPushUpsper = counterPushUpsper + counterPushUps;
                    counterJumpRopeper = counterJumpRopeper + counterJumpRope;
                    counterSquatsper = counterSquatsper + counterSquats;
                    caloriesPushUosper = caloriesPushUosper + caloriesPushUps;
                    caloriesJumpRopeper = caloriesJumpRopeper + caloriesJumpRope;
                    caloriesSquatsper = caloriesSquatsper + caloriesSquats;

                    counterJumpRopeymm = counterPushUpsper + counterJumpRopeper + counterSquatsper;
                    caloriesJumpRopeymm = caloriesPushUosper + caloriesJumpRopeper + caloriesSquatsper;

                    pushUpsResultLabel.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUpsper) + " , calories " + df.format(caloriesPushUosper) + ";");
                    jumpRopeResultsLabel.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRopeper) + " , calories " + df.format(caloriesJumpRopeper) + ";");
                    squatsResultsLabel.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterSquatsper) + " , calories " + df.format(caloriesSquatsper) + ";");
                    allResultsLabel.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " calories "
                            + df.format(caloriesJumpRopeymm));      //вывод результатов

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
                objectOutputStream.writeInt(counterPushUpsper);
                objectOutputStream.writeInt(counterJumpRopeper);
                objectOutputStream.writeInt(counterSquatsper);
                objectOutputStream.writeFloat(caloriesPushUosper);
                objectOutputStream.writeFloat(caloriesJumpRopeper);
                objectOutputStream.writeFloat(caloriesSquatsper);

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
            counterSquats = 0;      //обнуление всех таймеров в сессии
        }
    }

    class clearAllResultsEventListener implements ActionListener {
        @Override
        // обработка события нажатия на кнопку обнуление
        public void actionPerformed(ActionEvent tsn) {

            if ((new File(fileName)).exists()) {
                File file = new File("data.txt");
                file.delete();      //если есть удалить

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

    public static void main(String[] args) throws IOException {

        Main graphic = new Main();
        graphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphic.setVisible(true);

    }
}