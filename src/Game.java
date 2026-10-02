import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends JFrame {

    private final GhostAnimation blueGhost;
    private final GhostAnimation redGhost;
    private final GhostAnimation pinkGhost;

    public Game(MapElement[][] gameBoard) {
        setTitle("Gra");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(36, 7, 116));

        Images.load();

        // Inicjalizacja obiektów gry
        pacmanAnimation pacmanAnimation = new pacmanAnimation(Images.pacmanSpriteBasic, 20, 20, 100);
        redGhost = new GhostAnimation(4, 4, Images.redGhostImg, MapElement.redGhost, 75, gameBoard);
        pinkGhost = new GhostAnimation(4, 4, Images.pinkGhostImg, MapElement.pinkGhost, 75, gameBoard);
        blueGhost = new GhostAnimation(4, 4, Images.blueGhostImg, MapElement.blueGhost, 75, gameBoard);
        GhostAnimation.allGhosts = new GhostAnimation[]{blueGhost, redGhost, pinkGhost};

        // Skrót do powrotu do menu
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

        // Plansza gry
        PaintBoard paintBoard = new PaintBoard(gameBoard, pacmanAnimation, redGhost, pinkGhost, blueGhost);
        JTable table = paintBoard.getTable();
        JScrollPane scrollPane = paintBoard.getScrollPane();

        scrollPane.setBackground(new Color(36, 7, 116));
        scrollPane.getViewport().setBackground(new Color(36, 7, 116));
        table.setBackground(new Color(36, 7, 116));
        table.setForeground(Color.WHITE);
        add(scrollPane);


        // Panel wyników i żyć
        ScoreInGame scorePanel = new ScoreInGame(3);
        scorePanel.setBackground(new Color(36, 7, 116));
        scorePanel.setForeground(Color.WHITE);
        add(scorePanel, BorderLayout.SOUTH);

        // Obsługa ruchu gracza
        playerMove move = new playerMove(gameBoard, table, ScoreInGame.getPoints(), pacmanAnimation);
        table.addKeyListener(move);
        table.setFocusable(true);
        addKeyListener(move);
        setFocusable(true);
        requestFocusInWindow();

        // Pętla aktualizacji duchów
        Thread ghostUpdate = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (gameBoard) {
                    redGhost.update();
                    pinkGhost.update();
                    blueGhost.update();
                }

                SwingUtilities.invokeLater(table::repaint);
            }
        });
        ghostUpdate.start();

        setVisible(true);
    }


    public static void gameOver() {
        String nickname = JOptionPane.showInputDialog(null, "Game Over!\nType your nickname:");
        if (nickname != null && !nickname.trim().isEmpty()) {
            Ranking entry = new Ranking(nickname.trim(), Integer.parseInt(ScoreInGame.getPoints().getText().replaceAll("[^0-9]", "")));
            Ranking.addToRanking(entry);
        }

        Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (activeWindow != null) {
            activeWindow.dispose();
        }

        SwingUtilities.invokeLater(() -> new menuFrame());
    }



}
