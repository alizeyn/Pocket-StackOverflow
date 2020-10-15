package sonofman.plugin;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class BaseToolWindow  extends SimpleToolWindowPanel {

    private JPanel contentHolder;

    public BaseToolWindow(ToolWindow toolWindow) {
        super(true, false);
        contentHolder.setName("ParentCardView");
    }

    public JPanel getContent() {
        return contentHolder;
    }

    public void addView(JComponent component) {
        contentHolder.add(component);
    }

    public void removeAll() {
        contentHolder.removeAll();
    }

    public void updateView() {
        contentHolder.revalidate();
        contentHolder.repaint();
    }

}
