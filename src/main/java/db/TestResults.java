package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static data.Configuration.databaseName;

public class TestResults extends BaseDB{
    public TestResults() {
        super(databaseName);
    }

    public TestResults insertResult(String testName, String status, Date date) throws SQLException {
        String sql = """
                INSERT INTO test_results (test_name, status, execution_time)
                VALUES (?, ?, ?)
                ON CONFLICT(test_name)
                DO UPDATE SET
                    status = excluded.status,
                    execution_time = excluded.execution_time;
                """;

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, testName);
        pstmt.setString(2, status);
        pstmt.setDate(3, date);
        pstmt.executeUpdate();
        pstmt.close();
        return this;
    }
}
