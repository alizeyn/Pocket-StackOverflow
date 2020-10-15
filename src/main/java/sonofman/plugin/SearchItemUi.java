package sonofman.plugin;

import com.intellij.notification.NotificationType;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import lombok.Data;
import sonofman.model.Answer;
import sonofman.model.ParseResult;
import sonofman.model.Question;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
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
        setQuestion(searchItem.getQuestion().getBody());
        setAnswer(searchItem.getAnswers().get(0).getBody());
        listeners();
    }

    public SearchItemUi(int i) {

        setLookAndFeel();
        setQuestion(String.valueOf(i));
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
            copyToClipboard(questionLink);
        });

        cpAnswerLinkButton.addActionListener(actionEvent -> {

            Answer firstAnswer = resultModel.getAnswers().get(0);
            String answerLink = firstAnswer.getLink();
            copyToClipboard(answerLink);
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

    private void copyToClipboard(String text) {

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(text),
                        null
                );

        NotificationManager.showMyMessage("Link Copied To Clipboard!",
                NotificationType.INFORMATION, 3000);
    }

    public JPanel getContent() {
        return searchToolWindowContent;
    }

    public void setQuestionLabel(String question) {
        questionTitle.setText(question);
    }

    public void setAnswer(String answer) {

        //TODO: what if there is <code> tag in the real code !?
        String ans = answer.replace("<pre>", "<pre class='codeblock'>")
                .replaceAll("<code>(?!.*</pre>)", "<code class='inlinecode'>");
        answerDescription.setText(getModifiedText(ans));
    }

    public void setQuestion(String question) {
        String q = question.replace("<pre>", "<pre class='codeblock'>")
                .replaceAll("<code>(?!.*</pre>)", "<code class='inlinecode'>");
        questionDescription.setText(getModifiedText(q));
    }

    public void setQuestionTitle(String title) {
        questionTitle.setText(getModifiedText(title));
    }

    private String getModifiedText(String rawText) {

        String text = rawText.replace("\\n", "");
        JBColor color = new JBColor(JBColor.WHITE, JBColor.BLACK);
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

        String codeBlockStyle = ".codeblock{font-size:xx-small; background-color:white;padding:5px;margin:5px;}";
        String contentHolderStyle = "img{max-width:200px;}";
        String inlineCodeStyle = String.format(".inlinecode{background-color:%s;}", hexColor);

        return String.format("<html><style>" + codeBlockStyle + inlineCodeStyle + contentHolderStyle + "</style><dev>%s</dev></html>", text);
    }

    void setItemBorder(JComponent jComponent) {

        Border border = JBUI.Borders.empty(16);
        jComponent.setBorder(border);
    }

    private void setLookAndFeel() {

        setItemBorder(searchToolWindowContent);
        DefaultCaret caret = (DefaultCaret) questionDescription.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        DefaultCaret caret2 = (DefaultCaret) answerDescription.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        HyperlinkMouseListener hyperlinkListener = new HyperlinkMouseListener();
        questionDescription.addMouseListener(hyperlinkListener);
        answerDescription.addMouseListener(hyperlinkListener);
    }
}
