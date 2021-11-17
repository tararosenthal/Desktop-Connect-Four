package connect4;
import javax.swing.*;
import java.awt.*;

public class Connect4 extends JFrame {
    public Connect4() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(675, 705);
        setLayout(new BorderLayout());
        setVisible(true);
        setTitle("Connect 4");

        Board board = new Board();
        add(board, BorderLayout.CENTER);
    }
}


