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
import javax.swing.Timer;// у нас он будет секундомером
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


class Main extends JFrame {

    private int counterPushUps, counterJumpRope, counterSquats;     //время для каждой тренировки

    private int timeDelPushUps = 0;
    private int timeDelJumpRope = 0;
    private int timeDelSquats = 0;
    private float caloriesInHour;
    private float caloriesPushUps, caloriesJumpRope, caloriesSquats;
    private float caloriesInSecond;
    private int timerStep = 1000;   //шаг равен 1 секунде, период секундомера 1000мс = 1 сек

    private JTextArea trainingStatus = new JTextArea("START YOUR TRAINING");
    private JScrollPane scrollPaneInput = new JScrollPane(trainingStatus);
    private JPanel panelTraining = new JPanel();
    private JPanel panelGoTraining = new JPanel();
    private JPanel panelResults = new JPanel();
    private JButton startPushUps = new JButton("Start PUSH UPS");
    private JButton startJumpRope = new JButton("Start JUMP ROPE");
    private JButton startSquats = new JButton("Start SQUATS");
    private JButton stop = new JButton("STOP, SHOW MY RESULTS");
    private JButton clearAllResults = new JButton("CLEAR ALL RESULTS");
    private JLabel labelPushUps = new JLabel("");
    private JLabel labelJumpRope = new JLabel("");
    private JLabel labelSquats = new JLabel("");
    private JLabel labelPushUpsResult = new JLabel("");
    private JLabel labelJumpRopeResults = new JLabel("");
    private JLabel labelSquatsResults = new JLabel("");
    private JLabel labelAllResults = new JLabel("");
    private DecimalFormat df = new DecimalFormat("#####.##");
    private String fileName = "data.txt";

    private Timer timePushUps;
    private Timer timeJumpRope;
    private Timer timeSquats;

    public Main() throws IOException {

        initComponents();

        TimeClassPushUps timeClassPushUps = new TimeClassPushUps(timeDelPushUps);

        timePushUps = new Timer(timerStep, timeClassPushUps);

        TimeClassJumpRope timeClassJumpRope = new TimeClassJumpRope(timeDelJumpRope);

        timeJumpRope = new Timer(timerStep, timeClassJumpRope);

        TimeClassSquats timeClassSquats = new TimeClassSquats(timeDelSquats);

        timeSquats = new Timer(timerStep, timeClassSquats);
    }

    private void initComponents() {

        setBounds(30, 30, 1200, 200);

        setSize(1200, 300);

        Container container = getContentPane();

        trainingStatus.setPreferredSize(new Dimension(250, 20));        // окно вывода статуса тренировки
        trainingStatus.setSize(20, 20);

        startPushUps.setPreferredSize(new Dimension(200, 40));
        startJumpRope.setPreferredSize(new Dimension(200, 40));
        startSquats.setPreferredSize(new Dimension(200, 40));

        startPushUps.addActionListener(new startEventListenerPushUps());
        startJumpRope.addActionListener(new startEventListenerJumpRope());
        startSquats.addActionListener(new startEventListenerSquats());

        stop.setPreferredSize(new Dimension(200, 40));
        stop.addActionListener(new stopEventListener());

        clearAllResults.setPreferredSize(new Dimension(200, 40));

        clearAllResults.addActionListener(new clearAllResultsEventListener());

        panelGoTraining.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("CHOOSE Training")));
        panelGoTraining.setPreferredSize(new Dimension(400, 200));
        panelGoTraining.add(startPushUps);
        panelGoTraining.add(startJumpRope);
        panelGoTraining.add(startSquats);
        panelGoTraining.add(stop);
        panelGoTraining.setBackground(Color.pink);

        panelTraining.add(BorderLayout.EAST, scrollPaneInput);
        panelTraining.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("YOUR training")));
        panelTraining.setPreferredSize(new Dimension(400, 200));
        panelTraining.add(labelPushUps);
        panelTraining.add(labelJumpRope);
        panelTraining.add(labelSquats);
        panelTraining.setBackground(Color.orange);


        panelResults.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder("RESULTS")));
        panelResults.setPreferredSize(new Dimension(400, 200));
        panelResults.add(labelPushUpsResult);
        panelResults.add(labelJumpRopeResults);
        panelResults.add(labelSquatsResults);
        panelResults.add(labelAllResults);
        panelResults.add(clearAllResults);
        panelResults.setBackground(Color.lightGray);

        container.add(BorderLayout.WEST, panelGoTraining);
        container.add(BorderLayout.CENTER, panelTraining);
        container.add(BorderLayout.EAST, panelResults);
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
    //EventListener eventListener = new EventListener("PUSH UPS", trainingStatus, timePushUps, timeJumpRope, timeSquats);
    //траблы

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

        // конструктор
        public TimeClassPushUps(int count) {
            counterPushUps = count;
        }

        // время пошло , отжимание
        @Override
        public void actionPerformed(ActionEvent ts) {

            caloriesInHour = 30;        // за один час
            caloriesInSecond = caloriesInHour / 3600;       // в одну секунду
            counterPushUps ++;       // секундомер текущий
            caloriesPushUps ++;     // текущий подсчет
            caloriesPushUps = counterPushUps * caloriesInSecond;        // формулы для подсчета
            if (counterPushUps > 0) {
                // если время пошло, появляется надпись
                labelPushUps.setText("PUSH UPS - time: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUps));
            }
        }
    }

