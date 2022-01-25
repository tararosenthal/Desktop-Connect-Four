import org.assertj.swing.fixture.JButtonFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import connect4.Connect4;

import javax.swing.JButton;
import java.awt.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import static java.util.stream.IntStream.range;
import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;




class OsCheck {

    public static void main(String[] args) {
        System.out.println(
                System.getProperty(
                                "os.name", "generic")
                        .toLowerCase(Locale.ENGLISH)
        );
    }

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    };

    // cached result of OS detection
    protected static OSType detectedOS;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty(
                            "os.name", "generic")
                    .toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac"))
                    || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}

public class Connect4Test extends SwingTest {
    private static final String EMPTY_CELL = " ";
    private static final String MARK_X = "X";
    private static final String MARK_O = "O";
    private static final int NUM_OF_ROWS = 6;
    private static final int NUM_OF_COLUMNS = 7;
    private static int playerCount = 0;

    public Connect4Test() {
        super(new Connect4());
    }

    @SwingComponent
    private JButtonFixture buttonA1;
    @SwingComponent
    private JButtonFixture buttonA2;
    @SwingComponent
    private JButtonFixture buttonA3;
    @SwingComponent
    private JButtonFixture buttonA4;
    @SwingComponent
    private JButtonFixture buttonA5;
    @SwingComponent
    private JButtonFixture buttonA6;
    @SwingComponent
    private JButtonFixture buttonB1;
    @SwingComponent
    private JButtonFixture buttonB2;
    @SwingComponent
    private JButtonFixture buttonB3;
    @SwingComponent
    private JButtonFixture buttonB4;
    @SwingComponent
    private JButtonFixture buttonB5;
    @SwingComponent
    private JButtonFixture buttonB6;
    @SwingComponent
    private JButtonFixture buttonC1;
    @SwingComponent
    private JButtonFixture buttonC2;
    @SwingComponent
    private JButtonFixture buttonC3;
    @SwingComponent
    private JButtonFixture buttonC4;
    @SwingComponent
    private JButtonFixture buttonC5;
    @SwingComponent
    private JButtonFixture buttonC6;
    @SwingComponent
    private JButtonFixture buttonD1;
    @SwingComponent
    private JButtonFixture buttonD2;
    @SwingComponent
    private JButtonFixture buttonD3;
    @SwingComponent
    private JButtonFixture buttonD4;
    @SwingComponent
    private JButtonFixture buttonD5;
    @SwingComponent
    private JButtonFixture buttonD6;
    @SwingComponent
    private JButtonFixture buttonE1;
    @SwingComponent
    private JButtonFixture buttonE2;
    @SwingComponent
    private JButtonFixture buttonE3;
    @SwingComponent
    private JButtonFixture buttonE4;
    @SwingComponent
    private JButtonFixture buttonE5;
    @SwingComponent
    private JButtonFixture buttonE6;
    @SwingComponent
    private JButtonFixture buttonF1;
    @SwingComponent
    private JButtonFixture buttonF2;
    @SwingComponent
    private JButtonFixture buttonF3;
    @SwingComponent
    private JButtonFixture buttonF4;
    @SwingComponent
    private JButtonFixture buttonF5;
    @SwingComponent
    private JButtonFixture buttonF6;
    @SwingComponent
    private JButtonFixture buttonG1;
    @SwingComponent
    private JButtonFixture buttonG2;
    @SwingComponent
    private JButtonFixture buttonG3;
    @SwingComponent
    private JButtonFixture buttonG4;
    @SwingComponent
    private JButtonFixture buttonG5;
    @SwingComponent
    private JButtonFixture buttonG6;
    @SwingComponent
    private JButtonFixture buttonReset;

    private static final List<JButton> buttons = new ArrayList<>();
    private static final List<JButtonFixture> buttonFixtures = new ArrayList<>();
    private static String[][] expectedArray;

    @DynamicTest(feedback = "Cells should be visible")
    CheckResult test1() {
        Map<String, JButtonFixture> cells = cells();
        cells.forEach((label, button) -> {
            requireVisible(button);
            buttons.add(button.target());
            buttonFixtures.add(button);
        });
        return correct();
    }

