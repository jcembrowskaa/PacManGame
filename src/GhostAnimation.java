import java.awt.image.BufferedImage;

public class GhostAnimation {
    private int currentRow, currentColumn;
    private final BufferedImage[] animationFrame;
    private int currentAnimationIndex = 0;
    private final long animationTime;
    private long lastAnimationUpdateTime;
    private int directionRow = 0, directionCol = 0;
    private final MapElement[][] gameboard;
    private final MapElement ghostType;
    private long lasyMovementUpdateTime;
    private MapElement previousBoardElement = MapElement.street;
    private long lastTryToGenerateBoost = System.currentTimeMillis();

    private boolean isFrozen = false; // nowo dodane

    public static volatile double ghostSpeedMultiplier = 1.0;
    public static final Object ghostSpeedLock = new Object();
    public static GhostAnimation[] allGhosts;

    public GhostAnimation(int startRow, int startCol, BufferedImage spriteSheet, MapElement ghostType,
                          long animationTime, MapElement[][] gameboard) {
        this.currentRow = startRow;
        this.currentColumn = startCol;
        this.ghostType = ghostType;
        this.animationTime = animationTime;
        this.gameboard = gameboard;
        this.lastAnimationUpdateTime = System.currentTimeMillis();
        this.lasyMovementUpdateTime = System.currentTimeMillis();

        int frameCount = 2;
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();

        animationFrame = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            animationFrame[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }

        synchronized (gameboard) {
            gameboard[currentRow][currentColumn] = ghostType;
        }

        randomWayToGo();
    }

    public void update() {
        if (isFrozen) return; // duch zamrożony — nie rusza się

        long now = System.currentTimeMillis();

        // Animacja
        if (now - lastAnimationUpdateTime >= animationTime) {
            currentAnimationIndex = (currentAnimationIndex + 1) % animationFrame.length;
            lastAnimationUpdateTime = now;
        }

        // boosty co 5 s, 25%
        if (now - lastTryToGenerateBoost >= 5000) {
            if (gameboard[currentRow][currentColumn] == ghostType && ((int) (Math.random() * 4) + 1) == 1) {
                previousBoardElement = chooseRandomBooster();
            }
            lastTryToGenerateBoost = now;
        }

        long baseDelay = 500;
        if (now - lasyMovementUpdateTime >= (long) (baseDelay * ghostSpeedMultiplier)) {
            int newRow = currentRow + directionRow;
            int newCol = currentColumn + directionCol;

            synchronized (gameboard) {
                if (isValidMove(newRow, newCol)) {

                    if (gameboard[newRow][newCol] == MapElement.pacman) {
                        if (playerMove.ghostCanBite) {
                            ScoreInGame.addPoints(1000);
                        } else {
                            ScoreInGame.loseLife();
                            if (ScoreInGame.getLives() < 1) {
                                Game.gameOver();
                            }
                        }
                    }

                    MapElement nextElement = gameboard[newRow][newCol];
                    gameboard[currentRow][currentColumn] = previousBoardElement;

                    currentRow = newRow;
                    currentColumn = newCol;

                    if (nextElement != MapElement.pacman) {
                        previousBoardElement = nextElement;
                    }

                    gameboard[currentRow][currentColumn] = ghostType;

                } else {
                    randomWayToGo();
                }
            }

            lasyMovementUpdateTime = now;
        }
    }


    public BufferedImage getCurrentFrame() {
        return animationFrame[currentAnimationIndex];
    }

    private boolean isValidMove(int row, int column) {
        return row >= 0 && row < gameboard.length &&
                column >= 0 && column < gameboard[0].length &&
                gameboard[row][column] != MapElement.wall &&
                gameboard[row][column] != MapElement.redGhost &&
                gameboard[row][column] != MapElement.pinkGhost &&
                gameboard[row][column] != MapElement.blueGhost;
    }

    private void randomWayToGo() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int index = (int) (Math.random() * directions.length);
        int[] dir = directions[index];
        directionRow = dir[0];
        directionCol = dir[1];
    }

    private MapElement chooseRandomBooster() {
        MapElement[] boosts = {
                MapElement.apple,
                MapElement.blueberry,
                MapElement.cherry,
                MapElement.strawberry,
                MapElement.star
        };
        int index = (int) (Math.random() * boosts.length);
        return boosts[index];
    }

    public static void sendGhostsToSpawn() {
        if (allGhosts == null) return;

        for (GhostAnimation ghost : allGhosts) {
            synchronized (ghost.gameboard) {
                ghost.gameboard[ghost.currentRow][ghost.currentColumn] = ghost.previousBoardElement;
                ghost.currentRow = 1;
                ghost.currentColumn = 1;
                ghost.previousBoardElement = MapElement.street;
                ghost.gameboard[ghost.currentRow][ghost.currentColumn] = ghost.ghostType;
            }
        }
    }

    public void freeze(boolean freeze) {
        this.isFrozen = freeze;
    }
}
