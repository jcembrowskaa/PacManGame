public class Boosts {

    public static void activateBoost(MapElement type, playerMove pacman) {
        switch (type) {

            case MapElement.apple -> timeEffects(
                    () -> pacman.ghostCanBite = true,
                    () -> pacman.ghostCanBite = false
            );

            case MapElement.cherry -> stopGhostsTemporarily(5000);

            case MapElement.star -> timeEffects(
                    () -> {
                        pacman.speedPacman *= 1.4;
                        pacman.refreshSpeed();
                    },
                    () -> {
                        pacman.speedPacman /= 1.4;
                        pacman.refreshSpeed();
                    }
            );

            case MapElement.blueberry -> timeEffects(
                    () -> pacman.pointsX3 = true,
                    () -> pacman.pointsX3 = false
            );

            case MapElement.strawberry -> GhostAnimation.sendGhostsToSpawn();
        }
    }

    private static void timeEffects(Runnable startEffect, Runnable endEffect) {
        startEffect.run();
        new Thread(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            endEffect.run();
        }).start();
    }

    private static void stopGhostsTemporarily(int duration) {
        for (GhostAnimation ghost : GhostAnimation.allGhosts) {
            ghost.freeze(true);
        }

        new Thread(() -> {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (GhostAnimation ghost : GhostAnimation.allGhosts) {
                ghost.freeze(false);
            }
        }).start();
    }
}
