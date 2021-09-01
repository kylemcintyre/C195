package DBAccess;

import Model.Reports;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBReports {

    public static ObservableList<Reports> getTypeMonth() {
        ObservableList<Reports> rList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT MONTH(Start), MONTHNAME(Start), Type, COUNT(*) FROM appointments GROUP BY " +
                    "MONTH(Start), MONTHNAME(Start), Type ORDER BY MONTH(Start);";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type = rs.getString("Type");
                String month = rs.getString("MONTHNAME(Start)");
                int monthInt = rs.getInt("MONTH(Start)");
                int appointmentCount = rs.getInt("COUNT(*)");

                Reports report = new Reports(type, month, monthInt, appointmentCount);
                rList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rList;
    }

    public static ObservableList<Reports> getContactSchedules() {
        ObservableList<Reports> rList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, appointments.Customer_ID, " +
                    "customers.Customer_Name, appointments.Contact_ID, contacts.Contact_Name FROM appointments, " +
                    "contacts, customers WHERE appointments.Contact_ID = contacts.Contact_ID AND " +
                    "appointments.Customer_ID = customers.Customer_ID ORDER BY appointments.Contact_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Reports report = new Reports(appointmentID, title, type, description, start, end, customerID, customerName,
                        contactID, contactName);
                rList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rList;
    }

    public static ObservableList<Reports> getContactEmails() {
        ObservableList<Reports> rList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Reports report = new Reports(contactName, contactEmail);
                rList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rList;
    }
}
