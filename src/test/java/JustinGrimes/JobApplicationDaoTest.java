package JustinGrimes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationDaoTest {
    private Dao<JobApplication> jobAppDao;
    private String dbURL = "jdbc:sqlite::memory:";

    @BeforeEach
    void setUp() throws SQLException {
        jobAppDao = new JobApplicationDao(dbURL);
    }



    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void add() {
        JobApplication testJobApplication = new JobApplication(
                "Awesome New Co",
                LocalDate.now(),
                JobStatus.ACCEPTED,
                "I really want this job"
        );
        String sql = "SELECT * FROM job_applications " +
                "WHERE company_name = \"Awesome New Co\";";

        jobAppDao.add(testJobApplication);

        // TODO finish tests
        ResultSet rs;
        try(Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ){
            rs = preparedStatement.executeQuery(sql);
            assert rs.
        } catch (SQLException e) {
            fail();
        }

    }
}