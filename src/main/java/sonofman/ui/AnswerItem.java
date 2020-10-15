package sonofman.ui;

import com.intellij.util.ui.JBUI;
import sonofman.model.Answer;
import sonofman.plugin.HyperlinkMouseListener;
import sonofman.util.HtmlTweak;
import sonofman.util.Tools;
import sonofman.util.UiUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.Date;


public class AnswerItem {

    private final Answer answer;

    private JTextPane answerBodyTextPane;
    private JButton copyLinkButton;
    private JLabel acceptedAnswerLabel;
    private JLabel answerDateLabel;
    private JLabel votesLabel;
    private JPanel contentHolder;

    public AnswerItem(Answer answer) {

        this.answer = answer;
        setLookAndFeel();
        setAnswerUi(answer);
        listeners();
    }

    public void setAnswerUi(Answer answer) {

        setAnswerBody(answer.getBody());

        //TODO guess accepted answer is not set serverside
        if (answer.isAccepted()) {
            acceptedAnswerLabel.setVisible(true);
            contentHolder.revalidate();
            contentHolder.repaint();
        }

        votesLabel.setText(String.valueOf(answer.getVotes()));

        System.out.println(answer.getCreationData());
        Date date = new Date(answer.getCreationData());
        String formattedDate = date.getYear() +"/"+date.getMonth()+"/"+date.getDay();
        answerDateLabel.setText(formattedDate);
    }

    private void listeners() {

        copyLinkButton.addActionListener(actionEvent -> {
            Tools.copyToClipboard(answer.getLink());
        });
    }

    private void setAnswerBody(String body) {
        answerBodyTextPane.setText(HtmlTweak.refineHtmlResponse(body));
    }

    public JPanel getContentHolder() {
        return contentHolder;
    }

    void setItemBorder(JComponent jComponent) {

        Border border = JBUI.Borders.empty(16);
        jComponent.setBorder(border);
    }

    private void setLookAndFeel() {

        setItemBorder(contentHolder);
        UiUtil.disableUpdateCaret(answerBodyTextPane);
        HyperlinkMouseListener hyperlinkListener = new HyperlinkMouseListener();
        answerBodyTextPane.addMouseListener(hyperlinkListener);
    }
}