    @DynamicTest(feedback = "Cells should be enabled")
    CheckResult test2() {
        buttonFixtures.forEach(this::requireEnabled);
        return correct();
    }

    @DynamicTest(feedback = "All cells should be empty before the game starts")
    CheckResult test3() {
        buttonFixtures.forEach(button -> button.requireText(EMPTY_CELL));
        return correct();
    }

    private int[] cols;
    private int[] rows;

    @DynamicTest(feedback = "The board should have exactly three rows and columns")
    CheckResult test4() {
        cols = buttons.stream().mapToInt(JButton::getX).distinct().sorted().toArray();
        rows = buttons.stream().mapToInt(JButton::getY).distinct().sorted().toArray();

        assertEquals(7, cols.length,
                "The board should have exactly 7 columns. "
                        + "The column coordinates are {0}, "
                        + "the buttons have {1} different column coordinates",
                Arrays.toString(cols), cols.length);

        assertEquals(6, rows.length,
                "The board should have exactly 6 rows. "
                        + "The row coordinates are {0}, "
                        + "The buttons have {0} different row coordinates",
                Arrays.toString(rows), rows.length);

        return correct();
    }

    private static final String[] ROW_NAME = new String[]{"sixth", "fifth", "fourth", "third", "second", "first"};
    private static final String[] COL_NAME = new String[]{"first", "second", "third", "fourth", "fifth", "sixth", "seventh"};

    @DynamicTest(feedback = "The buttons are incorrectly placed on the board")
    CheckResult test5() {
        range(0, NUM_OF_ROWS * NUM_OF_COLUMNS).forEach(index -> {

            assertEquals(rows[index / NUM_OF_COLUMNS], buttons.get(index).getY(),
                    "The button {0} should be located in the {1} row, with the bottom " +
                            "row being the first row",
                    buttons.get(index).getName(), ROW_NAME[index / NUM_OF_COLUMNS]);

            assertEquals(cols[index % NUM_OF_COLUMNS], buttons.get(index).getX(),
                    "The button {0} should be located in the {1} column, with the leftmost " +
                            "column being the first column",
                    buttons.get(index).getName(), COL_NAME[index % NUM_OF_COLUMNS]);
        });

        return correct();
    }

    @DynamicTest(feedback = "Add a JButton with the name of 'ButtonReset' and enable it")
    CheckResult test6() {
        buttonReset.requireEnabled();
        return correct();
    }

