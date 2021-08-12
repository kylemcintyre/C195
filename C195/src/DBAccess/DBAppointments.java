package DBAccess;

import Model.Appointments;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBAppointments {

    public static ObservableList<Appointments> alist;

    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT *, customers.Customer_ID, users.User_ID, contacts.Contact_ID, customers.Customer_Name, contacts.Contact_Name, users.User_Name from appointments, customers, users, contacts" +
                    " WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
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

                Appointments A = new Appointments(appointmentID, title, description, location, contact, type, start, end, customerID);
                alist.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alist;
    }
}
