package plugin;

import lombok.Data;

import javax.swing.*;
import java.awt.event.ActionListener;

@Data
public class AnswersList {

    private JPanel contentHolder;
    private JButton backButton;
    private JScrollPane scrollPane;
    private JPanel holder;

    public void setOnBackClickedListener(ActionListener actionListener) {

        backButton.addActionListener(actionListener);
    }
}
