package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect(String dbName) {
        String url = "jdbc:sqlite:%s.db".formatted(dbName);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
