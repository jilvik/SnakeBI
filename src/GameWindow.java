import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    GameWindow() {
        setTitle("Snake");
        setSize(300, 350);
        setLocationRelativeTo(null);
        add(new GameBoard(), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
