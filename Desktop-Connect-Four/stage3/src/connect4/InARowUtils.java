package connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InARowUtils<T> {
    private final T[][] array;
    private final T element;
    private final int numOfRows;
    private final int numOfColumns;

    public InARowUtils(T[][] array, T element) {
        this.array = array;
        this.element = element;
        this.numOfRows = array.length;
        this.numOfColumns = array[0].length;

        for (T[] innerArray: array) {
            if (innerArray.length != numOfColumns) {
                throw new IndexOutOfBoundsException("2D Array must be rectangular to use InARowUtils");
            }
        }
    }

    public List<int[][]> findAllDirections(int numInARow, int row, int column) {
        List<int[][]> allWinningCoordinates = new ArrayList<>();

        int[][] winningCoordinates = findHorizontal(numInARow, row);
        if (winningCoordinates[0][0] != -1) {
            allWinningCoordinates.add(winningCoordinates);
        }

        winningCoordinates = findVertical(numInARow, column);
        if (winningCoordinates[0][0] != -1) {
            allWinningCoordinates.add(winningCoordinates);
        }

        winningCoordinates = findForwardSlash(numInARow, row, column);
        if (winningCoordinates[0][0] != -1) {
            allWinningCoordinates.add(winningCoordinates);
        }

        winningCoordinates = findBackSlash(numInARow, row, column);
        if (winningCoordinates[0][0] != -1) {
            allWinningCoordinates.add(winningCoordinates);
        }

        return allWinningCoordinates;
    }

    public int[][] findHorizontal(int numInARow, int row) {
        int winningCoordinatesCount = 0;
        int[][] winningCoordinates = new int[numInARow][2];
        for (int column = 0; column < numOfColumns; column++) {
            if (Objects.equals(array[row][column], element)) {
                winningCoordinates[winningCoordinatesCount] = new int[]{row, column};
                winningCoordinatesCount++;
                if (winningCoordinatesCount >= numInARow) {
                    return winningCoordinates;
                }
            } else {
                winningCoordinatesCount = 0;
                winningCoordinates = new int[numInARow][2];
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findVertical(int numInARow, int column) {
        int winningCoordinatesCount = 0;
        int[][] winningCoordinates = new int[numInARow][2];
        for (int row = 0; row < numOfRows; row++) {
            if (Objects.equals(array[row][column], element)) {
                winningCoordinates[winningCoordinatesCount] = new int[]{row, column};
                winningCoordinatesCount++;
                if (winningCoordinatesCount >= numInARow) {
                    return winningCoordinates;
                }
            } else {
                winningCoordinatesCount = 0;
                winningCoordinates = new int[numInARow][2];
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findForwardSlash(int numInARow, int row, int column) {
        int leftLimit = Math.min(numOfRows - 1 - row, column);
        int rightLimit = Math.min(row, numOfColumns - 1 - column);
        int maxShift = leftLimit + rightLimit + 1;

        int shiftedRow = row + leftLimit;
        int shiftedColumn = column - leftLimit;
        int winningCoordinatesCount = 0;
        int[][] winningCoordinates = new int[numInARow][2];

        for (int i = 0; i < maxShift; i++) {
            if (Objects.equals(array[shiftedRow - i][shiftedColumn + i], element)) {
                winningCoordinates[winningCoordinatesCount] = new int[]{shiftedRow - i, shiftedColumn + i};
                winningCoordinatesCount++;
                if (winningCoordinatesCount >= numInARow) {
                    return winningCoordinates;
                }
            } else {
                winningCoordinatesCount = 0;
                winningCoordinates = new int[numInARow][2];
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findBackSlash(int numInARow, int row, int column) {
        int plusLimit = Math.min(numOfRows - 1 - row, numOfColumns - 1 - column);
        int minusLimit = Math.min(row, column);
        int maxShift = plusLimit + minusLimit + 1;

        int shiftedRow = row - minusLimit;
        int shiftedColumn = column - minusLimit;
        int winningCoordinatesCount = 0;
        int[][] winningCoordinates = new int[numInARow][2];

        for (int i = 0; i < maxShift; i++) {
            if (Objects.equals(array[shiftedRow + i][shiftedColumn + i], element)) {
                winningCoordinates[winningCoordinatesCount] = new int[]{shiftedRow + i, shiftedColumn + i};
                winningCoordinatesCount++;
                if (winningCoordinatesCount >= numInARow) {
                    return winningCoordinates;
                }
            } else {
                winningCoordinatesCount = 0;
                winningCoordinates = new int[numInARow][2];
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findAnyHorizontal(int numInARow) {
        for (int row = 0; row < numOfRows; row++) {
            int winningCoordinatesCount = 0;
            int[][] winningCoordinates = new int[numInARow][2];
            for (int column = 0; column < numOfColumns; column++) {
                if (Objects.equals(array[row][column], element)) {
                    winningCoordinates[winningCoordinatesCount] = new int[]{row, column};
                    winningCoordinatesCount++;
                    if (winningCoordinatesCount >= numInARow) {
                        return winningCoordinates;
                    }
                } else {
                    winningCoordinatesCount = 0;
                    winningCoordinates = new int[numInARow][2];
                }
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findAnyVertical(int numInARow) {
        for (int column = 0; column < numOfColumns; column++) {
            int winningCoordinatesCount = 0;
            int[][] winningCoordinates = new int[numInARow][2];
            for (int row = 0; row < numOfRows; row++) {
                if (Objects.equals(array[row][column], element)) {
                    winningCoordinates[winningCoordinatesCount] = new int[]{row, column};
                    winningCoordinatesCount++;
                    if (winningCoordinatesCount >= numInARow) {
                        return winningCoordinates;
                    }
                } else {
                    winningCoordinatesCount = 0;
                    winningCoordinates = new int[numInARow][2];
                }
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }

    public int[][] findAnyDiagonal(int numInARow) {
        for (int row = 0; row < numOfRows; row++) {
            int[][] winningCoordinates = findForwardSlash(numInARow, row, 0);
            if (winningCoordinates[0][0] != -1) {
                return winningCoordinates;
            }
        }
        for (int column = 0; column < numOfColumns; column++) {
            int[][] winningCoordinates = findForwardSlash(numInARow, 0, column);
            if (winningCoordinates[0][0] != -1) {
                return winningCoordinates;
            }
        }
        for (int column = 0; column < numOfColumns; column++) {
            int[][] winningCoordinates = findBackSlash(numInARow, numOfRows - 1, column);
            if (winningCoordinates[0][0] != -1) {
                return winningCoordinates;
            }
        }
        for (int row = 0; row < numOfRows; row++) {
            int[][] winningCoordinates = findBackSlash(numInARow, row, numOfColumns - 1);
            if (winningCoordinates[0][0] != -1) {
                return winningCoordinates;
            }
        }
        return new int[][]{new int[]{-1, -1}};
    }
}
