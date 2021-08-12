package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07jFq";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    // Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07jFq"; // Username
    private static final String password = "53689046315"; // Password

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }

        catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {

        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch (SQLException e) {
            // do nothing
        }
    }

}
