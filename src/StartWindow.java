import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.Image.SCALE_SMOOTH;

public class StartWindow extends JFrame {

    private final int IMG_SIZE = 150;

    private JButton startButton = new JButton("Start");
    private JButton settingButton = new JButton("Setting");

    StartWindow() {
        setTitle("Snake");
        setSize(300, 350);
        setLocationRelativeTo(null);

        JLabel label = new JLabel(loadIcon());
        add(label, BorderLayout.CENTER);

        JPanel grid = new JPanel(new GridLayout(1, 2, 10, 5));
        grid.add(startButton);
        grid.add(settingButton);
        add(grid, BorderLayout.SOUTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SettingWindow settingWindow = new SettingWindow();
        ActionListener settingListener = e -> settingWindow.setVisible(true);
        settingButton.addActionListener(settingListener);

        ActionListener startGameListener = e -> {
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            setVisible(false);
        };
        startButton.addActionListener(startGameListener);
    }

    public static void main(String[] args) {
        StartWindow startWindow = new StartWindow();
    }

    private ImageIcon loadIcon() {
        ImageIcon mainIcon = new ImageIcon("MainSnake.png");
        Image image = mainIcon.getImage();
        Image newIcon = image.getScaledInstance(IMG_SIZE, IMG_SIZE, SCALE_SMOOTH);
        return new ImageIcon(newIcon);
    }
}
