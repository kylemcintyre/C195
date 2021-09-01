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

    // lamba to load the main screen of the program which is used several times
    // throughout the program. This lambda is saving a lot of duplicate code
    // from being written
    MainScreenController.loadMainScreen loadMainScreen = () -> {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
    };

    @FXML
    public void initialize(URL url, ResourceBundle resources) {

            int appointmentID = MainScreenController.appointmentToModify.getAppointmentID();
            String title = MainScreenController.appointmentToModify.getTitle();
            String description = MainScreenController.appointmentToModify.getDescription();
            String location = MainScreenController.appointmentToModify.getLocation();
            String type = MainScreenController.appointmentToModify.getType();
            String times[] = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" };
            startTimeCombo.setItems(FXCollections.observableArrayList(times));
            endTimeCombo.setItems(FXCollections.observableArrayList(times));

            appointmentIDText.setPromptText(String.valueOf(appointmentID));
            titleText.setText(title);
            descriptionText.setText(description);
            locationText.setText(location);
            typeText.setText(type);
            startDate.setValue(MainScreenController.appointmentToModify.getStart().toLocalDateTime().toLocalDate());
            startTimeCombo.setValue(MainScreenController.appointmentToModify.getStart().toLocalDateTime().toLocalTime());
            endDate.setValue(MainScreenController.appointmentToModify.getEnd().toLocalDateTime().toLocalDate());
            endTimeCombo.setValue(MainScreenController.appointmentToModify.getEnd().toLocalDateTime().toLocalTime());

            Customers.setCustomers(DBCustomers.getAllCustomers());
            customerIDCombo.setItems(Customers.getCustomers());
            customerIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getCustomerString());

            Users.setUsers(DBUsers.getUsers());
            userIDCombo.setItems(Users.getUsers());
            //userIDCombo.setValue(MainScreenController.appointmentToModify.getUserID());
            userIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getUserString());

            Contacts.setContacts(DBContacts.getContacts());
            contactIDCombo.setItems(Contacts.getContacts());
            //contactIDCombo.setValue(MainScreenController.appointmentToModify.getContactID());
            contactIDCombo.getSelectionModel().select(MainScreenController.appointmentToModify.getContactString());

    }


    @FXML
    void cancelButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to cancel?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            try {
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkAppointmentOverlap(ObservableList<Appointments> appointments, int customerID,
                                           Timestamp startStamp, Timestamp endStamp, int appointmentID) {
        // checks for overlapping appointments for the selected customer and returns true starttimes match
        for (Appointments a : appointments) {
            if (a.getCustomerID() == customerID) {
                //System.out.println(a.getCustomerID());
                //System.out.println(customerID.getCustomerID());
                if (a.getAppointmentID() != appointmentID) {
                    if (a.getStart().before(endStamp) && a.getEnd().after(startStamp)) {
                        //System.out.println(a.getStart());
                        //System.out.println(a.getEnd());
                        return true;
                    }
                }
            }
        }
        // no overlapping appts return false
        return false;
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

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

        String customerIDString = customerID.replaceAll("[^0-9]", "");
        int customerIDInt = Integer.parseInt(customerIDString);

        String userIDString = userID.replaceAll("[^0-9]", "");
        int userIDInt = Integer.parseInt(userIDString);


        String contactIDString = contactID.replaceAll("[^0-9]", "");
        int contactIDInt = Integer.parseInt(contactIDString);


        /*System.out.println("customerIDInt: " + customerIDInt);
        System.out.println("userIDInt: " + userIDInt);
        System.out.println("contactIDInt: " + contactIDInt);*/



        System.out.println(checkAppointmentOverlap(aList, customerIDInt, startStamp, endStamp, appointmentID));

        if (titleText.getText() == null || descriptionText.getText() == null || locationText.getText() == null || typeText.getText() == null
                || startDate.getValue() == null || endDate.getValue() == null || startTimeCombo.getValue() == null || endTimeCombo.getValue() == null
                || customerIDCombo.getValue() == null || userIDCombo.getValue() == null || contactIDCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Input data missing");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();

        }
        else if (startDate.getValue().getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY
                || endDate.getValue().getDayOfWeek() == DayOfWeek.SATURDAY || endDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Outside business hours");
            alert.setContentText("Cannot add appointments on the weekend");
            alert.showAndWait();
        }
        else if (checkAppointmentOverlap(aList, customerIDInt, startStamp, endStamp, appointmentID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Overlapping appointment");
            alert.setContentText("Customer has an overlapping appointment, change date or time");
            alert.showAndWait();
        }
        else {
            try {
                DBAppointments.updateAppointment(appointmentID, title, description, location, type, startStamp, endStamp, customerIDInt, userIDInt, contactIDInt);

                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
