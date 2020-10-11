package plugin;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class SearchToolWindow extends SimpleToolWindowPanel {

    private JPanel searchToolWindowContent;
    private JLabel questionLabel;
    private JLabel questionPlaceHolderLabel;
    private JLabel answerLabel;
    private JLabel answerPlaceHolder;

    public SearchToolWindow(ToolWindow toolWindow) {
        super(true, false);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public JPanel getContent() {
        return searchToolWindowContent;
    }

    public void setQuestionLabel(String question) {
        questionLabel.setText(question);
    }

    public void setAnswer(String answer) {

        answerPlaceHolder.setText(answer);
    }

    public void setQuestion(String question) {

        questionPlaceHolderLabel.setText(question);
    }

}
