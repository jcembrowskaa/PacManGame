import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    public static BufferedImage pacmanSpriteBasic;
    public static BufferedImage redGhostImg, pinkGhostImg, blueGhostImg;
    public static BufferedImage apple, blueberry, cherry, strawberry, star;
    public static BufferedImage wall, point, street;

    public static void load() {
        try {
            pacmanSpriteBasic = ImageIO.read(new File("pacmanSpriteBasic.png"));
            redGhostImg = ImageIO.read(new File("redGhost.png"));
            pinkGhostImg = ImageIO.read(new File("pinkGhost.png"));
            blueGhostImg = ImageIO.read(new File("blueGhost.png"));
            apple = ImageIO.read(new File("apple.png"));
            blueberry = ImageIO.read(new File("blueberry.png"));
            cherry = ImageIO.read(new File("cherry.png"));
            strawberry = ImageIO.read(new File("strawberry.png"));
            star = ImageIO.read(new File("star.png"));
            wall = ImageIO.read(new File("wall.png"));
            point = ImageIO.read(new File("point.png"));
            street = ImageIO.read(new File("street.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
