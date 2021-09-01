package Views;

import DBAccess.DBReports;
import Model.Reports;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private Button closeButton;

    @FXML
    private Label reportsLabel;

    @FXML
    private Button typeMonthButton;

    @FXML
    private Button contactScheduleButton;

    @FXML
    private Label column1Label;

    @FXML
    private Label column2Label;

    @FXML
    private Label column3Label;

    @FXML
    private Button customerCountryButton;

    @FXML
    private TextArea textArea;

    ObservableList<Reports> rList;
    public static Reports reports;

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

        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        reportsLabel.setText("Reports");
        reportsLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    void closeButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to close reports?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void contactScheduleClicked(ActionEvent event) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        reportsLabel.setText("Reports - Contact Schedules");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        StringBuilder sb1 = new StringBuilder();
        rList = DBReports.getContactSchedules();

        for (Reports reports : rList) {
            sb1.append("Appointment ID: " + reports.getAppointmentID() + " Title: " + reports.getTitle() + " Type: " + reports.getType() + " Description: " + reports.getDescription() + " Start: "
                    + (reports.getStart().toLocalDateTime().format(dtf)) + " End: " + (reports.getEnd().toLocalDateTime().format(dtf)) + " Customer ID: " + reports.getCustomerID() + "\n");
        }
        textArea.setText(sb1.toString());
    }

    @FXML
    void customerCountryClicked(ActionEvent event) {

        reportsLabel.setText("Reports - Contact Emails");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        textArea.setText("");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        rList = DBReports.getContactEmails();
        sb1.append("Contacts\n");
        sb2.append("Emails\n");

        for (Reports reports : rList) {
            sb1.append(reports.getContactName()).append("\n");
        }
        column1Label.setText(sb1.toString());

        for (Reports reports : rList) {
            sb2.append(reports.getContactEmail()).append("\n");
        }
        column2Label.setText(sb2.toString());
    }

    @FXML
    void typeMonthButtonClicked(ActionEvent event) {

        reportsLabel.setText("Reports - Appointments by Type and Month");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        textArea.setText("");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        rList = DBReports.getTypeMonth();

        sb1.append("Month\n");
        sb2.append("Type\n");
        sb3.append("Appointments\n");

        for (Reports reports : rList) {
            sb1.append(reports.getMonth()).append("\n");
        }
        column1Label.setText(sb1.toString());

        for (Reports reports : rList) {
            sb2.append(reports.getType()).append("\n");
        }
        column2Label.setText(sb2.toString());

        for (Reports reports : rList) {
            sb3.append(reports.getCustomerAppointments()).append("\n");
        }
        column3Label.setText(sb3.toString());
    }

}
