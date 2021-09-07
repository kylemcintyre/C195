package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**Class that sets up the connection to the database. Uses the startConnection method on Main startup
 *
 */
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

    // my username and password to connect to the database
    private static final String username = "U07jFq"; // Username
    private static final String password = "53689046315"; // Password

    /**Method to pass information for the database to the DriverManager.
     *
     * @return Returns DriverManager connection conn
     */
    public static Connection startConnection() {
        try {
            // connects to database by building connection object conn
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }

        catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**Method to reestablish database connection.
     *
     * @return Returns DriverManager connection conn
     */
    public static Connection getConnection() {
        return conn;
    }

    /**Method to close connection to the database when program is closed.
     *
     */
    public static void closeConnection() {

        // closes connection and prints out
        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch (SQLException e) {
            // do nothing
        }
    }

}
