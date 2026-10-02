import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class playerMove extends KeyAdapter {

    private final MapElement[][] gameboardMatrix;
    private int pacmanCurrentRow = 1;
    private int pacmanCurrentColumn = 1;
    private int score = 0;
    private final int basePointsPerPoint = 10;
    private int directionRow = 0;
    private int directionCol = 0;
    private final pacmanAnimation pacmanAnimation;
    private final int pacmanFast = 200;
    private final JTable gameboardTable;
    private final JLabel scoreOnBottomLabel;

    private Thread moveThread;
    private volatile boolean isMovementActive = true;

    public double speedPacman = 1.0;
    public static boolean ghostCanBite = false;
    public boolean pointsX3 = false;

    public playerMove(MapElement[][] gameboardMatrix, JTable gameboardTable, JLabel scoreOnBottomLabel, pacmanAnimation pacmanAnimation) {
        this.gameboardMatrix = gameboardMatrix;
        this.gameboardTable = gameboardTable;
        this.scoreOnBottomLabel = scoreOnBottomLabel;
        this.pacmanAnimation = pacmanAnimation;

        synchronized (gameboardMatrix) {
            if (gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn] == MapElement.point) {
                score += basePointsPerPoint;
                scoreOnBottomLabel.setText("Wynik: " + score);
            }
            gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn] = MapElement.pacman;
        }

        startMoveThread();
        gameboardTable.repaint();
    }

    private void startMoveThread() {
        isMovementActive = true;
        moveThread = new Thread(() -> {
            while (isMovementActive) {
                try {
                    Thread.sleep((int) (pacmanFast * speedPacman));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                if (directionRow == 0 && directionCol == 0) continue;

                synchronized (gameboardMatrix) {
                    int nextPacmanRow = pacmanCurrentRow + directionRow;
                    int nextPacmanCol = pacmanCurrentColumn + directionCol;

                    if (nextPacmanRow >= 0 && nextPacmanRow < gameboardMatrix.length &&
                            nextPacmanCol >= 0 && nextPacmanCol < gameboardMatrix[0].length &&
                            gameboardMatrix[nextPacmanRow][nextPacmanCol] != MapElement.wall) {

                        gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn] = MapElement.street;

                        pacmanCurrentRow = nextPacmanRow;
                        pacmanCurrentColumn = nextPacmanCol;

                        MapElement current = gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn];

                        if (current == MapElement.redGhost || current == MapElement.blueGhost || current == MapElement.pinkGhost) {
                            ScoreInGame.loseLife();
                            if (ScoreInGame.getLives() < 1) {
                                Game.gameOver();
                                return;
                            }
                        }

                        if (current.isBoost()) {
                            Boosts.activateBoost(current, this);
                            gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn] = MapElement.street;
                            score += 500;
                        }

                        if (current == MapElement.point) {
                            score += pointsX3 ? basePointsPerPoint * 3 : basePointsPerPoint;
                            SwingUtilities.invokeLater(() ->
                                    scoreOnBottomLabel.setText("Points: " + score)
                            );
                        }

                        gameboardMatrix[pacmanCurrentRow][pacmanCurrentColumn] = MapElement.pacman;
                        SwingUtilities.invokeLater(gameboardTable::repaint);

                        if (!anyPointsOnBoard()) {
                            SwingUtilities.invokeLater(() -> {
                                String nickname = JOptionPane.showInputDialog(null, "You win!\nType your nickname:");
                                if (nickname != null && !nickname.trim().isEmpty()) {
                                    Ranking entry = new Ranking(nickname.trim(), score);
                                    Ranking.addToRanking(entry);
                                }

                                Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                                if (activeWindow != null) {
                                    activeWindow.dispose();
                                }

                                new menuFrame();
                            });
                        }
                    }
                }
            }
        });
        moveThread.start();
    }


    public void refreshSpeed() {
        int lastDirectionRow = directionRow;
        int lastDirectionCol = directionCol;

        stop();

        directionRow = lastDirectionRow;
        directionCol = lastDirectionCol;

        startMoveThread();
    }

    public void stop() {
        isMovementActive = false;
        if (moveThread != null) moveThread.interrupt();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                directionRow = -1;
                directionCol = 0;
                pacmanAnimation.setDirection(Direction.up);
            }
            case KeyEvent.VK_S -> {
                directionRow = 1;
                directionCol = 0;
                pacmanAnimation.setDirection(Direction.down);
            }
            case KeyEvent.VK_A -> {
                directionRow = 0;
                directionCol = -1;
                pacmanAnimation.setDirection(Direction.left);
            }
            case KeyEvent.VK_D -> {
                directionRow = 0;
                directionCol = 1;
                pacmanAnimation.setDirection(Direction.right);
            }
        }
    }

    private boolean anyPointsOnBoard() {
        synchronized (gameboardMatrix) {
            for (MapElement[] row : gameboardMatrix) {
                for (MapElement mapElement : row) {
                    if (mapElement == MapElement.point) return true;
                }
            }
            return false;
        }
    }
}
