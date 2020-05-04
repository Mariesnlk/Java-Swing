
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphApp extends JFrame {

    private JTextField radiusField = new JTextField();
    private JTextField xMinField = new JTextField();
    private JTextField xMaxField = new JTextField();

    private JLabel radius = new JLabel("Радіус [0;4]");
    private JLabel xMin = new JLabel("Градус, з якого починається дуга");
    private JLabel xMax = new JLabel("Кут дуги");
    private JLabel sign = new JLabel("Синельник Марія");
    private JLabel info = new JLabel("Маштаб: 1-30");

    JButton saveButton = new JButton("Зберегти");
    JButton drawButton = new JButton("Малювати");

    static GraphicsConfiguration gc;
    JFrame frame = new JFrame(gc);


    GraphApp() {

        setTitle("Graphics Applet");
        setBounds(100, 100, 400, 450);
        setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
        setVisible(true);

        add(createPanel());
        validate();

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);


        radius.setBounds(10, 10, 200, 50);
        panel.add(radius);
        radiusField.setBounds(220, 10, 50, 50);
        panel.add(radiusField);
        xMin.setBounds(10, 70, 200, 50);
        panel.add(xMin);
        xMinField.setBounds(220, 70, 50, 50);
        panel.add(xMinField);
        xMax.setBounds(10, 130, 200, 50);
        panel.add(xMax);
        xMaxField.setBounds(220, 130, 50, 50);
        panel.add(xMaxField);
        drawButton.setBounds(10, 190, 200, 50);
        panel.add(drawButton);
        saveButton.setBounds(10, 250, 200, 50);
        panel.add(saveButton);
        sign.setBounds(100, 300, 200, 30);
        panel.add(sign);
        info.setBounds(100, 340, 200, 30);
        panel.add(info);

        addSaveButtonListener(saveButton);
        addDrawButtonListener(drawButton);


        return panel;
    }

    private void addDrawButtonListener(JButton drawButton) {

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkValues()) {
//                    drawGraphCanvas();
                    JFrame form1 = new JFrame("Коло");
                    form1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    form1.setSize(400, 400);
                    form1.setLocation(800, 100);
                    frame.setVisible(false);
                    form1.setVisible(true);

                    JPanel panel = new JPanel() {
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.BLACK);
                            g.drawLine(200, 50, 200, 350);
                            g.drawLine(50, 200, 350, 200);
                            g.drawLine(190, 60, 200, 50);
                            g.drawLine(200, 50, 210, 60);
                            g.drawLine(340, 190, 350, 200);
                            g.drawLine(340, 210, 350, 200);
                            g.drawString("0", 180, 220);
                            g.drawString("X", 350, 220);
                            g.drawString("Y", 180, 40);
                            g.setColor(Color.RED);
                            g.drawArc(200 - 30 * Integer.parseInt(radiusField.getText()),
                                    200 - 30 * Integer.parseInt(radiusField.getText()),
                                    2 * 30 * Integer.parseInt(radiusField.getText()),
                                    2 * 30 * Integer.parseInt(radiusField.getText()),
                                    Integer.parseInt(xMinField.getText()),
                                    Integer.parseInt(xMaxField.getText()));
                        }
                    };
                    form1.add(panel);
                }
            }
        });

    }

    private void addSaveButtonListener(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileToSave = new JFileChooser();
                int ret = fileToSave.showDialog(null, "Save");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileToSave.getSelectedFile();
                    try {
                        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D graphics2D = image.createGraphics();
                        getContentPane().paint(graphics2D);
                        ImageIO.write(image, "jpeg", file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Немає графіку!", "Wrong", JOptionPane.ERROR_MESSAGE);
                }
            }


        });
    }


    private boolean checkValues() {
        boolean canDraw = false;

        if (isNull()) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Введіть для початку всі значення", "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        } else {
            try {
                if ((Integer.parseInt(xMinField.getText()) <= Integer.parseInt(xMaxField.getText()) ||
                        (Integer.parseInt(xMinField.getText()) >= Integer.parseInt(xMaxField.getText())))) {
                    canDraw = true;

                }

            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Це не цифра! Введіть цифру", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return canDraw;
    }


    private boolean isNull() {
        return radiusField.getText() == null || radiusField.getText().length() == 0 ||
                xMaxField.getText() == null || xMaxField.getText().length() == 0 ||
                xMinField.getText() == null || xMinField.getText().length() == 0;
    }
}