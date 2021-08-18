package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Divisions {

    private int divisionID;
    private String division;
    private int countryID;
    private String countryName;

    private static ObservableList<Divisions> divisions = FXCollections.observableArrayList();

    public Divisions(int divisionID, String division, int countryID, String countryName) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getDivisionId() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryId() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public static ObservableList<Divisions> getAllDivisions() {
        return divisions;
    }

    public static void setAllDivisions(ObservableList<Divisions> divisions) {
        Divisions.divisions = divisions;
        System.out.println("Divisions set");
    }

    @Override
    public String toString() {
        return division + " [" + divisionID + "]";
    }
}
