import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SnakeGame extends JFrame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    private static final int GAME_SPEED = 150;

    private List<Point> snake;
    private Point food;
    private int direction;
    private boolean gameOver;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game by semihdursungul");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        addKeyListener(new KeyHandler());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startGame();
    }

    private void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        generateFood();

        direction = KeyEvent.VK_RIGHT;
        gameOver = false;

        timer = new Timer(GAME_SPEED, e -> gameLoop());
        timer.start();

        if (timer != null) {
            timer.stop();
        }

        snake = new ArrayList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        generateFood();

        direction = KeyEvent.VK_RIGHT;
        gameOver = false;

        timer = new Timer(GAME_SPEED, e -> gameLoop());
        timer.start();
    }

    private void gameLoop() {
        if (!gameOver) {
            moveSnake();
            checkCollision();
            repaint();
        }
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case KeyEvent.VK_UP:
                newHead.y--;
                break;
            case KeyEvent.VK_DOWN:
                newHead.y++;
                break;
            case KeyEvent.VK_LEFT:
                newHead.x--;
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x++;
                break;
        }

        snake.add(0, newHead);
        snake.remove(snake.size() - 1);
    }

    private void checkCollision() {
        Point head = snake.get(0);

        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }

        if (head.equals(food)) {
            snake.add(new Point(-1, -1));
            generateFood();
        }
    }

    private void generateFood() {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));

        food = new Point(x, y);
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

            g.setColor(Color.RED);
            g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

            if (gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = g.getFontMetrics();
                String gameOverText = "Game Over! Press Enter to Restart.";
                int x = (getWidth() - metrics.stringWidth(gameOverText)) / 2;
                int y = (getHeight() - metrics.getHeight()) / 2;
                g.drawString(gameOverText, x, y);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }
    }

    private class KeyHandler implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
                startGame();
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) {
                direction = KeyEvent.VK_UP;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) {
                direction = KeyEvent.VK_DOWN;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) {
                direction = KeyEvent.VK_LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) {
                direction = KeyEvent.VK_RIGHT;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}
