package DBAccess;

import Model.Divisions;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivisions {

    public static ObservableList<Divisions> getDivisions() {
        ObservableList<Divisions> dList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, first_level_divisions.Country_ID, countries.Country FROM " +
                    "first_level_divisions, countries WHERE countries.Country_ID = first_level_divisions.Country_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Divisions divisions = new Divisions(divisionID, division, countryId, country);
                dList.add(divisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dList;
    }

    public static ObservableList<Divisions> getDivisionsByCountry(int countryID) {
        ObservableList<Divisions> dList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, first_level_divisions.Country_ID, countries.Country FROM " +
                    "first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID " +
                    "AND first_level_divisions.Country_ID = ?;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Divisions divisions = new Divisions(divisionId, division, countryId, country);
                dList.add(divisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dList;
    }

    public static Divisions getDivisionByID(int divisionNum) {
        Divisions divisions = null;

        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions WHERE Division_ID = ?;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionNum);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                String country = null;

                divisions = new Divisions(divisionID, division, countryID, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
}
