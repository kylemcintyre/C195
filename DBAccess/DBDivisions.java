package DBAccess;

import Model.Divisions;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Class that makes calls to first_level_divisions table.
 *
 */
public class DBDivisions {

    /**Method that uses SQL to retrieve all divisions from the first_level_divisions table.
     *
     * @return Returns ObservableList dList
     */
    public static ObservableList<Divisions> getDivisions() {
        ObservableList<Divisions> dList = FXCollections.observableArrayList();

        // sql query to get divisions from countries and first_level_divisions tables
        try {
            String sql = "SELECT Division_ID, Division, first_level_divisions.Country_ID, countries.Country FROM " +
                    "first_level_divisions, countries WHERE countries.Country_ID = first_level_divisions.Country_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // scan through resultset and set variables
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                // create new Divisions object with variables and add to dlist
                Divisions divisions = new Divisions(divisionID, division, countryId, country);
                dList.add(divisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dList;
    }
}
