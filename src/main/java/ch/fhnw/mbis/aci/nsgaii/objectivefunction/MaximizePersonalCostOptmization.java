package ch.fhnw.mbis.aci.nsgaii.objectivefunction;

import ch.fhnw.mbis.aci.nsgaii.population.TrainCrewSet;
import ch.fhnw.mbis.aci.nsgaii.population.TrainServiceSet;
import ch.fhnw.mbis.aci.nsgaii.sets.models.DistanceBetweenPlaces;
import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainCrew;
import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainService;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;

import java.util.List;

import static com.debacharya.nsgaii.Configuration.HARD_CONSTRAINT_PENALTY;

public class MaximizePersonalCostOptmization extends AbstractObjectiveFunction {

    public MaximizePersonalCostOptmization() {
        this.objectiveFunctionTitle = "Maximize Personal Cost optmization";
    }

    @Override
    public double getValue(Chromosome chromosome) {
        List<String> geneList = chromosome.retrieveChromosomeGenes();

        double chromosomeFitness = 0.0;
        int locus = 0;

        for(TrainService trainService:
            TrainServiceSet.getPopulation().values()){
            String gene = geneList.get(locus);

            TrainCrew trainCrew = TrainCrewSet.getPopulation().get(Integer.parseInt(gene,2));
            if(trainCrew != null && trainService != null){
                chromosomeFitness = chromosomeFitness - calculateGeneCost(trainCrew, trainService);
            } else {
                chromosomeFitness = chromosomeFitness  + HARD_CONSTRAINT_PENALTY;
            }

            locus++;
        }

        return chromosomeFitness;
    }

    private double calculateGeneCost(TrainCrew trainCrew, TrainService trainService){
        double totalDistance = DistanceBetweenPlaces.caculateDistance(trainCrew.getPlaceOfResidence(),trainService.getStartPlace());
        totalDistance = totalDistance + DistanceBetweenPlaces.caculateDistance(trainService.getEndPlace(),trainCrew.getPlaceOfResidence());
        return totalDistance;
    }
}
