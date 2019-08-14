package JustinGrimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobAppplicationDao implements Dao<JobApplication> {
    private final static String dbFile = "applications.db";
    private final static String defaultDbUrl = "jdbc:sqlite:" + dbFile;
    private final String dbUrl;


    public JobAppplicationDao() throws SQLException {
        this(defaultDbUrl);
    }

    public JobAppplicationDao(String url) throws SQLException {
        dbUrl = url;
        setupDB();

    }


    private void setupDB() throws SQLException {
        // setup db based off of dbURL. If it does not exist, it creates the file and
        //the table


        Connection conn = DriverManager.getConnection(dbUrl);
        Statement statement = conn.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS job_applications " +
                        "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "company_name VARCHAR NOT NULL," +
                        "app_date INTEGER NOT NULL," +
                        "job_status VARCHAR, " +
                        "notes TEXT" +
                        ")"
        );
    }

    @Override
    public List<JobApplication> getAll() {
        List<JobApplication> allApplications = new ArrayList<>();
        String sql = "SELECT * FROM job_applications";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                JobApplication jobApp;
                jobApp = new JobApplication(
                        resultSet.getString("company_name"),
                        resultSet.getDate("app_date").toLocalDate(),
                        JobStatus.valueOf(resultSet.getString("job_status")),
                        resultSet.getString("notes")
                );
                allApplications.add(jobApp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allApplications;
    }

    @Override
    public void update(int id, JobApplication jobApplication) {
        String sql = "UPDATE job_applications SET " +
                "company_name = ?, " +
                "app_date = ?, " +
                "job_status = ?, " +
                "notes = ? " +
                "WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, jobApplication.getCompanyName());
            preparedStatement.setString(2, jobApplication.getApplicationDate().toString());
            preparedStatement.setString(3, jobApplication.getStatus().toString());
            preparedStatement.setString(4, jobApplication.getNotes());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM job_applications WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt( 1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void add(JobApplication jobApplication) {
        String sql = "INSERT INTO job_applications (company_name, app_date, job_status, notes) \n" +
                " VALUES (?,?,?,?);";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, jobApplication.getCompanyName());
            preparedStatement.setString(2, jobApplication.getApplicationDate().toString());
            preparedStatement.setString(3, jobApplication.getStatus().toString());
            preparedStatement.setString(4, jobApplication.getNotes());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
