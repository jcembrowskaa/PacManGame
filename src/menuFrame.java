import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuFrame extends JFrame {
    public menuFrame() {
        this.setTitle("Pacman");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(36, 7, 116));

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");


        for (JButton button : new JButton[]{newGameButton, highScoresButton, exitButton}) {
            button.setFont(new Font("Courier new", Font.BOLD, 20));
            button.setBackground(new Color(99, 57, 207));
            button.setForeground(new Color(118, 206, 246));
            button.setFocusable(false);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40)); // Równa szerokość
            menuPanel.add(Box.createVerticalStrut(20)); // Odstęp
            menuPanel.add(button);
        }

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(36, 7, 116));
        centerPanel.add(menuPanel, new GridBagConstraints());

        this.getContentPane().add(centerPanel);
        this.setVisible(true);

        newGameButton.addActionListener(e -> new gameBoardSize(menuFrame.this));
        highScoresButton.addActionListener(e -> new RankingFrame());
        exitButton.addActionListener(e -> System.exit(0));
    }
}
