package sonofman.ui;

import sonofman.model.Answer;
import sonofman.plugin.HyperlinkMouseListener;
import sonofman.util.HtmlTweak;
import sonofman.util.Tools;
import sonofman.util.UiUtil;

import javax.swing.*;
import java.time.Instant;
import java.util.Calendar;
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

        long date = answer.getCreationData();
        answerDateLabel.setText(unixTimeStampToDateFormat(date));
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

    private String unixTimeStampToDateFormat(long unixTimeStamp) {

        Date date = Date.from(Instant.ofEpochSecond(answer.getCreationData()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) +"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setLookAndFeel() {

        UiUtil.disableUpdateCaret(answerBodyTextPane);
        HyperlinkMouseListener hyperlinkListener = new HyperlinkMouseListener();
        answerBodyTextPane.addMouseListener(hyperlinkListener);
    }
}
