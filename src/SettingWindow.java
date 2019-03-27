import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private JButton comeBackButton = new JButton("Return");
    private JRadioButton radioButtonFast = new JRadioButton("Fast", true);
    private JRadioButton radioButtonSlow = new JRadioButton("Slow");

    SettingWindow() {
        setTitle("Setting");
        setSize(300, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioButtonFast);
        radioGroup.add(radioButtonSlow);

        JPanel grid = new JPanel(new GridLayout(3, 1, 10, 5));
        grid.add(radioButtonFast);
        grid.add(radioButtonSlow);

        add(grid, BorderLayout.CENTER);
        add(comeBackButton, BorderLayout.SOUTH);

        ActionListener comeBackListener = e -> {
            if (radioButtonFast.isSelected()) {
                GameBoard.delay = 90;
                GameBoard.point = 4;
            }
            if (radioButtonSlow.isSelected()) {
                GameBoard.delay = 150;
                GameBoard.point = 2;
            }
            setVisible(false);
        };

        comeBackButton.addActionListener(comeBackListener);
    }
}
