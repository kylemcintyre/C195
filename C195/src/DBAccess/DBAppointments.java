package DBAccess;

import Model.Appointments;
import Utilities.DBConnection;
import Views.MainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBAppointments {

    public static ObservableList<Appointments> alist;

    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");



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
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
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

                start.toInstant();

                Appointments A = new Appointments(appointmentID, title, description, location, contact, type, start, end, customerID);
                alist.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alist;
    }

    public static ObservableList<Appointments> getWeeklyAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        LocalDate currentWeek = LocalDate.now();
        LocalDate nextWeek = currentWeek.plusWeeks(1);

        try {
            String sql = "SELECT *, customers.Customer_ID, users.User_ID, contacts.Contact_ID, customers.Customer_Name, contacts.Contact_Name, users.User_Name from appointments, customers, users, contacts" +
                    " WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID" +
                    " AND Start >= ? AND Start < ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, String.valueOf(currentWeek));
            ps.setString(2, String.valueOf(nextWeek));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
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

    public static ObservableList<Appointments> getMonthlyAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate nextMonth = currentMonth.plusMonths(1);

        try {
            String sql = "SELECT *, customers.Customer_ID, users.User_ID, contacts.Contact_ID, customers.Customer_Name, contacts.Contact_Name, users.User_Name from appointments, customers, users, contacts" +
                    " WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID" +
                    " AND Start >= ? AND Start < ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, String.valueOf(currentMonth) + " 00:00:00");
            ps.setString(2, String.valueOf(nextMonth) + " 00:00:00");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
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

    public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {

        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (NULL,?,?,?,?,?,?,?,?,?);";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
