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

/**Class to control the logic for the Reports.fxml file
 *
 */
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
    private Button contactEmailsButton;

    @FXML
    private TextArea textArea;

    // declares ObservableList and Reports object
    ObservableList<Reports> rList;
    public static Reports reports;

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

    /**Method that is initialized on class load.
     * Sets labels and textarea, and title.
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        textArea.setText("");
        reportsLabel.setText("Reports");
        reportsLabel.setAlignment(Pos.CENTER);
    }

    /**Method to go back to mainScreen when the cancel button is clicked.
     *
     * @param event Performs action when cancel button is clicked
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void closeButtonClicked(ActionEvent event) {
        // confirmation alert asking if the user is sure they want to cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to close reports?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, closes window and runs lambda to load mainScreen
        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            // runs lambda to load mainScreen
            try {
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**Method to display contactSchedule report.
     * Saves report to a StringBuilder and then displays in textArea.
     * @param event Performs action when the contactSchedule button is clicked
     */
    @FXML
    void contactScheduleClicked(ActionEvent event) {

        // sets variables and labels
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        reportsLabel.setText("Reports - Contact Schedules");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        StringBuilder sb1 = new StringBuilder();
        // loads ObservableList rList with contactSchedules
        rList = DBReports.getContactSchedules();

        // creates StringBuilder by scanning through rList and displays results to textArea
        for (Reports reports : rList) {
            sb1.append("Appointment ID: " + reports.getAppointmentID() + " Title: " + reports.getTitle() + " Type: " + reports.getType() + " Description: " + reports.getDescription() + " Start: "
                    + (reports.getStart().toLocalDateTime().format(dtf)) + " End: " + (reports.getEnd().toLocalDateTime().format(dtf)) + " Customer ID: " + reports.getCustomerID() + "\n");
        }
        textArea.setText(sb1.toString());
    }

    /**Method to display contactEmails report.
     * Saves report to two StringBuilders and then displays them in column1Label and column2Label
     * @param event Performs action when contactEmailsButton is clicked
     */
    @FXML
    void contactEmailsClicked(ActionEvent event) {

        // sets labels and creates StringBuilders
        reportsLabel.setText("Reports - Contact Emails");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        textArea.setText("");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        // loads rList with contactEmails
        rList = DBReports.getContactEmails();
        // sets sb1 to contacts and sb2 to emails
        sb1.append("Contacts\n");
        sb2.append("Emails\n");

        // Scans through rList and appends sb1 with contactNames
        // displays results of sb1 to column1Label
        for (Reports reports : rList) {
            sb1.append(reports.getContactName()).append("\n");
        }
        column1Label.setText(sb1.toString());

        // Scans through rList and appends sb2 with contactEmails
        // displays results of sb2 to column2Label
        for (Reports reports : rList) {
            sb2.append(reports.getContactEmail()).append("\n");
        }
        column2Label.setText(sb2.toString());
    }

    /**Method to display typeMonthAppointments report.
     * Saves report to three StringBuilders and then displays them in column1Label, column2Label, and column3Label
     * @param event Performs action when typeMonthButton is clicked
     */
    @FXML
    void typeMonthButtonClicked(ActionEvent event) {

        // sets labels and creates StringBuilders
        reportsLabel.setText("Reports - Appointments by Type and Month");
        reportsLabel.setAlignment(Pos.CENTER);
        column1Label.setText("");
        column2Label.setText("");
        column3Label.setText("");
        textArea.setText("");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        // loads rList with typeMonth appointments
        rList = DBReports.getTypeMonth();

        // sets sb1 to month, sb2 to type, and sb3 to appointments
        sb1.append("Month\n");
        sb2.append("Type\n");
        sb3.append("Appointments\n");

        // scans through rList and appends sb1 with months
        // displays results of sb1 to column1Label
        for (Reports reports : rList) {
            sb1.append(reports.getMonth()).append("\n");
        }
        column1Label.setText(sb1.toString());

        // scans through rList and appends sb2 with types
        // displays results of sb2 to column2Label
        for (Reports reports : rList) {
            sb2.append(reports.getType()).append("\n");
        }
        column2Label.setText(sb2.toString());

        // scans through rList and appends sb3 with number of appointments
        // displays results of sb3 to column3Label
        for (Reports reports : rList) {
            sb3.append(reports.getCustomerAppointments()).append("\n");
        }
        column3Label.setText(sb3.toString());
    }

}
