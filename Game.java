package ua.edu.ukma.mariia_synelnyk;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {

    private Table gameModel;
    private JTable table;//таблиця
    private final Tester gameController;
    private JLabel maxNumberLabel = new JLabel("Максимальне число");//текст
    private JLabel taskAmountLabel = new JLabel("Кількість прикладів");

    private JTextField maxNumberField = new JTextField();//дозволяє вводити і редагувати текстову строку
    private JTextField taskAmountField = new JTextField();

    private JButton startButton = new JButton("Почати");//кнопка
    private JButton submitButton = new JButton("Перевірити");

    public Game(Tester gameController, Table gameModel) throws HeadlessException {

        this.gameController = gameController;
        this.gameModel = gameModel;

        setTitle("Математична гра");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setVisible(true);
        setBounds(400, 200, 800, 500);


        JPanel summaryPanel = createTopPanel();
        add(summaryPanel);

    }

    /**
     * JPanel контейнер панелі
     * @return summaryPanel дана пенель
     */
    public JPanel createTopPanel() {
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(null);

        summaryPanel.add(new JLabel("0000"));

        maxNumberLabel.setBounds(20, 15, 150, 50);
        summaryPanel.add(maxNumberLabel);
        maxNumberField.setBounds(150, 20, 30, 30);
        summaryPanel.add(maxNumberField);

        taskAmountLabel.setBounds(200, 15, 150, 50);
        summaryPanel.add(taskAmountLabel);
        taskAmountField.setBounds(330, 20, 30, 30);
        summaryPanel.add(taskAmountField);

        startButton.setBounds(450, 20, 100, 30);
        summaryPanel.add(startButton);
        submitButton.setBounds(600, 20, 100, 30);
        summaryPanel.add(submitButton);


        startButtonListener();
        submitButtonListener();

        return summaryPanel;
    }

    /**
     *
     */
    private void submitButtonListener() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submitButton) {

                    AbstractTableModel tableModel = (AbstractTableModel) table.getModel();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        Integer userAnswer;

                        try {
                            userAnswer = Integer.valueOf((String) tableModel.getValueAt(i, 1));
                        } catch (Exception ex) {
                            userAnswer = null;
                        }


                        if (userAnswer != null && userAnswer.equals(gameModel.getTask()[i].getResult())) {

                            tableModel.setValueAt("✓", i, 2);
                        } else {
                            tableModel.setValueAt("❌", i, 2);
                        }

                    }

                }
            }
        });

    }

    public void startButtonListener() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    boolean readyToPlay = true;
                    int maxNumber = gameController.checkIfInputIsNumber(maxNumberField.getText());

                    if (maxNumber == -1) {

                        JOptionPane.showMessageDialog(getParent(), "Така цифра не підходить");
                        readyToPlay = false;
                    }

                    int taskAmount = gameController.checkIfInputIsNumber(taskAmountField.getText());

                    if (taskAmount != -1) {
                        if (taskAmount < 0 || taskAmount > 12) {
                            JOptionPane.showMessageDialog(getParent(), "Можна обчислити не більше 12 прикладів");
                            readyToPlay = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(getParent(), "Ви ввели не цифру");
                        readyToPlay = false;
                    }

                    if (readyToPlay) {

                        gameModel.setMaxNumber(maxNumber);
                        gameModel.setTaskAmount(taskAmount);
                        createTable();

                    }


                }
            }
        });
    }

    public void createTable() {


        String[] columnNames = {"Приклад",
                "Відповідь", "Оцінка"};


        Task[] tasks = gameModel.generateTasks();


        if (table == null) {

            table = new JTable();
            JScrollPane panel = new JScrollPane(table);
            table.setVisible(true);
            table.setFillsViewportHeight(true);
            add(panel);
            validate();
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(gameModel.getTaskAmount());
        model.setColumnCount(3);
        model.setColumnIdentifiers(columnNames);

        for (int i = 0; i < tasks.length; i++) {
            model.setValueAt(tasks[i].toString(), i, 0);
            model.setValueAt(null, i, 1);
            model.setValueAt(null, i, 2);
        }


    }


}