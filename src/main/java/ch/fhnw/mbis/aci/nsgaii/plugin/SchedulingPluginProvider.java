package ch.fhnw.mbis.aci.nsgaii.plugin;

import ch.fhnw.mbis.aci.nsgaii.population.TrainCrewSet;
import ch.fhnw.mbis.aci.nsgaii.sets.models.TrainCrew;
import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.ChildPopulationProducer;
import com.debacharya.nsgaii.plugin.GeneticCodeProducer;
import com.debacharya.nsgaii.plugin.PopulationProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SchedulingPluginProvider {

    private static String geneFormat = "%0"+ Configuration.calculateGeneSize() +"d";

    public static GeneticCodeProducer defaultGeneticCodeProducer() {
        return (length) -> {
            List<BooleanAllele> chromosomeCode = new ArrayList<>();

            while(chromosomeCode.size()< length){
                int trainCrewID = ThreadLocalRandom.current().nextInt(1, TrainCrewSet.getPopulation().size());
                TrainCrew trainCrew = TrainCrewSet.getPopulation().get(trainCrewID);

                String gene = String.format(geneFormat,returnBinaryValueFromInt(trainCrew.getID()));

                for(char alleleChar : gene.toCharArray()){
                    chromosomeCode.add(new BooleanAllele(returnBooleanValueFromChar(alleleChar)));
                }
            }

            return chromosomeCode;
        };
    }

    public static PopulationProducer defaultPopulationProducer() {
        return (populationSize, chromosomeLength, geneticCodeProducer, fitnessCalculator) -> {

            List<Chromosome> populace = new ArrayList<>();

            for(int i = 0; i < populationSize; i++)
                populace.add(
                        new Chromosome(
                                geneticCodeProducer.produce(chromosomeLength)
                        )
                );

            return new Population(populace);
        };
    }

    public static ChildPopulationProducer defaultChildPopulationProducer() {
        return (parentPopulation, crossover, mutation, populationSize) -> {

            List<Chromosome> populace = new ArrayList<>();

            while(populace.size() < populationSize)
                if((populationSize - populace.size()) == 1)
                    populace.add(
                            mutation.perform(
                                    Service.crowdedBinaryTournamentSelection(parentPopulation)
                            )
                    );
                else
                    for(Chromosome chromosome : crossover.perform(parentPopulation))
                        populace.add(mutation.perform(chromosome));

            return new Population(populace);
        };
    }

    private static boolean returnBooleanValueFromChar(char charValue){
        if('1' == charValue){
            return true;
        } else if ('0' == charValue){
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static int returnBinaryValueFromInt(int intToTransform){
        return Integer.parseInt(Integer.toBinaryString(intToTransform));
    }
}
