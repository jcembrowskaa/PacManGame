import java.awt.*;
import java.awt.image.BufferedImage;

public class pacmanAnimation {

    private final BufferedImage[] animation;
    private int currentAnimationIndex = 0;
    private final long animationTime;
    private long lastUpdate;
    private Direction pacmanDirection = Direction.right;

    public pacmanAnimation(BufferedImage images, int animationWidth, int animationHeight, long animationTime) {
        this.animationTime = animationTime;

        int howMuchAnimation = images.getWidth() / animationWidth;
        animation = new BufferedImage[howMuchAnimation];
        for (int i = 0; i < howMuchAnimation; i++) {
            animation[i] = images.getSubimage(i * animationWidth, 0, animationWidth, animationHeight);
        }

        lastUpdate = System.currentTimeMillis();
    }

    public void setDirection(Direction direction) {
        this.pacmanDirection = direction;
    }

    public BufferedImage getRotatedCurrentFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate >= animationTime) {
            currentAnimationIndex = (currentAnimationIndex + 1) % animation.length;
            lastUpdate = currentTime;
        }

        BufferedImage original = animation[currentAnimationIndex];
        int pictureWidth = original.getWidth();
        int pictureHeight = original.getHeight();

        BufferedImage rotated = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        switch (pacmanDirection) {
            case right -> {
            }
            case left -> g2d.rotate(Math.toRadians(180), pictureWidth / 2.0, pictureHeight / 2.0);
            case up -> g2d.rotate(Math.toRadians(270), pictureWidth / 2.0, pictureHeight / 2.0);
            case down -> g2d.rotate(Math.toRadians(90), pictureWidth / 2.0, pictureHeight / 2.0);
        }

        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();
        return rotated;
    }

}
