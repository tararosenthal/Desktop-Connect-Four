import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.Objects;

public class Connect4Test extends StageTest {
    static boolean player1 = true;

    @DynamicTest
    CheckResult test1() {
        TestedProgram main = new TestedProgram();
        String output = main.start().strip().toLowerCase();
        if (!gameBoardPrinted(output)) {
            return CheckResult.wrong("Make sure to label the columns of your game board 1-7.");
        }
        String[][] gameBoard = getGameBoard(output);
        if (!isBoardEmpty(gameBoard)) {
            return CheckResult.wrong("Make sure to print '_' for all empty spaces on the board.");
        }
        if (checkPlayer(output)) {
            return CheckResult.wrong("Make sure your program requests Player 1 to enter move first.");
        }
        String[] checkErrors = {"g", "20", "2 0", "1 f", "0", "8"};
        for (String string: checkErrors) {
            checkErrors(main.execute(string).strip().toLowerCase());
        }

        String[][] testBoard = createTestBoard();
        for (int i = 0; i < 7; i++) {
            output = main.execute("3").strip().toLowerCase();
            if (i < 6) {
                gameBoard = getGameBoard(output);
                updateTestBoard(testBoard, 3);
                compareBoards(gameBoard, testBoard);
                player1 = !player1;
            }
        }
        checkFull(output);
        player1 = true;
        if (checkPlayer(output)) {
            return CheckResult.wrong("Make sure your program requests additional input from the same player " +
                    "after invalid or full column input is entered.");
        }
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test2() {
        String[] checkHorizontalO = {"1", "2", "3", "4", "5", "6", "7", "1", "2", "1",
                "3", "1", "4", "7", "5"};
        TestedProgram main = new TestedProgram();
        checkWinner(main, checkHorizontalO);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test3() {
        String[] checkHorizontalX = {"1", "2", "1", "3", "1", "4", "7", "5"};
        TestedProgram main = new TestedProgram();
        checkWinner(main, checkHorizontalX);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test4() {
        String[] checkVerticalO = {"6", "7", "6", "7", "6", "7", "6"};
        TestedProgram main = new TestedProgram();
        checkWinner(main, checkVerticalO);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test5() {
        String[] checkVerticalX = {"3", "4", "3", "3", "4", "3", "4", "3", "4", "3"};
        TestedProgram main = new TestedProgram();
        checkWinner(main, checkVerticalX);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test6() {
        String[] checkDiagonalO = {"3", "4", "4", "5", "5", "6", "5", "6", "6", "1", "6"};
        TestedProgram main = new TestedProgram();
        checkWinner(main, checkDiagonalO);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test7() {
        TestedProgram main = new TestedProgram();
        String[] checkDiagonalX = {"7", "7", "6", "6", "5", "6", "5", "4", "5", "5", "4",
                "4", "4", "4"};
        checkWinner(main, checkDiagonalX);
        return CheckResult.correct();
    }

    private static void checkWinner(TestedProgram main, String[] winCondition) {
        String output = main.start().strip().toLowerCase();
        String[][] gameBoard = getGameBoard(output);
        String[][] testBoard = createTestBoard();
        player1 = true;
        int length = winCondition.length;

        for (String string: winCondition) {
            output = main.execute(string).strip().toLowerCase();
            gameBoard = getGameBoard(output);
            updateTestBoard(testBoard, Integer.parseInt(string));
            compareBoards(gameBoard, testBoard);
            if (--length > 0) {
                player1 = !player1;
                if (checkPlayer(output)) {
                    throw new WrongAnswer("Make sure to alternate player turn properly. Request player 1 " +
                            "or player 2 to input column depending on turn.");
                }
            }
        }

        if (checkPlayer(output) && (!output.contains("win") || !output.contains("won"))) {
            throw new WrongAnswer("Make sure your program prints \"Player X wins!\" after a player gets four in a row" +
                    " where X is 1 or 2 depending on who won.");
        }
        if (!main.isFinished()) {
            throw new WrongAnswer("Make sure to end program after game ends.");
        }
    }

    private static void checkErrors(String output) {
        if (!output.contains("invalid") || output.contains("full") || output.contains("filled")) {
            throw new WrongAnswer("Make sure you print an error statement containing the word " +
                    "\"invalid\" for invalid input. Should be a different error statement than when column is full.");
        }

        if (!output.contains("player 1") && !output.contains("player1")) {
            throw new WrongAnswer("Make sure your program requests additional input from the same player " +
                    "after invalid input is entered.");
        }
    }

    private static void checkFull(String output) {
        if (!output.contains("full") && !output.contains("filled")) {
            throw new WrongAnswer("Make sure to print an error statement containing the word \"full\" " +
                    "if user selects a column that is already full. This error statement should be " +
                    "different than with invalid input.");
        }
    }

    private static boolean checkPlayer(String output) {
        int player = player1 ? 1 : 2;
        int other = player1 ? 2 : 1;
        return !output.contains("player " + player) && !output.contains("player" + player)
                || output.contains("player " + other) || output.contains("player" + other);
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
        String piece = player1 ? "o" : "x";
        for (int i = 5; i >= 0; i--) {
            if (Objects.equals(board[i][column], "_")) {
                board[i][column] = piece;
                break;
            }
        }
    }

    private static void compareBoards(String[][] gameBoard, String[][] testBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (!Objects.equals(gameBoard[i][j], testBoard[i][j])) {
                    throw new WrongAnswer("Make sure to respond to user input correctly. Change '_' to the letter " +
                            "'O' or 'X', depending on player turn, in chosen column starting with the first available " +
                            "row from the bottom. Make sure all other parts of the board are not changed.");
                }
            }
        }
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
