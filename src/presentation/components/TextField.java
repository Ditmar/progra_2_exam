package presentation.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextField extends JTextField implements FocusListener {
    private String placeHolder;

    public TextField(String placeHolder) {
        super(placeHolder);
        this.placeHolder = placeHolder;
        this.config();
        this.setupEvents();
    }

    private void setupEvents() {
        this.addFocusListener(this);
    }

    private void config() {
        this.setSize(260, 40);
        this.setForeground(Color.DARK_GRAY);
        this.setBackground(Color.WHITE);
        this.setCaretColor(Color.BLUE);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().equals(placeHolder)) {
            this.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            this.setText(placeHolder);
        }
    }
}