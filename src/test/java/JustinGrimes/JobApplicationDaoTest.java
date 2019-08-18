package JustinGrimes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationDaoTest {
    private JobApplication testJobApplication;
    private Dao<JobApplication> jobAppDao;
    private Connection testConn;

    void emptyDB() throws SQLException {
        //noinspection SqlWithoutWhere
        String sql =
                "DELETE FROM job_applications;";
        Statement statement = testConn.createStatement();
        statement.execute(sql);
    }

    @SuppressWarnings("WeakerAccess")
    public JobApplicationDaoTest() throws SQLException {
        String dbURL = "jdbc:sqlite:src/test/resources/test.db";
        jobAppDao = new JobApplicationDao(dbURL);
        testConn = DriverManager.getConnection(dbURL);
        LocalDate testDate = LocalDate.now();
        testJobApplication = new JobApplication(
                "Awesome New Co",
                testDate,
                JobStatus.ACCEPTED,
                "I really want this job"
        );
    }


    @BeforeEach
    void clearDB() throws SQLException {
        emptyDB();
    }


    @Test
    void getAll() throws SQLException {
        List<JobApplication> allApps = new ArrayList<>();
        JobApplication secondJobApp = new JobApplication("Second Best Company:",
                LocalDate.of(1985,7,3),
                JobStatus.REJECTED,
                "" );

        String getSQL = "SELECT * FROM job_applications;";
        PreparedStatement preparedStatement = testConn.prepareStatement(getSQL);

        jobAppDao.add(testJobApplication);
        jobAppDao.add(secondJobApp);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            JobApplication ja = new JobApplication(
                    rs.getString("company_name"),
                    rs.getDate("app_date").toLocalDate(),
                    JobStatus.valueOf(rs.getString("job_status")),
                    rs.getString("notes")
            );
            ja.setId(rs.getInt("id"));

            allApps.add(ja);
        }
        rs.close();

        assertTrue(allApps.contains(testJobApplication) &&
                allApps.contains(secondJobApp) &&
                allApps.size() == 2);

    }

    @Test
    void update() throws SQLException {
        jobAppDao.add(testJobApplication);
        String getSQL = "SELECT * FROM job_applications " +
                "WHERE company_name LIKE ?";

        PreparedStatement preparedStatement = testConn.prepareStatement(getSQL);
        preparedStatement.setString(1, testJobApplication.getCompanyName());

        int id;
        ResultSet rs = preparedStatement.executeQuery();

        rs.next();
        id = rs.getInt("id");
        rs.close();

        JobApplication secondJobApp = new JobApplication("Second Best Company:",
                LocalDate.of(1985,7,3),
                JobStatus.REJECTED,
                "" );

        jobAppDao.update(id, secondJobApp);
        preparedStatement.setString(1, secondJobApp.getCompanyName());

        rs = preparedStatement.executeQuery();

        rs.next();
        JobApplication compJA = new JobApplication(
                rs.getString("company_name"),
                rs.getDate("app_date").toLocalDate(),
                JobStatus.valueOf(rs.getString("job_status")),
                rs.getString("notes")
        );
        compJA.setId(rs.getInt("id"));
        rs.close();
        assertEquals(secondJobApp,compJA);

    }

    @Test
    void delete() throws SQLException {
        jobAppDao.add(testJobApplication);

        String sql = "SELECT * FROM job_applications " +
                "WHERE company_name LIKE ?";


        ResultSet rs;
        PreparedStatement preparedStatement = testConn.prepareStatement(sql);
        preparedStatement.setString(1, testJobApplication.getCompanyName());

        rs = preparedStatement.executeQuery();
        rs.next();
        int id = rs.getInt("id");
        rs.close();
        jobAppDao.delete(id);
        rs = preparedStatement.executeQuery();
        // passes if execute() returns false meaning there are no records.
        assertFalse(rs.next());
    }

    @Test
    void add() throws SQLException {

        jobAppDao.add(testJobApplication);
        String sql = "SELECT * FROM job_applications " +
                "WHERE company_name LIKE ?";

        ResultSet rs;
        PreparedStatement preparedStatement = testConn.prepareStatement(sql);
        preparedStatement.setString(1, testJobApplication.getCompanyName());
        rs = preparedStatement.executeQuery();
        rs.next();
        JobApplication compJA = new JobApplication(
                rs.getString("company_name"),
                rs.getDate("app_date").toLocalDate(),
                JobStatus.valueOf(rs.getString("job_status")),
                rs.getString("notes")
        );
        rs.close();
        assertEquals(testJobApplication, compJA);

    }

    @Test
    void getById() throws SQLException {
        jobAppDao.add(testJobApplication);
        String sql = "SELECT * FROM job_applications " +
                "WHERE company_name LIKE ?;";

        JobApplication secondJobApp = new JobApplication("Second Best Company:",
                LocalDate.of(1985,7,3),
                JobStatus.REJECTED,
                "" );

        ResultSet rs;
        PreparedStatement preparedStatement = testConn.prepareStatement(sql);
        preparedStatement.setString(1, testJobApplication.getCompanyName());
        rs = preparedStatement.executeQuery();
        rs.next();
        int id = rs.getInt("id");
        rs.close();
        jobAppDao.add(secondJobApp);
        JobApplication retJobApp = jobAppDao.getById(id + 1);

        assertEquals(id + 1, retJobApp.getId());

    }
}