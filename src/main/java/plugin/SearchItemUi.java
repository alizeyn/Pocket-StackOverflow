package plugin;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import model.ParseResult;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SearchItemUi {

    private JPanel searchToolWindowContent;
    private JLabel questionTitle;
    private JLabel answerTitle;
    private JTextPane questionDescription;
    private JTextPane answerDescription;

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

        return String.format("<html><style>" + codeBlockStyle + inlineCodeStyle + contentHolderStyle + "</style><dev class='contentholder'>%s</dev></html>", text);
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
        questionDescription.addMouseListener(new HyperlinkMouseListener());
        answerDescription.addMouseListener(new HyperlinkMouseListener());
    }

    private Element getHyperlinkElement(MouseEvent event) {
        JEditorPane editor = (JEditorPane) event.getSource();
        int pos = editor.getUI().viewToModel(editor, event.getPoint());
        if (pos >= 0 && editor.getDocument() instanceof HTMLDocument) {
            HTMLDocument hdoc = (HTMLDocument) editor.getDocument();
            Element elem = hdoc.getCharacterElement(pos);
            if (elem.getAttributes().getAttribute(HTML.Tag.A) != null) {
                return elem;
            }
        }
        return null;
    }

    private final class HyperlinkMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Element h = getHyperlinkElement(e);
                if (h != null) {
                    Object attribute = h.getAttributes().getAttribute(HTML.Tag.A);
                    if (attribute instanceof AttributeSet) {
                        AttributeSet set = (AttributeSet) attribute;
                        String href = (String) set.getAttribute(HTML.Attribute.HREF);
                        if (href != null) {
                            try {
                                Desktop.getDesktop().browse(new URI(href));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (URISyntaxException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
