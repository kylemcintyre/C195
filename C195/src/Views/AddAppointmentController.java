package Views;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

/**Class to handle all logic for the AddAppointment fxml page.
 *
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private Label addAppointmentLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField appointmentIDText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField typeText;

    @FXML
    private ComboBox<Customers> customerIDCombo;

    @FXML
    private ComboBox<Users> userIDCombo;

    @FXML
    private ComboBox<Contacts> contactIDCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox startTimeCombo;

    @FXML
    private ComboBox endTimeCombo;

    @FXML
    private Label textLabel;

    public static Appointments customerAppointments;

    // lambda to load the main screen of the program which is used several times
    // throughout the program. This lambda is saving a lot of duplicate code
    // from being written
    MainScreenController.loadMainScreen loadMainScreen = () -> {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
    };

    /**Method to load data into the AddAppointment fxml file upon load.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // declare dateTimeFormatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // set datepickers to current date
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        startTimeCombo.setValue("08:00");
        endTimeCombo.setValue("09:00");

        // sets items in the customerIDCombo with getAllCustomers()
        Customers.setCustomers(DBCustomers.getAllCustomers());
        customerIDCombo.setItems(Customers.getCustomers());

        // sets items in the userIDCombo with getUsers()
        Users.setUsers(DBUsers.getUsers());
        userIDCombo.setItems(Users.getUsers());

        // sets items in the contactIDCombo with getContacts()
        Contacts.setContacts(DBContacts.getContacts());
        contactIDCombo.setItems(Contacts.getContacts());

        // load array of times into the start and end time comboboxes
        String times[] = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        startTimeCombo.setItems(FXCollections.observableArrayList(times));
        endTimeCombo.setItems(FXCollections.observableArrayList(times));
    }

    /**Method to go back to mainScreen when the cancel button is clicked
     *
     * @param event Performs action when cancel button is clicked
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) {
        // confirmation alert asking if the user is sure they want to cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to cancel?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, closes current window and loads mainScreen
        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            try {
                // runs lambda to load mainScreen
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**Method to check if customer has an overlapping appointment when creating a new appointment or updating an existing one.
     *
     * @param appointments ObservableList Appointments
     * @param customerID Customers object
     * @param startStamp Timestamp
     * @param endStamp Timestamp
     * @return Returns true if an overlapping appointment is found, otherwise returns false
     */
    public boolean checkAppointmentOverlap(ObservableList<Appointments> appointments, Customers customerID,
                                           Timestamp startStamp, Timestamp endStamp) {
        for (Appointments a : appointments) {
            if (a.getCustomerID() == customerID.getCustomerID()) {
                if (a.getStart().before(endStamp) && a.getEnd().after(startStamp)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**Method to send user input to DBAppointments.addAppointment method to create users appointment.
     *
     * @param event
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {

        // create variables with user input
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        String start = startDate.getValue() + " " + startTimeCombo.getValue() + ":00";
        String end = endDate.getValue() + " " + endTimeCombo.getValue() + ":00";
        Customers customerID = customerIDCombo.getValue();
        Users userID = userIDCombo.getValue();
        Contacts contactID = contactIDCombo.getValue();
        ObservableList<Appointments> aList = DBAppointments.getAllAppointments();
        Timestamp startStamp = Timestamp.valueOf(start);
        Timestamp endStamp = Timestamp.valueOf(end);

        // checks if any fields are not filled out and displays error message to user
        if (titleText.getText() == null || descriptionText.getText() == null || locationText.getText() == null || typeText.getText() == null
                || startDate.getValue() == null || endDate.getValue() == null || startTimeCombo.getValue() == null || endTimeCombo.getValue() == null
                || customerIDCombo.getValue() == null || userIDCombo.getValue() == null || contactIDCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Input missing data");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
        }
        // checks to make sure user did not schedule an appointment on a weekend
        else if (startDate.getValue().getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY
                || endDate.getValue().getDayOfWeek() == DayOfWeek.SATURDAY || endDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Outside business hours");
            alert.setContentText("Cannot add appointments on the weekend");
            alert.showAndWait();
        }
        // sends input data to check for possible appointment overlap
        else if (checkAppointmentOverlap(aList, customerID, startStamp, endStamp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Overlapping appointment");
            alert.setContentText("Customer has an overlapping appointment, change date or time");
            alert.showAndWait();
        }
        // if all checks are passed, sends user input to DBAppointments.addAppointment to be added into the database
        // and then closes the page and loads the main screen using the lambda
        else {
            try {
                DBAppointments.addAppointment(title, description, location, type, startStamp, endStamp, customerID.getCustomerID(), userID.getUserID(), contactID.getContactID());

                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

                // runs lambda to load mainScreen
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
