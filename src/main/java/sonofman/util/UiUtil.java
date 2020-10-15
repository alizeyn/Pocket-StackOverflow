package sonofman.util;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class UiUtil {

    public static void disableUpdateCaret(JTextPane jTextPane) {

        DefaultCaret caret = (DefaultCaret) jTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    }
}
