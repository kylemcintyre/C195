package DBAccess;

import Model.Appointments;
import Model.Customers;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**Class that selects various customer information from the customers table.
 *
 */
public class DBCustomers {

    public static ObservableList<Customers> clist;

    /**Method that uses SQL to get all customer information from customers, first_level_divisions, and countries tables.
     *
     * @return Returns ObservableLst clist
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> clist = FXCollections.observableArrayList();

        // sql query to get all information needed to display in customer table
        try {
            String sql = "SELECT *, first_level_divisions.Division_ID, first_level_divisions.Country_ID, countries.Country_ID, countries.Country from customers, first_level_divisions, countries" +
                    " WHERE first_level_divisions.Division_ID = customers.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // scan through resultset and set variables
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String country = rs.getString("Country");
                String phone = rs.getString("phone");
                int divisionID = rs.getInt("Division_ID");

                // create new Customers object with variables and add to clist
                Customers C = new Customers(customerID, customerName, address, postalCode, country, phone, divisionID);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

    /**Method that uses SQL to insert user supplied data into the customers table.
     *
     * @param customerName Customers name
     * @param address Customers address
     * @param postalCode Customers ZIP code
     * @param phoneNumber Customer phone number
     * @param divisionID Customers divisionID
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionID) {

        // sql query to add a new customer to the customers table
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                    "Division_ID) VALUES (NULL,?,?,?,?,?);";

            // set variables to be added into values
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, divisionID);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Method that uses SQL to update an existing customer in the customers table.
     *
     * @param customerID CustomerID
     * @param customerName Customer name
     * @param address Customer address
     * @param postalCode Customer ZIP code
     * @param phoneNumber Customer phone number
     * @param divisionID Customer divisionID
     */
    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) {

        // sql query to update existing customer in the customers table
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Division_ID = ? WHERE Customer_ID = ?;";

            // variables to be set into values
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, divisionID);
            preparedStatement.setInt(6, customerID);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Method that first deletes a customers appointments, and then the customer from the customers table where the customerID matches.
     *
     * @param customerID CustomerID
     */
    public static void deleteCustomer(int customerID) {

        // sql query to delete customers appointments from appointments table with matching customerID
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?;";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // sql query to delete customer from  the customers table with matching customerID
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
