package Main;

import Utilities.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utilities.DBConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**Main class that first loads when the program is run. Opens the Login page.*/
public class Main extends Application {

    /**Method that provides the location for the Login.fxml file to load. */
    @Override
    public void start(Stage primaryStage) throws Exception {
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

    /**Main method that runs when application is launched. Connects application to database upon loading using DBConnection.startConnection().*/
    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnection.startConnection();
        DBQuery.setStatement(conn);
        launch(args);
        DBConnection.closeConnection();
    }
}
