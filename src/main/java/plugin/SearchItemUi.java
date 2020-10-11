package plugin;

import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBUI;
import model.ParseResult;

import javax.swing.*;
import javax.swing.border.Border;

public class SearchItemUi {

    private JPanel searchToolWindowContent;
    private JLabel questionTitle;
    private JLabel questionDescription;
    private JLabel answerTitle;
    private JLabel answerDescription;

    public SearchItemUi(ParseResult searchItem) {

        setQuestion(searchItem.getQuestion().getBody());
        setAnswer(searchItem.getAnswers().get(0).getBody());
    }

    public SearchItemUi(int i) {

        setLookAndFeel();
        setQuestion(String.valueOf(i));
    }


    public JPanel getContent() {
        return searchToolWindowContent;
    }

    public void setQuestionLabel(String question) {
        questionTitle.setText(question);
    }

    public void setAnswer(String answer) {
        answerDescription.setText("<html>" + answer + "</html>");
    }

    public void setQuestion(String question) {
        questionDescription.setText("<html>" + question + "</html>");
    }

    public void setQuestionTitle(String title) {
        String labelText = String.format("<html><div WIDTH=%d>%s</div></html>", 480, title);
        questionTitle.setText(labelText);
    }

    private void setItemBorder(JPanel panel) {

        Border border = JBUI.Borders.empty(16);
        panel.setBorder(border);
    }

    private void setLookAndFeel() {

        setItemBorder(searchToolWindowContent);
        JBLabel jbLabel = new JBLabel();
        questionTitle.setUI(jbLabel.getUI());

    }

}
