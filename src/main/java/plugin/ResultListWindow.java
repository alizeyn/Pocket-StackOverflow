package plugin;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class ResultListWindow extends SimpleToolWindowPanel {

    private JPanel contentHolder;

    private JBScrollPane scrollPanel;
    private BoxLayout boxLayout;

    public ResultListWindow(ToolWindow toolWindow) {
        super(true, false);
        contentHolder = new JPanel();
        boxLayout = new BoxLayout(contentHolder, BoxLayout.Y_AXIS);
        contentHolder.setLayout(boxLayout);
        scrollPanel = new JBScrollPane(contentHolder,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JBScrollPane getContent() {
        return scrollPanel;
    }

    //    public void updateData(List<ParseResult> resultList) {
    public void updateData() {

        for (int i = 0; i < 50; i++) {
            SearchItemUi itemUi = new SearchItemUi(i);
            itemUi.setQuestionTitle("How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?How Can I Fuck You ?");
            itemUi.setAnswer("Ask Me Gently :)");
            JPanel panel = itemUi.getContent();
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
//            if (i % 2 != 0) {
//                panel.setBackground(new JBColor(new Color(255, 0, 0), new Color(0, 134, 0)));
//            } else {
//                panel.setBackground(new JBColor(new Color(0, 134, 0), new Color(255, 0, 0)));
//            }
            Dimension d = panel.getPreferredSize();
            Dimension itemSize = new Dimension(500, d.height);
            panel.setMaximumSize(itemSize);
            contentHolder.add(panel);

            contentHolder.add(new JSeparator());
        }
    }
}
