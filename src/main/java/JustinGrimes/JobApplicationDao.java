package JustinGrimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDao implements Dao<JobApplication> {
    private final static String dbFile = "applications.db";
    private final static String defaultDbUrl = "jdbc:sqlite:" + dbFile;
    private final String dbUrl;


    public JobApplicationDao() throws SQLException {
        this(defaultDbUrl);
    }

    public JobApplicationDao(String url) throws SQLException {
        dbUrl = url;
        setupDB();

    }


    private void setupDB() throws SQLException {
        // setup db based off of dbURL. If it does not exist, it creates the file and
        //the table


        try (Connection conn = DriverManager.getConnection(dbUrl);
        Statement statement = conn.createStatement())
        {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS job_applications " +
                            "(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " company_name TEXT NOT NULL, " +
                            " app_date INTEGER NOT NULL, " +
                            "job_status TEXT, " +
                            "notes TEXT" +
                            ");"
            );
        } catch (SQLException e) {
            throw new SQLException("applications.db unable to be setup");
        }

    }

    @Override
    public List<JobApplication> getAll() {
        List<JobApplication> allApplications = new ArrayList<>();
        String sql = "SELECT * FROM job_applications;";

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
                jobApp.setId(resultSet.getInt("id"));
                allApplications.add(jobApp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allApplications;
    }

    @Override
    public JobApplication getById(int id) {
        String sql = "SELECT * FROM job_applications WHERE ID = ?;";
        JobApplication jobApp = null;

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            jobApp = new JobApplication(
                    resultSet.getString("company_name"),
                    resultSet.getDate("app_date").toLocalDate(),
                    JobStatus.valueOf(resultSet.getString("job_status")),
                    resultSet.getString("notes")
            );
                jobApp.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobApp;
    }

    @Override
    public void update(int id, JobApplication jobApplication) {
        //update the Application at id with the @param jobApplication
        String sql = "UPDATE job_applications SET " +
                "company_name = ?, " +
                "app_date = ?, " +
                "job_status = ?, " +
                "notes = ? " +
                "WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, jobApplication.getCompanyName());
            preparedStatement.setDate(2, Date.valueOf(jobApplication.getApplicationDate()));
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
        String sql = "DELETE FROM job_applications WHERE id = ?;";

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
            preparedStatement.setDate(2, Date.valueOf(jobApplication.getApplicationDate()));
            preparedStatement.setString(3, jobApplication.getStatus().toString());
            preparedStatement.setString(4, jobApplication.getNotes());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
