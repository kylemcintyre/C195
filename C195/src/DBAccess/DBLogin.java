package DBAccess;

import Utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBLogin {

    public static boolean goodLogin(String username, String password) {
        boolean goodLogin = false;

        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                goodLogin = true;
            }
            else {
                // do nothing
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goodLogin;
    }
}