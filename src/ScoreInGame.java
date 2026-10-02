import javax.swing.*;
import java.awt.*;

public class ScoreInGame extends JPanel {
    private static JLabel sumPoints;
    private static JLabel[] heartImages;
    private static int lives;

    public ScoreInGame(int initialLives) {
        lives = initialLives;
        sumPoints = new JLabel("Wynik: 0");
        heartImages = new JLabel[3];


        Color backgroundColor = new Color(36, 7, 116);
        Color textColor = new Color(118, 206, 246);
        Font font = new Font("Courier New", Font.BOLD, 18);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(backgroundColor); // tło panelu

        sumPoints.setForeground(textColor);
        sumPoints.setFont(font);
        add(sumPoints);

        for (int i = 0; i < heartImages.length; i++) {
            heartImages[i] = new JLabel(new ImageIcon("heart.png"));
            add(heartImages[i]);
        }

        updateHearts();
    }

    public static JLabel getPoints() {
        return sumPoints;
    }

    public static void loseLife() {
        if (lives > 0) {
            lives--;
            updateHearts();
        }
    }

    public static int getLives() {
        return lives;
    }

    public static void updateHearts() {
        for (int i = 0; i < heartImages.length; i++) {
            heartImages[i].setVisible(i < lives);
        }
    }
    public static void addPoints(int points) {
        int current = Integer.parseInt(sumPoints.getText().replaceAll("[^0-9]", ""));
        current += points;
        sumPoints.setText("Points: " + current);
    }

}
