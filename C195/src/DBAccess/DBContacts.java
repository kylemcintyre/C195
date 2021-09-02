package DBAccess;

import Model.Contacts;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Class that gets contact information from contacts table with SQL.
 *
 */
public class DBContacts {

    /**Selects contactID and contactName from contacts table.
     *
     * @return Returns ObservableList contacts
     */
    public static ObservableList<Contacts> getContacts() {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        try {

            // sql query to select contactID and contactName
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts ORDER BY Contact_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // sets variables and adds them to contacts ObservableList
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contacts contact = new Contacts(contactID, contactName);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
