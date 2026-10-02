import javax.swing.table.AbstractTableModel;

public class GameboardMatrix extends AbstractTableModel {

    private final MapElement[][] matrix;

    public GameboardMatrix(MapElement[][] matrix) {
        this.matrix = matrix;
    }

    public static MapElement[][] generateMatrix(int size) {
        if (size < 10 || size > 100) {
            throw new IllegalArgumentException("Size must be between 10 and 100");
        }

        MapElement[][] basicMatrix = baseMatrix(); //podstawowa macierz do kopiowania
        MapElement[][] fullMatrix = new MapElement[size][size];

        for (int i = 0; i < size; i += 9) {
            for (int j = 0; j < size; j += 9) {
                int copyLines = Math.min(10, size - i);
                int copyColumns = Math.min(10, size - j);
                copyBasicMatrix(fullMatrix, basicMatrix, i, j, copyLines, copyColumns);
            }
        }

        //zamienianie wszystkich 'nie scian' na sciany dookola planszy
        for (int i = 0; i < size; i++) {
            if (fullMatrix[i][0] == MapElement.point) fullMatrix[i][0] = MapElement.wall;
            if ( fullMatrix[i][size - 1] == MapElement.point) fullMatrix[i][size - 1] = MapElement.wall;
        }
        for (int j = 0; j < size; j++) {
            if (fullMatrix[0][j] == MapElement.point) fullMatrix[0][j] = MapElement.wall;
            if ( fullMatrix[size - 1][j] == MapElement.point) fullMatrix[size - 1][j] = MapElement.wall;
        }

        return fullMatrix;
    }

    private static void copyBasicMatrix(MapElement[][] fullBoard, MapElement[][] basicMatrix, int linesOutOfScope, int columnsOutOfScope, int copyLines, int copyColumns) {
        for (int i = 0; i < copyLines; i++) {
            for (int j = 0; j < copyColumns; j++) {
                fullBoard[linesOutOfScope + i][columnsOutOfScope + j] = basicMatrix[i][j];
            }
        }
    }

    private static MapElement[][] baseMatrix() {
        return new MapElement[][]{
                {MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall},
                {MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.wall},
                {MapElement.point, MapElement.point, MapElement.wall, MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.wall, MapElement.point, MapElement.wall},
                {MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall, MapElement.point, MapElement.point},
                {MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.wall},
                {MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall},
                {MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point},
                {MapElement.wall, MapElement.point, MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.point, MapElement.wall},
                {MapElement.wall, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.point, MapElement.wall},
                {MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall, MapElement.point, MapElement.wall, MapElement.wall, MapElement.wall}
        };
    }

    @Override
    public int getRowCount() {
        return matrix.length;
    }

    @Override
    public int getColumnCount() {
        return matrix[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return matrix[rowIndex][columnIndex];
    }
}
