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
        answerDescription.setText(getModifiedText(answer));
    }

    public void setQuestion(String question) {
        questionDescription.setText(getModifiedText(question));
    }

    public void setQuestionTitle(String title) {
        questionTitle.setText(getModifiedText(title));
    }

    private String getModifiedText(String rawText) {

        String text = rawText.replace("\\n", "");
        return String.format("<html><div WIDTH=%d>%s</div></html>", 500, text);
    }

    private void setItemBorder(JComponent jComponent) {

        Border border = JBUI.Borders.empty(16);
        jComponent.setBorder(border);
    }

    private void setLookAndFeel() {

        setItemBorder(searchToolWindowContent);

        JBLabel jbLabel = new JBLabel();
        questionTitle.setUI(jbLabel.getUI());
    }
}