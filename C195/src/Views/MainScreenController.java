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

/**Class that controls logic for the MainScreen.fxml file.
 *
 */
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

    // create Appointments and Customers object to store selected rows for updating
    public static Appointments appointmentToModify;
    public static Customers customerToModify;

    /**Method that is initialized when the class loads.
     * Sets columns and values for appointments and customers tables.
     * @param location URL
     * @param resources ResourceBundle
     * <p><b>
     * Interfaces for lambdas getMessage and loadMainScreen are in this method
     * </b></p>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // set columns and values for appointments table
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

        // set columns and values for customers table
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

    /**Abstract interface used for lambda in LoginController.
     *
     */
    @FunctionalInterface
    public interface getMessage {
        void message();
    }

    /**Abstract interface used for lambda in AddAppointmentController, AddCustomerController, LoginController, ReportsController, UpdateAppointmentController, and UpdateCustomerController.
     *
     */
    @FunctionalInterface
    public interface loadMainScreen {
        void mainScreen() throws IOException;
    }

    /**Method to load AddAppointment.fxml.
     *
     * @param event Performs action when addAppointmentButton is clicked
     */
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

    /**Method to load AddCustomer.fxml.
     *
     * @param event Performs action when addCustomerButton is clicked
     */
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

    /**Method to change what appointments show in appointments table.
     *
     * @param event Performs action on appointments table depending on what radio button is selected
     */
    @FXML
    void appointmentRadioSelection(ActionEvent event) {

        // sets table to all appointments
        if (appointmentGroup.getSelectedToggle() == allAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            appointmentsTable.refresh();
        }
        // sets table to weekly appointments
        else if (appointmentGroup.getSelectedToggle() == weeklyAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getWeeklyAppointments());
            appointmentsTable.refresh();
        }
        // sets table to monthly appointments
        else if (appointmentGroup.getSelectedToggle() == monthlyAppointmentsRadio) {
            appointmentsTable.setItems(DBAppointments.getMonthlyAppointments());
            appointmentsTable.refresh();
        }

    }

    /**Method to delete selected appointment from the database.
     *
     * @param event Performs action when deleteAppointmentButton is clicked
     */
    @FXML
    void deleteAppointmentButtonClicked(ActionEvent event) {

        // sets variables
        Appointments appointmentToDelete = appointmentsTable.getSelectionModel().getSelectedItem();
        int appointmentID = appointmentToDelete.getAppointmentID();
        String appointmentType = appointmentToDelete.getType();

        // alerts user and verifies this is the correct appointment to delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete Appointment ID:" + appointmentID + ", Type:" + appointmentType);
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, calls DBAppointments.deleteAppointment with the selected appointmentID, then refreshes table
        if (result.get() == buttonTypeOne) {
            if (!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {

                DBAppointments.deleteAppointment(appointmentID);

                appointmentsTable.setItems(DBAppointments.getAllAppointments());
                appointmentsTable.refresh();
            }
        }
    }

    /**Method to delete selected customer from the database.
     *
     * @param event Performs action when the deleteCustomerButton is clicked
     */
    @FXML
    void deleteCustomerButtonClicked(ActionEvent event) {

        // sets variables
        Customers customerToDelete = customerTable.getSelectionModel().getSelectedItem();
        int customerID = customerToDelete.getCustomerID();
        String customerName = customerToDelete.getCustomerName();

        // alerts user and verifies this is the correct customer to delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to delete " + customerName + "?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, calls DBCustomers.deleteCustomer with the selected customerID, then refreshes tables
        if (result.get() == buttonTypeOne) {

            DBCustomers.deleteCustomer(customerID);

            customerTable.setItems(DBCustomers.getAllCustomers());
            customerTable.refresh();
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            appointmentsTable.refresh();
        }
    }

    /**Method to logout from the MainScreen and back to the login page.
     *
     * @param event Performs action when the logoutButton is clicked
     */
    @FXML
    void logoutButtonClicked(ActionEvent event) {
        // alerts user and verifies that they want to logout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to logout?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, closes current window and loads Login.fxml
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

    /**Method to load UpdateAppointment.fxml.
     * Saves selected row in appointmentsTable to appointmentToModify Appointment object.
     * @param event Performs action when the updateAppointmentButton is clicked
     */
    @FXML
    void updateAppointmentButtonClicked(ActionEvent event) {
        try {
            // adds selected row to Appointment object which passes information to UpdateAppointment screen
            appointmentToModify = appointmentsTable.getSelectionModel().getSelectedItem();

            // loads UpdateAppointment.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

            // closes current MainScreen window
            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**Method to load UpdateCustomer.fxml.
     * Saves selected row in customerTable to customerToModify Customer object.
     * @param event Performs action when updateCustomerButton is clicked
     */
    @FXML
    void updateCustomerButtonClicked(ActionEvent event) {
        try {
            // adds selected row to Customer object which passes information to UpdateCustomer screen
            customerToModify = customerTable.getSelectionModel().getSelectedItem();

            // loads UpdateCustomer.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

            // closes current MainScreen window
            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**Method to load Reports.fxml.
     *
     * @param event Performs action when the reportsButton is clicked
     */
    @FXML
    void reportsButtonClicked(ActionEvent event) {
        try {
            // loads Reports.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reports.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

            // closes current MainScreen window
            Stage stage2 = (Stage) logoutButton.getScene().getWindow();
            stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
