package sonofman.util;

import com.intellij.notification.NotificationType;
import sonofman.plugin.NotificationManager;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Tools {

    public static void copyToClipboard(String text) {

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(text),
                        null
                );

        NotificationManager.showMyMessage("Link Copied To Clipboard!",
                NotificationType.INFORMATION, 3000);
    }
}
