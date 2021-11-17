package connect4;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    public Cell(Board board, String label) {
        this.setName("Button" + label);
        this.setText(" ");
        //this.setBackground(Color.LIGHT_GRAY);
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setFocusPainted(false);
        this.addActionListener(actionEvent ->
        {if(!board.isGameOver()) {
            if(this.getText().isBlank()) {
                board.setElementInCorrectLocationIfValidMove(this);
            }
        }});
    }

    public void resetCell() {
        this.setText(" ");
        this.setBackground(Color.LIGHT_GRAY);
    }
}
