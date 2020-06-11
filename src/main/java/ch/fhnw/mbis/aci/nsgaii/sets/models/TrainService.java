package ch.fhnw.mbis.aci.nsgaii.sets.models;


import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Place;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Shift;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.TrainType;

import java.time.LocalDateTime;

public class TrainService {

    private static final int MORNING_SHIFT_START = 4;
    private static final int DAY_SHIFT_START = 10;
    private static final int NIGHT_SHIFT_START = 16;

    private int ID;
    private Place startPlace;
    private Place endPlace;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TrainType trainType;
    private Shift serviceShift;


    public TrainService(int ID, Place startPlace, Place endPlace, LocalDateTime startTime, LocalDateTime endTime, TrainType trainType) {
        this.ID = ID;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainType = trainType;

        if (startTime.getHour() >= NIGHT_SHIFT_START){
            serviceShift = Shift.NIGHT_SHIFT;
        } else if (startTime.getHour() >= DAY_SHIFT_START){
            serviceShift = Shift.DAY_SHIFT;
        } else if (startTime.getHour() >= MORNING_SHIFT_START){
            serviceShift = Shift.MORNING_SHIFT;
        }
    }

    public Place getStartPlace() {
        return startPlace;
    }

    public Place getEndPlace() {
        return endPlace;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public Shift getServiceShift() {
        return serviceShift;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Service{" +
                "startPlace=" + startPlace +
                ", endPlace=" + endPlace +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", train=" + trainType +
                ", serviceShift=" + serviceShift +
                '}';
    }
}
