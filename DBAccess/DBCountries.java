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

            // sql query to get all countries
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // scan through resultset and set variables
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                // add variables to Countries object and add to clist
                Countries C = new Countries(countryID, countryName);
                clist.add(C);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return clist;
    }
}
