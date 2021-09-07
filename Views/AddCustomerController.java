package Views;

import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import DBAccess.DBUsers;
import Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class to handle all logic for the AddCustomer fxml page.
 *
 */
public class AddCustomerController implements Initializable {

    @FXML
    private Label addCustomerLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private TextField customerIDText;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private ComboBox stateCombo;

    @FXML
    private ComboBox countryCombo;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

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

    /**Method that is initialized when class is loaded.
     * Sets countyCombo with array with countries.
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // sets countryCombo with countries array
        String countries[] = { "U.S", "UK", "Canada"};
        countryCombo.setItems(FXCollections.observableArrayList(countries));
    }

    /**Method that sets stateCombo box with appropriate states depending on what country is selected.
     *
     * @param event Performs action to load stateCombo with appropriate states when countryCombo is interacted with.
     */
    @FXML
    void setStates(ActionEvent event) {

        // array for U.S states
        String usStates[] = {
                "Alaska",
                "Alabama",
                "Arkansas",
                "Arizona",
                "California",
                "Colorado",
                "Connecticut",
                "District of Columbia",
                "Delaware",
                "Florida",
                "Georgia",
                "Guam",
                "Hawaii",
                "Iowa",
                "Idaho",
                "Illinois",
                "Indiana",
                "Kansas",
                "Kentucky",
                "Louisiana",
                "Massachusetts",
                "Maryland",
                "Maine",
                "Michigan",
                "Minnesota",
                "Missouri",
                "Mississippi",
                "Montana",
                "North Carolina",
                "North Dakota",
                "Nebraska",
                "New Hampshire",
                "New Jersey",
                "New Mexico",
                "Nevada",
                "New York",
                "Ohio",
                "Oklahoma",
                "Oregon",
                "Pennsylvania",
                "Rhode Island",
                "South Carolina",
                "South Dakota",
                "Tennessee",
                "Texas",
                "Utah",
                "Virginia",
                "Vermont",
                "Washington",
                "Wisconsin",
                "West Virginia",
                "Wyoming" };

        // array for Canada states
        String canadaStates[] = {
                "Northwest Territories",
                "Alberta",
                "British Columbia",
                "Manitoba",
                "New Brunswick",
                "Nova Scotia",
                "Prince Edward Island",
                "Ontario",
                "Québec",
                "Saskatchewan",
                "Nunavut",
                "Yukon",
                "Newfoundland and Labrador" };

        // array for UK states
        String ukStates[] = { "England", "Wales", "Scotland", "Ireland" };

        // sets stateCombo with appropriate array depending on value selected in countryCombo
        if (countryCombo.getValue() == "U.S") {
            stateCombo.setItems(FXCollections.observableArrayList(usStates));
        }
        else if (countryCombo.getValue() == "Canada") {
            stateCombo.setItems(FXCollections.observableArrayList(canadaStates));
        }
        else if (countryCombo.getValue() == "UK") {
            stateCombo.setItems(FXCollections.observableArrayList(ukStates));
        }
    }

