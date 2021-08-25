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

    @FXML
    public void initialize(URL url, ResourceBundle resources) {

        try {
            int appointmentID = MainScreenController.appointmentToModify.getAppointmentID();
            String title = MainScreenController.appointmentToModify.getTitle();
            String description = MainScreenController.appointmentToModify.getDescription();
            String location = MainScreenController.appointmentToModify.getLocation();
            String type = MainScreenController.appointmentToModify.getType();
            String times[] = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
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

            customerIDCombo.setValue(MainScreenController.appointmentToModify.getCustomerID());
            Customers.setCustomers(DBCustomers.getAllCustomers());
            customerIDCombo.setItems(Customers.getCustomers());

            userIDCombo.setValue(MainScreenController.appointmentToModify.getUserID());
            Users.setUsers(DBUsers.getUsers());
            userIDCombo.setItems(Users.getUsers());

            contactIDCombo.setValue(MainScreenController.appointmentToModify.getContactID());
            Contacts.setContacts(DBContacts.getContacts());
            contactIDCombo.setItems(Contacts.getContacts());
        } catch (Exception e) {
            // do nothing
        }

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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(root1));
                stage2.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

        System.out.println(appointmentIDText);
        try {
            int appointmentID = MainScreenController.appointmentToModify.getAppointmentID();
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();
            String start = startDate.getValue() + " " + startTimeCombo.getValue() + ":00";
            String end = endDate.getValue() + " " + endTimeCombo.getValue() + ":00";
            Customers customerID = (Customers) customerIDCombo.getValue();
            Users userID = (Users) userIDCombo.getValue();
            Contacts contactID = (Contacts) contactIDCombo.getValue();
            Timestamp startStamp = Timestamp.valueOf(start);
            Timestamp endStamp = Timestamp.valueOf(end);

            DBAppointments.updateAppointment(appointmentID, title, description, location, type, startStamp, endStamp, customerID.getCustomerID(), userID.getUserID(), contactID.getContactID());

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(root1));
                stage2.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}
