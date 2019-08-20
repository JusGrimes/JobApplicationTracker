package JustinGrimes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
    private AppTableModel appTableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobApplicationTracker::new
        );
    }

    public JobApplicationTracker() {
        super("Job Application Tracker");
        this.setContentPane(this.rootPanel);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                EditApplicationForm dialog = new EditApplicationForm(JobApplicationTracker.this);
                dialog.setVisible(true);
                JobApplication newApp = dialog.getValue();
                if (newApp != null) {
                    applicationDao.add(newApp);
                    appTableModel.setApplications(applicationDao.getAll());
                }
            }
        });


        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final int selectedRow = applicationTable.getSelectedRow();

                // prevent against OOB exception for no rows selected
                if (selectedRow == -1) return;

                JobApplication selectedApp = appTableModel.getApplicationAtRow(selectedRow);
                applicationDao.delete(selectedApp.getId());
                appTableModel.setApplications(applicationDao.getAll());
            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final int selectedRow = applicationTable.getSelectedRow();

                // prevent against OOB exception for no rows selected
                if (selectedRow == -1) return;

                JobApplication selectedApp = appTableModel.getApplicationAtRow(selectedRow);

                EditApplicationForm dialog = new EditApplicationForm(JobApplicationTracker.this, selectedApp);
                dialog.setVisible(true);

                applicationDao.update(selectedApp.getId(), dialog.getValue());
                appTableModel.setApplications(applicationDao.getAll());

            }
        });
    }

    private void createUIComponents() {

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
