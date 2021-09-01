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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        startTimeCombo.setValue("08:00");
        endTimeCombo.setValue("09:00");

        Customers.setCustomers(DBCustomers.getAllCustomers());
        customerIDCombo.setItems(Customers.getCustomers());

        Users.setUsers(DBUsers.getUsers());
        userIDCombo.setItems(Users.getUsers());

        Contacts.setContacts(DBContacts.getContacts());
        contactIDCombo.setItems(Contacts.getContacts());

        String times[] = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        startTimeCombo.setItems(FXCollections.observableArrayList(times));
        endTimeCombo.setItems(FXCollections.observableArrayList(times));
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

    public boolean checkAppointmentOverlap(ObservableList<Appointments> appointments, Customers customerID,
                                           Timestamp startStamp, Timestamp endStamp) {
        // checks for overlapping appointments for the selected customer and returns true starttimes match
        for (Appointments a : appointments) {
            if (a.getCustomerID() == customerID.getCustomerID()) {
                //System.out.println(a.getCustomerID());
                //System.out.println(customerID.getCustomerID());
                if (a.getStart().before(endStamp) && a.getEnd().after(startStamp)) {
                    //System.out.println(a.getStart());
                    //System.out.println(a.getEnd());
                    return true;
                }
            }
        }
        // no overlapping appts return false
        return false;
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

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

        System.out.println(checkAppointmentOverlap(aList, customerID, startStamp, endStamp));
        System.out.println(startDate.getValue());
        System.out.println(endDate.getValue());

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
        else if (checkAppointmentOverlap(aList, customerID, startStamp, endStamp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Overlapping appointment");
            alert.setContentText("Customer has an overlapping appointment, change date or time");
            alert.showAndWait();
        }
        else {
            try {
                DBAppointments.addAppointment(title, description, location, type, startStamp, endStamp, customerID.getCustomerID(), userID.getUserID(), contactID.getContactID());

                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
