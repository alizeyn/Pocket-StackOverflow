package plugin;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import lombok.extern.slf4j.Slf4j;
import model.Answer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.util.List;

@Slf4j
public class AnswerListView {

    private final JPanel contentHolder;
    private final JBScrollPane scrollPane;
    private final JButton backButton;

    private final TopBackPanel topBackPanel;

    public AnswerListView() {

        topBackPanel = new TopBackPanel();
        backButton = topBackPanel.getBackToQuestionButton();

        contentHolder = new JPanel();

        BoxLayout boxLayout = new BoxLayout(contentHolder, BoxLayout.Y_AXIS);
        contentHolder.setLayout(boxLayout);

        Border border = JBUI.Borders.empty(16);
        contentHolder.setBorder(border);

        scrollPane = new JBScrollPane(contentHolder,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    public JComponent getContent() {
        return scrollPane;
    }

    public void setOnBackClickedListener(ActionListener actionListener) {

        backButton.addActionListener(actionListener);
    }

    public void updateData(List<Answer> answers) {

        contentHolder.removeAll();
        contentHolder.add(topBackPanel.getContentHolder());
        contentHolder.add(new JSeparator());

        for (Answer answer :
                answers) {
            AnswerItem answerItem = new AnswerItem();
            answerItem.setAnswer(answer);
            contentHolder.add(answerItem.getContentHolder());
            contentHolder.add(new JSeparator());
        }
    }

}
