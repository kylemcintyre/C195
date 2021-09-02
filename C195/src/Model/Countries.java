package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**Class that gets and sets countries variables and creates Country objects.
 *
 */
public class Countries {
    private int countryID;
    private String countryName;
    private String createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    private static ObservableList<Countries> countries = FXCollections.observableArrayList();

    /**Method to create Country objects.
     *
     * @param countryID int
     * @param countryName String
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public static ObservableList<Countries> getCountries() {
        return countries;
    }

    public static void setAllCountries(ObservableList<Countries> countries) {
        Countries.countries = countries;
    }

    @Override
    public String toString() {
        return countryName + " [" + countryID + "]";
    }
}
