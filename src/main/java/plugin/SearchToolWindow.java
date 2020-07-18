package plugin;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class SearchToolWindow {

    private JPanel searchWindowContent;

    public SearchToolWindow(ToolWindow toolWindow) {

    }

    public JPanel getContent() {
        return searchWindowContent;
    }
}
