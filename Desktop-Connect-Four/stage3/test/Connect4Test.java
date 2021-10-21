import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.Objects;

public class Connect4Test extends StageTest {
    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram();
        String output = main.start().strip().toLowerCase();
        if (!gameBoardPrinted(output)) {
            return CheckResult.wrong("Make sure to label the columns of your game board 1-7.");
        }
        String[][] gameBoard = getGameBoard(output);
        if (!isBoardEmpty(gameBoard)) {
            return CheckResult.wrong("Make sure to print '_' for all empty spaces on the board.");
        }
        if (!output.contains("column")) {
            return CheckResult.wrong("Make sure to request users input column number.");
        }

        String[] checkErrors = {"g", "20", "2 0", "1 f", "0", "8"};
        for (String string: checkErrors) {
            checkErrors(main.execute(string).strip().toLowerCase());
        }

        String[][] testBoard = createTestBoard();
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 8; j++) {
                output = main.execute(Integer.toString(j)).strip().toLowerCase();
                gameBoard = getGameBoard(output);
                updateTestBoard(testBoard, j);
                if (!compareBoards(gameBoard, testBoard)) {
                    return CheckResult.wrong("Make sure to respond to user input correctly. Change '_' to the letter 'O' in " +
                            "chosen column starting with the first available row from the bottom." +
                            " Make sure all other parts of the board are not changed.");
                }
                if (i < 5) {
                    if (!output.contains("column")) {
                        return CheckResult.wrong("Make sure to request users input column number.");
                    }
                } else if (j == 3) {
                    output = main.execute("3").strip().toLowerCase();
                    if (!output.contains("full") && !output.contains("filled")) {
                        return CheckResult.wrong("Make sure to print an error statement containing the word \"full\" " +
                                "if user selects a column that is already full. This error statement should be " +
                                "different than with invalid input.");
                    }
                }
            }
        }

        if (!output.contains("game over")) {
            return CheckResult.wrong("Make sure your program prints \"Game over\" after the last turn.");
        }
        if (!main.isFinished()) {
            return CheckResult.wrong("Make sure to end program after game ends.");
        }
        return CheckResult.correct();
    }

    private static void checkErrors(String output) {
        if (!output.contains("invalid") || output.contains("full") || output.contains("filled")) {
            throw new WrongAnswer("Make sure you print an error statement containing the word " +
                    "\"invalid\" for invalid input. Should be a different error statement than when column is full.");
        }
    }

    private static String[][] createTestBoard() {
        String[][] board = new String[6][7];
        for (int i = 0; i < 6; i++) {
            Arrays.fill(board[i], "_");
        }
        return board;
    }

    private static void updateTestBoard(String[][] board, int column) {
        column -= 1;
        for (int i = 5; i >= 0; i--) {
            if (Objects.equals(board[i][column], "_")) {
                board[i][column] = "o";
                break;
            }
        }
    }

    private static boolean compareBoards(String[][] gameBoard, String[][] testBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (!Objects.equals(gameBoard[i][j], testBoard[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBoardEmpty(String[][] board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (!Objects.equals(board[i][j], "_")) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String[][] getGameBoard(String output) {
        String[][] board = new String[6][7];
        String[] outputByLine = output.split("\n");
        WrongAnswer wrongAnswer = new WrongAnswer("Can't parse game board. Make sure to format like in examples. " +
                "Make sure board is printed at the start and after each turn.");

        try {
            int startIndex = 0;
            while (!gameBoardPrinted(outputByLine[startIndex])) {
                startIndex++;
                if (startIndex > 1000) {
                    throw wrongAnswer;
                }
            }
            startIndex++;

            for (int i = 0; i < 6; i++) {
                String temp = outputByLine[startIndex++].strip();
                String[] outputByCharacter = temp.split(" ");
                if (outputByCharacter.length != 7) {
                    throw wrongAnswer;
                }
                board[i] = outputByCharacter;
            }
        } catch (IndexOutOfBoundsException e) {
            throw wrongAnswer;
        }
        return board;
    }

    private static boolean gameBoardPrinted(String output) {
        return  (output.contains("1") && output.contains("2")
                && output.contains("3") && output.contains("4")
                && output.contains("5") && output.contains("6")
                && output.contains("7"));
    }
}
