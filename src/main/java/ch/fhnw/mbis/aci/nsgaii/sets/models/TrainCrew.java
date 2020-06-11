package ch.fhnw.mbis.aci.nsgaii.sets.models;

import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Place;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Shift;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainType;

public class TrainCrew {

    private int ID;
    private String name;
    private Place placeOfResidence;
    private Shift shiftPreference;
    private boolean favoriseSameShiftPreference;
    private boolean favroriseSameTrainPreference;
    private TrainType favoriteTrainType;

    /**
     *
     * @param name
     * @param placeOfResidence
     * @param shiftPreference
     * @param favoriseSameShiftPreference
     * @param favroriseSameTrainPreference
     */
    public TrainCrew(int ID, String name, Place placeOfResidence, Shift shiftPreference, boolean favoriseSameShiftPreference, boolean favroriseSameTrainPreference, TrainType favoriteTrainType) {
        this.ID = ID;
        this.name = name;
        this.placeOfResidence = placeOfResidence;
        this.shiftPreference = shiftPreference;
        this.favoriseSameShiftPreference = favoriseSameShiftPreference;
        this.favroriseSameTrainPreference = favroriseSameTrainPreference;
        this.favoriteTrainType = favoriteTrainType;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Place getPlaceOfResidence() {
        return placeOfResidence;
    }

    public Shift getShiftPreference() {
        return shiftPreference;
    }

    public boolean isFavoriseSameShiftPreference() {
        return favoriseSameShiftPreference;
    }

    public boolean isFavroriseSameTrainPreference() {
        return favroriseSameTrainPreference;
    }

    public TrainType getFavoriteTrainType() {
        return favoriteTrainType;
    }

    @Override
    public String toString() {
        return "TrainCrew{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", placeOfResidence='" + placeOfResidence + '\'' +
                ", shiftPreference=" + shiftPreference +
                ", favoriseSameShiftPreference=" + favoriseSameShiftPreference +
                ", favroriseSameTrainPreference=" + favroriseSameTrainPreference +
                '}';
    }
}
