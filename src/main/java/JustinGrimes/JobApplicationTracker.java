package JustinGrimes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class JobApplicationTracker extends JFrame {
    private JPanel rootPanel;
    private JTable applicationTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JScrollPane scrollPane;
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private Dao<JobApplication> applicationDao;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobApplicationTracker::new
        );
    }

    public JobApplicationTracker() {
        super("Job Application Tracker");
        this.setContentPane(this.rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
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
