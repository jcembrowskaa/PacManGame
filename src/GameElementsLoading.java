import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameElementsLoading extends DefaultTableCellRenderer {

    private final pacmanAnimation pacman;
    private final GhostAnimation redGhost, pinkGhost, blueGhost;

    public GameElementsLoading(pacmanAnimation pacman, GhostAnimation red, GhostAnimation pink, GhostAnimation blue) {
        this.pacman = pacman;
        this.redGhost = red;
        this.pinkGhost = pink;
        this.blueGhost = blue;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        MapElement element = (MapElement) value;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.Src);

                switch (element) {
                    case wall -> drawImage(g2d, Images.wall);
                    case point -> drawImage(g2d, Images.point);
                    case pacman -> drawImage(g2d, pacman.getRotatedCurrentFrame());
                    case redGhost -> drawImage(g2d, redGhost.getCurrentFrame());
                    case pinkGhost -> drawImage(g2d, pinkGhost.getCurrentFrame());
                    case blueGhost -> drawImage(g2d, blueGhost.getCurrentFrame());
                    case apple -> drawImage(g2d, Images.apple);
                    case blueberry -> drawImage(g2d, Images.blueberry);
                    case cherry -> drawImage(g2d, Images.cherry);
                    case strawberry -> drawImage(g2d, Images.strawberry);
                    case star -> drawImage(g2d, Images.star);
                    case street -> drawImage(g2d, Images.street);
                }

                g2d.dispose();
            }

            private void drawImage(Graphics2D g2d, BufferedImage image) {
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };

        panel.setOpaque(false);
        return panel;
    }
}
