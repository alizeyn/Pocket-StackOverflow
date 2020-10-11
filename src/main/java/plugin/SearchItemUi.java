package plugin;

import model.ParseResult;

import javax.swing.*;

public class SearchItemUi {

    private JPanel searchToolWindowContent;
    private JLabel questionLabel;
    private JLabel questionPlaceHolderLabel;
    private JLabel answerLabel;
    private JLabel answerPlaceHolder;

    public SearchItemUi(ParseResult searchItem) {

        setQuestion(searchItem.getQuestion().getBody());
        setAnswer(searchItem.getAnswers().get(0).getBody());
    }

    public SearchItemUi(int i) {

        setQuestion(String.valueOf(i));
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
        answerPlaceHolder.setText("<html>" + answer + "</html>");
    }

    public void setQuestion(String question) {
        questionPlaceHolderLabel.setText("<html>" + question + "</html>");
    }

}
