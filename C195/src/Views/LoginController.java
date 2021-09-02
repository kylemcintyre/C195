package Views;

import DBAccess.DBAppointments;
import DBAccess.DBLogin;
import Main.Main;
import Model.Appointments;
import Utilities.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**Class that implements logic for the LoginController.fxml page.
 *
 */
public class LoginController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField usernameText;

   @FXML
   private Label usernameLabel;

   @FXML
   private Label passwordLabel;

    @FXML
    private Label zoneIDLabel;

    // lambda to display alert that there are no upcoming appointments
    // This lambda is used to reduce clutter in the saveButtonClicked method
    MainScreenController.getMessage noAppointments = () -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Appointments");
        alert.setHeaderText("No upcoming appointments");
        alert.setContentText("No upcoming appointments");
        alert.showAndWait();
    };

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


    // declare variables dtf, alert and rb
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Alert alert;
    ResourceBundle rb;

    /**Method that is initialized when the class is loaded.
     * Sets login page to English or French based on Locale.
     * @param location URL
     * @param resources ResourceBundle
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // sets login page to English(en) or French(fr) based on Locale.getDefault()
        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
            try {
                rb = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
                usernameText.setPromptText(rb.getString("username"));
                usernameLabel.setText(rb.getString("username"));
                passwordText.setPromptText(rb.getString("password"));
                passwordLabel.setText(rb.getString("password"));
                loginButton.setText(rb.getString("login"));
                zoneIDLabel.setText(ZoneId.systemDefault().getId());

            } catch (MissingResourceException e) {
                e.printStackTrace();
            }
        } else {
            // if Locale is not English or French, sets login page to default English
            try {
                Locale.setDefault(new Locale("en"));
                usernameText.setPromptText(rb.getString("username"));
                usernameLabel.setText(rb.getString("username"));
                passwordText.setPromptText(rb.getString("password"));
                passwordLabel.setText(rb.getString("password"));
                loginButton.setText(rb.getString("login"));
                zoneIDLabel.setText(ZoneId.systemDefault().getId());
            } catch (MissingResourceException e) {
                e.printStackTrace();
            }
        }
    }

    /**Method run when save button is clicked.
     * Gets user input and runs through SQL statement to verify valid input, log input attempt, and then loads mainScreen.
     * @param event Performs action to check username and password and login when save button is clicked
     * @throws Exception Throws exception
     * <p><b>
     * Lambda noAppointments.message() is used in this method to reduce code clutter for displaying alert to user that they have no upcoming appointments.
     * </b></p>
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void loginButtonClicked(ActionEvent event) throws Exception {

        // stores userinput in variables and sends to method goodLogin for verification
        String userName = usernameText.getText();
        String password = passwordText.getText();
        // sends username and password to DBLogin.goodLogin to verify
        boolean goodLogin = DBLogin.goodLogin(userName, password);
        // gets all current appointments and stores in ObservableList to check if there are any
        // upcoming appoints for the user when they login
        ObservableList<Appointments> A = DBAppointments.getAllAppointments();
        ZonedDateTime currentDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));

        // SQL query to appointments table to check if user has any appointments in the next 15 minutes
        String sql = "SELECT Appointment_ID, Start FROM appointments " +
                "WHERE CAST(Start as DATE) = '" + currentDateTime.toLocalDate() + "' AND ('"
                + dtf.format(currentDateTime) + "' <= Start AND '" +
                dtf.format(currentDateTime.plusMinutes(15)) + "' >= Start);";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        // open filewriter to append login attempt to file including timestamp, username, and true or false login
        FileWriter fw = new FileWriter("C195/src/login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(dtf.format(currentDateTime) + " Username:" + userName + " Login:" + goodLogin);
        pw.close();

        // loop entered if DBLogin.goodLogin() returned as true
        if (goodLogin) {

            // loop entered if user does have an upcoming appointment in the next 15 minutes
            // scans through ObservableList A and displays alert to user with appointmentID, date, and time
            if(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                LocalDateTime dateTime = rs.getTimestamp("Start").toLocalDateTime();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointments");
                alert.setHeaderText("Upcoming Appointment");
                alert.setContentText("Appointment ID: " + appointmentID +
                        "\nDate: " + dateTime.toString().substring(0, 10) +
                        "\nTime: " + dateTime.toString().substring(11,16));
                alert.showAndWait();
            }
            // runs lambda to display there are no upcoming appointments if ObservableList A is empty
            else {
                noAppointments.message();
            }
            // closes the login window and runs lambda to load mainScreen
            try {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // displays error message in English or French is DBLogin.goodLogin() returns false
            // clears username and password field
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("title"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("content"));
            alert.showAndWait();
            usernameText.clear();
            passwordText.clear();
            usernameText.requestFocus();
        }
    }
}
