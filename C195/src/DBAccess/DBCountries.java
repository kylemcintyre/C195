package DBAccess;

import Utilities.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/** Class that selects all from the countries table.
 *
 */
public class DBCountries {

    /**Method that selects all from the countries table using SQL.
     *
     * @return Returns ObservableList clist
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Countries C = new Countries(countryID, countryName);
                clist.add(C);
            }
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }
}
