import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class EndlessRunnerGame extends JPanel implements ActionListener {
    private Timer timer;
    private int delay = 10;

    private int playerY; // Vertical position of the player
    private int playerHeight = 50;
    private boolean isJumping = false;
    private boolean isSliding = false;

    private int obstacleX; // Horizontal position of the obstacle
    private int obstacleY; // Vertical position of the obstacle
    private int obstacleWidth = 30;
    private int obstacleSpeed = 5;

    private int score = 0;
    private boolean isGameOver = false;

    public EndlessRunnerGame() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 400));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    resetGame();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                    jump();
                } else if (e.getKeyCode() == KeyEvent.VK_S && !isJumping) {
                    slide();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    stopSliding();
                }
            }
        });

        playerY = 200;
        obstacleX = 800;
        obstacleY = getRandomVerticalPosition();

        timer = new Timer(delay, this);
        timer.start();
    }

    public void jump() {
        isJumping = true;
        new Thread(() -> {
            int jumpHeight = 150;
            int jumpSpeed = 7;

            for (int i = 0; i <= jumpHeight; i += jumpSpeed) {
                playerY -= jumpSpeed;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i <= jumpHeight; i += jumpSpeed) {
                playerY += jumpSpeed;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isJumping = false;
        }).start();
    }

    public void slide() {
        isSliding = true;
        playerHeight = 25;
    }

    public void stopSliding() {
        isSliding = false;
        playerHeight = 50;
    }

    public void move() {
        if (!isGameOver) {
            if (obstacleX + obstacleWidth >= 0) {
                obstacleX -= obstacleSpeed;
            } else {
                // Generate a new obstacle
                obstacleX = 800;
                obstacleY = getRandomVerticalPosition();
                score++;

                if (score % 10 == 0) {
                    obstacleSpeed += 2;
                }
            }
        }
    }

    public void checkCollisions() {
        Rectangle playerBounds = new Rectangle(100, playerY, 50, playerHeight);
        Rectangle obstacleBounds = new Rectangle(obstacleX, obstacleY, obstacleWidth, 50);

        if (playerBounds.intersects(obstacleBounds)) {
            // Collision occurred, handle game over or score decrement logic here
            gameOver();
        }
    }

    public void gameOver() {
        isGameOver = true;
        timer.stop();
        System.out.println("Game Over");
    }

    public void resetGame() {
        isGameOver = false;
        score = 0;
        obstacleSpeed = 5;
        obstacleX = 800;
        playerY = 200;
        playerHeight = 50;
        timer.start();
    }

    public int getRandomVerticalPosition() {
        int playerVerticalLevel = playerY + playerHeight / 2;
        int[] options = { playerVerticalLevel, playerVerticalLevel - 75 };
        int randomIndex = (int) (Math.random() * options.length);
        return options[randomIndex];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw player
        g.setColor(Color.WHITE);
        g.fillRect(100, playerY, 50, playerHeight);

        // Draw obstacle
        g.setColor(Color.RED);
        g.fillRect(obstacleX, obstacleY, obstacleWidth, 50);

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 20, 30);

        if (isGameOver) {
            g.drawString("Press Enter to Play Again", 300, 200);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Endless Runner Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new EndlessRunnerGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