    @DynamicTest(feedback = "After the first click on the A1 cell, this cell should contain the X symbol.")
    CheckResult test7() {
        try {
            buttonA1.click();
            buttonA1.requireText(MARK_X);
            return correct();
        } catch (Throwable ex) {
            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
                return wrong(
                        "Please, make sure that Intellij Idea has access to control your mouse and keyboard: \n" +
                                "go to System Preferences -> Security & Privacy -> Accessibility\n" +
                                "and grant Intellij IDEA access to control your computer.");
            }
            throw ex;
        }
    }

    @DynamicTest(feedback = "After the second click on the A2 cell, this cell should contain the O symbol.")
    CheckResult test8() {
        buttonA2.click();
        buttonA2.requireText(MARK_O);
        return correct();
    }

    @DynamicTest(feedback = "The Reset button should clear the board")
    CheckResult test9() {
        buttonReset.click();
        buttonFixtures.forEach(cell -> cell.requireText(EMPTY_CELL));
        return correct();
    }

    @DynamicTest(feedback = "Clicking on a cell in an already full column should do nothing")
    CheckResult test10() {
        initializeExpectedArray();
        for (int clickCount = 0; clickCount < 8; clickCount++) {
            buttonA6.click();
            updateExpectedArrayFromButtonClicked(buttonA6.target());
            String[][] actualArray = getActualArray();
            for (int i = 0; i < NUM_OF_ROWS; i++) {
                for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                    assertEquals(expectedArray[i][j], actualArray[i][j],
                            "The text for the cell {0}{1} should be \"{2}\" " +
                                    "but is instead \"{3}\"", (char) ('A' + j), NUM_OF_ROWS - i, expectedArray[i][j], actualArray[i][j]);
                }
            }
        }
        return correct();
    }

    @DynamicTest(feedback = "Incorrect state of board during play or unable to identify win condition correctly")
    CheckResult test11() {
            List<int[]> winConditions = winConditions();
            for (int[] winCondition:
                    winConditions) {
                buttonReset.click();
                initializeExpectedArray();
                for (int columnToClick :
                        winCondition) {
                    for (JButtonFixture button :
                            buttonFixtures) {
                        if (getColumnFromJButton(button.target()) == columnToClick) {
                            button.click();
                            updateExpectedArrayFromButtonClicked(button.target());
                            String[][] actualArray = getActualArray();
                            for (int i = 0; i < NUM_OF_ROWS; i++) {
                                for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                                    assertEquals(expectedArray[i][j], actualArray[i][j],
                                            "The text for the cell {0}{1} should be \"{2}\" " +
                                                    "but is instead \"{3}\"", (char) ('A' + j), NUM_OF_ROWS - i, expectedArray[i][j], actualArray[i][j]);
                                }
                            }
                            break;
                        }
                    }
                }
                checkColorOfCells(winConditions.indexOf(winCondition));
            }
            return correct();
    }

    @DynamicTest(feedback = "Clicking on any cell after the game has been won (before resetting) should do nothing")
    CheckResult test12() {
        buttonFixtures.forEach(button -> {
            button.click();
            String[][] actualArray = getActualArray();
            for (int i = 0; i < NUM_OF_ROWS; i++) {
                for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                    assertEquals(expectedArray[i][j], actualArray[i][j],
                            "The text for the cell {0}{1} should be \"{2}\" " +
                                    "but is instead \"{3}\"",  (char) ('A' + j), NUM_OF_ROWS - i, expectedArray[i][j], actualArray[i][j]);
                }
            }
        });
        return correct();
    }

    private static void assertEquals(
            final Object expected,
            final Object actual,
            final String error,
            final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = MessageFormat.format(error, args);
            throw new WrongAnswer(feedback);
        }
    }

    private Map<String, JButtonFixture> cells() {
        return mapOf(
                "A6", buttonA6, "B6", buttonB6, "C6", buttonC6, "D6", buttonD6, "E6", buttonE6, "F6", buttonF6, "G6", buttonG6,
                "A5", buttonA5, "B5", buttonB5, "C5", buttonC5, "D5", buttonD5, "E5", buttonE5, "F5", buttonF5, "G5", buttonG5,
                "A4", buttonA4, "B4", buttonB4, "C4", buttonC4, "D4", buttonD4, "E4", buttonE4, "F4", buttonF4, "G4", buttonG4,
                "A3", buttonA3, "B3", buttonB3, "C3", buttonC3, "D3", buttonD3, "E3", buttonE3, "F3", buttonF3, "G3", buttonG3,
                "A2", buttonA2, "B2", buttonB2, "C2", buttonC2, "D2", buttonD2, "E2", buttonE2, "F2", buttonF2, "G2", buttonG2,
                "A1", buttonA1, "B1", buttonB1, "C1", buttonC1, "D1", buttonD1, "E1", buttonE1, "F1", buttonF1, "G1", buttonG1);
    }

    private List<int[]> winConditions() {
        List<int[]> winConditions = new ArrayList<>();

        int[] checkHorizontalX = {0, 1, 2, 3, 4, 5, 6, 0, 1, 0,
                2, 0, 3, 6, 4};
        int[] checkHorizontalO = {0, 1, 0, 2, 0, 3, 6, 4};
        int[] checkVerticalX = {5, 6, 5, 6, 5, 6, 5};
        int[] checkVerticalO = {2, 3, 2, 2, 3, 2, 3, 2, 3, 2};
        int[] checkDiagonalX = {2, 3, 3, 4, 4, 5, 4, 5, 5, 0, 5};
        int[] checkDiagonalO = {6, 6, 5, 5, 4, 5, 4, 3, 4, 4, 3,
                3, 3, 3};

        winConditions.add(checkHorizontalX);
        winConditions.add(checkHorizontalO);
        winConditions.add(checkVerticalX);
        winConditions.add(checkVerticalO);
        winConditions.add(checkDiagonalX);
        winConditions.add(checkDiagonalO);

        return winConditions;
    }

    private void checkColorOfCells(int indexOfWinCondition) {
        Color baselineColor;
        Color winningColor;
        List<JButtonFixture> winningCells = new ArrayList<>();

        switch (indexOfWinCondition) {
            case 0:
                baselineColor = buttonA1.target().getBackground();
                winningColor = buttonB2.target().getBackground();
                winningCells.add(buttonB2);
                winningCells.add(buttonC2);
                winningCells.add(buttonD2);
                winningCells.add(buttonE2);
                break;
            case 1:
                baselineColor = buttonA1.target().getBackground();
                winningColor = buttonB1.target().getBackground();
                winningCells.add(buttonB1);
                winningCells.add(buttonC1);
                winningCells.add(buttonD1);
                winningCells.add(buttonE1);
                break;
            case 2:
                baselineColor = buttonG1.target().getBackground();
                winningColor = buttonF1.target().getBackground();
                winningCells.add(buttonF1);
                winningCells.add(buttonF2);
                winningCells.add(buttonF3);
                winningCells.add(buttonF4);
                break;
            case 3:
                baselineColor = buttonC1.target().getBackground();
                winningColor = buttonC6.target().getBackground();
                winningCells.add(buttonC6);
                winningCells.add(buttonC5);
                winningCells.add(buttonC4);
                winningCells.add(buttonC3);
                break;
            case 4:
                baselineColor = buttonD1.target().getBackground();
                winningColor = buttonC1.target().getBackground();
                winningCells.add(buttonC1);
                winningCells.add(buttonD2);
                winningCells.add(buttonE3);
                winningCells.add(buttonF4);
                break;
            case 5:
                baselineColor = buttonG1.target().getBackground();
                winningColor = buttonG2.target().getBackground();
                winningCells.add(buttonG2);
                winningCells.add(buttonF3);
                winningCells.add(buttonE4);
                winningCells.add(buttonD5);
                break;
            default:
                throw new IndexOutOfBoundsException("Error in test checking color for win conditions");
        }

        for (JButtonFixture button:
             buttonFixtures) {
            if (winningCells.contains(button)) {
                assertEquals(winningColor, button.target().getBackground(), "{0} should have the winning color: {1} " +
                        "but is instead has the color {2}", button.target().getName(), winningColor, button.target().getBackground());
            } else {
                assertEquals(baselineColor, button.target().getBackground(), "{0} should have the baseline color: " +
                        "{1} but instead has the color {2}", button.target().getName(), baselineColor, button.target().getBackground());
            }
        }
    }

    private static Map<String, JButtonFixture> mapOf(Object... keyValues) {
        Map<String, JButtonFixture> map = new LinkedHashMap<>();

        for (int index = 0; index < keyValues.length / 2; index++) {
            map.put((String) keyValues[index * 2], (JButtonFixture) keyValues[index * 2 + 1]);
        }

        return map;
    }

    private static String updatePlayer() {
        return playerCount++ % 2 == 0 ? MARK_X : MARK_O;
    }


    private static void updateExpectedArrayFromButtonClicked(JButton button) {
        int column = getColumnFromJButton(button);
        for (int i = NUM_OF_ROWS - 1; i >= 0; i--) {
            if (Objects.equals(expectedArray[i][column], EMPTY_CELL)) {
                expectedArray[i][column] = updatePlayer();
                break;
            }
        }
    }

    private static void initializeExpectedArray() {
        expectedArray = new String[NUM_OF_ROWS][NUM_OF_COLUMNS];
        for (String[] array: expectedArray) {
            Arrays.fill(array, EMPTY_CELL);
        }
        playerCount = 0;
    }

    private static String[][] getActualArray() {
        String[][] actualArray = new String[NUM_OF_ROWS][NUM_OF_COLUMNS];
        buttons.forEach(button -> {
            actualArray[getRowFromJButton(button)][getColumnFromJButton(button)]
                    = button.getText();
        });
        return actualArray;
    }

    private static int getColumnFromJButton(JButton button) {
        return buttons.indexOf(button) % NUM_OF_COLUMNS;
    }

    private static int getRowFromJButton(JButton button) {
        return buttons.indexOf(button) / NUM_OF_COLUMNS;
    }
}
