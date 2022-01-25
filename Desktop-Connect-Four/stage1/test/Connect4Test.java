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

import static java.util.stream.IntStream.range;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class Connect4Test extends SwingTest {
    private static final int NUM_OF_ROWS = 6;
    private static final int NUM_OF_COLUMNS = 7;

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


    private final List<JButton> buttons = new ArrayList<>();

    @DynamicTest(feedback = "Buttons should have a name (ButtonA1..ButtonG6), be visible, and have the following labels: 'A1'...'F7'")
    CheckResult test1() {
        Map<String, JButtonFixture> board = mapOf(
                "A6", buttonA6, "B6", buttonB6, "C6", buttonC6, "D6", buttonD6, "E6", buttonE6, "F6", buttonF6, "G6", buttonG6,
                "A5", buttonA5, "B5", buttonB5, "C5", buttonC5, "D5", buttonD5, "E5", buttonE5, "F5", buttonF5, "G5", buttonG5,
                "A4", buttonA4, "B4", buttonB4, "C4", buttonC4, "D4", buttonD4, "E4", buttonE4, "F4", buttonF4, "G4", buttonG4,
                "A3", buttonA3, "B3", buttonB3, "C3", buttonC3, "D3", buttonD3, "E3", buttonE3, "F3", buttonF3, "G3", buttonG3,
                "A2", buttonA2, "B2", buttonB2, "C2", buttonC2, "D2", buttonD2, "E2", buttonE2, "F2", buttonF2, "G2", buttonG2,
                "A1", buttonA1, "B1", buttonB1, "C1", buttonC1, "D1", buttonD1, "E1", buttonE1, "F1", buttonF1, "G1", buttonG1);

        board.forEach((label, button) -> {
            requireVisible(button);
            button.requireText(label);
            buttons.add(button.target());
        });

        return correct();
    }

    private int[] cols;
    private int[] rows;

    @DynamicTest(feedback = "The board should have exactly six rows and seven columns")
    CheckResult test2() {
        cols = buttons.stream().mapToInt(JButton::getX).distinct().sorted().toArray();
        rows = buttons.stream().mapToInt(JButton::getY).distinct().sorted().toArray();

        assertEquals(NUM_OF_COLUMNS, cols.length,
                "The board should have exactly 7 columns. "
                        + "The coordinates for columns are {0}, "
                        + "the buttons have {1} different coordinates for columns",
                Arrays.toString(cols), cols.length);

        assertEquals(NUM_OF_ROWS, rows.length,
                "The board should have exactly 6 rows. "
                        + "The coordinates for rows are {0}, "
                        + "The buttons have {0} different coordinates for rows",
                Arrays.toString(rows), rows.length);

        return correct();
    }

    private static final String[] ROW_NAME = new String[]{"sixth", "fifth", "fourth", "third", "second", "first"};
    private static final String[] COL_NAME = new String[]{"first", "second", "third", "fourth", "fifth", "sixth", "seventh"};

    @DynamicTest(feedback = "The buttons are incorrectly placed on the board")
    CheckResult test3() {
        range(0, NUM_OF_ROWS * NUM_OF_COLUMNS).forEach(index -> {

            assertEquals(rows[index / NUM_OF_COLUMNS], buttons.get(index).getY(),
                    "The button {0} should be located in the {1} row, with the bottom row being the first row",
                    buttons.get(index).getText(), ROW_NAME[index / NUM_OF_COLUMNS]);

            assertEquals(cols[index % NUM_OF_COLUMNS], buttons.get(index).getX(),
                    "The button {0} should be located in the {1} column, with the leftmost column being the first column",
                    buttons.get(index).getText(), COL_NAME[index % NUM_OF_COLUMNS]);
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

    private static Map<String, JButtonFixture> mapOf(Object... keyValues) {
        Map<String, JButtonFixture> map = new LinkedHashMap<>();

        for (int index = 0; index < keyValues.length / 2; index++) {
            map.put((String) keyValues[index * 2], (JButtonFixture) keyValues[index * 2 + 1]);
        }

        return map;
    }
}

