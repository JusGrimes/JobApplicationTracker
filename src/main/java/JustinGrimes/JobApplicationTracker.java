package JustinGrimes;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class JobApplicationTracker extends JFrame {
    private final static String appName = "Job Application Tracker";

    private Connection conn;
    private MainWindow mainWindow;
    private Dao<JobApplication> jobAppDao;

    public JobApplicationTracker() throws HeadlessException {
        super(appName);
        try {
            this.setMinimumSize(new Dimension(600, 300));
            initComponents();
            jobAppDao = new JobApplicationDao();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    private void initComponents() {
        mainWindow = new MainWindow(this);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
            new JobApplicationTracker().setVisible(true)
        );
    }
}
