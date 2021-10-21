import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Objects;

public class Connect4Test extends StageTest{
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
        if (!main.isFinished()) {
            return CheckResult.wrong("Make sure to end program after outputting board.");
        }
        return CheckResult.correct();
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
        WrongAnswer wrongAnswer = new WrongAnswer("Can't parse game board. Make sure to format like in examples.");

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

