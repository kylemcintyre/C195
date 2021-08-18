package Views;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private TextField startTimeText;

    @FXML
    private TextField endTimeText;

    @FXML
    private Label textLabel;

    ObservableList<Users> users = Users.getUsers();
    ObservableList<Contacts> contacts = Contacts.getContacts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());

        contactIDCombo.setItems(Contacts.getContacts());

        Customers.setCustomers(DBCustomers.getAllCustomers());
        customerIDCombo.setItems(Customers.getCustomers());
        userIDCombo.setItems(users);
        contactIDCombo.setItems(contacts);
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
        }
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

        int appointmentID = Integer.parseInt(appointmentIDText.getText());
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        Timestamp start = java.sql.Timestamp.valueOf(startDate.getValue() + " " + startTimeText.getText());
        Timestamp end = java.sql.Timestamp.valueOf(endDate.getValue() + " " + endTimeText.getText());
        Customers customerID = customerIDCombo.getValue();
        Users userID = userIDCombo.getValue();
        Contacts contactID = contactIDCombo.getValue();

        DBAppointments.addAppointment(title, description, location, type, start, end, customerID.getCustomerID(), userID.getUserID(), contactID.getContactID());

    }

}
