package Views;

import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import Main.Main;
import Model.Contacts;
import Model.Customers;
import Model.Users;
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
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;
import Views.MainScreenController;

/**Class that handles logic for UpdateCustomer.fxml
 *
 */
public class UpdateCustomerController implements Initializable {

    @FXML
    private Label updateCustomerLabel;

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

    /**Method that is initialized when class loads.
     * Sets text and comboBoxes to MainScreenController.customerToModify Customer Object
     * @param url URL
     * @param resources ResourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resources) {

        // creates variables
        String countries[] = {"U.S", "UK", "Canada"};
        countryCombo.setItems(FXCollections.observableArrayList(countries));
        int customerID = MainScreenController.customerToModify.getCustomerID();
        String customerName = MainScreenController.customerToModify.getCustomerName();
        String address = MainScreenController.customerToModify.getAddress();
        String postalCode = MainScreenController.customerToModify.getPostalCode();
        String phoneNumber = MainScreenController.customerToModify.getPhone();

        // sets text equal to selected customer from the MainScreen
        customerIDText.setPromptText(String.valueOf(customerID));
        customerNameText.setText(customerName);
        addressText.setText(address);
        postalCodeText.setText(postalCode);
        phoneNumberText.setText(phoneNumber);
        countryCombo.setValue(MainScreenController.customerToModify.getCountry());

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
                "Wyoming"};

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
                "Newfoundland and Labrador"};

        // array for UK states
        String ukStates[] = {"England", "Wales", "Scotland", "Ireland"};

        // sets stateCombo with appropriate array depending on value selected in countryCombo
        if (countryCombo.getValue() == "U.S") {
            stateCombo.setItems(FXCollections.observableArrayList(usStates));
        } else if (countryCombo.getValue() == "Canada") {
            stateCombo.setItems(FXCollections.observableArrayList(canadaStates));
        } else if (countryCombo.getValue() == "UK") {
            stateCombo.setItems(FXCollections.observableArrayList(ukStates));
        }

        // creates hashtable with divisionID and corresponding state
        Hashtable<Integer, String> stateDict = new Hashtable<Integer, String>();
            stateDict.put(1, "Alabama");
            stateDict.put(2, "Arizona");
            stateDict.put(3, "Arkansas");
            stateDict.put(4, "California");
            stateDict.put(5, "Colorado");
            stateDict.put(6, "Conneticut");
            stateDict.put(7, "Delaware");
            stateDict.put(8, "District of Columbia");
            stateDict.put(9, "Florida");
            stateDict.put(10, "Georgia");
            stateDict.put(11, "Idaho");
            stateDict.put(12, "Illinois");
            stateDict.put(13, "Indiana");
            stateDict.put(14, "Iowa");
            stateDict.put(15, "Kansas");
            stateDict.put(16, "Kentucky");
            stateDict.put(17, "Louisiana");
            stateDict.put(18, "Maine");
            stateDict.put(19, "Maryland");
            stateDict.put(20, "Massachusetts");
            stateDict.put(21, "Michigan");
            stateDict.put(22, "Minnesota");
            stateDict.put(23, "Mississippi");
            stateDict.put(24, "Missouri");
            stateDict.put(25, "Montana");
            stateDict.put(26, "Nebraska");
            stateDict.put(27, "Nevada");
            stateDict.put(28, "New Hampshire");
            stateDict.put(29, "New Jersey");
            stateDict.put(30, "New Mexico");
            stateDict.put(31, "New York");
            stateDict.put(32, "North Carolina");
            stateDict.put(33, "North Dakota");
            stateDict.put(34, "Ohio");
            stateDict.put(35, "Oklahoma");
            stateDict.put(36, "Oregon");
            stateDict.put(37, "Pennsylvania");
            stateDict.put(38, "Rhode Island");
            stateDict.put(39, "South Carolina");
            stateDict.put(40, "South Dakota");
            stateDict.put(41, "Tennessee");
            stateDict.put(42, "Texas");
            stateDict.put(43, "Utah");
            stateDict.put(44, "Vermont");
            stateDict.put(45, "Virginia");
            stateDict.put(46, "Washington");
            stateDict.put(47, "West Virginia");
            stateDict.put(48, "Wisconsin");
            stateDict.put(49, "Wyoming");
            stateDict.put(52, "Hawaii");
            stateDict.put(54, "Alaska");
            stateDict.put(60, "Northwest Territories");
            stateDict.put(61, "Alberta");
            stateDict.put(62, "British Columbia");
            stateDict.put(63, "Manitoba");
            stateDict.put(64, "New Brunswick");
            stateDict.put(65, "Nova Scotia");
            stateDict.put(66, "Prince Edward Island");
            stateDict.put(67, "Ontario");
            stateDict.put(68, "Québec");
            stateDict.put(69, "Saskatchewan");
            stateDict.put(70, "Nunavut");
            stateDict.put(71, "Yukon");
            stateDict.put(72, "Newfoundland and Labrador");
            stateDict.put(101, "England");
            stateDict.put(102, "Wales");
            stateDict.put(103, "Scotland");
            stateDict.put(104, "Northern Ireland");

            // sets the stateCombo with item from hashtable
            stateCombo.setValue(stateDict.get(MainScreenController.customerToModify.getDivisionID()));
    }


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

    /**Method to go back to the MainScreen when the cancel button is clicked.
     *
     * @param event Performs action when cancelButton is clicked
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

    /**Method to send user input to DBCustomers.updateCustomer when save clicked.
     *
     * @param event Performs action when the saveButton is clicked
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

        // store input from user in variables
        int customerID = MainScreenController.customerToModify.getCustomerID();
        String customerName = customerNameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phoneNumber = phoneNumberText.getText();
        int divisionID = stateDict.get(stateCombo.getValue());
        int countryID = countryDict.get(countryCombo.getValue());

        // checks if any fields are not filled out and displays error message to user
        if (customerName.equals("") || address.equals("") || postalCode.equals("") || phoneNumber.equals("") || countryCombo.getValue() == null || stateCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error updating customer");
            alert.setHeaderText("Input data missing");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
        }
        // if all checks are passed, sends user input to DBCustomers.updateCustomer to be updated in the database
        // and then closes the page and loads the main screen using the lambda
        else {
            try {
                DBCustomers.updateCustomer(customerID, customerName, address, postalCode, phoneNumber, divisionID);

                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

                // runs lambda to load mainScreen
                loadMainScreen.mainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}