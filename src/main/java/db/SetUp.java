package db;

import java.sql.SQLException;
import java.sql.Statement;

import static data.Configuration.databaseName;

public class SetUp extends BaseDB {
    public SetUp() {
        super(databaseName);
    }

    public SetUp createTables() {
        String sql = """
                CREATE TABLE IF NOT EXISTS test_results (
                               id INTEGER PRIMARY KEY AUTOINCREMENT,
                               test_name TEXT UNIQUE,
                               status TEXT,
                               execution_time DATETIME
                );
                """;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this;
    }
}
