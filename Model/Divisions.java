package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class that gets and sets divisions variables and creates Division objects.
 *
 */
public class Divisions {

    private int divisionID;
    private String division;
    private int countryID;
    private String countryName;

    private static ObservableList<Divisions> divisions = FXCollections.observableArrayList();

    /**Method to create Division objects.
     *
     * @param divisionID int
     * @param division String
     * @param countryID int
     * @param countryName String
     */
    public Divisions(int divisionID, String division, int countryID, String countryName) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public Divisions(int divisionID, String division) {
        this.divisionID = divisionID;
        this.division = division;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public static ObservableList<Divisions> getDivisions() {
        return divisions;
    }

    public static void setDivisions(ObservableList<Divisions> divisions) {
        Divisions.divisions = divisions;
    }

    @Override
    public String toString() {
        return division + " [" + divisionID + "]";
    }
}
