package db;

import org.testng.Assert;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseDB {
    protected Connection conn;
    public BaseDB(String dbName){
        conn = DBConnection.connect(dbName);
        Assert.assertNotNull(conn, "Something went wrong");
    }

    public void closeConn() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
