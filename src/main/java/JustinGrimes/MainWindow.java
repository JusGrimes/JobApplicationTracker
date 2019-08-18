package JustinGrimes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class MainWindow {
    private JPanel rootPanel;
    private JTable applicationTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JScrollPane scrollPane;
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private Dao<JobApplication> applicationDao;


    public MainWindow(JobApplicationTracker frame) {
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void createUIComponents() {
        AppTableModel appTableModel = null;
        try {
            applicationDao = new JobApplicationDao();
            appTableModel = new AppTableModel(applicationDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        applicationTable = new JTable(appTableModel);
        applicationTable.getTableHeader().setReorderingAllowed(false);

        // set all of the cells to centered
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < applicationTable.getColumnCount(); i++) {
            applicationTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}
