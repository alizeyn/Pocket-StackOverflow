package plugin;

import lombok.Data;
import model.Answer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

@Data
public class AnswersList {

    private JPanel contentHolder;
    private JButton backButton;
    private JScrollPane scrollPane;
    private JPanel holder;

    public void setOnBackClickedListener(ActionListener actionListener) {

        backButton.addActionListener(actionListener);
    }

    public void updateData(List<Answer> answers) {

        contentHolder.removeAll();
        for (Answer answer :
                answers) {
            AnswerItem answerItem = new AnswerItem();
            answerItem.setAnswer(answer);
            contentHolder.add(answerItem.getContentHolder());
        }
    }
}
