package Model;

import java.sql.Timestamp;

/**Class that gets and sets report variables and creates Report objects.
 *
 */
public class Reports {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int userID;
    private int contactID;
    private String customerName;
    private String contactName;
    private String userName;
    private String month;
    private int monthInt;
    private int customerAppointments;
    private String country;
    private String contactEmail;


    /**Method to create Report object for typeMonth report.
     *
     * @param type
     * @param month
     * @param monthInt
     * @param customerAppointments
     */
    public Reports(String type, String month, int monthInt, int customerAppointments) {
        this.type = type;
        this.month = month;
        this.monthInt = monthInt;
        this.customerAppointments = customerAppointments;
    }

    /**Method to create Report object for contactSchedule report.
     *
     * @param appointmentID
     * @param title
     * @param type
     * @param description
     * @param start
     * @param end
     * @param customerID
     * @param customerName
     * @param ContactID
     * @param contactName
     */
    public Reports(int appointmentID, String title, String type, String description, Timestamp start, Timestamp end, int customerID, String customerName, int ContactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.customerName = customerName;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**Method to create Report object for contactEmail report.
     *
     * @param contactName
     * @param contactEmail
     */
    public Reports(String contactName, String contactEmail) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getMonthInt() {
        return monthInt;
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }

    public int getCustomerAppointments() {
        return customerAppointments;
    }

    public void setCustomerAppointments(int customerAppointments) {
        this.customerAppointments = customerAppointments;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
