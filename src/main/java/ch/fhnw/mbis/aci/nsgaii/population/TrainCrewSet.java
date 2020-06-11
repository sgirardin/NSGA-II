package ch.fhnw.mbis.aci.nsgaii.population;

import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainCrew;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Place;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Shift;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainCrewPopulationSize;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainType;
import com.github.javafaker.Faker;

import java.util.*;

public class TrainCrewSet {

    private static int trainCrewID = 0;
    private static Map<Integer, TrainCrew> population = new HashMap<>();
    private static Faker faker = new Faker(new Locale("de-CH"));

    public static void setupPopulation(TrainCrewPopulationSize populationSize){
        List<TrainCrew> serviceList = null;
        switch (populationSize){
            case TEN:
                serviceList = generateTrain10Crew();
                break;
            case FIFTEEN:
                serviceList = generateTrain15Crew();
                break;
            case TWENTY:
                serviceList = generateTrain20Crew();
                break;
            default:
                serviceList = generateTrain10Crew();
        }
        serviceList.stream().parallel().forEach(trainCrew -> population.put(trainCrew.getID(), trainCrew));
    }

    private static List<TrainCrew> generateTrain10Crew(){

        List<TrainCrew> trainCrewList = new ArrayList<>();

        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.DAY_SHIFT,false,true,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,true,true,TrainType.Y));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.MORNING_SHIFT,false,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,false,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.NIGHT_SHIFT,true,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.NIGHT_SHIFT,false,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.DAY_SHIFT,true,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.MORNING_SHIFT,true,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,true,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.A,Shift.DAY_SHIFT,true,false,TrainType.Y));

        return trainCrewList;
    }

    private static List<TrainCrew> generateTrain15Crew(){

        List<TrainCrew> trainCrewList = generateTrain10Crew();

        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.DAY_SHIFT,false,true,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,true,true,TrainType.Y));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.MORNING_SHIFT,false,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,false,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.NIGHT_SHIFT,true,true,TrainType.X));

        return trainCrewList;
    }

    private static List<TrainCrew> generateTrain20Crew(){

        List<TrainCrew> trainCrewList = generateTrain15Crew();

        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.NIGHT_SHIFT,false,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.D,Shift.DAY_SHIFT,true,true,TrainType.X));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.B,Shift.MORNING_SHIFT,true,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.C,Shift.MORNING_SHIFT,true,false,TrainType.Z));
        trainCrewList.add(createTrainCrew(faker.name().firstName(), Place.A,Shift.DAY_SHIFT,true,false,TrainType.Y));

        return trainCrewList;
    }

    private static TrainCrew createTrainCrew(String name, Place placeOfResidence, Shift shiftPreference, boolean favoriseSameShiftPreference, boolean favroriseSameTrainPreference, TrainType favoriteTrainType){
        trainCrewID++;
        int id = trainCrewID;
        return new TrainCrew(id, name, placeOfResidence,shiftPreference,favoriseSameShiftPreference,favroriseSameTrainPreference, favoriteTrainType);
    }

    public static Map<Integer, TrainCrew> getPopulation() {
        return population;
    }
}
