package Views;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private TableColumn<Customers, Integer> customerIDCol;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    @FXML
    private TableColumn<Customers, String> customerPostalCol;

    @FXML
    private TableColumn<Customers, String> customerCountryCol;

    @FXML
    private TableColumn<Customers, String> customerPhoneCol;

    @FXML
    private TableColumn<Customers, Integer> customerDivisionCol;

    @FXML
    private TableView<Appointments> appointmentsTable;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointments, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointments, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointments, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointments, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointments, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointments, Integer> appointmentCustomerIDCol;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Label customersLabel;

    @FXML
    private Label appointmentsLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private RadioButton allAppointmentsRadio;

    @FXML
    private ToggleGroup appointmentGroup;

    @FXML
    private RadioButton monthlyAppointmentsRadio;

    @FXML
    private RadioButton weeklyAppointmentsRadio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set columns and values for appointsments table
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDateTime>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDateTime>("end"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerID"));

        appointmentsTable.setItems(DBAppointments.getAllAppointments());
        appointmentsTable.refresh();

        // set columns and vlaues for customers table
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("postalCode"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("country"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("divisionID"));

        customerTable.setItems(DBCustomers.getAllCustomers());
        customerTable.refresh();
    }

    @FXML
    void addAppointmentButtonClicked(ActionEvent event) {
        // launch add appointment window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addCustomerButtonClicked(ActionEvent event) {
        // launch add customer window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void appointmentRadioSelection(ActionEvent event) {

    }

    @FXML
    void deleteAppointmentButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete this appointment?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {

        }
    }

    @FXML
    void deleteCustomerButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete this customer?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {

        }
    }

    @FXML
    void logoutButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to logout?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void updateAppointmentButtonClicked(ActionEvent event) {
        // launch update appointment window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateCustomerButtonClicked(ActionEvent event) {
        // launch update customer window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reportsButtonClicked(ActionEvent evenet) {
        // launch reports window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reports.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