//    TimeClass timeClassPushUps = new TimeClass(counterPushUps, "PUSH UPS", labelPushUps, df);
//
//    public TimeClass getTimeClassPushUps() {
//        return timeClassPushUps;
//    }
//
//    public void setTimeClassPushUps(TimeClass timeClassPushUps) {
//        this.timeClassPushUps = timeClassPushUps;
//    }

    //траблы

    public class TimeClassJumpRope implements ActionListener {

        public TimeClassJumpRope(int count) {
            counterJumpRope = count;
        }

        @Override
        public void actionPerformed(ActionEvent ts1) {

            caloriesInHour = 100;
            caloriesInSecond = caloriesInHour / 3600;
            counterJumpRope++;
            caloriesJumpRope++;
            caloriesJumpRope = counterJumpRope * caloriesInSecond;
            if (counterJumpRope > 0) {
                labelJumpRope.setText("JUMP ROPE - time: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope));
            }
        }

    }

    public class TimeClassSquats implements ActionListener {

        public TimeClassSquats(int count) {
            counterSquats = count;
        }

        @Override
        public void actionPerformed(ActionEvent ts) {

            caloriesInHour = 200;
            caloriesInSecond = caloriesInHour / 3600;
            counterSquats++;
            caloriesSquats++;
            caloriesSquats = counterSquats * caloriesInSecond;
            if (counterSquats >= -1) {

                labelSquats.setText("SQUATS - time: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats));
            }
        }
    }

    class stopEventListener implements ActionListener {

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
                //вынести логику в отдельный класс, траблы

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

                    labelPushUpsResult.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUps) + ";");

                    labelJumpRopeResults.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope) + ";");

                    labelSquatsResults.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats) + ";");

                    labelAllResults.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " , calories "
                            + df.format(caloriesJumpRopeymm));      // выведем результаты первой тренировки
                    counterPushUpsper = counterPushUps;
                    counterJumpRopeper = counterJumpRope;
                    counterSquatsper = counterSquats;
                    caloriesPushUosper = caloriesPushUps;
                    caloriesJumpRopeper = caloriesJumpRope;
                    caloriesSquatsper = caloriesSquats;     //приравняем наши переменнные для записи в файл после if else

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

                    labelPushUpsResult.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUpsper) + " , calories " + df.format(caloriesPushUosper) + ";");
                    labelJumpRopeResults.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRopeper) + " , calories " + df.format(caloriesJumpRopeper) + ";");
                    labelSquatsResults.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterSquatsper) + " , calories " + df.format(caloriesSquatsper) + ";");
                    labelAllResults.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " calories "
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

            labelPushUps.setText("");
            labelJumpRope.setText("");
            labelSquats.setText("");

            labelPushUpsResult.setText("All results are cleared");
            labelJumpRopeResults.setText("");
            labelSquatsResults.setText("");
            labelAllResults.setText("");
        }
    }

    public static void main(String[] args) throws IOException {

        Main graphic = new Main();
        graphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphic.setVisible(true);

    }
}