package ch.fhnw.mbis.aci.nsgaii.sets.models;


import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Place;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class DistanceBetweenPlaces {

    private static Map<Place, Integer> distanceFromA = setupMapDistanceFromA();
    private static Map<Place, Integer> distanceFromB = setupMapDistanceFromB();
    private static Map<Place, Integer> distanceFromC = setupMapDistanceFromC();
    private static Map<Place, Integer> distanceFromD = setupMapDistanceFromD();

    public static Integer caculateDistance(Place startPlace, Place endPlace){
        int distance = 0;
        Map<Place, Integer> distanceFromStartPlace;
        switch (startPlace){
            case A:
                distanceFromStartPlace = distanceFromA;
                break;
            case B:
                distanceFromStartPlace = distanceFromB;
                break;
            case C:
                distanceFromStartPlace = distanceFromC;
                break;
            case D:
                distanceFromStartPlace = distanceFromD;
                break;
            default: throw new InvalidParameterException(startPlace.toString());
        }

        return distanceFromStartPlace.get(endPlace);
    }

    //TODO
    private static Map<Place, Integer> setupMapDistance(Place startPlace, int x, int y, int z){
        Map<Place, Integer> distanceFromPlace = new HashMap<>();
        for (Place place :
              Place.values()) {
            if(place != startPlace){
            }
        }
        return distanceFromPlace;
    }

    private static Map<Place, Integer> setupMapDistanceFromA(){
        Map<Place, Integer> distanceFromA = new HashMap<>();
        distanceFromA.put(Place.A,2);
        distanceFromA.put(Place.B,2);
        distanceFromA.put(Place.C,5);
        distanceFromA.put(Place.D,10);
        return distanceFromA;
    }

    private static Map<Place, Integer> setupMapDistanceFromB(){
        Map<Place, Integer> distanceFromB = new HashMap<>();
        distanceFromB.put(Place.A,2);
        distanceFromB.put(Place.B,0);
        distanceFromB.put(Place.C,3);
        distanceFromB.put(Place.D,7);
        return distanceFromB;
    }

    private static Map<Place, Integer> setupMapDistanceFromC(){
        Map<Place, Integer> distanceFromC = new HashMap<>();
        distanceFromC.put(Place.A,5);
        distanceFromC.put(Place.B,3);
        distanceFromC.put(Place.C,0);
        distanceFromC.put(Place.D,5);
        return distanceFromC;
    }

    private static Map<Place, Integer> setupMapDistanceFromD(){
        Map<Place, Integer> distanceFromD = new HashMap<>();
        distanceFromD.put(Place.A,10);
        distanceFromD.put(Place.B,7);
        distanceFromD.put(Place.C,5);
        distanceFromD.put(Place.D,0);
        return distanceFromD;
    }
}
