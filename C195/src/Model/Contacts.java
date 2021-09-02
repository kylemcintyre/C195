package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class that gets and sets contact variables and creates Contact objects.
 *
 */
public class Contacts {

    private int contactID;
    private String contactName;

    private static ObservableList<Contacts> contacts = FXCollections.observableArrayList();

    /**Method to create Contact objects.
     *
     * @param contactID int
     * @param contactName String
     */
    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public int getContactID() {
        return contactID;
    }


    public String getContactName() {
        return contactName;
    }

    public static ObservableList<Contacts> getContacts() {
        return contacts;
    }

    public static void setContacts(ObservableList<Contacts> contacts) {
        Contacts.contacts = contacts;
    }

    @Override
    public String toString() {
        return contactName + " [" + contactID + "]";
    }
}
