package DBAccess;

import Model.Users;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Class to get all current users from the users table
 *
 */
public class DBUsers {

    /**Method that gets userID and userName from the users table.
     *
     * @return Returns Users object
     */
    public static ObservableList<Users> getUsers() {
        ObservableList<Users> users = FXCollections.observableArrayList();

        // sql query to get userID and username
        try {
            String sql = "SELECT User_ID, User_Name FROM users ORDER BY User_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // scan through resultset and set variables
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");

                // set variables to new Users object and add to users
                Users user = new Users(userID, username);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
