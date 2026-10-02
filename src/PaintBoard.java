import javax.swing.*;

public class PaintBoard {
    private final JTable table;
    private final JScrollPane scrollPane;

    public PaintBoard(MapElement[][] gameBoard, pacmanAnimation pacman, GhostAnimation redGhost, GhostAnimation pinkGhost, GhostAnimation blueGhost) {
        int blockSize = 20;

        table = new JTable(new GameboardMatrix(gameBoard));
        table.setRowHeight(blockSize);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(blockSize);
        }

        table.setTableHeader(null);
        table.setDefaultRenderer(Object.class, new GameElementsLoading(pacman, redGhost, pinkGhost, blueGhost));
        scrollPane = new JScrollPane(table);
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
