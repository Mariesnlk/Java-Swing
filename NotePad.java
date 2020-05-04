import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.io.*;

public class NotePad extends JFrame implements ActionListener {
    private JComboBox<String> fontFamilyComboBox;
    private JComboBox<String> fontSizeComboBox;
    private JComboBox<String> textAlignComboBox;
    private static final String[] FONT_SIZES = {"Font Size", "8", "10", "10.5", "14", "16", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40"};
    private static final String[] TEXT_ALIGNMENTS = {"Text Align", "Left", "Center", "Right", "Justified"};
    private static final String[] FONT_LIST = {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Font Family", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Symbol", "Tahoma", "Times New Roman", "Verdana"};
    private static final Color backgroundFunctions = new Color(173, 216, 230);
    private static final Font font = new Font("Times New Roman", Font.PLAIN, 20);
    private JTextPane textPane = new JTextPane();
    private JPanel panel = new JPanel();
    private JScrollPane scrollPane;

    private static final int NUM_CHARS = 15;

    private JButton searchButton = new JButton("Search");
    private JTextField searchField = new JTextField(NUM_CHARS);
    private JCheckBox searchCaseSensitiveBox= new JCheckBox("Case sensitive", false);
    private JCheckBox reverseSearchBox= new JCheckBox("Reverse search", true);

    private JButton replaceAllButton= new JButton("Replace all");
    private JButton replaceSelectionButton= new JButton("Replace selection");
    private JTextField replaceField = new JTextField(NUM_CHARS);;

    public NotePad() {

        setTitle("Text Editor");

        JPanel functions = new JPanel(new GridLayout(4, 4));
        functions.setBackground(backgroundFunctions);
        textPane.setFont(font);
        scrollPane = new JScrollPane(textPane);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu open = new JMenu("Open");
        JMenu infoMenu = new JMenu("Info");

        JMenuItem info = new JMenuItem("Information");
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Notepad is a simple text editor for Microsoft Windows and a basic text-editing program which enables computer users to create documents.\n It was first released as a mouse-based MS-DOS program in 1983, and has been included in all versions of Microsoft Windows since Windows 1.0 in 1985.");
            }
        });

        JMenuItem newText = new JMenuItem("New");
        newText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.setText("");
            }
        });

        JMenuItem text = new JMenuItem("Text");
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileChooserText();
            }
        });

        JMenuItem img = new JMenuItem("Image");
        img.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooserImage();
            }
        });

        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.cut();
            }
        });

        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.copy();
            }
        });

        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.paste();
            }
        });

        JMenuItem saveItem = new JMenuItem("Save (...)");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fr = new FileWriter("Notepad.txt");
                    fr.write(textPane.getText());
                    fr.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textPane.print();
                } catch (PrinterException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton bold = new JButton(new StyledEditorKit.BoldAction());
        bold.setText("Bold");
        functions.add(bold);

        JButton italic = new JButton(new StyledEditorKit.ItalicAction());
        italic.setText("Italic");
        functions.add(italic);

        JButton underline = new JButton(new StyledEditorKit.UnderlineAction());
        underline.setText("Underline");
        functions.add(underline);

        JButton color = new JButton("Set Color");
        color.addActionListener(new NotePad.ColorActionListener());
        functions.add(color);

        JButton breakParagraph = new JButton(new DefaultEditorKit.InsertBreakAction());
        breakParagraph.setText("Paragraph break");
        functions.add(breakParagraph);

        JButton tab = new JButton(new DefaultEditorKit.InsertTabAction());
        tab.setText("Tab break");
        functions.add(tab);

        fontSizeComboBox = new JComboBox<String>(FONT_SIZES);
        fontSizeComboBox.setEditable(false);
        fontSizeComboBox.addItemListener(new FontSizeItemListener());
        functions.add(fontSizeComboBox);

        textAlignComboBox = new JComboBox<String>(TEXT_ALIGNMENTS);
        textAlignComboBox.setEditable(false);
        textAlignComboBox.addItemListener(new NotePad.TextAlignItemListener());
        functions.add(textAlignComboBox);

        fontFamilyComboBox = new JComboBox<String>(FONT_LIST);
        fontFamilyComboBox.setEditable(false);
        fontFamilyComboBox.addItemListener(new FontFamilyItemListener());
        functions.add(fontFamilyComboBox);

        add(functions, BorderLayout.SOUTH);

        searchButton.addActionListener(this);
        functions.add(searchButton);
        functions.add(searchField);
        functions.add(searchCaseSensitiveBox);
        functions.add(reverseSearchBox);

        replaceAllButton.addActionListener(this);
        replaceSelectionButton.addActionListener(this);

        functions.add(replaceAllButton);
        functions.add(replaceSelectionButton);
        functions.add(replaceField);

        open.add(text);
        open.add(img);


        fileMenu.add(newText);
        fileMenu.addSeparator();
        fileMenu.add(open);
        fileMenu.add(saveItem);

        fileMenu.addSeparator();
        fileMenu.add(print);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        jMenuBar.add(fileMenu);

        jMenuBar.add(editMenu);
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        infoMenu.add(info);
        jMenuBar.add(infoMenu);

        add(jMenuBar, BorderLayout.NORTH);
        add(panel);
        setPreferredSize(new Dimension(700, 700));
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void fileChooserImage() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "Enter expansion of image(.jpg, .png, .gif)";
            }

            @Override
            public boolean accept(File f) {
                String s = "";
                if (f != null && (s = f.toString()) != null) {

                    if (f.isDirectory())
                        return true;
                    return s.endsWith(".jpg") || s.endsWith(".png") || s.endsWith("gif");
                }
                return false;
            }
        });

        if (jFileChooser.showDialog(null, "Add Picture") == JFileChooser.APPROVE_OPTION) {
            ImageIcon imageIcon = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            Image image = imageIcon.getImage();
            Image newImage = image.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(newImage);
            textPane.insertIcon(img);
        }
    }

    private class FileChooserText extends JFrame {
        JFileChooser choice;

        public FileChooserText() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            start();
            pack();
            setVisible(true);
        }

        private void start() {

            choice = new JFileChooser();
            choice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            FileFilter f = new FileFilter() {

                @Override
                public boolean accept(File file) {
                    if (file.isDirectory())
                        return true;
                    if (file.getAbsolutePath().endsWith(".txt") || file.getAbsolutePath().endsWith(".pages")
                            || file.getAbsolutePath().endsWith(".doc") || file.getAbsolutePath().endsWith(".docx"))
                        return true;
                    else
                        return false;
                }

                @Override
                public String getDescription() {
                    return ".txt, .pages, .doc, .docx";
                }
            };
            choice.setFileFilter(f);

            int returnValue = choice.showOpenDialog(FileChooserText.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    FileReader fr = new FileReader(choice.getSelectedFile().getAbsolutePath());
                    BufferedReader br = new BufferedReader(fr);
                    File file = choice.getSelectedFile();
                    textPane.setText(textPane.getText() + "" + DataInput.getString(br));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    public void actionPerformed(ActionEvent event) {
        JButton clickedButton = (JButton) event.getSource();
        if (clickedButton == searchButton) {
            search();
        } else if (clickedButton == replaceAllButton) {
            replaceAll(searchField.getText(), replaceField.getText());
        } else if (clickedButton == replaceSelectionButton) {
            replaceSelection();
        }
    }

    private void replaceAll(String oldString, String newString) {
        if (!oldString.equals("")) {
            String editorText = textPane.getText();
            editorText = editorText.replaceAll(oldString, newString);
            textPane.setText(editorText);
        }
    }


    private void replaceSelection() {
        String editorText = textPane.getText();

        String start = editorText.substring(0, textPane.getSelectionStart());
        String end = editorText.substring(textPane.getSelectionEnd());

        editorText = start + replaceField.getText() + end;
        textPane.setText(editorText);
    }

    private void search() {
        String editorText = textPane.getText();

        String searchValue = searchField.getText();

        if (searchCaseSensitiveBox.getSelectedObjects() == null) {
            editorText = editorText.toLowerCase();
            searchValue = searchValue.toLowerCase();
        }

        int start;
        if (reverseSearchBox.getSelectedObjects() == null) {
            start = editorText.indexOf(searchValue, textPane.getSelectionEnd());
        } else {
            start = editorText.lastIndexOf(searchValue, textPane.getSelectionStart() - 1);
        }
        if (start != -1) {
            textPane.setCaretPosition(start);
            textPane.moveCaretPosition(start + searchValue.length());
            textPane.getCaret().setSelectionVisible(true);
        }
    }

    private class ColorActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Color newColor =
                    JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            if (newColor == null) {
                textPane.requestFocusInWindow();
                return;
            }
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            textPane.setCharacterAttributes(attr, false);
            textPane.requestFocusInWindow();
        }
    }

    private class FontFamilyItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontFamilyComboBox.getSelectedIndex() == 0)) {
                return;
            }
            String fontFamily = (String) e.getItem();
            fontFamilyComboBox.setAction(new StyledEditorKit.FontFamilyAction(fontFamily, fontFamily));
            fontFamilyComboBox.setSelectedIndex(0);
        }
    }

    private class TextAlignItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (textAlignComboBox.getSelectedIndex() == 0)) {

                return;
            }
            String alignmentStr = (String) e.getItem();
            int newAlignment = textAlignComboBox.getSelectedIndex() - 1;
            textAlignComboBox.setAction(new StyledEditorKit.AlignmentAction(alignmentStr, newAlignment));
            textAlignComboBox.setSelectedIndex(0);
        }
    }

    private class FontSizeItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontSizeComboBox.getSelectedIndex() == 0)) {
                return;
            }

            String fontSizeStr = (String) e.getItem();
            int newFontSize = 0;

            try {
                newFontSize = Integer.parseInt(fontSizeStr);
            } catch (NumberFormatException ex) {

                return;
            }

            fontSizeComboBox.setAction(new StyledEditorKit.FontSizeAction(fontSizeStr, newFontSize));
            fontSizeComboBox.setSelectedIndex(0);

        }
    }
}
