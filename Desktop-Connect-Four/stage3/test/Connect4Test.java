import org.assertj.swing.fixture.JButtonFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import connect4.Connect4;

import javax.swing.*;
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

    private static final List<JButton> buttons = new ArrayList<>();
    private Map<String, JButtonFixture> cells;
    private static String[][] expectedArray;

    @DynamicTest(feedback = "Cells should be visible")
    CheckResult test1() {
        cells = cells();
        cells.forEach((label, button) -> {
            requireVisible(button);
            buttons.add(button.target());
        });
        return correct();
    }

    @DynamicTest(feedback = "Cells should be enabled")
    CheckResult test2() {
        cells.forEach((label, button) -> requireEnabled(button));
        return correct();
    }

    @DynamicTest(feedback = "All cells should be empty before the game starts")
    CheckResult test3() {
        cells.forEach((label, button) -> button.requireText(EMPTY_CELL));
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
                    "The button {0} should be located in the {1} row, with the bottom row being the first row",
                    buttons.get(index).getName(), ROW_NAME[index / NUM_OF_COLUMNS]);

            assertEquals(cols[index % NUM_OF_COLUMNS], buttons.get(index).getX(),
                    "The button {0} should be located in the {1} column, with the leftmost column being the first column",
                    buttons.get(index).getName(), COL_NAME[index % NUM_OF_COLUMNS]);
        });

        return correct();
    }

    @DynamicTest(feedback = "After clicking on a cell, it should fill the first open cell in that column either X or O, " +
            "starting with X and alternating with every click until the column is full")
    CheckResult test6() {
        initializeExpectedArray();
        try {
            frame.toFront();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            cells.forEach((label, button) -> {
                button.click();
                updateExpectedArrayFromButtonClicked(button.target());
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
