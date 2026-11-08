import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class ClassicCarGame extends JPanel {

    int carX = 175;
    int carY = 450;

    int score = 0;
    int speed = 5;

    boolean gameOver = false;

    Random random = new Random();

    ArrayList<Rectangle> enemies = new ArrayList<>();

    Timer timer;

    public ClassicCarGame() {

        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.GREEN);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    carX -= 25;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    carX += 25;
                }

                if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
                    restartGame();
                }

                carX = Math.max(55, Math.min(295, carX));

                repaint();
            }
        });

        timer = new Timer(30, e -> updateGame());
        timer.start();
    }

    void updateGame() {

        if (gameOver) return;

        if (random.nextInt(30) == 0) {
            enemies.add(new Rectangle(
                random.nextInt(250) + 55,
                -100,
                45,
                75
            ));
        }

        for (int i = 0; i < enemies.size(); i++) {

            Rectangle enemy = enemies.get(i);

            enemy.y += speed;

            Rectangle player =
                new Rectangle(carX, carY, 45, 75);

            if (player.intersects(enemy)) {
                gameOver = true;
            }

            if (enemy.y > 600) {
                enemies.remove(i);
                score++;
                i--;
            }
        }

        if (score > 10) speed = 7;
        if (score > 20) speed = 9;

        repaint();
    }

    void restartGame() {

        carX = 175;
        score = 0;
        speed = 5;

        enemies.clear();

        gameOver = false;

        repaint();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // الطريق
        g.setColor(Color.DARK_GRAY);
        g.fillRect(50, 0, 300, 600);

        // خطوط الطريق
        g.setColor(Color.WHITE);

        for (int y = 0; y < 600; y += 100) {
            g.fillRect(195, y, 8, 50);
        }

        // السيارة الرئيسية
        drawCar(g, carX, carY, Color.RED);

        // سيارات المنافسين
        for (Rectangle enemy : enemies) {
            drawCar(g, enemy.x, enemy.y, Color.BLUE);
        }

        // النقاط
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("SCORE: " + score, 10, 30);

        if (gameOver) {

            g.setColor(Color.BLACK);
            g.fillRect(50, 230, 300, 130);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("GAME OVER", 95, 280);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));

            g.drawString("Press R to Restart", 115, 320);
        }
    }

    void drawCar(Graphics g, int x, int y, Color color) {

        g.setColor(Color.BLACK);

        g.fillRoundRect(x - 5, y + 10, 12, 25, 5, 5);
        g.fillRoundRect(x + 38, y + 10, 12, 25, 5, 5);

        g.fillRoundRect(x - 5, y + 50, 12, 25, 5, 5);
        g.fillRoundRect(x + 38, y + 50, 12, 25, 5, 5);

        g.setColor(color);
        g.fillRoundRect(x, y, 45, 75, 10, 10);

        g.setColor(Color.CYAN);
        g.fillRect(x + 7, y + 12, 31, 18);

        g.setColor(Color.YELLOW);
        g.fillRect(x + 5, y + 65, 8, 5);
        g.fillRect(x + 32, y + 65, 8, 5);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Classic Car Racing");

        ClassicCarGame game = new ClassicCarGame();

        frame.add(game);

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        frame.setVisible(true);
    }
}