    /**Method to go back to mainScreen when the cancel button is clicked.
     *
     * @param event Performs action when cancel button is clicked
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) {

        // confirmation alert asking if the user is sure they want to cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to cancel?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(true);
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks yes, closes current window and loads mainScreen
        if (result.get() == buttonTypeOne) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            // runs lambda to load mainScreen
            try {
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**Method to send user input to DBCustomers.addCustomer when save clicked.
     *
     * @param event Performs action when the saveButton is clicked
     * <p><b>
     * Lambda loadMainScreen.mainScreen() is used in this method to reduce code clutter for loading back to the mainScreen.
     * </b></p>
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {

        // creates hashtable with country and corresponding countryID
        Hashtable<String, Integer> countryDict = new Hashtable<String, Integer>();
        countryDict.put("U.S", 1);
        countryDict.put("UK", 2);
        countryDict.put("Canada", 3);

        // creates hashtable with state and corresponding divisionID
        Hashtable<String, Integer> stateDict = new Hashtable<String, Integer>();
        stateDict.put("Alabama", 1);
        stateDict.put("Arizona", 2);
        stateDict.put("Arkansas", 3);
        stateDict.put("California", 4);
        stateDict.put("Colorado", 5);
        stateDict.put("Conneticut", 6);
        stateDict.put("Delaware", 7);
        stateDict.put("District of Columbia", 8);
        stateDict.put("Florida", 9);
        stateDict.put("Georgia", 10);
        stateDict.put("Idaho", 11);
        stateDict.put("Illinois", 12);
        stateDict.put("Indiana", 13);
        stateDict.put("Iowa", 14);
        stateDict.put("Kansas", 15);
        stateDict.put("Kentucky", 16);
        stateDict.put("Louisiana", 17);
        stateDict.put("Maine", 18);
        stateDict.put("Maryland", 19);
        stateDict.put("Massachusetts", 20);
        stateDict.put("Michigan", 21);
        stateDict.put("Minnesota", 22);
        stateDict.put("Mississippi", 23);
        stateDict.put("Missouri", 24);
        stateDict.put("Montana", 25);
        stateDict.put("Nebraska", 26);
        stateDict.put("Nevada", 27);
        stateDict.put("New Hampshire", 28);
        stateDict.put("New Jersey", 29);
        stateDict.put("New Mexico", 30);
        stateDict.put("New York", 31);
        stateDict.put("North Carolina", 32);
        stateDict.put("North Dakota", 33);
        stateDict.put("Ohio", 34);
        stateDict.put("Oklahoma", 35);
        stateDict.put("Oregon", 36);
        stateDict.put("Pennsylvania", 37);
        stateDict.put("Rhode Island", 38);
        stateDict.put("South Carolina", 39);
        stateDict.put("South Dakota", 40);
        stateDict.put("Tennessee", 41);
        stateDict.put("Texas", 42);
        stateDict.put("Utah", 43);
        stateDict.put("Vermont", 44);
        stateDict.put("Virginia", 45);
        stateDict.put("Washington", 46);
        stateDict.put("West Virginia", 47);
        stateDict.put("Wisconsin", 48);
        stateDict.put("Wyoming", 49);
        stateDict.put("Hawaii", 52);
        stateDict.put("Alaska", 54);
        stateDict.put("Northwest Territories", 60);
        stateDict.put("Alberta", 61);
        stateDict.put("British Columbia", 62);
        stateDict.put("Manitoba", 63);
        stateDict.put("New Brunswick", 64);
        stateDict.put("Nova Scotia", 65);
        stateDict.put("Prince Edward Island", 66);
        stateDict.put("Ontario", 67);
        stateDict.put("Québec", 68);
        stateDict.put("Saskatchewan", 69);
        stateDict.put("Nunavut", 70);
        stateDict.put("Yukon", 71);
        stateDict.put("Newfoundland and Labrador", 72);
        stateDict.put("England", 101);
        stateDict.put("Wales", 102);
        stateDict.put("Scotland", 103);
        stateDict.put("Northern Ireland", 104);

        // stores input from user in variables
        String customerName = customerNameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phoneNumber = phoneNumberText.getText();
        int divisionID = stateDict.get(stateCombo.getValue());
        int countryID = countryDict.get(countryCombo.getValue());

        // checks if any fields are not filled out and displays error message to user
        if (customerName.equals("") || address.equals("") || postalCode.equals("") || phoneNumber.equals("") || countryCombo.getValue() == null || stateCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding customer");
            alert.setHeaderText("Input data missing");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
        }
        // if all checks are passed, sends user input to DBCustomers.addCustomer to be added into the database
        // and then closes the page and loads the main screen using the lambda
        else {
            try {
                DBCustomers.addCustomer(customerName, address, postalCode, phoneNumber, divisionID);

                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

                // runs lambda to load mainScreen
                loadMainScreen.mainScreen();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
