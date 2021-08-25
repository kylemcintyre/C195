package Views;

import DBAccess.DBAppointments;
import DBAccess.DBLogin;
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


    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static int timeDifference;
    public static int startTime = 8;
    public static int endTime = 22;
    Alert alert;
    ResourceBundle rb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Locale.setDefault(new Locale("fr"));
        //TimeZone.setDefault(TimeZone.getTimeZone("Europe/France"));
        //TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));

        ZoneId currentZoneID = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime currentTimeZone = ZonedDateTime.of(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime(), currentZoneID);

        Instant currentUTC = currentTimeZone.toInstant();

        ZonedDateTime utcEST = currentTimeZone.withZoneSameInstant(ZoneId.of("America/New_York"));

        ZonedDateTime utcLocal = currentUTC.atZone(currentZoneID);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        timeDifference = utcLocal.getHour() - utcEST.getHour();
        startTime += timeDifference;
        endTime += timeDifference;
        if (timeDifference > 2) {
            endTime -= 24;
        } else if (timeDifference < -8) {
            startTime += 24;
        }

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

    @FXML
    void loginButtonClicked(ActionEvent event) throws Exception {

        String userName = usernameText.getText();
        String password = passwordText.getText();
        boolean goodLogin = DBLogin.goodLogin(userName, password);
        ObservableList<Appointments> A = DBAppointments.getAllAppointments();
        ZonedDateTime currentDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));

        String sql = "SELECT Appointment_ID, Start FROM appointments " +
                "WHERE CAST(Start as DATE) = '" + currentDateTime.toLocalDate() + "' AND ('"
                + dtf.format(currentDateTime) + "' <= Start AND '" +
                dtf.format(currentDateTime.plusMinutes(15)) + "' >= Start);";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        FileWriter fw = new FileWriter("C195/src/login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(dtf.format(currentDateTime) + " Username:" + userName + " Login:" + goodLogin);
        pw.close();

        if (goodLogin) {

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
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointments");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("No Upcoming Appointments");
                alert.showAndWait();
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

                Stage stage2 = (Stage) loginButton.getScene().getWindow();
                stage2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
