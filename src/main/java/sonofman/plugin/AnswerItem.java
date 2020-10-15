package sonofman.plugin;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import sonofman.model.Answer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.util.Date;


public class AnswerItem {

    private JTextPane answerBodyTextPane;
    private JButton copyLinkButton;
    private JLabel acceptedAnswerLabel;
    private JLabel answerDateLabel;
    private JLabel votesLabel;
    private JPanel contentHolder;

    public AnswerItem() {
        setLookAndFeel();
    }

    public void setAnswer(Answer answer) {

        setAnswerBody(answer.getBody());

        //TODO guess it's borken server side

        if (answer.isAccepted()) {
            acceptedAnswerLabel.setVisible(true);
            contentHolder.revalidate();
            contentHolder.repaint();
        }
        votesLabel.setText(votesLabel.getText().replace("-", String.valueOf(answer.getVotes())));

        Date date = new Date(answer.getCreationData() * 1000);
        String formattedDate = answerDateLabel.getText()
                .replace("-", date.getYear() +"/"+date.getMonth()+"/"+date.getDay());
        answerDateLabel.setText(formattedDate);
    }

    private void setAnswerBody(String body) {

        String ans = body.replace("<pre>", "<pre class='codeblock'>")
                .replaceAll("<code>(?!.*</pre>)", "<code class='inlinecode'>");

        answerBodyTextPane.setText(getModifiedText(ans));
    }

    private String getModifiedText(String rawText) {

        String text = rawText.replace("\\n", "");
        JBColor color = new JBColor(JBColor.WHITE, JBColor.BLACK);
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

        String codeBlockStyle = ".codeblock{font-size:xx-small; background-color:white;padding:5px;margin:5px;}";
        String contentHolderStyle = "img{max-width:200px;}";
        String inlineCodeStyle = String.format(".inlinecode{background-color:%s;}", hexColor);

        return String.format("<html><style>" + codeBlockStyle + inlineCodeStyle + contentHolderStyle + "</style>%s</html>", text);
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
        DefaultCaret caret = (DefaultCaret) answerBodyTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        HyperlinkMouseListener hyperlinkListener = new HyperlinkMouseListener();
        answerBodyTextPane.addMouseListener(hyperlinkListener);
    }
}
