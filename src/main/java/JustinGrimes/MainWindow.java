package JustinGrimes;

import javax.swing.*;

public class MainWindow {
    private JPanel rootPanel;
    private JTable applicationTable;
    private JTextField searchField;
    private JLabel companyNameSearchLabel;

    public MainWindow(JobApplicationTracker frame) {
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
