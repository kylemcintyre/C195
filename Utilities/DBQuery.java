package Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**Class to createStatement and getStatement for Connection conn
 *
 */
public class DBQuery {

    private static Statement statement;

    /**Method to setStatement for Connection conn.
     *
     * @param conn Connection object
     * @throws SQLException throws exception
     */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**Method to getStatement conn.
     *
     * @return Returns statement
     */
    public static Statement getStatement() {
        return statement;
    }
}
