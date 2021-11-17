package connect4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends JPanel {
    private final int numOfRows = 6;
    private final int numOfColumns = 7;
    private final List<Cell> cells = new ArrayList<>();
    private final String[][] cellValuesArray = new String[numOfRows][numOfColumns];
    private boolean isPlayerXTurn = true;
    private final StatusBar statusBar;
    private boolean gameOver = false;

    public Board() {
        this.setSize(675, 675);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        JPanel jCells = new JPanel();
        jCells.setSize(675, 650);
        jCells.setVisible(true);
        jCells.setLayout(new GridLayout(numOfRows, numOfColumns));
        createCells();
        for (Cell cell: cells) {
            jCells.add(cell);
        }
        this.add(jCells, BorderLayout.CENTER);

        statusBar = new StatusBar(this);
        this.add(statusBar, BorderLayout.SOUTH);

        for (String[] array: cellValuesArray) {
            Arrays.fill(array, " ");
        }
    }

    private void createCells() {
        for (int i = 6; i > 0; i--) {
            for (char j = 'A'; j < 'H'; j++) {
                cells.add(new Cell(this, j + "" + i));
            }
        }
    }

    void resetBoard() {
        for (Cell cell: cells) {
            cell.resetCell();
        }
        isPlayerXTurn = true;
        gameOver = false;
        for (String[] array: cellValuesArray) {
            Arrays.fill(array, " ");
        }
    }

    void setElementInCorrectLocationIfValidMove(Cell clickedCell) {
        clickedCell.setText(getPlayerTurn());
        /* int column = cells.indexOf(clickedCell) % numOfColumns;
        for (int row = numOfRows - 1; row >= 0; row--) {
            Cell cellToUpdate = cells.get(row * numOfColumns + column);
            if(cellToUpdate.getText().isBlank()) {
                cellToUpdate.setText(getPlayerTurn());
                checkBoardStatus(cellToUpdate);
                break;
            }
        }
         */
    }

    private String getPlayerTurn() {
        String temp = isPlayerXTurn ? "X" : "O";
        isPlayerXTurn = !isPlayerXTurn;
        return temp;
    }

    void checkBoardStatus(Cell updatedCell) {
        int index = cells.indexOf(updatedCell);
        int row = index / numOfColumns;
        int column = index % numOfColumns;
        boolean isBoardFull = true;

        cellValuesArray[row][column] = updatedCell.getText();

        for (String[] array: cellValuesArray) {
            for (String s: array) {
                if (s.isBlank()) {
                    isBoardFull = false;
                }
            }
        }

        setBoardStatus(isBoardFull, row, column);
    }

    private void setBoardStatus(boolean isBoardFull, int row, int column) {
        statusBar.setStatus(
                checkIfWin("X", row, column) ?
                        BoardStatus.X_WINS :
                        checkIfWin("O", row, column) ?
                                BoardStatus.O_WINS :
                                isBoardFull ?
                                        BoardStatus.DRAW :
                                        BoardStatus.GAME_IN_PROGRESS);
    }

    private boolean checkIfWin(String element, int row, int column) {
        InARowUtils<String> inARowUtils = new InARowUtils<>(cellValuesArray, element);
        int numInARow = 4;
        List<int[][]> allWinningCoordinates = inARowUtils.findAllDirections(numInARow, row, column);

        if (!allWinningCoordinates.isEmpty()) {
            gameOver = true;
            highlightWinningMove(allWinningCoordinates);
        }

        return gameOver;
    }

    private void highlightWinningMove(List<int[][]> allWinningCoordinates) {
        List<Cell> winningCells = new ArrayList<>();

        for (int[][] oneSetWinningCoordinates: allWinningCoordinates) {
            for (int[] oneCellCoordinates: oneSetWinningCoordinates) {
                winningCells.add(cells.get(numOfColumns * oneCellCoordinates[0] + oneCellCoordinates[1]));
            }
        }

        winningCells.forEach(cell -> cell.setBackground(Color.CYAN));
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
