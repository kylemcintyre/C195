package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class that gets and sets user variables and creates User objects.
 *
 */
public class Users {

    private int userID;
    private String username;

    private static ObservableList<Users> users = FXCollections.observableArrayList();

    /**Method to create User objects.
     *
     * @param userID
     * @param username
     */
    public Users(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ObservableList<Users> getUsers() {
        return users;
    }

    public static void setUsers(ObservableList<Users> users) {
        Users.users = users;
    }

    @Override
    public String toString() {
        return username + " [" + userID + "]";
    }
}
