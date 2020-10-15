package sonofman.ui;

import com.intellij.util.ui.JBUI;
import lombok.Data;

import javax.swing.*;

@Data
public class CenterMessageView {

    private JPanel contentHolder;
    private JLabel messageLabel;
    private JLabel subMessageLabel;

    public CenterMessageView() {

        contentHolder.setName("CenterMessage");
        messageLabel.setBorder(JBUI.Borders.emptyBottom(16));
    }
}
