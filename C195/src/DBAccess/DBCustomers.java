package DBAccess;

import Model.Appointments;
import Model.Customers;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBCustomers {

    public static ObservableList<Customers> clist;

    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT *, first_level_divisions.Division_ID, first_level_divisions.Country_ID, countries.Country_ID, countries.Country from customers, first_level_divisions, countries" +
                    " WHERE first_level_divisions.Division_ID = customers.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String country = rs.getString("Country");
                String phone = rs.getString("phone");
                int divisionID = rs.getInt("Division_ID");
                /*LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String customerName = rs.getString("Customer_Name");
                String userName = rs.getString("User_Name");
                String contactName = rs.getString("Contact_Name");*/

                Customers C = new Customers(customerID, customerName, address, postalCode, country, phone, divisionID);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }
}
