package Views;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
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
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController {

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private TableColumn customerIDCol, customerNameCol, customerAddressCol, customerPostalCol, customerCountryCol, customerPhoneCol, customerDivisionCol;

    @FXML
    private TableView<Appointments> appointmentsTable;

    @FXML
    private TableColumn<?, ?> appointmentIDCol, appointmentTitleCol, appointmentDescriptionCol, appointmentLocationCol, appointmentContactCol, appointmentTypeCol, appointmentStartCol, appointmentEndCol, appointmentCustomerIDCol;

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

    /* @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentsTable.setItems(DBAppointments.getAllAppointments());
        appointmentsTable.refresh();

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, ZonedDateTime>("startDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, ZonedDateTime>("endDateTime"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentsTable.setItems(inputList);
    } */



    @FXML
    void addAppointmentButtonClicked(ActionEvent event) {
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
