package ch.fhnw.mbis.aci.nsgaii.population;


import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainService;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Place;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainServiceSet {

    private static int serviceID = 0;

    private static Map<Integer, TrainService> population = new HashMap<>();

    public static void setupServiceSet(){
        List<TrainService> trainServiceList = generate63Services();
        trainServiceList.stream().forEach(trainService -> population.put(trainService.getID(), trainService));
    }


    private static List<TrainService> generate63Services(){
        List<TrainService> trainServiceList = new ArrayList<>();
        trainServiceList.add(createService(Place.A,Place.B,1,4,11,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.C,1,4,11,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.A,1,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.B,Place.D,1,11,18,TrainType.W));
        trainServiceList.add(createService(Place.C,Place.A,1,11,18,TrainType.X));
        trainServiceList.add(createService(Place.A,Place.B,1,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.A,Place.C,1,18,0,TrainType.X));
        trainServiceList.add(createService(Place.B,Place.A,1,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.B,1,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.D,Place.C,2,4,11,TrainType.W));
        trainServiceList.add(createService(Place.A,Place.B,2,4,11,TrainType.X));
        trainServiceList.add(createService(Place.B,Place.D,2,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.C,Place.A,2,11,18,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.D,2,11,18,TrainType.X));
        trainServiceList.add(createService(Place.D,Place.C,2,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.B,2,18,0,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.D,2,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.B,Place.A,2,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.A,Place.C,3,4,11,TrainType.W));
        trainServiceList.add(createService(Place.D,Place.B,3,4,11,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.A,3,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.C,Place.D,3,11,18,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.A,3,11,18,TrainType.X));
        trainServiceList.add(createService(Place.A,Place.C,3,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.A,Place.B,3,18,0,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.D,3,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.A,Place.C,3,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.D,Place.C,4,4,11,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.A,4,4,11,TrainType.X));
        trainServiceList.add(createService(Place.D,Place.B,4,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.C,Place.D,4,11,18,TrainType.W));
        trainServiceList.add(createService(Place.A,Place.B,4,11,18,TrainType.X));
        trainServiceList.add(createService(Place.B,Place.C,4,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.B,Place.D,4,18,0,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.A,4,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.C,Place.B,4,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.D,Place.B,5,4,11,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.C,5,4,11,TrainType.X));
        trainServiceList.add(createService(Place.A,Place.D,5,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.B,Place.A,5,11,18,TrainType.W));
        trainServiceList.add(createService(Place.C,Place.D,5,11,18,TrainType.X));
        trainServiceList.add(createService(Place.D,Place.C,5,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.A,5,18,0,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.B,5,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.B,Place.C,5,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.A,Place.D,6,4,11,TrainType.W));
        trainServiceList.add(createService(Place.A,Place.C,6,4,11,TrainType.X));
        trainServiceList.add(createService(Place.B,Place.A,6,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.C,6,11,18,TrainType.W));
        trainServiceList.add(createService(Place.C,Place.D,6,11,18,TrainType.X));
        trainServiceList.add(createService(Place.A,Place.A,6,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.B,6,18,0,TrainType.X));
        trainServiceList.add(createService(Place.A,Place.C,6,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.C,Place.D,6,18,0,TrainType.Z));
        trainServiceList.add(createService(Place.C,Place.A,7,4,11,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.B,7,4,11,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.C,7,4,11,TrainType.Y));
        trainServiceList.add(createService(Place.A,Place.D,7,11,18,TrainType.W));
        trainServiceList.add(createService(Place.B,Place.A,7,11,18,TrainType.X));
        trainServiceList.add(createService(Place.C,Place.B,7,11,18,TrainType.Y));
        trainServiceList.add(createService(Place.A,Place.C,7,18,0,TrainType.X));
        trainServiceList.add(createService(Place.B,Place.D,7,18,0,TrainType.Y));
        trainServiceList.add(createService(Place.D,Place.A,7,18,0,TrainType.Z));
        return trainServiceList;
    }

    private static TrainService createService(Place startPlace, Place endPlace, int dayInWeek, int starthour, int endhour, TrainType trainType){
        serviceID = serviceID +1;
        int id = serviceID;

        int serviceDayInMonth = 24 + dayInWeek;

        LocalDateTime startTime = LocalDateTime.of(LocalDate.of(2020,05,serviceDayInMonth), LocalTime.of(starthour,0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.of(2020,05,serviceDayInMonth), LocalTime.of(endhour,0));
        return new TrainService(id, startPlace, endPlace, startTime, endTime, trainType);
    }

    public static Map<Integer, TrainService> getPopulation() {
        return population;
    }
}
