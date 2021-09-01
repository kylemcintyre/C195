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

/** This class contains methods that connect to the database and run SQL code for appointments. */
public class DBAppointments {

    public static ObservableList<Appointments> alist;

    /**Method that runs SQL on the database to select all appointment information for the appointment table.
     * @return Returns ObservableList alist*/
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "appointments.Customer_ID, Customer_Name, appointments.User_ID, User_Name, appointments.Contact_ID, " +
                    "Contact_Name FROM appointments, users, customers, contacts WHERE appointments.Customer_ID = " +
                    "customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = " +
                    "contacts.Contact_ID ORDER BY appointments.Appointment_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointments A = new Appointments(appointmentID, title, description, location, type, start, end, customerID, customerName, userID, userName, contactID, contactName);
                alist.add(A);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alist;
    }

    /**Method that runs SQL on the database to select weekly appointment information for the appointment table.
     * @return Returns ObservableList alist*/
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
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointments A = new Appointments(appointmentID, title, description, location, type, start, end, customerID, customerName, userID, userName, contactID, contactName);
                alist.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alist;
    }

    /**Method that runs SQL on the database to select monthly appointment information for the appointment table.
     * @return Returns ObservableList alist*/
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
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointments A = new Appointments(appointmentID, title, description, location, type, start, end, customerID, customerName, userID, userName, contactID, contactName);
                alist.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alist;
    }

    /**Method that runs SQL on the database to select all appointment information for the appointment table for a matching customerID.
     * @param customerID Supplied int for customerID
     * @return Returns ObservableList alist*/
    public static ObservableList<Appointments> getCustomerAppointments(int customerID) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE Customer_ID = ?";


            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
        } catch (Exception e) {
            //
        }
        return alist;
    }

    /**Method that runs SQL on the database to add a new appointment to the appointment table.
     * @param title Title for the appointment
     * @param description Description for the appointment
     * @param location Location for the appointment
     * @param type Type of appointment
     * @param start Start date and time of the appointment
     * @param end End date and time of the appointment
     * @param customerID CustomerID for the appointment
     * @param userID UserID for the appointment
     * @param contactID ContactID for the appointment
     * @return Returns ObservableList alist*/
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

    /**Method that runs SQL on the database to update an existing appointment to the appointment table.
     * @param title Title for the appointment
     * @param description Description for the appointment
     * @param location Location for the appointment
     * @param type Type of appointment
     * @param start Start date and time of the appointment
     * @param end End date and time of the appointment
     * @param customerID CustomerID for the appointment
     * @param userID UserID for the appointment
     * @param contactID ContactID for the appointment
     * @return Returns ObservableList alist*/
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type,
                                         Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                    "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";

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
            ps.setInt(10, appointmentID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Method that runs SQL on the database to delete the appointment that matches the param appointmentID.
     * @param appointmentID Supplied int for appointmentID */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
