package Views;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import Main.Main;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import Utilities.DBConnection;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class to handle all logic for UpdateAppointment.fxml file
 *
 */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private Label updateAppointmentLabel;

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
    private DatePicker startDate;

    @FXML
    private ComboBox startTimeCombo;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox endTimeCombo;

    @FXML
    private ComboBox customerIDCombo;

    @FXML
    private ComboBox userIDCombo;

    @FXML
    private ComboBox contactIDCombo;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

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

    /**Method that is initialized when the class is loaded.
     * Sets text and comboBoxes to MainScreenController.appointmentToModify Appointment Object
     * @param url URL
     * @param resources ResourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resources) {

        // creates variables
        int appointmentID = MainScreenController.appointmentToModify.getAppointmentID();
        String title = MainScreenController.appointmentToModify.getTitle();
        String description = MainScreenController.appointmentToModify.getDescription();
        String location = MainScreenController.appointmentToModify.getLocation();
        String type = MainScreenController.appointmentToModify.getType();
        String times[] = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" };

        // sets time comboBoxes with time[] and sets text equal to selected appointment information from MainScreen
        startTimeCombo.setItems(FXCollections.observableArrayList(times));
        endTimeCombo.setItems(FXCollections.observableArrayList(times));
        appointmentIDText.setPromptText(String.valueOf(appointmentID));
        titleText.setText(title);
        descriptionText.setText(description);
        locationText.setText(location);
        typeText.setText(type);

        // sets datePickers and time comboBoxes
        startDate.setValue(MainScreenController.appointmentToModify.getStart().toLocalDateTime().toLocalDate());
        startTimeCombo.setValue(MainScreenController.appointmentToModify.getStart().toLocalDateTime().toLocalTime());
        endDate.setValue(MainScreenController.appointmentToModify.getEnd().toLocalDateTime().toLocalDate());
        endTimeCombo.setValue(MainScreenController.appointmentToModify.getEnd().toLocalDateTime().toLocalTime());

        // sets all customers and then loads customers into customerIDCombo
        Customers.setCustomers(DBCustomers.getAllCustomers());
        customerIDCombo.setItems(Customers.getCustomers());
        customerIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getCustomerString());

        // sets all users and then loads users into userIDCombo
        Users.setUsers(DBUsers.getUsers());
        userIDCombo.setItems(Users.getUsers());
        userIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getUserString());

        // sets all contacts and then loads contacts into contactIDCombo
        Contacts.setContacts(DBContacts.getContacts());
        contactIDCombo.setItems(Contacts.getContacts());
        contactIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getContactString());

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

            // runs lambda to load mainScreen
            try {
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**Method to check if customer has an overlapping appointment when creating a new appointment or updating an existing one.
     *
     * @param appointments ObservableList
     * @param customerID int
     * @param startStamp Timestamp
     * @param endStamp Timestamp
     * @param appointmentID int
     * @return Returns true if an overlapping appointment is found, otherwise returns false
     */
    public boolean checkAppointmentOverlap(ObservableList<Appointments> appointments, int customerID,
                                           Timestamp startStamp, Timestamp endStamp, int appointmentID) {
        for (Appointments a : appointments) {
            if (a.getCustomerID() == customerID) {
                if (a.getAppointmentID() != appointmentID) {
                    if (a.getStart().before(endStamp) && a.getEnd().after(startStamp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**Method to send user input to DBAppointments.updateAppointment.
     *
     * @param event Performs action when the saveButton is clicked
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {

        // create variables with user input
        int appointmentID = MainScreenController.appointmentToModify.getAppointmentID();
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        String start = startDate.getValue() + " " + startTimeCombo.getValue() + ":00";
        String end = endDate.getValue() + " " + endTimeCombo.getValue() + ":00";
        String customerID = customerIDCombo.getValue().toString();
        String userID = userIDCombo.getValue().toString();
        String contactID = contactIDCombo.getValue().toString();
        ObservableList<Appointments> aList = DBAppointments.getAllAppointments();
        Timestamp startStamp = Timestamp.valueOf(start);
        Timestamp endStamp = Timestamp.valueOf(end);

        // remove alpha characters from string and convert customerID to int
        String customerIDString = customerID.replaceAll("[^0-9]", "");
        int customerIDInt = Integer.parseInt(customerIDString);

        // remove alpha characters from string and convert userID to int
        String userIDString = userID.replaceAll("[^0-9]", "");
        int userIDInt = Integer.parseInt(userIDString);

        // remove alpha characters from string and convert contactID to int
        String contactIDString = contactID.replaceAll("[^0-9]", "");
        int contactIDInt = Integer.parseInt(contactIDString);

        // checks if any fields are not filled out and displays error message to user
        if (titleText.getText() == null || descriptionText.getText() == null || locationText.getText() == null || typeText.getText() == null
                || startDate.getValue() == null || endDate.getValue() == null || startTimeCombo.getValue() == null || endTimeCombo.getValue() == null
                || customerIDCombo.getValue() == null || userIDCombo.getValue() == null || contactIDCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Input data missing");
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
        else if (checkAppointmentOverlap(aList, customerIDInt, startStamp, endStamp, appointmentID)) {
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
                DBAppointments.updateAppointment(appointmentID, title, description, location, type, startStamp, endStamp, customerIDInt, userIDInt, contactIDInt);

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
