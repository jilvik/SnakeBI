import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static java.awt.Image.SCALE_SMOOTH;

public class GameBoard extends JPanel implements ActionListener {

    private final int DOT_SIZE = 15;
    private final int BOARD_SCALE = 20;
    private final int SIZE = DOT_SIZE * BOARD_SCALE;
    private final int ALL_DOTS = BOARD_SCALE * BOARD_SCALE;

    private int length;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    private int preyX;
    private int preyY;

    private boolean isGame;
    private boolean isGoRight;
    private boolean isGoLeft;
    private boolean isGoUp;
    private boolean isGoDown;
    private int score;

    static int point = 4;
    static int delay = 90;

    private Image dot;
    private Timer timer;


    GameBoard() {
        loadIcon();
        initGame();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    private void initGame() {
        length = 2;
        score = 0;
        isGoRight = true;
        isGame = true;
        for (int i = 0; i < length; i++) {
            x[i] = 2 * DOT_SIZE - i * DOT_SIZE;
            y[i] = 3 * DOT_SIZE;
        }

        timer = new Timer(delay, this);
        timer.start();
        createPrey();
    }

    private void createPrey() {
        preyX = new Random().nextInt(BOARD_SCALE) * DOT_SIZE;
        preyY = new Random().nextInt(BOARD_SCALE) * DOT_SIZE;
        for (int i = length; i > 0; i--) {
            if (x[i] == preyX) {
                preyX = new Random().nextInt(BOARD_SCALE) * DOT_SIZE;
            } else {
                if (y[i] == preyY) {
                    preyY = new Random().nextInt(BOARD_SCALE) * DOT_SIZE;
                }
            }
        }
    }

    private void loadIcon() {
        ImageIcon dotIcon = new ImageIcon("blackDot.png");
        Image image = dotIcon.getImage();
        Image newIcon = image.getScaledInstance(DOT_SIZE, DOT_SIZE, SCALE_SMOOTH);
        dotIcon = new ImageIcon(newIcon);
        dot = dotIcon.getImage();
    }

    private void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (isGoRight) {
            if (x[0] < SIZE - DOT_SIZE) {
                x[0] = x[0] + DOT_SIZE;
            } else {
                x[0] = x[0] + DOT_SIZE - SIZE;
            }
        }
        if (isGoLeft) {
            if (x[0] > 0 ) {
                x[0] = x[0] - DOT_SIZE;
            } else {
                x[0] = x[0] - DOT_SIZE + SIZE;
            }
        }
        if (isGoUp) {
            if (y[0] > 0) {
                y[0] = y[0] - DOT_SIZE;
            } else {
                y[0] = y[0] - DOT_SIZE + SIZE;
            }
        }
        if (isGoDown) {
            if (y[0] < SIZE - DOT_SIZE) {
                y[0] = y[0] + DOT_SIZE;
            } else {
                y[0] = y[0] + DOT_SIZE - SIZE;
            }
        }
    }

    private void checkPrey() {
        if (x[0] == preyX && y[0] == preyY) {
            length++;
            score += point;
            createPrey();
        }
    }

    private void checkOuroboros() {
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                isGame = false;
                timer.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGame) {
            move();
            checkOuroboros();
            checkPrey();
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0, 0, SIZE, 0);
        g.drawLine(0, 0, 0, SIZE);
        g.drawLine(SIZE, 0, SIZE, SIZE);
        g.drawLine(0, SIZE, SIZE, SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isGame) {
            g.drawImage(dot, preyX, preyY, this);
            for (int i = 0; i < length; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            g.drawString("Score: " + score, 5, SIZE + 20);
        } else {
            g.drawString("Game over!", 117, 130);
            g.drawString("Score: " + score, 117, 170);
        }
    }


    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && ! isGoRight) {
                isGoLeft = true;
                isGoUp = false;
                isGoDown = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! isGoLeft) {
                isGoRight = true;
                isGoUp = false;
                isGoDown = false;
            }
            if (key == KeyEvent.VK_UP && ! isGoDown) {
                isGoUp = true;
                isGoLeft = false;
                isGoRight = false;
            }
            if (key == KeyEvent.VK_DOWN && ! isGoUp) {
                isGoDown = true;
                isGoLeft = false;
                isGoRight = false;
            }

            if (key == KeyEvent.VK_SPACE && isGame) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }

            if (key == KeyEvent.VK_ENTER && ! isGame) {
                initGame();
            }
        }
    }
}
