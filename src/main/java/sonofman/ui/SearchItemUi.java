package sonofman.ui;

import lombok.Data;
import sonofman.model.Answer;
import sonofman.model.ParseResult;
import sonofman.model.Question;
import sonofman.plugin.HyperlinkMouseListener;
import sonofman.util.HtmlTweak;
import sonofman.util.Tools;
import sonofman.util.UiUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Data
public class SearchItemUi {

    private ParseResult resultModel;

    private JPanel searchToolWindowContent;
    private JLabel questionTitle;
    private JLabel answerTitle;
    private JTextPane questionDescription;
    private JTextPane answerDescription;
    private JButton moreAnswersButton;
    private JButton openWebsiteButton;
    private JButton cpQuestionLinkButton;
    private JButton cpAnswerLinkButton;

    public SearchItemUi(ParseResult searchItem) {

        resultModel = searchItem;
        setLookAndFeel();
        setQuestion(searchItem.getQuestion().getBody());
        setAnswer(searchItem.getAnswers().get(0).getBody());
        listeners();
    }

    public void listeners() {

        openWebsiteButton.addActionListener(actionEvent -> {

            try {
                Question question = resultModel.getQuestion();
                Desktop desktop = Desktop.getDesktop();
                URI uri = URI.create(question.getLink());
                desktop.browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cpQuestionLinkButton.addActionListener(actionEvent -> {

            Question question = resultModel.getQuestion();
            String questionLink = question.getLink();
            Tools.copyToClipboard(questionLink);
        });

        cpAnswerLinkButton.addActionListener(actionEvent -> {

            Answer firstAnswer = resultModel.getAnswers().get(0);
            String answerLink = firstAnswer.getLink();
            Tools.copyToClipboard(answerLink);
        });

        moreAnswersButton.addActionListener(actionEvent -> {

            AnswerListView answersList = new AnswerListView();
            answersList.updateData(resultModel.getAnswers());
            Container parentCardView = searchToolWindowContent;
            do {
                parentCardView = parentCardView.getParent();
            } while (!"ParentCardView".equalsIgnoreCase(parentCardView.getName()));

            Container panel = parentCardView;
            Component[] children = panel.getComponents();
            panel.removeAll();
            panel.add(answersList.getContent());
            answersList.setOnBackClickedListener(action -> {
                panel.removeAll();
                for (Component child : children) {
                    panel.add(child);
                }
            });
            panel.revalidate();
            panel.repaint();
        });
    }

    public JPanel getContent() {
        return searchToolWindowContent;
    }

    public void setQuestionLabel(String question) {
        questionTitle.setText(question);
    }

    public void setAnswer(String answer) {
        answerDescription.setText(HtmlTweak.refineHtmlResponse(answer));
    }

    public void setQuestion(String question) {
        questionDescription.setText(HtmlTweak.refineHtmlResponse(question));
    }

    public void setQuestionTitle(String title) {
        questionTitle.setText(HtmlTweak.refineHtmlResponse(title));
    }


    private void setLookAndFeel() {

        UiUtil.disableUpdateCaret(questionDescription);
        UiUtil.disableUpdateCaret(answerDescription);
        HyperlinkMouseListener hyperlinkListener = new HyperlinkMouseListener();
        questionDescription.addMouseListener(hyperlinkListener);
        answerDescription.addMouseListener(hyperlinkListener);
    }
}
