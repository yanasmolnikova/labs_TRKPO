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

    private int counterPushUps, counterJumpRope, counterSquats;       // время на разные виды тренировок

    // счетчик срабатываний секундомера
    //  private int timerCount;
    // период срабатывания секундомером  для всех упражнений

    private int timerDelPushUps = 0;
    private int timerDelJumpRope = 0;
    private int timerDelSquats = 0;
    private float caloriesInHour;        //калория в 1 час
    private float caloriesPushUos, caloriesJumpRope, caloriesSquats;       //подсчет калорий
    private float caloriesInSecond;        // в одну секунду
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
    private JLabel labelPushUps = new JLabel("");        //отжимания
    private JLabel labelJumpRope = new JLabel("");     //скакалка
    private JLabel labelSquats = new JLabel("");       //приседания
    private JLabel labelPushUpsResult = new JLabel("");
    private JLabel labelJumpRopeResults = new JLabel("");
    private JLabel labelSquatsResults = new JLabel("");
    private JLabel labelAllResults = new JLabel("");
    private DecimalFormat df = new DecimalFormat("#####.##");       // округление калорий до
    private String fileName = "data.txt";       // файл для персистенции

    private Timer timerPushUps;
    private Timer timerJumpRope;       // секундомеры для трех кнопок
    private Timer timerSquats;

    // конструктор
    public Main() throws IOException {

        initComponents();

        TimeClass timerClassPushUps = new TimeClass(timerDelPushUps);

        timerPushUps = new Timer(timerStep, timerClassPushUps);

        TimeClass1 timerClassJumpRope = new TimeClass1(timerDelJumpRope);

        timerJumpRope = new Timer(timerStep, timerClassJumpRope);

        TimeClass2 timerClassSquats = new TimeClass2(timerDelSquats);

        timerSquats = new Timer(timerStep, timerClassSquats);
    }

    // метод инициализации компонентов формы
    private void initComponents() throws IOException {

        setBounds(15, 30, 1200, 200);        //положение на экране

        setSize(1200, 300);      // размер формы

        Container container = getContentPane();     // контейнер для размещения компонентов формы

        trainingStatus.setPreferredSize(new Dimension(250, 20));        // окно вывода статуса тренировки
        trainingStatus.setSize(20, 20);

        startPushUps.setPreferredSize(new Dimension(200, 40));
        startJumpRope.setPreferredSize(new Dimension(200, 40));
        startSquats.setPreferredSize(new Dimension(200, 40));

        startPushUps.addActionListener(new startEventListener());
        startJumpRope.addActionListener(new startEventListener1());
        startSquats.addActionListener(new startEventListener2());

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

    // класс имплементации события нажатия старт
    class startEventListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {        // обработка события нажатия на button start отжимание
            trainingStatus.setText("PUSH UPS");

            timerPushUps.start();
            timerPushUps.setRepeats(true);      // старт секундомера с повторением

            timerJumpRope.stop();       // остановка других секундомеров, если они включены
            timerSquats.stop();
        }
    }

    class startEventListener1 implements ActionListener {

        @Override
        // обработка события нажатия на button start скакалка
        public void actionPerformed(ActionEvent e) {

            trainingStatus.setText("JUMP ROPE");

            timerJumpRope.start();
            timerJumpRope.setRepeats(true);

            timerPushUps.stop();
            timerSquats.stop();
        }
    }

    class startEventListener2 implements ActionListener {

        @Override
        // обработка события нажатия на button start приседание
        public void actionPerformed(ActionEvent e) {
            trainingStatus.setText("SQUATS");

            timerSquats.start();
            timerSquats.setRepeats(true);

            timerJumpRope.stop();
            timerPushUps.stop();
        }
    }

    //  класс пререзагружаемого счетчика секундомера
    public class TimeClass implements ActionListener {

        // конструктор
        public TimeClass(int count) {
            counterPushUps = count;
        }

        // время пошло , отжимание
        @Override
        public void actionPerformed(ActionEvent ts) {

            caloriesInHour = 30;        // за один час
            caloriesInSecond = caloriesInHour / 3600;       // в одну секунду
            counterPushUps ++;       // секундомер текущий
            caloriesPushUos ++;     // текущий подсчет
            caloriesPushUos = counterPushUps * caloriesInSecond;        // формулы для подсчета
            if (counterPushUps > 0) {
                // если время пошло, появляется надпись
                labelPushUps.setText("PUSH UPS - time: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUos));
            }
        }
    }

    public class TimeClass1 implements ActionListener {
        // время пошло , скакалка
        public TimeClass1(int count) {
            counterJumpRope = count;
        }

        @Override
        public void actionPerformed(ActionEvent ts1) {

            caloriesInHour = 100;
            caloriesInSecond = caloriesInHour / 3600;
            counterJumpRope ++;
            caloriesJumpRope ++;
            caloriesJumpRope = counterJumpRope * caloriesInSecond;
            if (counterJumpRope > 0) {
                labelJumpRope.setText("JUMP ROPE - time: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope));
            }
        }

    }

    // время пошло , приседание
    public class TimeClass2 implements ActionListener {

        // конструктор
        public TimeClass2(int count) {
            counterSquats = count;
        }

        // время пошло приседание
        @Override
        public void actionPerformed(ActionEvent ts) {

            caloriesInHour = 200;
            caloriesInSecond = caloriesInHour / 3600;
            counterSquats ++;
            caloriesSquats ++;
            caloriesSquats = counterSquats * caloriesInSecond;
            if (counterSquats >= -1) {

                labelSquats.setText("SQUATS - time: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats));
            }
        }
    }

    // клас имплементации события нажатия стоп
    class stopEventListener implements ActionListener {

        @Override
        // обработка события нажатия на button start
        public void actionPerformed(ActionEvent e) {

            float caloriesJumpRopeymm;      // общий затрат трех тренировок калорий
            long counterJumpRopeymm;        // времени

            timerPushUps.stop();
            timerJumpRope.stop();
            timerSquats.stop();

            int counterPushUpsper = 0;
            int counterJumpRopeper = 0;
            int counterpper = 0;
            float caloriesPushUosper = 0;
            float caloriesJumpRopeper = 0;
            float caloriesSquatsper = 0;

            if (!(new File(fileName)).exists()) {

                File file = new File("data.txt");

                try {

                    file.createNewFile();       //если нет, создать файл

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }

                FileOutputStream fout = null;

                try {

                    fout = new FileOutputStream("data.txt");

                } catch (FileNotFoundException fileNotFoundException) {

                    fileNotFoundException.printStackTrace();
                }

                ObjectOutputStream oot = null;

                try {
                    oot = new ObjectOutputStream(fout);
                    oot.writeInt(counterPushUpsper);        //запись всех переменных
                    oot.writeInt(counterJumpRopeper);
                    oot.writeInt(counterpper);
                    oot.writeFloat(caloriesPushUosper);
                    oot.writeFloat(caloriesJumpRopeper);
                    oot.writeFloat(caloriesSquatsper);

                    caloriesJumpRopeymm = caloriesPushUos + caloriesSquats + caloriesJumpRope;    // реализуем сумму
                    counterJumpRopeymm = counterPushUps + counterSquats + counterJumpRope;

                    labelPushUpsResult.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUps) + " , calories " + df.format(caloriesPushUos) + ";");

                    labelJumpRopeResults.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRope) + " , calories " + df.format(caloriesJumpRope) + ";");

                    labelSquatsResults.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterSquats) + " , calories " + df.format(caloriesSquats) + ";");

                    labelAllResults.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " , calories "
                            + df.format(caloriesJumpRopeymm));      // выведем результаты первой тренировки
                    counterPushUpsper = counterPushUps;
                    counterJumpRopeper = counterJumpRope;
                    counterpper = counterSquats;
                    caloriesPushUosper = caloriesPushUos;
                    caloriesJumpRopeper = caloriesJumpRope;
                    caloriesSquatsper = caloriesSquats;     //приравняем наши переменнные для записи в файл после if else

                    oot.close();

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }

            } else {

                FileInputStream fin = null;

                try {

                    fin = new FileInputStream("data.txt");

                } catch (FileNotFoundException fileNotFoundException) {

                    fileNotFoundException.printStackTrace();
                }

                ObjectInputStream oit = null;

                try {
                    oit = new ObjectInputStream(fin);
                    counterPushUpsper = oit.readInt();        // иначе вытащем переменные для персистенции
                    counterJumpRopeper = oit.readInt();
                    counterpper = oit.readInt();
                    caloriesPushUosper = oit.readFloat();
                    caloriesJumpRopeper = oit.readFloat();
                    caloriesSquatsper = oit.readFloat();

                    counterPushUpsper = counterPushUpsper + counterPushUps;       // сложем их с дааными за одну серию
                    counterJumpRopeper = counterJumpRopeper + counterJumpRope;
                    counterpper = counterpper + counterSquats;
                    caloriesPushUosper = caloriesPushUosper + caloriesPushUos;
                    caloriesJumpRopeper = caloriesJumpRopeper + caloriesJumpRope;
                    caloriesSquatsper = caloriesSquatsper + caloriesSquats;

                    counterJumpRopeymm = counterPushUpsper + counterJumpRopeper + counterpper;
                    caloriesJumpRopeymm = caloriesPushUosper + caloriesJumpRopeper + caloriesSquatsper;      //не забываем про сумму

                    labelPushUpsResult.setText("PUSH UPS: " + LocalTime.ofSecondOfDay(counterPushUpsper) + " , calories " + df.format(caloriesPushUosper) + ";");
                    labelJumpRopeResults.setText("JUMP ROPE: " + LocalTime.ofSecondOfDay(counterJumpRopeper) + " , calories " + df.format(caloriesJumpRopeper) + ";");
                    labelSquatsResults.setText("SQUATS: " + LocalTime.ofSecondOfDay(counterpper) + " , calories " + df.format(caloriesSquatsper) + ";");
                    labelAllResults.setText("All results: " + LocalTime.ofSecondOfDay(counterJumpRopeymm) + " calories "
                            + df.format(caloriesJumpRopeymm));      //вывод результатов

                    oit.close();

                } catch (IOException ioException) {

                    ioException.printStackTrace();
                }
            }
            //выйдем из if  else и совершим запись, так мы запишем нужные данные вне зависимости от того
            // какое из двух действий было совершенно программой

            FileOutputStream fout = null;
            try {

                fout = new FileOutputStream("data.txt");

            } catch (FileNotFoundException fileNotFoundException) {

                fileNotFoundException.printStackTrace();
            }

            ObjectOutputStream oot = null;

            try {
                oot = new ObjectOutputStream(fout);
                oot.writeInt(counterPushUpsper);
                oot.writeInt(counterJumpRopeper);
                oot.writeInt(counterpper);
                oot.writeFloat(caloriesPushUosper);
                oot.writeFloat(caloriesJumpRopeper);
                oot.writeFloat(caloriesSquatsper);

                oot.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            trainingStatus.setText("Training is over, quit or start a new one");

            //   timerCount = 0;
            caloriesPushUos = 0;
            caloriesJumpRope = 0;
            caloriesSquats = 0;
            counterPushUps = 0;
            counterJumpRope = 0;
            counterSquats = 0;       // обнуление секундомеров за сессию
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