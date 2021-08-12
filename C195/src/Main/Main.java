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

public class Main extends Application {

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


    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnection.startConnection();
        DBQuery.setStatement(conn);

        Statement statement = DBQuery.getStatement();

        /* String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) VALUES('Jamaica', '2021-08-08 00:00:00', 'Admin', 'Admin')";
        statement.execute(insertStatement);
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " Row(s) affected");
        else
            System.out.println("No change"); */

        /* String deleteStatement = "DELETE from countries where Country = 'Jamaica'";
        statement.execute(deleteStatement);
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " Row(s) affected");
        else
            System.out.println("No change"); */

        /* String countryName = "Canada";
        String createDate = "2020-02-22 00:00:00";
        String createdBy = "admin";
        String lastUpdateBy = "admin";

        String insertStatementVariable =    "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By)" +
                                            "VALUES (" +
                                            "'" + countryName +"'," +
                                            "'" + createDate +"'," +
                                            "'" + createdBy +"'," +
                                            "'" + lastUpdateBy +"'" +
                                            ")";

        statement.execute(insertStatementVariable);
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " Row(s) affected");
        else
            System.out.println("No change"); */

        String updateStatement = "UPDATE countries set Country = 'Japan' WHERE Country = 'Jamaica'";

        statement.execute(updateStatement);
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " Row(s) affected");
        else
            System.out.println("No change");

        launch(args);
        DBConnection.closeConnection();
    }
}
