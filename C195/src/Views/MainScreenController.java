package Views;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public static Appointments appointmentToModify;
    public static Customers customerToModify;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    @FunctionalInterface
    public interface getMessage {
        public void message();
    }

    @FunctionalInterface
    public interface loadMainScreen {
        public void mainScreen() throws IOException;
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
            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
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
            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void appointmentRadioSelection(ActionEvent event) {

        if (appointmentGroup.getSelectedToggle() == allAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            appointmentsTable.refresh();
        }
        else if (appointmentGroup.getSelectedToggle() == weeklyAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getWeeklyAppointments());
            appointmentsTable.refresh();
        }
        else if (appointmentGroup.getSelectedToggle() == monthlyAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getMonthlyAppointments());
            appointmentsTable.refresh();
        }

    }

    @FXML
    void deleteAppointmentButtonClicked(ActionEvent event) {

        Appointments appointmentToDelete = appointmentsTable.getSelectionModel().getSelectedItem();
        int appointmentID = appointmentToDelete.getAppointmentID();
        String appointmentType = appointmentToDelete.getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete Appointment ID:" + appointmentID + ", Type:" + appointmentType);
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            if (!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {

                DBAppointments.deleteAppointment(appointmentID);

                appointmentsTable.setItems(DBAppointments.getAllAppointments());
                appointmentsTable.refresh();
            }
        }
    }

    @FXML
    void deleteCustomerButtonClicked(ActionEvent event) {

        Customers customerToDelete = customerTable.getSelectionModel().getSelectedItem();
        int customerID = customerToDelete.getCustomerID();
        String customerName = customerToDelete.getCustomerName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete " + customerName + "?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {

            DBCustomers.deleteCustomer(customerID);

            customerTable.setItems(DBCustomers.getAllCustomers());
            customerTable.refresh();
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            appointmentsTable.refresh();
        }
    }

    @FXML
    void logoutButtonClicked(ActionEvent event) {
        // logout back to login screen
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

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateAppointmentButtonClicked(ActionEvent event) {
        // launch update appointment window
        try {
            appointmentToModify = appointmentsTable.getSelectionModel().getSelectedItem();
            /*System.out.println(appointmentToModify.getAppointmentID());
            System.out.println(appointmentToModify.getTitle());
            System.out.println(appointmentToModify.getDescription());
            System.out.println(appointmentToModify.getLocation());
            System.out.println(appointmentToModify.getType());
            System.out.println(appointmentToModify.getCustomer());
            System.out.println(appointmentToModify.getUserID());
            System.out.println(appointmentToModify.getContactID());*/

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateCustomerButtonClicked(ActionEvent event) {
        // launch update customer window
        try {
            customerToModify = customerTable.getSelectionModel().getSelectedItem();
            System.out.println(customerToModify.getCustomerID());
            System.out.println(customerToModify.getCustomerName());
            System.out.println(customerToModify.getAddress());
            System.out.println(customerToModify.getPostalCode());
            System.out.println(customerToModify.getPhone());
            System.out.println(customerToModify.getCountry());
            System.out.println(customerToModify.getDivisionID());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
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

            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
