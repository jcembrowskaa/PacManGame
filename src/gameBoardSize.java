import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class gameBoardSize implements ChangeListener {

    private final JFrame gameSizeFrame;
    private final JLabel gameSizeLabel;
    private final JSlider gameSizeSlider;
    private int choosedSize;
    private boolean confirmedSize = false;

    public gameBoardSize(JFrame oldFrame) {
        Point oldLocation = oldFrame.getLocation();
        oldFrame.dispose();

        // Kolory jak w menuFrame
        Color backgroundColor = new Color(36, 7, 116);
        Color buttonColor = new Color(99, 57, 207);
        Color textColor = new Color(118, 206, 246);
        Font font = new Font("Courier New", Font.BOLD, 20);

        gameSizeFrame = new JFrame("Game Board Size");
        JPanel gameSizePanel = new JPanel();
        gameSizePanel.setBackground(backgroundColor);

        gameSizeLabel = new JLabel();
        gameSizeLabel.setForeground(textColor);
        gameSizeLabel.setFont(font);

        gameSizeSlider = new JSlider(10, 100, 50);
        JButton startGameButton = new JButton("Start");

        gameSizeFrame.setLocation(oldLocation);
        gameSizeFrame.setSize(640, 480);
        gameSizeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameSizeFrame.getContentPane().setBackground(backgroundColor);

        // PANEL DOLNY
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(backgroundColor);
        startGameButton.setBackground(buttonColor);
        startGameButton.setForeground(textColor);
        startGameButton.setFont(font);
        startGameButton.setFocusable(false);
        bottomPanel.add(startGameButton);
        gameSizeFrame.add(bottomPanel, BorderLayout.SOUTH);

        // SUWAK
        gameSizeSlider.setPreferredSize(new Dimension(300, 50));
        gameSizeSlider.setPaintTicks(true);
        gameSizeSlider.setMinorTickSpacing(10);
        gameSizeSlider.setPaintLabels(true);
        gameSizeSlider.addChangeListener(this);
        gameSizeSlider.setBackground(backgroundColor);
        gameSizeSlider.setForeground(textColor);

        // ETYKIETA
        gameSizeLabel.setText("Game Board Size: " + gameSizeSlider.getValue());

        // PANEL GŁÓWNY
        gameSizePanel.add(gameSizeSlider);
        gameSizePanel.add(gameSizeLabel);
        gameSizeFrame.add(gameSizePanel, BorderLayout.CENTER);

        // PRZYCISK
        startGameButton.addActionListener(e -> {
            choosedSize = gameSizeSlider.getValue();
            confirmedSize = true;
            MapElement[][] boardData = GameboardMatrix.generateMatrix(choosedSize);
            new Game(boardData);
            gameSizeFrame.dispose();
        });

        // SKRÓT KLAWISZOWY
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED &&
                    e.getKeyCode() == KeyEvent.VK_Q &&
                    e.isControlDown() &&
                    e.isShiftDown()) {
                SwingUtilities.invokeLater(() -> {
                    Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (activeWindow != null) activeWindow.dispose();
                    new menuFrame();
                });
                return true;
            }
            return false;
        });

        gameSizeFrame.setVisible(true);
    }

    public int getChoosedSize() {
        gameSizeFrame.setVisible(true);
        while (gameSizeFrame.isDisplayable()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return confirmedSize ? choosedSize : -1;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        gameSizeLabel.setText("Game Board Size: " + gameSizeSlider.getValue());
    }
}
