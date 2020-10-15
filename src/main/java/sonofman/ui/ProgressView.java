package sonofman.ui;

import lombok.Data;

import javax.swing.*;

@Data
public class ProgressView {

    private JProgressBar progressBar;
    private JPanel contentHolder;
    private JLabel progressMessageLabel;
}
