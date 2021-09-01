package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**Class that gets and sets appointment variables and creates Appointment objects.
 *
 */
public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String customerName;
    private String contactName;
    private String userName;

    private static ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    /**Method to create Appointment objects
     *
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param customerName
     * @param userID
     * @param userName
     * @param contactID
     * @param contactName
     */
    public Appointments(int appointmentID, String title, String description, String location, String type,
                        Timestamp start, Timestamp end, int customerID, String customerName, int userID,
                        String userName, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.customerName = customerName;
        this.userID = userID;
        this.userName = userName;
        this.contactID = contactID;
        this.contactName = contactName;

    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getCustomer() {
        return customerName;
    }

    public void setCustomer(String customer) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contactName;
    }

    public void setContact(String contact) {
        this.contactName = contactName;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String user) {
        this.userName = userName;
    }

    public static ObservableList<Appointments> getAppointments() {
        return appointments;
    }

    public static void setAppointments(ObservableList<Appointments> appointments) {
        Appointments.appointments = appointments;
    }

    public LocalDate getStartDate (Timestamp startDate) {
        String startDateString = startDate.toString();
        LocalDate startLocalDate = LocalDate.parse(startDateString);
        return startLocalDate;
    }

    public String getCustomerString() {
        return customerName + " [" + customerID + "]";
    }

    public String getUserString() {
        return userName + " [" + userID + "]";
    }

    public String getContactString() {
        return contactName + " [" + contactID + "]";
    }
}
