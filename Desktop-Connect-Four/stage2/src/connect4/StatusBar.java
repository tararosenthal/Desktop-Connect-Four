package connect4;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private final JLabel status = new JLabel();

    public StatusBar(Board board) {
        setSize(675, 10);
        setVisible(true);
        setLayout(new BorderLayout());

        status.setName("LabelStatus");
        setStatus(BoardStatus.GAME_IS_NOT_STARTED);

        JButton reset = new JButton();
        reset.setName("ButtonReset");
        reset.setText("Reset");
        reset.addActionListener(actionEvent -> {
            board.resetBoard();
            setStatus(BoardStatus.GAME_IS_NOT_STARTED);
        });

        //add(status, BorderLayout.LINE_START);
        //add(reset, BorderLayout.LINE_END);
    }

    void setStatus(BoardStatus boardStatus) {
        status.setText(boardStatus.toString());
    }
}
