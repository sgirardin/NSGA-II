package ch.fhnw.mbis.aci.nsgaii.objectivefunction;

import ch.fhnw.mbis.aci.nsgaii.population.TrainCrewSet;
import ch.fhnw.mbis.aci.nsgaii.population.TrainServiceSet;
import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainCrew;
import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainService;
import ch.fhnw.mbis.aci.nsgaii.sets.models.enums.Shift;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.debacharya.nsgaii.Configuration.HARD_CONSTRAINT_PENALTY;

public class MaximizeEmployeeSatisfaction extends AbstractObjectiveFunction {

    private int penaltyConstraintSameTrain = - 2;
    private int penaltyConstraintShiftPreference = - 3;
    private int penaltyConstraintSameShiftDuringWeek = - 5;

    public MaximizeEmployeeSatisfaction() {
        this.objectiveFunctionTitle = "Maximize Employee Satisfaction";
    }

    @Override
    public double getValue(Chromosome chromosome) {
        List<String> geneList = chromosome.retrieveChromosomeGenes();

        Map<TrainCrew, List<TrainService>> trainCrewServices = new HashMap<>();

        double chromosomeFitness = 0d;
        int locus = 0;

        for(TrainService currentTrainService:
                TrainServiceSet.getPopulation().values()){

            String gene = geneList.get(locus);

            TrainCrew trainCrew = TrainCrewSet.getPopulation().get(Integer.parseInt(gene,2));
            if (trainCrew != null ){
                List<TrainService> previousTrainServicesCrew = retrieveTrainService(trainCrew, trainCrewServices);

                chromosomeFitness = chromosomeFitness + calculatePenaltyShiftFollowing(previousTrainServicesCrew, currentTrainService);
                chromosomeFitness = chromosomeFitness + calculatePenaltyShiftSameDay(previousTrainServicesCrew, currentTrainService);
                chromosomeFitness = chromosomeFitness + calculatePenaltySoftConstraintSameShiftDuringWeek(previousTrainServicesCrew, currentTrainService);
                chromosomeFitness = chromosomeFitness + calculatePenaltyConstraintShiftPreference(trainCrew, currentTrainService);
                chromosomeFitness = chromosomeFitness + calculatePenaltySoftConstraintSameTrainType(trainCrew, currentTrainService);

                previousTrainServicesCrew.add(currentTrainService);
                trainCrewServices.put(trainCrew, previousTrainServicesCrew);
            } else {
                chromosomeFitness = chromosomeFitness + HARD_CONSTRAINT_PENALTY;
            }
            locus++;
        }

        return chromosomeFitness;
    }

    private double calculatePenaltyShiftFollowing(List<TrainService> previousTrainServices, TrainService currentTrainService){
        int penalty = 0;

        for(TrainService previousTrainService
                :previousTrainServices){
            if(isPreviousShiftDirectlyAfterNewShift(previousTrainService,currentTrainService)){
                    penalty = penalty + HARD_CONSTRAINT_PENALTY;

                }
        }
        return penalty;
    }

    private double calculatePenaltyShiftSameDay(List<TrainService> previousTrainServices, TrainService currentTrainService){
        int penalty = 0;

        for(TrainService previousTrainService
                :previousTrainServices){
            if(areServicesSameDay(previousTrainService,currentTrainService)){
                penalty = penalty + HARD_CONSTRAINT_PENALTY;

            }
        }
        return penalty;
    }

    private double calculatePenaltySoftConstraintSameShiftDuringWeek(List<TrainService> previousTrainServices, TrainService currentTrainService){
        int penalty = 0;

        for(TrainService previousTrainService
                :previousTrainServices){
            if(!previousTrainService.getServiceShift().equals(currentTrainService.getServiceShift())){
                penalty = penalty + penaltyConstraintSameShiftDuringWeek;
            }
        }
        return penalty;
    }

    private double calculatePenaltySoftConstraintSameTrainType(TrainCrew trainCrew, TrainService trainService){
        double penalty = 0d;
        if(trainCrew.isFavroriseSameTrainPreference()){
            if(trainCrew.getFavoriteTrainType() != trainService.getTrainType()){
            penalty = penaltyConstraintSameTrain;
            }
        }
        return penalty;
    }

    private double calculatePenaltyConstraintShiftPreference(TrainCrew trainCrew, TrainService trainService){
        double penalty = 0d;
        if(trainCrew.isFavoriseSameShiftPreference()){
            if(trainCrew.getShiftPreference() != trainService.getServiceShift()){
                penalty =  penaltyConstraintShiftPreference;
            }
        }
        return penalty;
    }

    private List<TrainService> retrieveTrainService(TrainCrew trainCrew, Map<TrainCrew, List<TrainService>> trainCrewServices){
        if (!trainCrewServices.containsKey(trainCrew)){
            trainCrewServices.put(trainCrew, new ArrayList<>());
        }
        return trainCrewServices.get(trainCrew);
    }

    private boolean isPreviousShiftDirectlyAfterNewShift(TrainService previousTrainService,TrainService currentTrainService){
        boolean isShiftPrevious = false;

        if (areServicesOneDayPriorDay(previousTrainService, currentTrainService)
                && (currentTrainService.getServiceShift().equals(Shift.MORNING_SHIFT)
                && previousTrainService.getServiceShift().equals(Shift.NIGHT_SHIFT))){
            isShiftPrevious = true;
        } else {
            //Already handeld in the calculatePenaltyShiftSameDay
        }

        return isShiftPrevious;
    }

    private boolean areServicesSameDay(TrainService previousTrainService,TrainService currentTrainService){
        return previousTrainService.getStartTime().getDayOfWeek().equals(currentTrainService.getStartTime().getDayOfWeek());
    }
    private boolean areServicesOneDayPriorDay(TrainService previousTrainService,TrainService currentTrainService){
        //TODO handle new year
        return currentTrainService.getStartTime().getDayOfYear() - previousTrainService.getStartTime().getDayOfYear() == 1;
    }
}
