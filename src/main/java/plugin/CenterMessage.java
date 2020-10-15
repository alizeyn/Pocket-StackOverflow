package plugin;

import com.intellij.util.ui.JBUI;
import lombok.Data;

import javax.swing.*;

@Data
public class CenterMessage {

    private JPanel contentHolder;
    private JLabel messageLabel;
    private JLabel subMessageLabel;

    public CenterMessage() {

        contentHolder.setName("CenterMessage");
        messageLabel.setBorder(JBUI.Borders.emptyBottom(16));
    }
}
