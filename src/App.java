import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.*;

public class App implements ActionListener {

    private JFrame frmCalculadora;
    private JTextField textField;
    private JPanel buttonsPanel;

    private static final String[] BUTTONS = { "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "C", ":", "0",
            "supr", "=" };

    private int fontSize = 22;
    private String fontName = "Ubuntu";
    private boolean isFullScreen = false;
    private Rectangle normalBounds;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        EventQueue.invokeLater(() -> {
            try {
                App window = new App();
                window.frmCalculadora.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Restart the program and contact the administrator if necessary", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public App() throws Exception {
        initialize();
    }

    private void initialize() {
        frmCalculadora = new JFrame();
        frmCalculadora.setTitle("Angles Calculator");
        frmCalculadora.setBounds(80, 80, 345, 460);
        frmCalculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonsPanel = new JPanel();
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        buttonsPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for (String button : App.BUTTONS) {
            createButton(button);
        }

        textField = new JTextField();
        mainPanel.add(textField, BorderLayout.NORTH);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setColumns(12);
        textField.setFont(new Font(fontName, Font.PLAIN, 28));

        frmCalculadora.getContentPane().add(mainPanel);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Opciones");
        JMenuItem fullScreenMenuItem = new JMenuItem("Fullscreen");
        
        fullScreenMenuItem.addActionListener(e -> toggleFullScreen());
        
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        exitMenuItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(fullScreenMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu themeMenu = new JMenu("Theme");
        JMenuItem darkThemeItem = new JMenuItem("Dark");
        JMenuItem lightThemeItem = new JMenuItem("Light");

        darkThemeItem.addActionListener(e -> {
            changeTheme('D');
        });

        lightThemeItem.addActionListener(e -> {
            changeTheme('L');
        });

        JMenu preferencesMenu = new JMenu("Preferences");
//        JMenuItem increaseFontSizeItem = new JMenuItem("Aumentar tamaño fuente");
//        JMenuItem decreaseFontSizeItem = new JMenuItem("Reducir tamaño fuente");
        JMenuItem changeFontItem = new JMenuItem("Change font");

//        increaseFontSizeItem.addActionListener(e -> {
//            fontSize += 2;
//            updateFont();
//        });
//
//        decreaseFontSizeItem.addActionListener(e -> {
//            fontSize -= 2;
//            updateFont();
//        });
//
        changeFontItem.addActionListener(e -> {
            changeFont();
        });
//
//        preferencesMenu.add(increaseFontSizeItem);
//        preferencesMenu.add(decreaseFontSizeItem);
        preferencesMenu.add(changeFontItem);

        themeMenu.add(darkThemeItem);
        themeMenu.add(lightThemeItem);

        menuBar.add(fileMenu);
        menuBar.add(themeMenu);
        menuBar.add(preferencesMenu);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);

        frmCalculadora.setJMenuBar(menuBar);
    }
    
    private void toggleFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            if (isFullScreen) {
                frmCalculadora.setExtendedState(JFrame.NORMAL);
                frmCalculadora.setBounds(normalBounds);
                isFullScreen = false;
            } else {
                normalBounds = frmCalculadora.getBounds();

                frmCalculadora.setExtendedState(JFrame.MAXIMIZED_BOTH);
                isFullScreen = true;
            }
        }
    }

    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frmCalculadora,
                "Angles Calculator\nVersión 1.0\n\nDeveloped By Alae",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }


    private void updateFont() {
        Font newFont = new Font(fontName, Font.PLAIN, fontSize);
        for (Component component : buttonsPanel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setFont(newFont);
            }
        }
        textField.setFont(new Font(fontName, Font.PLAIN, fontSize));
        frmCalculadora.pack();
    }

    private void changeFont() {
        String newFontName = JOptionPane.showInputDialog(frmCalculadora, "Font Name:", fontName);
        if (newFontName != null && !newFontName.isEmpty()) {
            fontName = newFontName;
            updateFont();
        }
    }

    public void changeTheme(char theme) {
        switch (theme) {
            case 'D':
                FlatDarculaLaf.setup();
                SwingUtilities.updateComponentTreeUI(frmCalculadora);
                break;
            case 'L':
                FlatLightLaf.setup();
                SwingUtilities.updateComponentTreeUI(frmCalculadora);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        printAction(button.getName());
    }

    public void printAction(String name) {
        String textFieldStr = this.textField.getText();

        switch (name) {
            case "C":
                this.textField.setText("");
                break;
            case "=":
                calculate();
                break;
            case "supr":
                if (textFieldStr.length() != 0)
                    this.textField.setText(textFieldStr.substring(0, textFieldStr.length() - 1));
                break;
            default:
                this.textField.setText(textFieldStr + name);
        }
    }

    public void createButton(String text) {
        JButton button = new JButton();

        if (text.equals("supr")) button.setIcon(new ImageIcon("img/backspace_54.png"));

        if (text.equals("=")) {
            button.setBackground(Color.decode("#2B35AF"));
            button.setBorder(personalizedButton(button, Color.decode("#2B35AF")));
            button.setForeground(Color.WHITE);
        }

        if (text.equals("C")) {
            button.setBackground(Color.RED);
            button.setBorder(personalizedButton(button, Color.RED));
            button.setFont(new Font(fontName, Font.PLAIN, 28));
            button.setForeground(Color.WHITE);
        }

        if (!text.equals("C") || !text.equals("=")) button.setFont(new Font(fontName, Font.PLAIN, fontSize));

        if (!text.equals("supr")) button.setText(text);

        button.setName(text);
        button.addActionListener(this);
        button.setFocusable(false);

        this.buttonsPanel.add(button);
    }

    public Border personalizedButton(JButton button, Color color) {
        Border defaultButtonBorder = button.getBorder();
        Border customBorder = BorderFactory.createLineBorder(color, 2);
        return new CompoundBorder(defaultButtonBorder, customBorder);
    }

    public void calculate() {
        try {
            String textField = this.textField.getText();
            
            if (textField.isEmpty()) {
            	throw new IllegalArgumentException("Invalid Format");
            }
            
            textField = textField.replace(" ", "");

            
            // Regex que separara las operaciones por operandos y los operadores
            // 23:32:23+-32:32:23 => 23:32:23 + -32:32:23
            // 30:-5:0-50:30:-1
            textField = textField.replaceAll("(?<=\\d)([+\\-])", " $1 ").trim();

            String[] splitedText = new String[50];

            splitedText = textField.split(" ");
            
            for (String text : splitedText) {
                System.out.println(text);
            }

            if (splitedText.length % 2 == 0) {
                throw new IllegalArgumentException("Invalid Format");
            }

            Angle result = Angle.format(splitedText[0]);

            if (splitedText.length <= 3) {
                for (int i = 1; i < splitedText.length; i += 2) {
                    char operator = splitedText[i].charAt(0);
                    Angle operand = Angle.format(splitedText[i + 1]);

                    switch (operator) {
                        case '+':
                            result = result.suma(operand);
                            break;
                        case '-':
                            result = result.resta(operand);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid Operator");
                    }
                }
            }

            result.normalize();
            this.textField.setText(result.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            this.textField.setText("Error");
        }
    }

}